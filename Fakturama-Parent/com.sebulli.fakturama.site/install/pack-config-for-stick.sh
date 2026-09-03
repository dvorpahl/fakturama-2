#!/usr/bin/env bash
#
# Packs the local Fakturama config/workspace-metadata folder (default
# ~/.fakturama2) into a tar.gz and places it right next to the Unix
# installer / Debian package output, so both can be copied onto a USB
# stick together: install the app on the new machine with the installer,
# then run restore-config-from-stick.sh from the stick to bring the old
# settings along.
#
# Usage:
#   ./pack-config-for-stick.sh                # uses ~/.fakturama2, asks if missing
#   ./pack-config-for-stick.sh /path/to/config
#   OUT_DIR=/some/dir ./pack-config-for-stick.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
OUT_DIR="${OUT_DIR:-$SCRIPT_DIR/install}"
DEFAULT_CONFIG_DIR="$HOME/.fakturama2"

CONFIG_DIR="${1:-}"

if [ -z "$CONFIG_DIR" ]; then
    if [ -d "$DEFAULT_CONFIG_DIR" ]; then
        CONFIG_DIR="$DEFAULT_CONFIG_DIR"
        echo "Gefunden: $CONFIG_DIR"
    else
        echo "Standard-Config-Ordner '$DEFAULT_CONFIG_DIR' nicht gefunden."
        read -r -p "Bitte Pfad zum Fakturama-Config-Ordner angeben: " CONFIG_DIR
    fi
fi

CONFIG_DIR="${CONFIG_DIR%/}"

if [ ! -d "$CONFIG_DIR" ]; then
    echo "error: '$CONFIG_DIR' ist kein Verzeichnis." >&2
    exit 1
fi

mkdir -p "$OUT_DIR"

TIMESTAMP="$(date +%Y%m%d-%H%M%S)"
ARCHIVE="$OUT_DIR/fakturama2-config_${TIMESTAMP}.tar.gz"

echo "Packe '$CONFIG_DIR' nach '$ARCHIVE' ..."
tar -czf "$ARCHIVE" -C "$(dirname "$CONFIG_DIR")" "$(basename "$CONFIG_DIR")"

echo "Fertig: $ARCHIVE ($(du -h "$ARCHIVE" | cut -f1))"
echo "Liegt neben dem Installer in: $OUT_DIR"
