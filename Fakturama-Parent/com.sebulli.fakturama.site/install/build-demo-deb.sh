#!/usr/bin/env bash
#
# Baut fakturama2-demo_<version>_amd64.deb: ein installierbares Debian-Paket
# der Fakturama2-Demo, App + JRE unter /opt/fakturama2-demo/ (kein System-Java
# noetig), Kommando /usr/bin/fakturama2-demo, Menueeintrag. Anders als
# fakturama-demo.sh/.ps1 ist das eine ECHTE Installation (dpkg -i / per
# Paketverwaltung) und bleibt bis zur Deinstallation auf dem System - aber
# jeder einzelne START verhaelt sich weiterhin wie die Demo-Skripte: frisches
# Temp-Verzeichnis, DB-Passwort abfragen, danach alles wieder loeschen (siehe
# deb-wrapper-template.sh, das dafuer als /usr/bin/fakturama2-demo installiert
# wird) - kein dauerhafter lokaler Zustand außerhalb des Pakets selbst.
#
# Voraussetzung: das Linux-Produkt wurde bereits gebaut, z.B. mit
#   mvn -B clean verify -DskipTests=true -P linux-x86_64   (aus Fakturama-Parent/)
#
# Usage:
#   ./build-demo-deb.sh
#   JRE_ARCHIVE=/pfad/zu/linux-amd64-*.tar.gz ./build-demo-deb.sh
#   DB_HOST=... DB_PORT=... DB_NAME=... DB_USER=... ./build-demo-deb.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SITE_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
PARENT_DIR="$(cd "$SITE_DIR/.." && pwd)"

APP_ARCHIVE="${APP_ARCHIVE:-$SITE_DIR/target/products/Fakturama.ID-linux.gtk.x86_64.tar.gz}"
JRE_ARCHIVE="${JRE_ARCHIVE:-}"
WRAPPER_TEMPLATE="${WRAPPER_TEMPLATE:-$SCRIPT_DIR/deb-wrapper-template.sh}"
DESKTOP_TEMPLATE="${DESKTOP_TEMPLATE:-$SCRIPT_DIR/fakturama2-demo.desktop}"
ICON_SOURCE="${ICON_SOURCE:-$PARENT_DIR/org.fakturama.graphics/icons/ProgramIcon/icon.svg}"
OUTPUT_DIR="${OUTPUT_DIR:-$SCRIPT_DIR/demo}"

DEB_VERSION="${DEB_VERSION:-2.2.1}"
DEB_ARCH="${DEB_ARCH:-amd64}"
PKG_NAME="fakturama2-demo"

DB_HOST="${DB_HOST:-192.168.1.11}"
DB_PORT="${DB_PORT:-3307}"
DB_NAME="${DB_NAME:-fakturama2_test}"
DB_USER="${DB_USER:-geko}"
WEBBROWSER_URL="${WEBBROWSER_URL:-https://devtest.vorpahl.online/?fakview=true}"

if [ ! -f "$APP_ARCHIVE" ]; then
    echo "error: '$APP_ARCHIVE' nicht gefunden." >&2
    echo "       Erst das Linux-Produkt bauen: mvn -B clean verify -DskipTests=true -P linux-x86_64 (aus Fakturama-Parent/)" >&2
    exit 1
fi
for f in "$WRAPPER_TEMPLATE" "$DESKTOP_TEMPLATE"; do
    if [ ! -f "$f" ]; then
        echo "error: Vorlage '$f' nicht gefunden." >&2
        exit 1
    fi
done

if [ -z "$JRE_ARCHIVE" ]; then
    JRE_ARCHIVE="$(find "$HOME/.cache/install4j" -iname 'linux-amd64-*.tar.gz' 2>/dev/null | sort | tail -1)"
fi
if [ -z "$JRE_ARCHIVE" ] || [ ! -f "$JRE_ARCHIVE" ]; then
    echo "error: keine JRE gefunden. JRE_ARCHIVE=/pfad/zu/linux-amd64-*.tar.gz setzen." >&2
    exit 1
fi

echo "App-Archiv: $APP_ARCHIVE"
echo "JRE-Archiv: $JRE_ARCHIVE"
echo "Ziel-DB:    ${DB_USER}@${DB_HOST}:${DB_PORT}/${DB_NAME}"
echo "Browser-URL: $WEBBROWSER_URL"

BUILD_DIR="$(mktemp -d)"
trap 'rm -rf "$BUILD_DIR"' EXIT

PKG_ROOT="$BUILD_DIR/${PKG_NAME}_${DEB_VERSION}_${DEB_ARCH}"
mkdir -p "$PKG_ROOT/DEBIAN" \
         "$PKG_ROOT/opt/$PKG_NAME/app" \
         "$PKG_ROOT/opt/$PKG_NAME/jre" \
         "$PKG_ROOT/usr/bin" \
         "$PKG_ROOT/usr/share/applications"

tar --warning=no-unknown-keyword -xzf "$APP_ARCHIVE" -C "$PKG_ROOT/opt/$PKG_NAME/app"
tar --warning=no-unknown-keyword -xzf "$JRE_ARCHIVE" -C "$PKG_ROOT/opt/$PKG_NAME/jre"
if [ -f "$ICON_SOURCE" ]; then
    cp "$ICON_SOURCE" "$PKG_ROOT/opt/$PKG_NAME/icon.svg"
fi

# Installed byte size, for the control file's Installed-Size field (in KiB, dpkg convention).
INSTALLED_SIZE_KB="$(du -sk "$PKG_ROOT/opt" | cut -f1)"

cat > "$PKG_ROOT/DEBIAN/control" <<CONTROL_EOF
Package: $PKG_NAME
Version: $DEB_VERSION
Section: office
Priority: optional
Architecture: $DEB_ARCH
Installed-Size: $INSTALLED_SIZE_KB
Maintainer: Danilo Vorpahl <danilo.vorpahl@googlemail.com>
Description: Fakturama2 Test-/Demo-Version
 Startet Fakturama2 gegen eine Test-Datenbank, im Demo-Modus (keine
 Rueckschreibung lokaler Einstellungen in die Datenbank). Jeder Start legt ein
 frisches, temporaeres Arbeitsverzeichnis an und raeumt es nach dem Beenden
 wieder auf - kein dauerhafter lokaler Zustand ausserhalb dieses Pakets.
 Enthaelt eine eigene Java-Laufzeit, kein System-Java noetig.
CONTROL_EOF

# sed-Ersetzung statt Heredoc-Interpolation, gleiches Prinzip wie bei
# build-demo-windows.sh's PowerShell-Header - der Wrapper ist selbst ein
# eigenstaendiges Bash-Skript mit seinen eigenen $-Variablen.
cp "$WRAPPER_TEMPLATE" "$PKG_ROOT/usr/bin/$PKG_NAME"
sed -i \
    -e "s|__DB_HOST__|${DB_HOST}|g" \
    -e "s|__DB_PORT__|${DB_PORT}|g" \
    -e "s|__DB_NAME__|${DB_NAME}|g" \
    -e "s|__DB_USER__|${DB_USER}|g" \
    -e "s|__WEBBROWSER_URL__|${WEBBROWSER_URL}|g" \
    "$PKG_ROOT/usr/bin/$PKG_NAME"
chmod 755 "$PKG_ROOT/usr/bin/$PKG_NAME"
chmod 755 "$PKG_ROOT/opt/$PKG_NAME/app/Fakturama" "$PKG_ROOT/opt/$PKG_NAME/jre/bin/java"

cp "$DESKTOP_TEMPLATE" "$PKG_ROOT/usr/share/applications/${PKG_NAME}.desktop"

mkdir -p "$OUTPUT_DIR"
OUTPUT_DEB="$OUTPUT_DIR/${PKG_NAME}_${DEB_VERSION}_${DEB_ARCH}.deb"
dpkg-deb --root-owner-group --build "$PKG_ROOT" "$OUTPUT_DEB" >/dev/null

echo "Fertig: $OUTPUT_DEB ($(du -h "$OUTPUT_DEB" | cut -f1))"
echo "Installieren:   sudo dpkg -i $(basename "$OUTPUT_DEB")"
echo "Starten:        fakturama2-demo   (oder ueber's Anwendungsmenue: 'Fakturama2 Demo')"
echo "Deinstallieren: sudo apt remove $PKG_NAME"
