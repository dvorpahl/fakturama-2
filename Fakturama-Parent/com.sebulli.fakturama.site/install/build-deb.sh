#!/usr/bin/env bash
#
# Baut fakturama2_<version>_amd64.deb: ein installierbares Debian-Paket der
# normalen (nicht Demo-)Version, App + JRE unter /opt/fakturama2/ (kein
# System-Java noetig), Kommando /usr/bin/fakturama2, Menueeintrag. Anders als
# fakturama2-demo (siehe build-demo-deb.sh) laeuft das mit dem echten $HOME
# des Benutzers - normale, dauerhafte ~/.fakturama2-Konfiguration ueber
# Neustarts hinweg, kein Demo-Modus, keine vorbefuellten Test-DB-Zugangsdaten.
# Was ein frisches Fakturama-Profil beim ersten Start von sich aus tut (z.B.
# die mitgelieferte HSQL-Datenbank), sieht der Nutzer dieses Pakets genauso
# wie bei den offiziellen install4j-Installern.
#
# Voraussetzung: das Linux-Produkt wurde bereits gebaut, z.B. mit
#   mvn -B clean verify -DskipTests=true -P linux-x86_64   (aus Fakturama-Parent/)
#
# Usage:
#   ./build-deb.sh
#   JRE_ARCHIVE=/pfad/zu/linux-amd64-*.tar.gz ./build-deb.sh
#   DEB_VERSION=2.2.1.PREVIEW ./build-deb.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SITE_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
PARENT_DIR="$(cd "$SITE_DIR/.." && pwd)"

APP_ARCHIVE="${APP_ARCHIVE:-$SITE_DIR/target/products/Fakturama.ID-linux.gtk.x86_64.tar.gz}"
JRE_ARCHIVE="${JRE_ARCHIVE:-}"
WRAPPER_TEMPLATE="${WRAPPER_TEMPLATE:-$SCRIPT_DIR/deb-wrapper-normal-template.sh}"
DESKTOP_TEMPLATE="${DESKTOP_TEMPLATE:-$SCRIPT_DIR/fakturama2.desktop}"
ICON_SOURCE="${ICON_SOURCE:-$PARENT_DIR/org.fakturama.graphics/icons/ProgramIcon/icon.svg}"
OUTPUT_DIR="${OUTPUT_DIR:-$SCRIPT_DIR/demo}"

DEB_VERSION="${DEB_VERSION:-2.2.1.PREVIEW}"
DEB_ARCH="${DEB_ARCH:-amd64}"
PKG_NAME="fakturama2"

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
echo "Version:    $DEB_VERSION"

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

INSTALLED_SIZE_KB="$(du -sk "$PKG_ROOT/opt" | cut -f1)"

cat > "$PKG_ROOT/DEBIAN/control" <<CONTROL_EOF
Package: $PKG_NAME
Version: $DEB_VERSION
Section: office
Priority: optional
Architecture: $DEB_ARCH
Installed-Size: $INSTALLED_SIZE_KB
Maintainer: Danilo Vorpahl <danilo.vorpahl@googlemail.com>
Description: Fakturama2 - freie Rechnungssoftware (Vorschauversion)
 Normale, dauerhaft installierte Version - kein Demo-Modus, keine
 Test-Datenbank, keine automatische Bereinigung nach dem Beenden. Nutzt beim
 ersten Start die mitgelieferte lokale Datenbank bzw. die vorhandene eigene
 Konfiguration. Enthaelt eine eigene Java-Laufzeit, kein System-Java noetig.
CONTROL_EOF

cp "$WRAPPER_TEMPLATE" "$PKG_ROOT/usr/bin/$PKG_NAME"
chmod 755 "$PKG_ROOT/usr/bin/$PKG_NAME"
chmod 755 "$PKG_ROOT/opt/$PKG_NAME/app/Fakturama" "$PKG_ROOT/opt/$PKG_NAME/jre/bin/java"

sed -e "s|__VERSION__|${DEB_VERSION}|g" "$DESKTOP_TEMPLATE" > "$PKG_ROOT/usr/share/applications/${PKG_NAME}.desktop"

mkdir -p "$OUTPUT_DIR"
OUTPUT_DEB="$OUTPUT_DIR/${PKG_NAME}_${DEB_VERSION}_${DEB_ARCH}.deb"
dpkg-deb --root-owner-group --build "$PKG_ROOT" "$OUTPUT_DEB" >/dev/null

echo "Fertig: $OUTPUT_DEB ($(du -h "$OUTPUT_DEB" | cut -f1))"
echo "Installieren:   sudo dpkg -i $(basename "$OUTPUT_DEB")"
echo "Starten:        fakturama2   (oder ueber's Anwendungsmenue: 'Fakturama2')"
echo "Deinstallieren: sudo apt remove $PKG_NAME"
