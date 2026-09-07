#!/usr/bin/env bash
# Statically weaves com.sebulli.fakturama.model's classes inside every product materialized by
# tycho-p2-director-plugin's materialize-products goal, so persistence.xml's lazy fetch mappings
# (only takes effect on woven entity classes - see Document.java's invoiceReference/sourceDocument
# comments) actually behave lazily at runtime instead of EclipseLink resolving them eagerly for
# every row regardless of the mapping's fetch type.
#
# Runs as a Tycho reactor step (see this module's pom.xml: bound to the package phase, right after
# materialize-products and before archive-products, which was moved to pre-integration-test to
# guarantee that order) - not a manual/demo-only tool. Safe to re-run: StaticWeave is idempotent
# (weaving an already-woven class is a no-op picked up via its PersistenceWeaved marker interface).
#
# Usage: weave-model-bundle.sh <products-dir>
#   <products-dir> is target/products (tycho-p2-director-plugin's own output root); this script
#   finds every materialized <product-id>/<os>/<ws>/<arch>/ underneath it itself, so it needs no
#   os/ws/arch-specific configuration and keeps working if a profile ever materializes more than
#   one environment (e.g. the macos profile's two architectures).
set -euo pipefail

PRODUCTS_DIR="${1:?usage: weave-model-bundle.sh <products-dir>}"

if [ ! -d "$PRODUCTS_DIR" ]; then
    echo "weave-model-bundle.sh: no such directory: $PRODUCTS_DIR" >&2
    exit 1
fi

WOVEN_ANY=false

# One directory per materialized product/os/ws/arch combination (there's exactly one for the
# win/linux-x86_64/linux-aarch64 profiles this project documents, two for macos's x86_64+aarch64).
#
# No fixed -mindepth/-maxdepth here: win/linux materialize straight to
# products/Fakturama.ID/<os>/<ws>/<arch>/plugins (depth 5), but macOS wraps that in an app
# bundle - products/Fakturama.ID/macosx/cocoa/<arch>/Fakturama2.app/Contents/Eclipse/plugins
# (depth 8) - so a fixed depth silently found nothing there ("nothing woven", exit 1, before this
# fix) instead of actually weaving the mac product's model bundle.
while IFS= read -r -d '' plugins_dir; do
    model_jar=$(find "$plugins_dir" -maxdepth 1 -iname "com.sebulli.fakturama.model_*.jar" | head -1)
    if [ -z "$model_jar" ]; then
        continue
    fi
    echo "weave-model-bundle.sh: weaving $model_jar"

    # Classpath: every other jar materialized alongside it, so StaticWeave can resolve the
    # superclass/attribute types it needs to generate correct bytecode (EMF, javax.money,
    # jakarta.persistence-api, the eclipselink modules themselves, ...) - excluding wrapped.*.jar,
    # which are Maven-wrapped non-OSGi dependencies of OTHER, unrelated bundles (POI, Jena, QR
    # code generators, okhttp, ...) that com.sebulli.fakturama.model has no Require-Bundle/
    # Import-Package visibility to at runtime. A first attempt that included them let StaticWeave
    # see wrapped.org.eclipse.persistence.eclipselink's bundled (but here inaccessible) JPA-RS
    # weaving classes and add an "implements PersistenceWeavedRest" reference the model bundle
    # then failed to resolve at OSGi activation with a ClassNotFoundException.
    cp_file="$(mktemp)"
    find "$plugins_dir" -iname "*.jar" ! -iname "wrapped.*" -print0 | tr '\0' ':' | sed 's/:$//' > "$cp_file"
    cp="$(cat "$cp_file")"
    rm -f "$cp_file"

    # Preserved and re-applied after the mv below - mktemp creates its output with mode 600
    # (owner-only), and mv onto model_jar makes that jar inherit the temp file's restrictive
    # mode instead of keeping the original (Tycho-materialized, group/world-readable) one. That
    # silently broke every packaged product: the app runs as a normal user, not root/the build
    # user, so it could no longer even open this one jar - FileNotFoundException (Permission
    # denied) cascading into com.sebulli.fakturama.model failing to load at all.
    model_jar_mode="$(stat -c '%a' "$model_jar")"

    out_jar="$(mktemp --suffix=.jar)"
    argfile="$(mktemp)"
    {
        echo "-cp"
        echo "$cp"
        echo "org.eclipse.persistence.tools.weaving.jpa.StaticWeave"
        echo "-persistenceinfo"
        echo "$model_jar"
        echo "-loglevel"
        echo "INFO"
        echo "-classpath"
        echo "$cp"
        echo "$model_jar"
        echo "$out_jar"
    } > "$argfile"

    java "@$argfile"
    mv "$out_jar" "$model_jar"
    chmod "$model_jar_mode" "$model_jar"
    rm -f "$argfile"
    WOVEN_ANY=true
    echo "weave-model-bundle.sh: done: $model_jar"
done < <(find "$PRODUCTS_DIR" -type d -iname plugins -print0)

if [ "$WOVEN_ANY" != "true" ]; then
    echo "weave-model-bundle.sh: no materialized product plugins directory with a model bundle found under $PRODUCTS_DIR - nothing woven." >&2
    exit 1
fi
