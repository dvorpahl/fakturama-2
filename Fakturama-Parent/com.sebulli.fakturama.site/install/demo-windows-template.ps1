<#
Fakturama Demo (Windows) - ein einziges PowerShell-Skript (Anwendung UND eine
passende Java-Laufzeit als Base64 am Ende dieser Datei angehaengt - gleiches
Prinzip wie das Linux-Pendant fakturama-demo.sh). Kein System-Java noetig, die
mitgelieferte JRE wird tatsaechlich beim Start verwendet. Jeder Start: frisches
Temp-Verzeichnis, entpacken, DB-Passwort per GUI-Dialog abfragen, gegen die
konfigurierte Test-DB starten, danach alles wieder loeschen - kein Rueckstand,
jeder Start identisch.

Erzeugt von build-demo-windows.sh - nicht von Hand editieren, sondern das
Build-Skript erneut laufen lassen (Vorlage: demo-windows-template.ps1).

Start z.B. per Rechtsklick -> "Mit PowerShell ausfuehren", oder:
  powershell.exe -ExecutionPolicy Bypass -File fakturama-demo.ps1
#>

$ErrorActionPreference = "Stop"

Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing

$DB_HOST = "__DB_HOST__"
$DB_PORT = "__DB_PORT__"
$DB_NAME = "__DB_NAME__"
$DB_USER = "__DB_USER__"
$WEBBROWSER_URL = "__WEBBROWSER_URL__"
$FKT_SHARED_SECRET = "__FKT_SHARED_SECRET__"

# Passwort-Dialog (maskiertes Eingabefeld, kein Klartext im Fenster) - das
# Windows-Pendant zu zenity --password auf der Linux-Seite (Assemblies bereits
# ganz oben geladen).

function Read-DbPassword {
    $form = New-Object System.Windows.Forms.Form
    $form.Text = "Fakturama Demo"
    $form.Width = 440
    $form.Height = 170
    $form.StartPosition = "CenterScreen"
    $form.FormBorderStyle = "FixedDialog"
    $form.MaximizeBox = $false
    $form.MinimizeBox = $false
    $form.TopMost = $true

    $label = New-Object System.Windows.Forms.Label
    $label.Text = "Datenbank-Passwort fuer ${DB_USER}@${DB_HOST}:${DB_PORT}/${DB_NAME}:"
    $label.AutoSize = $true
    $label.Location = New-Object System.Drawing.Point(10, 15)
    $form.Controls.Add($label)

    $textbox = New-Object System.Windows.Forms.TextBox
    $textbox.UseSystemPasswordChar = $true
    $textbox.Width = 400
    $textbox.Location = New-Object System.Drawing.Point(10, 45)
    $form.Controls.Add($textbox)

    $okButton = New-Object System.Windows.Forms.Button
    $okButton.Text = "OK"
    $okButton.Location = New-Object System.Drawing.Point(240, 85)
    $okButton.DialogResult = [System.Windows.Forms.DialogResult]::OK
    $form.Controls.Add($okButton)
    $form.AcceptButton = $okButton

    $cancelButton = New-Object System.Windows.Forms.Button
    $cancelButton.Text = "Abbrechen"
    $cancelButton.Location = New-Object System.Drawing.Point(325, 85)
    $cancelButton.DialogResult = [System.Windows.Forms.DialogResult]::Cancel
    $form.Controls.Add($cancelButton)
    $form.CancelButton = $cancelButton

    $form.Add_Shown({ $textbox.Focus() })
    $result = $form.ShowDialog()
    $form.Dispose()
    if ($result -ne [System.Windows.Forms.DialogResult]::OK -or [string]::IsNullOrEmpty($textbox.Text)) {
        return $null
    }
    return $textbox.Text
}

$dbPassword = Read-DbPassword
if (-not $dbPassword) {
    Write-Error "Kein Passwort eingegeben, breche ab."
    exit 1
}

$workDir = Join-Path $env:TEMP ("fakturama-demo." + [System.Guid]::NewGuid().ToString("N").Substring(0, 8))
New-Item -ItemType Directory -Path $workDir | Out-Null

try {
    Write-Host "Entpacke Anwendung und Java-Laufzeit ..."
    # Eigene Datei einlesen und den Anteil nach dem Payload-Marker als Base64 dekodieren -
    # Pendant zum "tail -n +N | tar -xz" der Linux-Demo, nur textsicher (Base64 statt
    # rohem Binaerdaten-Anhang, damit die .ps1-Datei ein gueltiges Textdokument bleibt).
    # Das Payload-ZIP enthaelt "app/" und "jre/" nebeneinander (von build-demo-windows.sh
    # zusammengefuehrt), genau wie build-demo.sh's tar-Payload fuer Linux.
    $scriptContent = Get-Content -LiteralPath $PSCommandPath -Raw
    $markerIndex = $scriptContent.IndexOf("__PAYLOAD_BELOW__")
    if ($markerIndex -lt 0) {
        throw "Payload-Marker nicht gefunden - Datei beschaedigt oder falsch erzeugt?"
    }
    $payloadStart = $scriptContent.IndexOf("`n", $markerIndex) + 1
    $base64Payload = $scriptContent.Substring($payloadStart).Trim()
    $zipBytes = [Convert]::FromBase64String($base64Payload)
    $zipPath = Join-Path $workDir "payload.zip"
    [System.IO.File]::WriteAllBytes($zipPath, $zipBytes)
    Expand-Archive -LiteralPath $zipPath -DestinationPath $workDir -Force
    Remove-Item -LiteralPath $zipPath
    $appDir = Join-Path $workDir "app"
    $javaPath = Join-Path $workDir "jre\bin\java.exe"
    if (-not (Test-Path -LiteralPath $javaPath)) {
        throw "Mitgelieferte Java-Laufzeit wurde nicht gefunden (erwartet unter jre\bin\java.exe) - Archiv beschaedigt oder falsch erzeugt?"
    }

    $fakeHome = Join-Path $workDir "home"
    $prefsDir = Join-Path $fakeHome ".fakturama2\.metadata\.plugins\org.eclipse.core.runtime\.settings"
    New-Item -ItemType Directory -Path $prefsDir -Force | Out-Null
    $documentsDir = Join-Path $workDir "documents"
    New-Item -ItemType Directory -Path $documentsDir -Force | Out-Null

    # Java .properties-Escaping (Doppelpunkt/Gleichheitszeichen) wie in der Linux-Demo -
    # gleiche Konvention, gleiche Ziel-Datei (com.sebulli.fakturama.rcp.prefs).
    # Backslash zuerst escapen (Windows-Pfad!), sonst wuerden die direkt danach
    # eingefuegten \: -Escapes selbst nochmal verdoppelt. Java-.properties-Dateien
    # behandeln unescapte Backslashes als Steuerzeichen (\f, \t, \n, ...) - ein
    # Pfadsegment, das zufaellig mit einem dieser Buchstaben beginnt, wuerde sonst
    # falsch geparst.
    $documentsDirEscaped = ($documentsDir -replace '\\', '\\') -replace ':', '\:'
    $webBrowserUrlEscaped = $WEBBROWSER_URL -replace ':', '\:' -replace '=', '\='
    $fktSecretEscaped = $FKT_SHARED_SECRET -replace ':', '\:' -replace '=', '\='

    $prefsLines = @(
        "eclipse.preferences.version=1",
        "isreinit=false",
        "jdbc_reconnect=true",
        "jakarta.persistence.jdbc.driver=org.mariadb.jdbc.Driver",
        "jakarta.persistence.jdbc.url=jdbc\:mariadb\://${DB_HOST}\:${DB_PORT}/${DB_NAME}",
        "jakarta.persistence.jdbc.user=${DB_USER}",
        "jakarta.persistence.jdbc.password=${dbPassword}",
        "GENERAL_WORKSPACE=${documentsDirEscaped}",
        "GENERAL_WEBBROWSER_URL=${webBrowserUrlEscaped}",
        "BROWSER_ALLOW_INVALID_CERTS=true",
        "BROWSER_FKT_SHARED_SECRET=${fktSecretEscaped}"
    )
    [System.IO.File]::WriteAllLines((Join-Path $prefsDir "com.sebulli.fakturama.rcp.prefs"), $prefsLines)
    $dbPassword = $null
    $prefsLines = $null

    $launcher = Get-ChildItem -Path $appDir -Filter "Fakturama.exe" -Recurse | Select-Object -First 1
    if (-not $launcher) {
        throw "Fakturama.exe wurde im entpackten Archiv nicht gefunden."
    }

    Write-Host "Starte Fakturama-Demo (DB: ${DB_HOST}:${DB_PORT}/${DB_NAME}) ..."
    # -Dfakturama.demoMode=true: startet maximiert und schreibt lokale Einstellungsaenderungen
    # (Tabellenspalten, Nummernkreise, ...) nicht in die geteilte Test-DB zurueck - siehe
    # LifecycleManager#processAdditions / PreferencesInDatabase#savePreferenceValue.
    #
    # Ein einzelner, manuell gequoteter Argument-String statt eines Arrays: Start-Process
    # -ArgumentList quotet Array-Elemente mit Leerzeichen (z.B. im Temp-Pfad) nicht
    # zuverlaessig selbst - bekannte PowerShell-Falle. Gleiche flache Struktur wie
    # build-demo.sh's "$LAUNCHER" -vm "$JAVA_BIN" -vmargs "-Duser.home=$FAKE_HOME" ... .
    $argumentString = '-vm "' + $javaPath + '" -vmargs -Duser.home="' + $fakeHome + '" -Dfakturama.demoMode=true'
    $proc = Start-Process -FilePath $launcher.FullName -ArgumentList $argumentString -PassThru -Wait
}
finally {
    Write-Host "Raeume auf: $workDir"
    Remove-Item -LiteralPath $workDir -Recurse -Force -ErrorAction SilentlyContinue
}

Write-Host "Fakturama beendet."
exit 0
__PAYLOAD_BELOW__
