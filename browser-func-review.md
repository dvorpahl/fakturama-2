# Browserfunktionen in Fakturama 2.2.1

Stand: 2026-10-08. Geltungsbereich: der eingebettete SWT-Browser auf Windows 10/11, Manjaro und Debian. Aufgeführt sind die für diese Installation belegten Engine-, Profil-, Laufzeit- und Eingabeoptionen. Beliebige Chromium-/WebKit-Entwicklerflags gehören nicht dazu.

## Verifizierte Ausgangslage

- [`BrowserEditor`](com.sebulli.fakturama.rcp/src/main/java/com/sebulli/fakturama/parts/BrowserEditor.java#L288-L309) erzeugt `new Browser(top, browserStyle)`. Standard ist `SWT.NONE`; die alte Preference `BROWSER_TYPE` kann WebKit oder Mozilla als Stil setzen. Die zugehörige Auswahl ist in [`BrowserPreferencePage`](com.sebulli.fakturama.rcp/src/main/java/com/sebulli/fakturama/preferences/BrowserPreferencePage.java#L65-L72) auskommentiert. [Aktuelles SWT](https://help.eclipse.org/latest/topic/org.eclipse.platform.doc.isv/reference/api/org/eclipse/swt/SWT.html) bezeichnet XULRunner/Mozilla als nicht mehr unterstützt.
- Das im Repository gebaute Linux-Produkt enthält `org.eclipse.swt` und `org.eclipse.swt.gtk.linux.x86_64` **3.124.0.v20230523-1044**, siehe [`bundles.info`](com.sebulli.fakturama.site/target/products/Fakturama.ID/linux/gtk/x86_64/configuration/org.eclipse.equinox.simpleconfigurator/bundles.info#L1307-L1308). Die Version auf einem anderen installierten Rechner ist dadurch noch nicht bewiesen.
- Das reguläre Windows-Produkt setzt `DefaultType=edge`, siehe [`com.sebulli.fakturama.product`](com.sebulli.fakturama.site/com.sebulli.fakturama.product#L27-L34). Das Windows-Demo-Startskript setzt zusätzlich `EdgeDir` auf die mitgelieferte feste Runtime, siehe [`demo-windows-template.ps1`](com.sebulli.fakturama.site/install/demo-windows-template.ps1#L180-L196).
- Manjaro deklariert `gtk3`, `libsoup>=2.74.3` und `webkit2gtk>=2.50.6` in [`PKGBUILD`](com.sebulli.fakturama.site/install/PKGBUILD#L23-L32). Das Debian-Paket deklariert derzeit nur `bash` in [`DEBIAN/control`](com.sebulli.fakturama.site/install/linux_64bit/fakturama/DEBIAN/control#L1-L6). Daraus folgt noch nicht, welche Bibliotheken auf einem konkreten Debian-Rechner vorhanden sind.

## Windows 10/11: SWT Edge/WebView2

Die fünf `Edge*`-Properties wurden im Bytecode des lokal vorhandenen SWT-Windows-Bundles **3.124.0** geprüft (`javap -c -p org.eclipse.swt.browser.Edge`), also in derselben SWT-Version wie im gebauten Linux-Produkt.

| Option | Funktion | Projektstatus / Grenze |
| --- | --- | --- |
| `-Dorg.eclipse.swt.browser.DefaultType=edge` | Bevorzugt WebView2 für Browser mit `SWT.NONE`. | Im normalen Windows-Produkt gesetzt. Der tatsächliche Browsertyp bleibt zur Laufzeit zu prüfen. |
| `-Dorg.eclipse.swt.browser.EdgeDir=<Pfad>` | Wählt eine feste WebView2-Runtime. | Im Windows-Demo-Starter gesetzt; für die reguläre Installation nicht belegt. Ohne feste Runtime ist eine installierte WebView2-Runtime nötig. [Microsoft: Runtime-Verteilung](https://learn.microsoft.com/en-us/microsoft-edge/webview2/concepts/distribution). |
| `-Dorg.eclipse.swt.browser.EdgeDataDir=<Pfad>` | Wählt das WebView2-Profilverzeichnis für Cookies, Website-Daten, Berechtigungen und Cache. | Nicht explizit gesetzt. Wichtigste offene Profil-Stellschraube; ein Profilwechsel als aktuelle Fehlerursache ist **nicht** nachgewiesen. [Microsoft: User Data Folder](https://learn.microsoft.com/en-us/microsoft-edge/webview2/concepts/user-data-folder). |
| `-Dorg.eclipse.swt.browser.EdgeArgs=<Argumente>` | Gibt zusätzliche Browserargumente an WebView2 weiter. | Nicht gesetzt. Es gibt hier keinen allgemeinen Schalter für „alle Webfunktionen“. |
| `-Dorg.eclipse.swt.browser.EdgeLanguage=<Sprache>` | Sprache der WebView2-Umgebung. | Nicht gesetzt; kein belegter Fix für Cookies oder Drag-and-drop. |
| `-Dorg.eclipse.swt.browser.IEVersion=12001` | Konfiguration der alten IE-Engine, falls sie verwendet wird. | Im [Produkt](com.sebulli.fakturama.site/com.sebulli.fakturama.product#L416) gesetzt; verbessert WebView2 nicht. |

WebView2 berücksichtigt zusätzlich die Umgebungsvariablen `WEBVIEW2_BROWSER_EXECUTABLE_FOLDER`, `WEBVIEW2_USER_DATA_FOLDER`, `WEBVIEW2_ADDITIONAL_BROWSER_ARGUMENTS` und `WEBVIEW2_RELEASE_CHANNEL_PREFERENCE`. Sie können die beim Start übergebenen Werte überschreiben oder ergänzen und sind deshalb bei abweichendem Verhalten eines Windows-Rechners relevant. [Microsoft: WebView2-Overrides](https://learn.microsoft.com/en-us/microsoft-edge/webview2/reference/win32/webview2-idl?preserve-view=true&view=webview2-1.0.2420.47).

**Windows-10-Besonderheit:** Bei einer festen WebView2-Runtime ab Version 120 braucht eine nicht paketierte Win32-App passende App-Container-Leserechte auf dem Runtime-Verzeichnis. Das ist für die entpackte Demo-Runtime am Zielrechner zu prüfen. [Microsoft: Fixed Version](https://learn.microsoft.com/en-us/microsoft-edge/webview2/concepts/distribution#the-fixed-version-runtime-distribution-mode).

## Manjaro und Debian: GTK/WebKitGTK

| Option | Funktion | Projektstatus / Grenze |
| --- | --- | --- |
| `-Dorg.eclipse.swt.browser.DefaultType=webkit` | Wählt WebKit für `SWT.NONE`. | Unter Linux keine zusätzliche Chromium-Engine. Die Fakturama-Preference könnte WebKit auch explizit wählen. [SWT-Konstanten](https://help.eclipse.org/latest/topic/org.eclipse.platform.doc.isv/reference/api/org/eclipse/swt/SWT.html). |
| `GDK_BACKEND=x11` bzw. `GDK_BACKEND=wayland` | Wählt beim Start den GTK-Anzeigebackend. | Konkreter A/B-Test für Maus- und Drag-and-drop-Ereignisse, falls beide Backends in der Sitzung verfügbar sind; kein allgemeiner Fix. [GTK-3-Laufzeitoptionen](https://docs.gtk.org/gtk3/running.html). |
| `-Dorg.eclipse.swt.internal.webkitgtk.ignoretlserrors=true` | Ignoriert TLS-Zertifikatsfehler. | Über die Fakturama-Einstellung bereits schaltbar. Wird vor dem ersten Browser gesetzt und wirkt nach Neustart; keine Cookie-/DnD-Option. [`LifecycleManager`](com.sebulli.fakturama.rcp/src/main/java/com/sebulli/fakturama/LifecycleManager.java#L149-L157). |
| GTK-/WebKitGTK-/libsoup-Version | Bestimmt die verfügbaren nativen Funktionen. | Mindestabhängigkeiten sind nur im Manjaro-Paket deklariert; die tatsächlich geladenen Versionen auf Debian und Manjaro sind getrennt festzustellen. |

Im Bytecode des ausgelieferten Linux-SWT-Bundles 3.124 wurde `org.eclipse.swt.internal.webkitgtk.ignoretlserrors` als WebKitGTK-System-Property gefunden. `org.eclipse.swt.internal.webkitgtk.version` ist eine von SWT gesetzte Versionsangabe, **kein** Versionswahlschalter. Ein SWT-Schalter, der HTML-Drag-and-drop allgemein aktiviert oder ein frei wählbares dauerhaftes WebKitGTK-Profil setzt, wurde dort nicht gefunden. Native WebKitGTK-APIs wären eine separate Codeänderung.

## Weitere Browserfunktionen im Anwendungscode

Die [SWT-Browser-API](https://help.eclipse.org/latest/rtopic/org.eclipse.platform.doc.isv/reference/api/org/eclipse/swt/browser/Browser.html) stellt `getBrowserType()` für die tatsächlich verwendete Engine, `setJavascriptEnabled()`, Cookie-/Sitzungsfunktionen, Navigation und Listener für Authentifizierung, neue Fenster, Downloads und Standortwechsel bereit. Das sind Java-APIs, keine Startschalter. Die [BrowserFunction-API](https://help.eclipse.org/latest/rtopic/org.eclipse.platform.doc.isv/reference/api/org/eclipse/swt/browser/BrowserFunction.html) beschreibt die Grenzen der JavaScript-/Java-Brücke. Fakturama registriert seine `FKT`-Brücke im [`BrowserEditor`](com.sebulli.fakturama.rcp/src/main/java/com/sebulli/fakturama/parts/BrowserEditor.java#L307-L315).

Die erfolgreich getestete automatische Anmeldung und der wieder sichtbare Mapping-Vorschlag belegen diesen konkreten Ablauf im installierten Build. Sie beweisen keine vollständige Gleichheit aller Browserfunktionen auf Windows, Manjaro und Debian.

## Drag-and-drop ist gesondert zu prüfen

Die Stücklistentabelle enthält sowohl [Pointer-/Maus-Verarbeitung](../fakturama-tool/src/templates/bauteil_tabelle/index.html#L1300-L1410) als auch [natives HTML-Drag-and-drop](../fakturama-tool/src/templates/bauteil_tabelle/index.html#L1495-L1565). Die [Kategorieansicht](../fakturama-tool/src/templates/stammdaten/kategorien.html#L843-L849) beschreibt einen eigenen Pointer-Ersatz, weil das native Ziehen von Tabellenzeilen unzuverlässig war. Ohne Angabe der betroffenen Ansicht und des Ereignisablaufs ist nicht belegt, dass ein aktueller DnD-Ausfall vom eingebetteten Browser verursacht wird.

## Für eine belastbare Freigabe der drei Zielsysteme

1. Windows: tatsächlich verwendeten Browsertyp und Runtime-Version, `EdgeDir`, `EdgeDataDir` sowie `WEBVIEW2_*`-Overrides feststellen. Bei fester Runtime unter Windows 10 die dokumentierten Ordnerrechte prüfen.
2. Manjaro und Debian: tatsächlich geladene GTK-/WebKitGTK-Versionen feststellen und den konkreten DnD-Vorgang unter X11 und Wayland vergleichen, soweit beides verfügbar ist. Debian-Paketabhängigkeiten überprüfen.
3. Jede benötigte Funktion auf den unterstützten Kombinationen aus Betriebssystem, Runtime und Browser-Engine testen. Kein einzelner der aufgeführten Schalter garantiert vollständige Kompatibilität einer beliebigen Web-App.
