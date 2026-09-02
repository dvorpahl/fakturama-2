#!/usr/bin/env bash
set -euo pipefail

# Bumps the Fakturama project version across every touchpoint in the Tycho
# reactor: all module pom.xml <version> tags, all MANIFEST.MF Bundle-Version
# headers, feature.xml self-versions, the .product file, and category.xml.
#
# The exact file/pattern list below was reverse-engineered from commit
# 4d364d59 ("version raised to 2.2.1-SNAPSHOT"), the last time a maintainer
# did this by hand, then verified against the current tree.
#
# Usage:
#   ./bump-version.sh <old-version> <new-version> [export-package-version]
#
# <old-version>          e.g. 2.2.0
# <new-version>          Used verbatim for BOTH the Maven <version> and the
#                         OSGi Bundle-Version, so for a non-SNAPSHOT version it
#                         must be valid OSGi syntax too: "X.Y.Z" or
#                         "X.Y.Z.qualifier-segment" (note the DOT before the
#                         qualifier - "2.2.1.rc", not "2.2.1-rc" or "2.2.1rc").
#                         A trailing "-SNAPSHOT" (e.g. "2.2.1-SNAPSHOT") is
#                         also fine - only the Maven pom files get the literal
#                         "-SNAPSHOT" string, MANIFEST.MF gets ".qualifier"
#                         instead (Tycho's own SNAPSHOT<->qualifier mapping),
#                         and Tycho substitutes a real timestamp at build time.
# [export-package-version]  Version for the one Export-Package self-declaration
#                         (org.fakturama.wizards) - OSGi package versions never
#                         carry a qualifier. Defaults to the X.Y.Z prefix of
#                         <new-version>.
#
# Deliberately NOT touched (matches commit 4d364d59's precedent):
#  - com.sebulli.fakturama.common.tests, com.sebulli.fakturama.rcp.test
#    (disabled/broken modules, excluded from the active reactor - see
#    Fakturama-Parent/pom.xml <modules>)
#  - Require-Bundle "bundle-version=OLD" / feature <import version="OLD"
#    match="greaterOrEqual"> constraints pointing at sibling modules - these
#    are MINIMUM-version floors, not pins, and stay satisfied as the version
#    climbs, exactly as the historical commit left them.
#  - .launch files (IDE debug configs, not part of the Maven/Tycho build).

if [[ $# -lt 2 || $# -gt 3 ]]; then
    echo "Usage: $0 <old-version> <new-version> [export-package-version]" >&2
    exit 1
fi

OLD_VERSION="$1"
NEW_VERSION="$2"
EXPORT_VERSION="${3:-$(echo "$NEW_VERSION" | grep -oE '^[0-9]+\.[0-9]+\.[0-9]+')}"

# MANIFEST.MF Bundle-Version doesn't take a literal "-SNAPSHOT" - Tycho's
# convention is ".qualifier" instead (auto-timestamped at build time).
MANIFEST_VERSION="${NEW_VERSION%-SNAPSHOT}"
if [[ "$NEW_VERSION" == *-SNAPSHOT ]]; then
    MANIFEST_VERSION="${MANIFEST_VERSION}.qualifier"
fi

cd "$(dirname "$0")"

echo "Bumping $OLD_VERSION -> $NEW_VERSION (Maven) / $MANIFEST_VERSION (OSGi) / $EXPORT_VERSION (Export-Package)"

# --- pom.xml: every module's own <version> tag (the parent-version reference,
#     plus org.fakturama.exporter.zugferd's extra internal dependency pin). ---
POM_FILES=(
    pom.xml
    com.sebulli.fakturama.common/pom.xml
    com.sebulli.fakturama.feature/pom.xml
    com.sebulli.fakturama.hsqlconnector/pom.xml
    com.sebulli.fakturama.model/pom.xml
    com.sebulli.fakturama.model.test/pom.xml
    com.sebulli.fakturama.rcp/pom.xml
    com.sebulli.fakturama.resources.icons/pom.xml
    com.sebulli.fakturama.resources/pom.xml
    com.sebulli.fakturama.resources.templates/pom.xml
    com.sebulli.fakturama.site/pom.xml
    org.fakturama.addon.purchaseorders.example/pom.xml
    org.fakturama.addon.purchaseorders.example.feature/pom.xml
    org.fakturama.connectors/pom.xml
    org.fakturama.database.hsql/pom.xml
    org.fakturama.database.mariadb/pom.xml
    org.fakturama.database.mysql/pom.xml
    org.fakturama.e4.ui.dialogs.ext/pom.xml
    org.fakturama.export/pom.xml
    org.fakturama.export.feature/pom.xml
    org.fakturama.exporter.zugferd/pom.xml
    org.fakturama.exporter.zugferd.feature/pom.xml
    org.fakturama.import/pom.xml
    org.fakturama.import.feature/pom.xml
    org.fakturama.logging/pom.xml
    org.fakturama.print.openoffice/pom.xml
    org.fakturama.qrcode/pom.xml
    org.fakturama.target/pom.xml
    org.fakturama.wizards/pom.xml
)
for f in "${POM_FILES[@]}"; do
    sed -i "s|<version>${OLD_VERSION}</version>|<version>${NEW_VERSION}</version>|g" "$f"
done

# --- MANIFEST.MF: Bundle-Version self-declaration. ---
MANIFEST_FILES=(
    com.sebulli.fakturama.common/META-INF/MANIFEST.MF
    com.sebulli.fakturama.hsqlconnector/META-INF/MANIFEST.MF
    com.sebulli.fakturama.model/META-INF/MANIFEST.MF
    com.sebulli.fakturama.model.test/META-INF/MANIFEST.MF
    com.sebulli.fakturama.rcp/META-INF/MANIFEST.MF
    com.sebulli.fakturama.resources.icons/META-INF/MANIFEST.MF
    com.sebulli.fakturama.resources/META-INF/MANIFEST.MF
    com.sebulli.fakturama.resources.templates/META-INF/MANIFEST.MF
    org.fakturama.addon.purchaseorders.example/META-INF/MANIFEST.MF
    org.fakturama.connectors/META-INF/MANIFEST.MF
    org.fakturama.database.hsql/META-INF/MANIFEST.MF
    org.fakturama.database.mariadb/META-INF/MANIFEST.MF
    org.fakturama.database.mysql/META-INF/MANIFEST.MF
    org.fakturama.e4.ui.dialogs.ext/META-INF/MANIFEST.MF
    org.fakturama.export/META-INF/MANIFEST.MF
    org.fakturama.exporter.zugferd/META-INF/MANIFEST.MF
    org.fakturama.import/META-INF/MANIFEST.MF
    org.fakturama.logging/META-INF/MANIFEST.MF
    org.fakturama.print.openoffice/META-INF/MANIFEST.MF
    org.fakturama.qrcode/META-INF/MANIFEST.MF
    org.fakturama.wizards/META-INF/MANIFEST.MF
)
for f in "${MANIFEST_FILES[@]}"; do
    # No trailing $ anchor: about half the MANIFEST.MF files in this repo have
    # CRLF line endings, which would sit between "OLD_VERSION" and the anchor
    # and silently make it not match.
    sed -i "s|^Bundle-Version: ${OLD_VERSION}|Bundle-Version: ${MANIFEST_VERSION}|" "$f"
done
# The one Export-Package self-version (package API version, no qualifier).
sed -i "s|;version=\"${OLD_VERSION}\"|;version=\"${EXPORT_VERSION}\"|" org.fakturama.wizards/META-INF/MANIFEST.MF

# --- feature.xml: each feature's own root version="..." (first occurrence
#     only - purchaseorders.example.feature also has an <import ... version=
#     match="greaterOrEqual"> floor further down that must stay untouched). ---
FEATURE_FILES=(
    com.sebulli.fakturama.feature/feature.xml
    org.fakturama.export.feature/feature.xml
    org.fakturama.exporter.zugferd.feature/feature.xml
    org.fakturama.import.feature/feature.xml
    org.fakturama.addon.purchaseorders.example.feature/feature.xml
)
for f in "${FEATURE_FILES[@]}"; do
    sed -i "0,/version=\"${OLD_VERSION}\"/s//version=\"${NEW_VERSION}\"/" "$f"
done

# --- .product: product-level version + the 4 bundled first-party feature refs. ---
sed -i "s|${OLD_VERSION}|${NEW_VERSION}|g" com.sebulli.fakturama.site/com.sebulli.fakturama.product

# --- category.xml: feature url suffix + version attribute, both entries. ---
sed -i "s|${OLD_VERSION}|${NEW_VERSION}|g" com.sebulli.fakturama.site/category.xml

echo "Done. Review with: git diff --stat"
