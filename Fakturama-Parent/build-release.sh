#!/usr/bin/env bash
#
# Git-tracked entry point for a full Fakturama release build: Windows
# (product zip + MSI installer, with/without bundled JRE), Linux (product
# tar.gz + .deb, with/without bundled JRE) and an Arch/Manjaro pacman
# package. Uses a temporary directory for the platform-by-platform build,
# then copies the installable packages to the Nextcloud mirror.
#
# build-packages.sh itself stays gitignored because it embeds the install4j
# license key. It needs an output directory to retain the Windows artifacts
# while the Linux Maven build cleans the shared target directory. This script
# gives it temporary space and removes that space on exit.
#
# Usage:
#   ./build-release.sh
#   MIRROR_DIR=/some/other/dir ./build-release.sh
#   TMPDIR=/some/other/dir ./build-release.sh
#   SIGN=1 ./build-release.sh          # forwarded to build-packages.sh
#   INSTALL4JC=/path/to/install4jc ./build-release.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

MIRROR_DIR="${MIRROR_DIR:-$HOME/GekoCloud/Vorlagen/Software/Fakturama2}"

log() { echo "== $* =="; }

if [ ! -x "$SCRIPT_DIR/build-packages.sh" ]; then
    echo "error: $SCRIPT_DIR/build-packages.sh not found (it holds the actual build steps + install4j license key)." >&2
    exit 1
fi

for mirror_subdir in Windows Linux/Debian Linux/Manjaro; do
    if [ ! -d "$MIRROR_DIR/$mirror_subdir" ]; then
        echo "error: mirror directory not found: $MIRROR_DIR/$mirror_subdir" >&2
        exit 1
    fi
done

STAGING_DIR="$(mktemp -d "${TMPDIR:-/tmp}/fakturama-release.XXXXXXXX")"
trap 'rm -rf -- "$STAGING_DIR"' EXIT

log "Building full release (Windows zip+MSI, Linux tar.gz+deb, Arch/Manjaro package)"
HEINZ_DIR="$STAGING_DIR" "$SCRIPT_DIR/build-packages.sh"

# The package builder names its output directory with its own timestamp.
# The temporary directory is empty before the build, so exactly one release
# directory must be present when it succeeds.
mapfile -d '' release_dirs < <(find "$STAGING_DIR" -maxdepth 1 -mindepth 1 -type d \
    -regextype posix-extended \
    -regex '.*/[0-9]+\.[0-9]+\.[0-9]+(\.[A-Za-z0-9]+)?_[0-9]{8}-[0-9]{4}' \
    -print0)
if [ "${#release_dirs[@]}" -ne 1 ]; then
    echo "error: expected one completed release directory under $STAGING_DIR, found ${#release_dirs[@]}" >&2
    exit 1
fi
BUILD_OUT_DIR="${release_dirs[0]}"

project_version="$(sed -n '/<artifactId>Fakturama-Parent<\/artifactId>/{n;s/.*<version>\(.*\)<\/version>.*/\1/p}' "$SCRIPT_DIR/pom.xml")"
if [[ -z "$project_version" || "${BUILD_OUT_DIR##*/}" != "$project_version"_* ]]; then
    echo "error: release directory does not match the project version: $BUILD_OUT_DIR" >&2
    exit 1
fi

windows_plain="$BUILD_OUT_DIR/Installer_Fakturama_windows-x64_${project_version}.msi"
windows_jre="$BUILD_OUT_DIR/Installer_Fakturama_windows-x64_${project_version}_with_jre.msi"
debian_plain="$BUILD_OUT_DIR/Installer_Fakturama__${project_version}.deb"
debian_jre="$BUILD_OUT_DIR/Installer_Fakturama_linux-amd64_${project_version}_with_jre.deb"

shopt -s nullglob
manjaro_packages=("$BUILD_OUT_DIR"/fakturama-"$project_version"-*-x86_64.pkg.tar.*)
shopt -u nullglob
if [ "${#manjaro_packages[@]}" -ne 1 ]; then
    echo "error: expected one Manjaro package in $BUILD_OUT_DIR, found ${#manjaro_packages[@]}" >&2
    exit 1
fi
for package in "$windows_plain" "$windows_jre" "$debian_plain" "$debian_jre" "${manjaro_packages[0]}"; do
    if [ ! -s "$package" ]; then
        echo "error: missing or empty release package: $package" >&2
        exit 1
    fi
done

# Copy to a temporary file in each destination so Nextcloud sees a complete
# package when it is renamed into place. Keep older versioned releases intact.
publish_package() {
    local source="$1" destination_dir="$2" temporary
    temporary="$(mktemp "$destination_dir/.${source##*/}.XXXXXX")"
    if ! cp -p "$source" "$temporary" || ! mv -f "$temporary" "$destination_dir/${source##*/}"; then
        rm -f "$temporary"
        return 1
    fi
    log "Published $destination_dir/${source##*/}"
}

log "Publishing installers from $BUILD_OUT_DIR to $MIRROR_DIR"
publish_package "$windows_plain" "$MIRROR_DIR/Windows"
publish_package "$windows_jre" "$MIRROR_DIR/Windows"
publish_package "$debian_plain" "$MIRROR_DIR/Linux/Debian"
publish_package "$debian_jre" "$MIRROR_DIR/Linux/Debian"
publish_package "${manjaro_packages[0]}" "$MIRROR_DIR/Linux/Manjaro"
