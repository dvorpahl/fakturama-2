#!/usr/bin/env bash
#
# Baut fakturama-demo.sh: ein einziges, selbstauspackendes Skript (App + JRE
# am Ende angehängt). Jeder Start davon: frisches Temp-Verzeichnis, entpacken,
# DB-Passwort per GUI-Dialog abfragen (zenity/kdialog, sonst stiller
# Terminal-Prompt), gegen die konfigurierte Test-DB starten, danach alles
# wieder löschen - kein Rückstand, jeder Start identisch.
#
# Voraussetzung: das Linux-Produkt wurde bereits gebaut, z.B. mit
#   mvn -B clean verify -DskipTests=true -P linux-x86_64   (aus Fakturama-Parent/)
#
# Nutzt nur Tools, die auf jedem Manjaro-Standardsystem vorhanden sind
# (tar, gzip, awk, coreutils) - für den Bau hier auf diesem Rechner ebenso.
#
# Usage:
#   ./build-demo.sh
#   JRE_ARCHIVE=/pfad/zu/jre-linux-amd64.tar.gz ./build-demo.sh
#   DB_HOST=... DB_PORT=... DB_NAME=... DB_USER=... ./build-demo.sh

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SITE_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

APP_ARCHIVE="${APP_ARCHIVE:-$SITE_DIR/target/products/Fakturama.ID-linux.gtk.x86_64.tar.gz}"
# Von install4j für die Linux-Installer gecachte JRE (siehe Fakturama-v2.x.install4j,
# <jreBundles jdkProviderId="Adoptium" release="17/jdk-17.0.19+10" />). Pfad ist
# install4j-Version-/Hash-abhängig, daher notfalls automatisch suchen.
JRE_ARCHIVE="${JRE_ARCHIVE:-}"
OUTPUT="${OUTPUT:-$SCRIPT_DIR/demo/fakturama-demo.sh}"

DB_HOST="${DB_HOST:-192.168.1.11}"
DB_PORT="${DB_PORT:-3307}"
DB_NAME="${DB_NAME:-fakturama2_test}"
DB_USER="${DB_USER:-geko}"
WEBBROWSER_URL="${WEBBROWSER_URL:-https://devtest.vorpahl.online/?fakview=true}"
# Must match APP_FKT_SHARED_SECRET on the devtest.vorpahl.online fakturama-tool deployment -
# set there separately, this script can't reach that server's config. Empty disables the
# FKT.getAuthToken() auto-login there; the normal password form still works either way.
FKT_SHARED_SECRET="${FKT_SHARED_SECRET:-}"

if [ ! -f "$APP_ARCHIVE" ]; then
    echo "error: '$APP_ARCHIVE' nicht gefunden." >&2
    echo "       Erst das Linux-Produkt bauen: mvn -B clean verify -DskipTests=true -P linux-x86_64 (aus Fakturama-Parent/)" >&2
    exit 1
fi

if [ -z "$JRE_ARCHIVE" ]; then
    JRE_ARCHIVE="$(find "$HOME/.cache/install4j" -iname 'linux-amd64-*.tar.gz' 2>/dev/null | sort | tail -1)"
fi
if [ -z "$JRE_ARCHIVE" ] || [ ! -f "$JRE_ARCHIVE" ]; then
    echo "error: keine JRE gefunden. JRE_ARCHIVE=/pfad/zu/linux-amd64-*.tar.gz setzen." >&2
    echo "       (normalerweise unter ~/.cache/install4j/v*/cached_jres/generated/*/linux-amd64-*.tar.gz," >&2
    echo "        wird beim Bauen eines Linux-Installers mit install4jc dort abgelegt)" >&2
    exit 1
fi

echo "App-Archiv: $APP_ARCHIVE"
echo "JRE-Archiv: $JRE_ARCHIVE"
echo "Ziel-DB:    ${DB_USER}@${DB_HOST}:${DB_PORT}/${DB_NAME}"
echo "Browser-URL: $WEBBROWSER_URL"
echo "FKT-Secret: $([ -n "$FKT_SHARED_SECRET" ] && echo "gesetzt" || echo "nicht gesetzt - Passwort-Login bleibt aktiv")"

BUILD_DIR="$(mktemp -d)"
trap 'rm -rf "$BUILD_DIR"' EXIT

mkdir -p "$BUILD_DIR/app" "$BUILD_DIR/jre"
tar --warning=no-unknown-keyword -xzf "$APP_ARCHIVE" -C "$BUILD_DIR/app"
tar --warning=no-unknown-keyword -xzf "$JRE_ARCHIVE" -C "$BUILD_DIR/jre"

PAYLOAD="$BUILD_DIR/payload.tar.gz"
tar -czf "$PAYLOAD" -C "$BUILD_DIR" app jre

# Java .properties escaping for the value side (matches the convention already used for the JDBC
# URL below, and for this exact key in a real prefs file: "GENERAL_WEBBROWSER_URL=http\://...").
# Resolved once here (build time), not deferred to the generated script, since it's a fixed value.
WEBBROWSER_URL_ESCAPED="${WEBBROWSER_URL//:/\\:}"
WEBBROWSER_URL_ESCAPED="${WEBBROWSER_URL_ESCAPED//=/\\=}"
FKT_SHARED_SECRET_ESCAPED="${FKT_SHARED_SECRET//:/\\:}"
FKT_SHARED_SECRET_ESCAPED="${FKT_SHARED_SECRET_ESCAPED//=/\\=}"

HEADER="$BUILD_DIR/header.sh"
cat > "$HEADER" <<HEADER_EOF
#!/usr/bin/env bash
#
# Fakturama Demo - ein einziges, selbstauspackendes Skript (Anwendung + JRE
# sind am Ende dieser Datei angehängt). Jeder Start: frisches Temp-Verzeichnis,
# entpacken, DB-Passwort per GUI-Dialog abfragen, starten, danach alles wieder
# löschen - kein Rückstand, jeder Start identisch.
#
# Nutzt nur Tools, die auf jedem Manjaro-Standardsystem vorhanden sind
# (tar, gzip, awk, coreutils). Für die Passwortabfrage wird zenity oder
# kdialog verwendet, falls vorhanden, sonst ein stiller Terminal-Prompt.
# Erzeugt von build-demo.sh - nicht von Hand editieren, sondern das
# Build-Skript erneut laufen lassen.

set -euo pipefail

DB_HOST="$DB_HOST"
DB_PORT="$DB_PORT"
DB_NAME="$DB_NAME"
DB_USER="$DB_USER"
WEBBROWSER_URL="$WEBBROWSER_URL"

prompt_password() {
    if command -v zenity >/dev/null 2>&1; then
        zenity --password --title="Fakturama Demo" \\
            --text="Datenbank-Passwort für \${DB_USER}@\${DB_HOST}:\${DB_PORT}/\${DB_NAME}:" 2>/dev/null
        return
    fi
    if command -v kdialog >/dev/null 2>&1; then
        kdialog --password "Datenbank-Passwort für \${DB_USER}@\${DB_HOST}:\${DB_PORT}/\${DB_NAME}:" 2>/dev/null
        return
    fi
    read -r -s -p "Datenbank-Passwort für \${DB_USER}@\${DB_HOST}:\${DB_PORT}/\${DB_NAME}: " pw >&2
    echo >&2
    printf '%s' "\$pw"
}

DB_PASSWORD="\$(prompt_password || true)"
if [ -z "\$DB_PASSWORD" ]; then
    echo "Kein Passwort eingegeben, breche ab." >&2
    exit 1
fi

WORKDIR="\$(mktemp -d /tmp/fakturama-demo.XXXXXX)"
cleanup() {
    echo "Räume auf: \$WORKDIR"
    rm -rf "\$WORKDIR"
}
trap cleanup EXIT

# Eigene Zeilennummer finden, ab der das angehängte Archiv beginnt.
PAYLOAD_LINE="\$(awk '/^__PAYLOAD_BELOW__\$/{print NR + 1; exit}' "\$0")"

echo "Entpacke Anwendung + Java-Laufzeit ..."
tail -n +"\$PAYLOAD_LINE" "\$0" | tar -xz -C "\$WORKDIR"

FAKE_HOME="\$WORKDIR/home"
PREFS_DIR="\$FAKE_HOME/.fakturama2/.metadata/.plugins/org.eclipse.core.runtime/.settings"
mkdir -p "\$PREFS_DIR" "\$WORKDIR/documents"

{
    echo "eclipse.preferences.version=1"
    echo "isreinit=false"
    echo "jdbc_reconnect=true"
    echo "jakarta.persistence.jdbc.driver=org.mariadb.jdbc.Driver"
    printf 'jakarta.persistence.jdbc.url=jdbc\\\\:mariadb\\\\://%s\\\\:%s/%s\n' "\$DB_HOST" "\$DB_PORT" "\$DB_NAME"
    printf 'jakarta.persistence.jdbc.user=%s\n' "\$DB_USER"
    printf 'jakarta.persistence.jdbc.password=%s\n' "\$DB_PASSWORD"
    echo "GENERAL_WORKSPACE=\$WORKDIR/documents"
    echo "GENERAL_WEBBROWSER_URL=$WEBBROWSER_URL_ESCAPED"
    # devtest.vorpahl.online's certificate isn't accepted as-is (not-yet-valid per the embedded
    # WebKitGTK browser's clock) - read in checksBeforeStartup() before any DB preference sync, so
    # this local value always wins regardless of what's stored in the shared test DB.
    echo "BROWSER_ALLOW_INVALID_CERTS=true"
    # Counterpart: devtest.vorpahl.online's fakturama-tool deployment must have the identical
    # value as APP_FKT_SHARED_SECRET, set separately there. Empty here just means the page falls
    # back to its normal password form (FKT.getAuthToken() returns "").
    echo "BROWSER_FKT_SHARED_SECRET=$FKT_SHARED_SECRET_ESCAPED"
} > "\$PREFS_DIR/com.sebulli.fakturama.rcp.prefs"
unset DB_PASSWORD

LAUNCHER="\$WORKDIR/app/Fakturama"
JAVA_BIN="\$WORKDIR/jre/bin/java"
chmod +x "\$LAUNCHER" "\$JAVA_BIN"

echo "Starte Fakturama-Demo (DB: \${DB_HOST}:\${DB_PORT}/\${DB_NAME}) ..."
# -Dfakturama.demoMode=true: makes the app start maximized and never write local preference
# edits (table settings, number ranges, ...) back into this shared test database - see
# LifecycleManager#processAdditions / PreferencesInDatabase#savePreferenceValue.
"\$LAUNCHER" -vm "\$JAVA_BIN" -vmargs "-Duser.home=\$FAKE_HOME" "-Dfakturama.demoMode=true"

echo "Fakturama beendet."
exit 0
__PAYLOAD_BELOW__
HEADER_EOF

mkdir -p "$(dirname "$OUTPUT")"
cat "$HEADER" "$PAYLOAD" > "$OUTPUT"
chmod +x "$OUTPUT"

echo "Fertig: $OUTPUT ($(du -h "$OUTPUT" | cut -f1))"
