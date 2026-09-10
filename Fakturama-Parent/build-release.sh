#!/usr/bin/env bash
#
# Git-tracked entry point for a full Fakturama release build: Windows
# (product zip + MSI installer, with/without bundled JRE), Linux (product
# tar.gz + .deb, with/without bundled JRE) and an Arch/Manjaro pacman
# package. Removes this script's own previous output directories under
# $HEINZ_DIR first, then delegates the actual platform-by-platform build to
# build-packages.sh.
#
# build-packages.sh itself stays gitignored because it embeds the install4j
# license key - it never overwrites or removes a previous run's output
# (each run gets its own <version>_<timestamp> folder under $HEINZ_DIR), so
# those pile up run after run. This script adds that "start from a clean
# slate" step and nothing else.
#
# Usage:
#   ./build-release.sh
#   HEINZ_DIR=/some/other/dir ./build-release.sh
#   SIGN=1 ./build-release.sh          # forwarded to build-packages.sh
#   INSTALL4JC=/path/to/install4jc ./build-release.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

HEINZ_DIR="${HEINZ_DIR:-$HOME/VorpahlCom/heinz}"

log() { echo "== $* =="; }

if [ ! -x "$SCRIPT_DIR/build-packages.sh" ]; then
    echo "error: $SCRIPT_DIR/build-packages.sh not found (it holds the actual build steps + install4j license key)." >&2
    exit 1
fi

if [ -d "$HEINZ_DIR" ]; then
    log "Removing previous release output under $HEINZ_DIR"
    # Only matches this pipeline's own "<version>_<timestamp>" output dirs
    # (e.g. "2.2.1_20260910-0016", "2.2.1.rc1_20260901-1230") - never touches
    # unrelated files/dirs that happen to also live under $HEINZ_DIR.
    find "$HEINZ_DIR" -maxdepth 1 -type d \
        -regextype posix-extended \
        -regex '.*/[0-9]+\.[0-9]+\.[0-9]+(\.[A-Za-z0-9]+)?_[0-9]{8}-[0-9]{4}' \
        -print -exec rm -rf {} +
fi

log "Building full release (Windows zip+MSI, Linux tar.gz+deb, Arch/Manjaro package)"
HEINZ_DIR="$HEINZ_DIR" exec "$SCRIPT_DIR/build-packages.sh"
