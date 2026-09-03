#!/usr/bin/env bash
#
# Counterpart to pack-config-for-stick.sh: run this on the NEW machine after
# installing Fakturama (via the Unix installer or the .deb) to bring the old
# config/workspace-metadata folder (default ~/.fakturama2) along from the
# stick. Restores next to itself the newest fakturama2-config_*.tar.gz it
# finds unless one is given explicitly.
#
# Usage:
#   ./restore-config-from-stick.sh                       # auto-picks newest archive next to this script
#   ./restore-config-from-stick.sh /path/to/archive.tar.gz

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
DEFAULT_CONFIG_DIR="$HOME/.fakturama2"

ARCHIVE="${1:-}"

if [ -z "$ARCHIVE" ]; then
    ARCHIVE="$(ls -t "$SCRIPT_DIR"/fakturama2-config_*.tar.gz 2>/dev/null | head -1 || true)"
    if [ -z "$ARCHIVE" ]; then
        echo "error: kein fakturama2-config_*.tar.gz neben diesem Skript gefunden." >&2
        echo "       Pfad zum Archiv als Argument angeben." >&2
        exit 1
    fi
    echo "Verwende Archiv: $ARCHIVE"
fi

if [ ! -f "$ARCHIVE" ]; then
    echo "error: '$ARCHIVE' existiert nicht." >&2
    exit 1
fi

if [ -d "$DEFAULT_CONFIG_DIR" ]; then
    BACKUP="${DEFAULT_CONFIG_DIR}.bak-$(date +%Y%m%d-%H%M%S)"
    echo "'$DEFAULT_CONFIG_DIR' existiert bereits, sichere es nach '$BACKUP' ..."
    mv "$DEFAULT_CONFIG_DIR" "$BACKUP"
fi

echo "Entpacke '$ARCHIVE' nach '$HOME' ..."
tar -xzf "$ARCHIVE" -C "$HOME"

echo "Fertig. Config liegt jetzt in '$DEFAULT_CONFIG_DIR'."
