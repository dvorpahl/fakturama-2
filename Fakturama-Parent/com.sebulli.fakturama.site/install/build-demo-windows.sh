#!/usr/bin/env bash
#
# Baut fakturama-demo.ps1: ein einziges PowerShell-Skript fuer Windows (App +
# JRE als Base64-kodiertes ZIP am Ende angehaengt - gleiches Prinzip wie
# build-demo.sh fuer Linux, nur Base64 statt rohem tar.gz, da eine .ps1-Datei
# gueltiger Text bleiben muss). Jeder Start des generierten Skripts: frisches
# Temp-Verzeichnis, entpacken, DB-Passwort per GUI-Dialog abfragen, gegen die
# konfigurierte Test-DB starten, danach alles wieder loeschen - kein Rueckstand,
# jeder Start identisch (gleiches Verhalten wie die Linux-Demo). Die mitgelieferte
# JRE wird tatsaechlich beim Start verwendet, kein System-Java noetig.
#
# Voraussetzung: das Windows-Produkt wurde bereits gebaut, z.B. mit
#   mvn -B clean verify -DskipTests=true -P win   (aus Fakturama-Parent/, Default-Profil)
#
# Die eigentliche PowerShell-Logik steht in der Vorlage demo-windows-template.ps1
# (Platzhalter __DB_HOST__ etc.) - dieses Skript hier ersetzt nur die Platzhalter
# und haengt das Base64-kodierte Payload-ZIP an. Vorlage aendern, nicht die
# generierte fakturama-demo.ps1 von Hand.
#
# Usage:
#   ./build-demo-windows.sh
#   JRE_ARCHIVE=/pfad/zu/windows-amd64-*.tar.gz ./build-demo-windows.sh
#   DB_HOST=... DB_PORT=... DB_NAME=... DB_USER=... ./build-demo-windows.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SITE_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

APP_ARCHIVE="${APP_ARCHIVE:-$SITE_DIR/target/products/Fakturama.ID-win32.win32.x86_64.zip}"
# Von install4j fuer die Windows-Installer gecachte JRE (siehe build-demo.sh's identischer
# Kommentar fuer die Linux-Variante) - gleiche install4j-Version/Hash-Abhaengigkeit, deshalb
# notfalls automatisch suchen.
JRE_ARCHIVE="${JRE_ARCHIVE:-}"
TEMPLATE="${TEMPLATE:-$SCRIPT_DIR/demo-windows-template.ps1}"
OUTPUT="${OUTPUT:-$SCRIPT_DIR/demo/fakturama-demo.ps1}"

DB_HOST="${DB_HOST:-192.168.1.11}"
DB_PORT="${DB_PORT:-3307}"
DB_NAME="${DB_NAME:-fakturama2_test}"
DB_USER="${DB_USER:-geko}"
WEBBROWSER_URL="${WEBBROWSER_URL:-https://devtest.vorpahl.online/?fakview=true}"
# Muss mit APP_FKT_SHARED_SECRET auf dem devtest.vorpahl.online-fakturama-tool-Deployment
# uebereinstimmen - siehe build-demo.sh's identischer Kommentar dazu.
FKT_SHARED_SECRET="${FKT_SHARED_SECRET:-}"

if [ ! -f "$APP_ARCHIVE" ]; then
    echo "error: '$APP_ARCHIVE' nicht gefunden." >&2
    echo "       Erst das Windows-Produkt bauen: mvn -B clean verify -DskipTests=true -P win (aus Fakturama-Parent/)" >&2
    exit 1
fi
if [ ! -f "$TEMPLATE" ]; then
    echo "error: Vorlage '$TEMPLATE' nicht gefunden." >&2
    exit 1
fi

if [ -z "$JRE_ARCHIVE" ]; then
    JRE_ARCHIVE="$(find "$HOME/.cache/install4j" -iname 'windows-amd64-*.tar.gz' 2>/dev/null | sort | tail -1)"
fi
if [ -z "$JRE_ARCHIVE" ] || [ ! -f "$JRE_ARCHIVE" ]; then
    echo "error: keine Windows-JRE gefunden. JRE_ARCHIVE=/pfad/zu/windows-amd64-*.tar.gz setzen." >&2
    echo "       (normalerweise unter ~/.cache/install4j/v*/cached_jres/generated/*/windows-amd64-*.tar.gz," >&2
    echo "        wird beim Bauen eines Windows-Installers mit install4jc dort abgelegt)" >&2
    exit 1
fi

echo "App-Archiv: $APP_ARCHIVE"
echo "JRE-Archiv: $JRE_ARCHIVE"
echo "Ziel-DB:    ${DB_USER}@${DB_HOST}:${DB_PORT}/${DB_NAME}"
echo "Browser-URL: $WEBBROWSER_URL"
echo "FKT-Secret: $([ -n "$FKT_SHARED_SECRET" ] && echo "gesetzt" || echo "nicht gesetzt - Passwort-Login bleibt aktiv")"

BUILD_DIR="$(mktemp -d)"
trap 'rm -rf "$BUILD_DIR"' EXIT

# App-ZIP und JRE-tar.gz in ein gemeinsames ZIP zusammenfuehren (app/ + jre/ Unterordner,
# gleiche Struktur wie build-demo.sh's tar-Payload fuer Linux), damit das generierte
# PowerShell-Skript nur ein einziges Archiv extrahieren muss.
mkdir -p "$BUILD_DIR/payload/app" "$BUILD_DIR/payload/jre"
( cd "$BUILD_DIR/payload/app" && unzip -q "$APP_ARCHIVE" )
tar -xzf "$JRE_ARCHIVE" -C "$BUILD_DIR/payload/jre"

PAYLOAD_ZIP="$BUILD_DIR/payload.zip"
( cd "$BUILD_DIR/payload" && zip -q -r "$PAYLOAD_ZIP" app jre )

# Pruefsumme des rohen (noch unkodierten) Payload-ZIPs - das generierte .ps1 prueft
# damit nach dem Base64-Decode, ob die ~300 MB grosse Datei auf dem Transportweg
# (Download/Kopie zu Heinz) unversehrt angekommen ist, statt bei Beschaedigung nur
# eine kryptische .NET-Fehlermeldung zu zeigen.
PAYLOAD_SHA256="$(sha256sum "$PAYLOAD_ZIP" | cut -d' ' -f1)"

HEADER="$BUILD_DIR/header.ps1"
cp "$TEMPLATE" "$HEADER"

# sed-Ersetzung statt Bash-Heredoc-Interpolation: der Header ist PowerShell, dessen
# eigene $variablen sonst mit Bashs $-Expansion kollidieren wuerden. Reihenfolge
# wichtig, falls ein Wert selbst "&" oder "/" enthaelt - deshalb | als Trenner.
sed -i \
    -e "s|__DB_HOST__|${DB_HOST}|g" \
    -e "s|__DB_PORT__|${DB_PORT}|g" \
    -e "s|__DB_NAME__|${DB_NAME}|g" \
    -e "s|__DB_USER__|${DB_USER}|g" \
    -e "s|__WEBBROWSER_URL__|${WEBBROWSER_URL}|g" \
    -e "s|__FKT_SHARED_SECRET__|${FKT_SHARED_SECRET}|g" \
    -e "s|__PAYLOAD_SHA256__|${PAYLOAD_SHA256}|g" \
    "$HEADER"

# PowerShell-Skripte muessen sauberer Text bleiben - das Payload-ZIP wird deshalb
# Base64-kodiert statt roh angehaengt (wie tar.gz beim Linux-Pendant).
PAYLOAD_B64="$BUILD_DIR/payload.b64"
base64 -w0 "$PAYLOAD_ZIP" > "$PAYLOAD_B64"

mkdir -p "$(dirname "$OUTPUT")"
cat "$HEADER" "$PAYLOAD_B64" > "$OUTPUT"
printf '\n' >> "$OUTPUT"

echo "Fertig: $OUTPUT ($(du -h "$OUTPUT" | cut -f1))"
echo "Payload-SHA256: $PAYLOAD_SHA256"
echo "Hinweis: dies ist ungetestet auf echtem Windows - vor Weitergabe einmal selbst starten:"
echo "  powershell.exe -ExecutionPolicy Bypass -File fakturama-demo.ps1"
