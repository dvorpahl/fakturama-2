# Analyse von Fakturama 2: Plugin-/Addon-Erweiterbarkeit und SQL-Fundstellen

Stand der statischen Analyse: 2026-08-30  
Untersuchter Stand: lokaler Arbeitsbaum in `/home/danilo/Workspace/fakturama-2` (einschließlich bereits vorhandener, nicht von dieser Analyse stammender Änderungen)

## Kurzfazit

Fakturama 2 besitzt mit Eclipse e4, Equinox/OSGi, Tycho, OSGi Declarative Services (DS), p2-Features und eigenen Extension Points bereits eine grundsätzlich sehr gute technische Basis für Plugins. Import-/Export-Wizards, PDF-Nachbearbeitung, Kontakt-Export, Datenbanktreiber, Formatierung, QR-Code-Erzeugung und weitere Dienste sind bereits in getrennten Bundles organisiert.

Die Erweiterbarkeit ist derzeit trotzdem nur **teilweise praxistauglich**:

- Eigene Import-/Export-Extension-Points sind vorhanden und werden über die Eclipse Extension Registry eingelesen.
- Zum Analysezeitpunkt umging die tatsächliche Wizard-Erzeugung `IConfigurationElement.createExecutableExtension(...)` und verwendete `Class.forName(...)` im Host-Bundle. Dadurch konnten Klassen aus eigenständigen Drittanbieter-Bundles normalerweise nicht geladen werden. Dieser Fehler ist inzwischen behoben; das vorhandene `org.fakturama.import.extra` hatte ihn als Host-Fragment verdeckt.
- Viele sinnvolle OSGi-Dienste existieren bereits, aber es fehlt eine kleine, stabile und ausdrücklich versionierte Plugin-API. Stattdessen exportiert insbesondere das große RCP-Bundle breite Implementierungs-Pakete, unter anderem konkrete DAOs und UI-Klassen.
- Das JPA-Modell und der Liquibase-Master-Changelog sind statisch. Für Plugins mit eigenen Entitäten oder Schemaänderungen gibt es keinen vorgesehenen Lifecycle-/Migration-Hook.
- p2 ist für Programmupdates enthalten; ein sichtbarer allgemeiner Installationsfluss bzw. ein Addon-Repository ist im aktuellen Application Model nicht vollständig verdrahtet. Das konfigurierte Repository ist im Produkt zudem deaktiviert.

**Gesamturteil:** Gute modulare Grundlage, aber vor einer öffentlichen Plugin-Schnittstelle sollten API-Abgrenzung, Lifecycle, Datenzugriff und Installation stabilisiert werden. Der zentrale Klassenladefehler des Wizard-Pfads ist inzwischen behoben; für unabhängig ausgelieferte Addons fehlen dennoch weitere stabile Verträge und ein vollständiger Installationsfluss.

### Umsetzungsstand nach der Analyse

Der kritischste Klassenladefehler wurde anschließend behoben:

- `ImportExportPage` erzeugt den ausgewählten Wizard nun über den zum Registry-Beitrag gehörenden `IE4WizardDescriptor`. Dieser ruft `IConfigurationElement.createExecutableExtension(...)` auf und verwendet damit den Klassenlader des beitragenden OSGi-Bundles.
- Danach injiziert `ContextInjectionFactory.inject(...)` den aktuellen e4-Kontext in das erzeugte Wizard-Objekt. Bestehende `@Inject`-Felder bleiben damit versorgt.
- Der frühere Umweg über `IFakturamaWizardService.createWizard(String)` und `Class.forName(...)` ist für diesen Laufzeitpfad entfallen. Die alte Service-Methode bleibt vorerst binär/API-kompatibel vorhanden, ist aber nicht mehr für die Auswahlseite zuständig.
- Verifiziert mit `mvn -pl org.fakturama.wizards -am -DskipTests package`: alle sechs betroffenen Reaktormodule erfolgreich gebaut. Ein echter Laufzeittest mit einem separat installierten Beispiel-Addon bleibt als nächster Integrationstest sinnvoll.

### Beispiel-Addon für Warenbestellungen

Als konkreter externer Beitrag wurde anschließend das Bundle `Fakturama-Parent/org.fakturama.addon.purchaseorders.example` samt eigenem Feature `org.fakturama.addon.purchaseorders.example.feature` erstellt:

- `plugin.xml` registriert `PurchaseOrdersWizard` am vorhandenen Extension Point `org.fakturama.wizards.importWizards`. Die Wizard-Klasse liegt damit tatsächlich in einem eigenständigen Anbieter-Bundle und nicht in einem Host-Fragment.
- Die unveränderlichen Objekte `PurchaseOrder`, `PurchaseOrderItem` und `PurchaseOrderStatus` bilden die in `../fakturama-tool/README.md:48-103` beschriebenen Tabellen `PART_BESTELLUNG` und `PART_BESTELLPOSITION` ab.
- `JpaPurchaseOrderRepository` liest beide Tabellen read-only über die von Fakturama konfigurierte `EntityManagerFactory`. Native Projektionen werden bewusst statt zusätzlicher JPA-Entities verwendet, weil das Entity-Set der Core-Persistence-Unit statisch ist.
- Der Wizard zeigt Kopf- und Summendaten sowie die Anzahl der Positionen an. Er schreibt nicht in die Tabellen und deutet Warenbestellungen nicht als Fakturama-Kundenaufträge um.
- Das Feature ist in `com.sebulli.fakturama.site/category.xml` der neuen Kategorie `Fakturama Add-ons` zugeordnet, wird aber absichtlich nicht automatisch in das Kernprodukt eingebaut.
- Build-Verifikation: `mvn -pl org.fakturama.addon.purchaseorders.example,org.fakturama.addon.purchaseorders.example.feature -am -DskipTests package` baute alle 24 benötigten Reaktormodule einschließlich Addon und Feature erfolgreich. Der Inhalt des erzeugten Bundle-JARs wurde kontrolliert; Java-Klassen, `plugin.xml`, Manifest und beide Lokalisierungen sind enthalten.
- Die vollständige Update-Site wurde anschließend mit `mvn -pl com.sebulli.fakturama.site -am -DskipTests package` erfolgreich über alle 28 Reaktormodule gebaut. Das p2-ZIP enthält das Addon-Bundle (17.840 Byte) und sein Feature; Tycho konnte außerdem das vorhandene Windows-Produkt materialisieren.

Die Bedienung und der Build sind zusätzlich in `Fakturama-Parent/org.fakturama.addon.purchaseorders.example/README.md` dokumentiert. Die SQL-Projektionen wurden außerdem read-only gegen die lokale MariaDB auf Port 3306 getestet: Im Schema `fakturama_test` wurden 5 Bestellungen und 22 Positionen gelesen; es gab keine verwaisten Positionen oder ungültigen Pflichtwerte. Für einen vollständigen UI-Laufzeittest muss das erzeugte Feature noch in einer gestarteten Fakturama-Installation geöffnet werden.

## Umfang und Vorgehen

Der Repository-weite statische Scan umfasste unter anderem:

- 690 Java-Dateien und 24 OSGi-Bundle-Manifeste;
- Maven-/Tycho-POMs, Features, Produkt- und p2-Site-Konfiguration;
- `plugin.xml`, `fragment.xml`, e4 Application Model und Extension-Point-Schemata;
- 21 DS-Komponentendateien unter `OSGI-INF`;
- JPA-Persistenzkonfiguration, DAO-Schicht und Alt-Datenmigration;
- Liquibase-Changelogs, eigenständige SQL-Skripte und CloverETL-Graphen (`*.grf`).

Gesucht wurde nach SQL-Schlüsselwörtern, JDBC-Aufrufen, JPQL-Strings, Criteria API, Liquibase-`sql`/`sqlCheck` sowie SQL-Dateien. Treffer in Kommentaren, UI-Texten oder fremden XML-Schemata wurden nicht als Abfrage gewertet. Die Analyse ist statisch; dynamisch zusammengesetzte Abfragen außerhalb der erkannten Muster könnten nur durch Laufzeit-Tracing vollständig ausgeschlossen werden.

## Architektur und vorhandene Erweiterungspunkte

### Build und Laufzeit

- Root-Aggregator: `pom.xml`, Version `2.2.0-SNAPSHOT`.
- Eclipse-RCP-Teil: `Fakturama-Parent/pom.xml`, Java 17, Tycho 4.0.8, EclipseLink 4.0.4.
- Das Produkt `Fakturama-Parent/com.sebulli.fakturama.site/com.sebulli.fakturama.product` ist feature-basiert und enthält Equinox, DS, p2, JPA/EclipseLink und die Fakturama-Features.
- Das Kern-Feature, Import-, Export- und ZUGFeRD-Feature zeigen, dass fachliche Funktionen bereits als separat installierbare Einheiten gedacht sind.

### Eigene deklarative Extension Points

| Fundort | Funktion | Bewertung |
|---|---|---|
| `Fakturama-Parent/org.fakturama.wizards/plugin.xml:4` | `org.fakturama.wizards.exportWizards` | Geeigneter deklarativer Einstieg für zusätzliche Export-Wizards. Schema mit Kategorie, Klasse, Icon und Beschreibung vorhanden. |
| `Fakturama-Parent/org.fakturama.wizards/plugin.xml:5` | `org.fakturama.wizards.importWizards` | Entsprechender Einstieg für Import-Wizards. |
| `Fakturama-Parent/org.fakturama.export/plugin.xml:4` | Registriert die eingebauten Exporter | Gutes Referenzbeispiel für Erweiterungsbeiträge. |
| `Fakturama-Parent/org.fakturama.import/plugin.xml:4` | Registriert die eingebauten Importer | Gutes Referenzbeispiel für Erweiterungsbeiträge. |
| `Fakturama-Parent/org.fakturama.import.extra/fragment.xml:4` | Fügt einen Order-Importer hinzu | Belegt Erweiterbarkeit, ist aber ein Host-Fragment und kein sauber entkoppeltes Drittanbieter-Bundle. |
| `Fakturama-Parent/org.fakturama.e4.ui.dialogs.ext/plugin.xml:4-5` | Extension Points für About-/Installationsseiten und Systeminformationen | Technisch erweiterbar, fachlich aber kein allgemeiner Addon-Einstieg. |
| Standard-Extension-Points in mehreren `plugin.xml` | Preferences, Help, e4 Workbench Model | Zusätzliche Einstellungsseiten, Hilfe und UI-Modellfragmente sind über Eclipse-Mechanismen prinzipiell möglich. |

Die Registries in `ImportWizardRegistry`, `ExportWizardRegistry`, `WizardsRegistryReader` und `WorkbenchWizardElement` lesen die Beiträge aus der Extension Registry korrekt. In `WorkbenchWizardElement.createWizard()` (`.../org.fakturama.wizards/.../WorkbenchWizardElement.java:254`) existiert bereits die richtige Erzeugung über `configurationElement.createExecutableExtension(...)`.

### OSGi-Dienste als weitere Addon-Punkte

Vorhandene, für Erweiterungen relevante Services sind insbesondere:

| Service/API | Anbieter und Fundort | Erweiterungspotenzial |
|---|---|---|
| `IPdfPostProcessor` | Mail-Service und ZUGFeRD-Exporter; DS-Dateien in `org.fakturama.connectors/OSGI-INF` und `org.fakturama.exporter.zugferd/OSGI-INF` | Sehr guter Whiteboard-Ansatz für weitere Signatur-, Archivierungs- oder E-Rechnungsprozessoren. |
| `IContactExporter` | `org.fakturama.export/...ContactDatasheetWriter.xml` | Austauschbarer Kontakt-Export; derzeit eher einzelner Dienst als eine dokumentierte Mehrfachanbieter-API. |
| `IFakturamaWizardService` | Import-/Export-Bundles | Interne Infrastruktur; sollte nicht der primäre Vertrag für Drittanbieter-Wizards sein. |
| `DataSourceFactory`, `IActivateDbServer`, `IDbConnection` | DB-Bundles und Model-Service | Datenbanktreiber sind sauber über OSGi auffindbar. |
| `IDbUpdateService`, `IDocumentAddressManager`, Formatter-, Locale-, Template-, QR- und Parcel-Services | Diverse `OSGI-INF/*.xml` | Gute Entkopplung; teils bereits brauchbare Plugin-APIs, aber ohne dokumentierte Stabilitäts-/Versionsgarantie. |

### Wesentliche Hindernisse

1. **Klassenladen der Wizard-Erweiterungen (Priorität P0, umgesetzt)**  
   Zum Analysezeitpunkt übergab `ImportExportPage.createWizardNode()` nur den Klassennamen an `FakturamaImportService` bzw. `FakturamaExportService`. Beide verwenden `Class.forName(className)` (`.../FakturamaImportService.java:78`, `.../FakturamaExportService.java:82`). Ein OSGi-Bundle kann fremde Bundle-Klassen nicht allein aufgrund eines Registry-Beitrags über seinen eigenen Klassenlader sehen.  
   **Umgesetzt:** `wizardElement.createWizard()` verwendet nun `IConfigurationElement.createExecutableExtension("class")`; anschließend führt die Auswahlseite `ContextInjectionFactory.inject(...)` mit dem aktuellen e4-Kontext aus. Die alten `Class.forName(...)`-Servicepfade bleiben nur aus Kompatibilitätsgründen bestehen und sollten bei einer späteren API-Bereinigung entfernt werden.

2. **Keine kleine, stabile Plugin-API (P0/P1)**  
   `com.sebulli.fakturama.rcp/META-INF/MANIFEST.MF` exportiert breite Pakete wie konkrete DAOs, Parts, Handler und Preferences. Plugins würden sich so leicht an interne Implementierung koppeln. Andere fachliche Bundles exportieren ihre eigentlichen Erweiterungsinterfaces teilweise gar nicht.  
   **Vorschlag:** Neues Bundle `org.fakturama.api` (oder mehrere schmale API-Bundles) mit semantisch versionierten Interfaces und DTOs. Implementierungspakete mit `x-internal:=true` markieren bzw. nicht exportieren. Für Service Provider eigene SPI-Pakete verwenden.

3. **Kein Plugin-Lifecycle und keine Capability-Metadaten (P1)**  
   Es fehlen ein einheitlicher Addon-Descriptor, Aktivierung/Deaktivierung, Kompatibilitätsbereich, Berechtigungsmodell und Diagnose.  
   **Vorschlag:** DS als bevorzugtes Modell beibehalten; einen `FakturamaAddon`-/`AddonDescriptor`-Service mit ID, API-Version, benötigten Capabilities und Health-Status definieren. Mehrfachdienste dynamisch mit `ServiceTracker` oder DS-References `0..n` konsumieren.

4. **Persistenz ist nicht erweiterbar (P0 für datenhaltende Addons)**  
   `com.sebulli.fakturama.model/META-INF/persistence.xml` enthält ein statisches Entity-Set. `db.changelog-master.xml` inkludiert fest nur 2.0, 2.1 und 2.2; `DbUpdateService` lädt diesen Changelog ausschließlich über den Resource Accessor des Model-Bundles.  
   **Vorschlag:** Core-Entitäten für Plugins nur über fachliche Repository-Services freigeben. Für plugin-eigene Daten bevorzugt ein eigenes Schema/Tabellenpräfix plus eigener versionierter Liquibase-Changelog. Ein `DatabaseMigrationContributor`-OSGi-Service sollte Changelog-Ressource, Plugin-ID, Version, DBMS-Unterstützung und Reihenfolge liefern. Alle Beiträge müssen vor Aktivierung des jeweiligen Addons transaktional und nachvollziehbar ausgeführt werden.

5. **Installation/Distribution ist nur teilweise vorhanden (P1)**  
   Das Produkt enthält p2-Core und einen Update-Handler, der nur `UpdateOperation` ausführt. Obwohl ein Text `Install New Software` existiert, wurde im Application Model kein dazugehöriger Install-Handler gefunden. Das Produkt-Repository in `com.sebulli.fakturama.product:411` ist `enabled="false"`; die Site-Kategorie veröffentlicht nur das Kernfeature.  
   **Vorschlag:** Signiertes offizielles Addon-p2-Repository, eigene Kategorie pro Addon, Install-/Remove-UI mit Neustart und Kompatibilitätsprüfung. Alternativ zunächst dokumentierte Installation signierter p2-Features; `dropins` nur als Entwicklerweg.

6. **UI-Erweiterungen sind möglich, aber nicht als Fakturama-API geführt (P2)**  
   Standard-e4-Modellfragmente können Menüs, Handler und Parts beitragen. Stabile Ziel-IDs und Platzhalter sind jedoch nicht dokumentiert. Persistierter Workbench-Zustand kann neue Fragmente zusätzlich erschweren.  
   **Vorschlag:** Bewusst definierte UI-Slots/Element-IDs, Beispiel-Fragment und Regeln für Modellmigration bzw. `clearPersistedState` bei inkompatiblen Modelländerungen.

## Empfohlenes Zielbild

Ein realistischer Ausbau in drei Stufen:

1. **Bestehenden Wizard-Pfad reparieren:** Registry-Konfiguration selbst instanziieren lassen, Integrationstest mit einem echten separaten Test-Bundle ergänzen.
2. **API/SPI konsolidieren:** `org.fakturama.api` für fachliche Lese-/Schreibdienste und DTOs; `org.fakturama.spi` für Wizard-, PDF-, Connector-, Exporter- und Migrationsbeiträge. Keine direkte DAO- oder `EntityManager`-Freigabe.
3. **Addon-Verteilung:** pro Addon ein OSGi-Bundle plus p2-Feature; signiertes Repository; Kompatibilitätsprüfung über OSGi-Versionen; Plugin-Diagnoseseite mit Status der DS-Komponenten und Migrationen.

Ein minimales eigenständiges Wizard-Addon sollte danach nur benötigen:

- `Require-Capability`/`Import-Package` gegen die versionierte Fakturama-API;
- einen Beitrag zu `org.fakturama.wizards.importWizards` oder `exportWizards`;
- eine Wizard-Klasse, die das jeweilige öffentliche Interface implementiert;
- optional DS-Services und einen eigenen Migration-Contributor;
- ein p2-Feature für Installation und Deinstallation.

## SQL- und Query-Inventar

### Einordnung

Im ursprünglichen Anwendungscode dominiert JPA Criteria API; dort wurde kein `createNativeQuery`, `@NamedNativeQuery` oder `@NamedQuery` gefunden. Direkte SQL-Ausführung im Java-Code war auf den Verbindungs-Ping und die Bildmigration beschränkt. Das danach erstellte Beispiel-Addon ergänzt bewusst zwei read-only Native Queries für seine eigenen Tabellen. Daneben gibt es JPQL-Strings, Liquibase-Roh-SQL, eigenständige Administrationsskripte und 30 `SELECT *`-Abfragen in historischen Migration-Graphen.

### Direkte JDBC-Abfragen im Java-Code

| Fundort | Query/Funktion | Verbesserungsvorschlag |
|---|---|---|
| `Fakturama-Parent/com.sebulli.fakturama.rcp/src/main/java/com/sebulli/fakturama/startup/InitialStartupDialog.java:326-334` | `/* ping */ select 1` testet die im Startdialog eingegebene Verbindung. | `Connection`, zwei zusätzliche Verbindungen für Metadaten und `Statement` werden nicht geschlossen. Eine einzige Connection und Try-with-resources verwenden; `Connection.isValid(timeout)` ist portabler als `SELECT 1`. Keine Passwörter protokollieren. |
| `org.fakturama.migtool/src/main/java/org/fakturama/migtool/MigrationWorker.java:305` | `SELECT id, picture FROM <table> WHERE picture IS NOT NULL`; liest BLOB-Bilder aus `FKT_DOCUMENTITEM` und `FKT_PRODUCT`. | Tabellenname ist verkettet. Die aktuellen Aufrufer sind Konstanten, trotzdem explizite Allowlist/Enum nutzen. `prepareStatement(sql, colNames)` ist für generierte Schlüssel gedacht und hier unnötig. `ResultSet` ebenfalls per Try-with-resources schließen und Fetch-Size/BLOB-Streaming für große Datenmengen erwägen. |
| `org.fakturama.migtool/src/main/java/org/fakturama/migtool/MigrationWorker.java:306-313` | `UPDATE <table> SET PICTURE=? WHERE ID=?`; kopiert jedes Bild in die Zieldatenbank. | Tabellen-Allowlist; Batches (`addBatch/executeBatch`) und sinnvolle Commit-Größe statt Commit/Log pro Datensatzgruppe. Bei Fehlern explizit rollbacken. |

### Native Queries im Beispiel-Addon

| Fundort | Query/Funktion | Verbesserungsvorschlag |
|---|---|---|
| `Fakturama-Parent/org.fakturama.addon.purchaseorders.example/src/main/java/org/fakturama/addon/purchaseorders/example/repository/PurchaseOrderSql.java:4-15` | Explizite Projektion aus `PART_BESTELLUNG`, sortiert nach Erstellungsdatum und ID. Erzeugt die Kopfdaten der `PurchaseOrder`-Objekte. | Für sehr große Bestellhistorien Status-/Datumsfilter und Pagination anbieten; die explizite Spaltenliste beibehalten. |
| `Fakturama-Parent/org.fakturama.addon.purchaseorders.example/src/main/java/org/fakturama/addon/purchaseorders/example/repository/PurchaseOrderSql.java:17-28` | Explizite Projektion aller Zeilen aus `PART_BESTELLPOSITION`, gruppierungsfreundlich nach Fremdschlüssel und ID sortiert. Erzeugt `PurchaseOrderItem` und ordnet sie über `FK_BESTELLUNG` zu. | Bei Pagination der Kopftabelle nur Positionen der geladenen Bestell-IDs abfragen. Eine leere/gechunkte Parameterliste DB-portabel behandeln. |

Beide Queries laufen read-only in einem kurzlebigen `EntityManager`, der garantiert geschlossen wird. Der Wizard lädt die Vorschau derzeit synchron; für produktive Datenmengen sollte der Repository-Aufruf als abbrechbarer Hintergrundjob laufen und die SWT-Tabelle anschließend auf dem UI-Thread aktualisieren.

### JPA Criteria API in der laufenden Anwendung

Die Criteria-Aufrufe sind keine SQL-Strings, erzeugen aber Datenbankqueries und gehören deshalb zum Inventar. Es wurden 24 DAO-Dateien mit etwa 74 Query-/Update-/Delete-Konstruktionen gefunden.

| Fundort | Funktionen/Zweck |
|---|---|
| `.../dao/AbstractDAO.java:208-498` | Generische Operationen `findAll`, `findByName`, `findOrCreate`, Query-by-example, `getCount`, Dublettenprüfung. |
| `.../dao/AbstractCategoriesDAO.java:41-80` | Kategoriepfad per Name auflösen und Kindkategorien prüfen. |
| `.../dao/ContactsDAO.java:41-157` | Kontakte listen, Altkontakt/Dubletten/Kontaktnummer finden. |
| `.../dao/DebitorsDAO.java:64-180` und `CreditorsDAO.java:41-99` | Debitoren/Kreditoren für Listen und Baumansicht, Nummern- und Kategoriesuche. |
| `.../dao/DocumentsDAO.java:75-713` | Dokumente suchen/listen, Buchungszeiträume, Webshop-Dubletten, Dokumenttypen, bezahlte Rechnungen, Mahnungen, Lieferungen, Referenzen/Transaktionen, Summen und Datumsbereiche; mehrere Bulk-Updates. |
| `.../dao/ProductsDAO.java:51-134` | Altprodukt, Kategorieanzahl, ausgewählte Produkte und Artikelnummer. |
| `.../dao/ExpendituresDAO.java:65-183` | Ausgabenlisten, Kontobuchungen, Sortierung, Zeitraum und Namensvorschläge. |
| `.../dao/ReceiptVouchersDAO.java:70-176` | Einnahmebelege, Kontobuchungen, Zeitraum und Namensvorschläge. |
| `.../dao/VatsDAO.java:90-150` | Altdatenabgleich, Kategorieanzahl, bevorzugte und steuerfreie Einträge. |
| `.../dao/ShippingsDAO.java:36-54`, `TextsDAO.java:38-41` | Altdatenabgleich bzw. Anzahl je Kategorie. |
| `.../dao/PaymentsDAO.java:41-45` | Zahlungsart aus Altdaten wiederfinden. |
| `.../dao/ItemAccountTypeDAO.java:53-152` | Kontotypen/Anreden suchen und zählen. |
| `.../dao/CEFACTCodeDAO.java:59-67` | CEFACT-Einheit nach sprachabhängiger Abkürzung suchen. |
| `.../dao/PropertiesDAO.java:39-125` | Alte und aktuelle Properties sowie Mapping-Spezifikationen suchen. |
| `.../dao/VoucherCategoriesDAO.java:53-105` | Belegkategorien und zuletzt verwendete Ausgabenkategorie. |
| `.../dao/WebshopDAO.java:63-128` | Status-Mappings eines Shops laden und alte Mappings per CriteriaDelete entfernen. |
| `.../dao/{Contact,Product,Shipping,Text,Vat,ItemListType}CategoriesDAO.java` | Beim Entfernen einer Kategorie referenzierende Entitäten per CriteriaUpdate auf die Elternkategorie umhängen. |

Verbesserungen für diese Schicht:

- Mehrere Methoden verwenden `getResultList().isEmpty()` für reine Existenzprüfungen (`AbstractCategoriesDAO.hasChildren`, `AbstractDAO.existsOther`); `SELECT COUNT`, `EXISTS` oder `setMaxResults(1)` reduziert Datenübertragung.
- Wiederkehrende Filter wie `deleted = false`, Datumsspannen und Kategorien sollten als kleine Specifications/Query-Objekte zentralisiert werden.
- `DocumentsDAO.executeCriteria()` schluckt `PersistenceException` nach Rollback. Fehler weiterwerfen oder mindestens strukturiert loggen; sonst kann der Aufrufer Erfolg annehmen.
- `WebshopDAO.clearOldMappings()` rollt bei Laufzeit-/Persistence-Fehlern nicht sicher zurück und behandelt nur `SQLException`. Einheitlichen Transaction-Helper verwenden.
- `AbstractDAO` hält einen langlebigen, nicht threadsicheren `EntityManager` im DAO. Für dynamische Plugins sind kurzlebige, transaktionsgebundene Repository-Aufrufe robuster als das Exportieren konkreter DAOs.
- `IN (...)` mit beliebig großen ID-Listen (`ProductsDAO`, `DocumentsDAO`) sollte geleerte Listen explizit behandeln und bei großen Mengen je nach DB-Limit partitioniert werden.

### JPQL-Strings

| Fundort | Funktion | Verbesserungsvorschlag |
|---|---|---|
| `.../dao/DebitorsDAO.java:180` | Liefert unterschiedliche, nicht gelöschte Debitor-Kategorien. | Typsicheres Criteria wie in der übrigen DAO-Schicht oder eine benannte Repository-Methode; kein Injection-Risiko, da keine Stringparameter eingefügt werden. |
| `.../dao/DocumentsDAO.java:206` | Ermittelt die tatsächlich verwendeten Dokument-Unterklassen mittels `type(d)`. | Fachlich sinnvoll; typisierte Query/Criteria `root.type()` bevorzugen. |
| `.../dao/VoucherCategoriesDAO.java:56` | Auskommentiertes altes `select p from VoucherCategory p`. | Toten Kommentar entfernen. |
| `.../migration/olddao/OldEntitiesDAO.java:83-378` | 34 aktive JPQL-Strings für Counts, Cursor-Vollscans, Einzelobjekte und unterschiedliche Kategorien der alten Tabellen: Kontakte (83-109), Properties (116-135), Versand (141-156), USt. (165-180), Konten (205-206), Texte (212-223), Dokumente (228-247), Zahlarten (273-288), Ausgaben (297-308), Einnahmen (323-345), Produkte (361-378). | Nur für einmalige Alt-Datenmigration. Wiederholte Muster in generische Loader/Count-/Category-Helfer zusammenfassen. Festwerte sind sicher; falls Filter dynamisch werden, ausschließlich benannte Parameter verwenden. Cursor und EntityManager garantiert schließen. |

### Liquibase-Changelogs

| Fundort | Funktion der SQL-Stellen | Verbesserungsvorschlag |
|---|---|---|
| `.../changelog/db.changelog-master.xml:8-10` | Bindet die Versions-Changelogs 2.0, 2.1 und 2.2 ein. | Für Addons einen OSGi-Migrations-Contributor ergänzen; bestehende ausgeführte Changesets nie nachträglich ändern. |
| `.../db.changelog-2.0.xml:949` | Übernimmt den Voucher-Typ aus `FKT_VOUCHERS` in `FKT_VOUCHERITEMS`. | DBMS-übergreifend testen; korrelierte Updates können teuer sein, Indizes auf `FK_VOUCHER`/`ID` sicherstellen. |
| `.../db.changelog-2.0.xml:971-979` | Temporäre Kontakttabelle und Korrektur von `CONTACTTYPE`. | Derselbe Fix erscheint erneut in 2.1. Historie nicht ändern, aber Regressionstest und Kommentar zur Duplizierung ergänzen. |
| `.../db.changelog-2.1.xml:95-103` | Wiederholt die `CONTACTTYPE`-Korrektur. | Siehe oben; Preconditions sollten die tatsächliche Notwendigkeit prüfen. |
| `.../db.changelog-2.1.xml:382-1243` | Große Kontakt-/Adressmigration: Ausgangsdaten in temporäre Tabellen kopieren, Rechnungs- und Lieferadressen DBMS-spezifisch zusammenführen, neue Kontakte/Adressen/Adressarten und Dokumentempfänger erzeugen. Roh-SQL-Blöcke beginnen bei 382, 501, 616, 649, 755, 791, 861, 904, 910, 918, 963, 1029, 1100 und 1174. | Sehr risikoreicher Block: Integrationstests mindestens für HSQLDB, MySQL und MariaDB; Row-count-/Null-/Eindeutigkeits-Preconditions und Rollback-/Backup-Hinweis ergänzen. Wo möglich Liquibase-Tags statt Roh-SQL; DBMS-Listen konsistent schreiben (`h2,hsqldb`, nicht teils `h2, hsqldb`). Temporärtabellen eindeutig pro Migration benennen und nach Erfolg entfernen. |
| `.../db.changelog-2.1.xml:1510-1513` | Ergänzt Namen und lokalen Ansprechpartner von Lieferadressen aus der temporären Migrationstabelle. | Abhängigkeit von einer dauerhaft stehen gelassenen Temp-Tabelle dokumentieren/auflösen; korreliertes Update und Mehrfachtreffer testen. |
| `.../db.changelog-2.2.xml:9-11` | `SELECT COUNT(*)` als Precondition vor Aufteilung der OpenOffice-Einstellung. | `COUNT(*) = 1` markiert bei mehreren passenden Altzeilen den Changeset als ausgeführt, ohne zu migrieren. Besser `expectedResult` mit sauberer Eindeutigkeitsregel oder eine `EXISTS`-/deduplizierende Migration. |
| `.../db.changelog-2.2.xml:13-56` | Liquibase-Insert/Delete/Update und Spaltenergänzungen für OpenOffice, Payment-Code und VAT-Code. | Deklarative Liquibase-Tags sind gegenüber Roh-SQL vorzuziehen; für neue NOT-NULL-Anforderungen erst Backfill, dann Constraint in separatem Changeset. |

### Eigenständige SQL-Skripte im Migrationstool

| Fundort | Funktion | Verbesserungsvorschlag |
|---|---|---|
| `org.fakturama.migtool/targetdb-COMPLETE.sql:1-113` | Vollständiger MySQL/MariaDB-Schema-Snapshot: 78 aktive DDL-Statements für Tabellen, Indizes und Foreign Keys. | Als generiertes Artefakt kennzeichnen und aus dem Liquibase-Master reproduzierbar erzeugen; sonst droht Drift. Zeichensatz `utf8` ist bei MySQL nicht volles UTF-8: `utf8mb4` plus aktuelle Collation erwägen. |
| `org.fakturama.migtool/cleanup_targetdb.sql:1-79` | Entfernt Foreign Keys und löscht anschließend Daten aus 35 Tabellen; 77 aktive Statements. | Destruktives Admin-Skript: Transaktion/Backup-Anweisung, Ziel-DB-Prüfung und deutlich sichtbare Schutzschranke ergänzen. Für reine Leerung DB-spezifisch `TRUNCATE` nur nach bewusster FK-Behandlung; nicht automatisch in Produktion ausführen. |
| `org.fakturama.migtool/enable_fk.sql:1-41` | Legt 41 Foreign Keys nach der Datenmigration wieder an. | Möglichst aus Metadaten/Liquibase generieren statt parallel zu Changelogs pflegen; vorab Orphan-Checks ausführen. |

### SQL in CloverETL-Migrationsgraphen

Alle folgenden Queries sind `SELECT *` und dienen dem vollständigen Transfer der jeweiligen Tabellen:

| Fundort | Tabellen/Funktion |
|---|---|
| `org.fakturama.migtool/graph/migration.grf:191-248` | `FKT_CATEGORY`, `FKT_VAT`, `FKT_PRODUCT`, `FKT_ITEMACCOUNTTYPE`, `FKT_PRODUCTBLOCKPRICE`, `FKT_ADDRESS`, `FKT_BANKACCOUNT`, `FKT_CEFACTCODE`, `FKT_PAYMENT`. |
| `org.fakturama.migtool/graph/migration_002.grf:314-370` | `FKT_CONTACT`, `FKT_VOUCHERITEMS`, `FKT_VOUCHERS`, `FKT_WEBSHOPSTATEMAPPING`, `FKT_INDIVIDUALDOCUMENTINFO`, `FKT_DOCUMENT`, `FKT_DOCUMENTITEM`, `FKT_DELIVERY`, `FKT_INVOICE`, `FKT_USERPROPERTY`. |
| `org.fakturama.migtool/graph/migration_003.grf:77-120` | `FKT_CONFIRMATION`, `FKT_CREDIT`, `FKT_DUNNING`, `FKT_LETTER`, `FKT_OFFER`, `FKT_ORDER`, `FKT_PROFORMA`, `FKT_TEXTMODULE`, `FKT_SHIPPING`. |
| `org.fakturama.migtool/graph/migration_004.grf:71-76` | `FKT_ADDRESS_CONTACTTYPES`, `FKT_DOCUMENTRECEIVER`. |

Verbesserung: explizite Spaltenlisten statt `SELECT *`. Das verhindert stille Fehlzuordnung bei Schemaänderungen, reduziert unnötige Daten und macht die Migration reviewbar. Für große Tabellen Paging/Streaming und konsistente Snapshot-Isolation verwenden. Die Graphen scheinen gegenüber dem neueren Liquibase-basierten `MigrationWorker` historisch zu sein; Status (aktiv/veraltet) dokumentieren oder veraltete Pfade archivieren.

## Priorisierte nächste Schritte

| Priorität | Maßnahme | Nutzen |
|---|---|---|
| P0 | **Umgesetzt:** Wizard-Erzeugung auf `IConfigurationElement.createExecutableExtension` umstellen. Noch offen: Laufzeittest mit separatem Bundle. | Macht den bereits versprochenen Extension Point tatsächlich drittanbieterfähig. |
| P0 | Stabile API/SPI-Bundles definieren; DAO-/UI-Implementierung nicht als Vertrag behandeln | Verhindert fragile Plugins und erlaubt kompatible Weiterentwicklung. |
| P0 | Migrationsbeitrag für plugin-eigene Daten definieren | Verhindert unkontrollierte SQL-Skripte und Schema-Kollisionen. |
| P1 | Bestehende Services als dynamische `0..n`-Whiteboard-Services konsolidieren | Ermöglicht mehrere parallele Addons, Prioritäten und sauberes Entfernen. |
| P1 | p2-Addon-Repository und Install-/Remove-Flow ergänzen | Macht Erweiterungen für Anwender installierbar und aktualisierbar. |
| P1 | SQL-/Migrationstests je unterstütztem DBMS sowie Connection-/Transaction-Fixes | Reduziert Datenverlust- und Portabilitätsrisiko. |
| P2 | SDK-Dokumentation, Beispiel-Addon und Kompatibilitätsmatrix | Senkt Einstiegskosten für externe Entwickler. |

## Verifikation dieser Analyse

- Neben dieser Zusammenfassungsdatei wurde `ImportExportPage.java` geändert, um externe Wizard-Klassen OSGi-konform zu erzeugen und anschließend per e4 zu injizieren.
- Bestehende Änderungen an `Fakturama-Parent/com.sebulli.fakturama.feature/feature.xml`, `Fakturama-Parent/org.fakturama.target/org.fakturama.target.target` und der unversionierte Pfad `Fakturama-Parent/~/` wurden nicht verändert.
- Der gezielte Tycho-Build `mvn -pl org.fakturama.wizards -am -DskipTests package` war erfolgreich. Die Target-Auflösung meldete Warnungen zu nicht benötigten optionalen Sprach-/Test-Bundles, aber keine Kompilierungs- oder Packaging-Fehler.
- Auch die gezielten Addon-/Feature-Builds und der vollständige p2-Site-Build waren erfolgreich. Das auslieferbare Repository liegt unter `Fakturama-Parent/com.sebulli.fakturama.site/target/com.sebulli.fakturama.site-2.2.0-SNAPSHOT.zip`.
- Die beiden nativen Addon-Projektionen und zusätzliche Konsistenzabfragen liefen erfolgreich und ausschließlich lesend gegen `fakturama_test` auf der lokalen MariaDB: 5 Bestellungen, 22 Positionen, 0 verwaiste Positionen und 0 ungültige Pflichtwerte. Die vorhandenen Statuswerte `offen`, `teilgeliefert` und `abgeschlossen` werden vom Addon unterstützt.
- Nicht durchgeführt wurde nur der interaktive UI-Laufzeittest: Die vorhandene Target-/Produktkonfiguration materialisiert Windows, während diese Analyseumgebung Linux nutzt. Das ist keine Einschränkung des Datenbankzugriffs; die lokale Datenbank wurde wie oben beschrieben erfolgreich geprüft und nicht verändert.
- Vor weiteren Persistenzänderungen sollten zusätzlich DAO-Tests und je ein Integrationstest mit HSQLDB und MySQL/MariaDB laufen.
