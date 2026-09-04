# Scope

Rechnungslegung und Anbindung an Shopsysteme

# Documentation / Contribute
https://fakturama.atlassian.net/wiki/display/FAK/Developer%27s+Corner


# Authors
* Ralf Heydenreich <ralf.heydenreich@fakturama.info>
* Gerd Schrick

# Plugin-/Addon-Erweiterbarkeit

Auf Basis einer statischen Analyse (siehe `research-summary.md`) wurden folgende Punkte umgesetzt:

* Klassenladefehler bei Import-/Export-Wizards aus Drittanbieter-Bundles behoben — Wizard-Erzeugung läuft nun über `IConfigurationElement.createExecutableExtension(...)` statt `Class.forName(...)` **
* Anschließende e4-Kontext-Injektion (`ContextInjectionFactory.inject(...)`) für extern erzeugte Wizards ergänzt **
* Beispiel-Addon `org.fakturama.addon.purchaseorders.example` inkl. eigenem p2-Feature als Referenzimplementierung für externe Drittanbieter-Bundles erstellt und dokumentiert **

Noch offen (siehe Priorisierung in `research-summary.md`):

* Stabile, separat versionierte Plugin-API/SPI (`org.fakturama.api` / `org.fakturama.spi`) statt breiter DAO-/UI-Exporte
* Migrations-Contributor-Mechanismus für plugin-eigene Datenbankschemata
* p2-Addon-Repository sowie Install-/Remove-Flow im Produkt (Repository derzeit `enabled="false"`)
* Interaktiver UI-Laufzeittest des Wizard-Pfads mit separat installiertem Addon

# Weitere Änderungen seit Version 2.2.0

Anhand der Commit-Historie des Release-Branchs `release/2.2.1` geprüft und im Code verifiziert:

## Performance & Datenzugriff
* Produkt-, Dokument-, Debitoren- und Kreditorenlisten laden nun serverseitig paginiert und sortiert statt aller Datensätze auf einmal (`findPage` in den jeweiligen DAOs, `PagedEntityEventList`, `ServerSortModel`) **
* Alle Listenansichten standardmäßig nach ID absteigend sortiert, neueste Einträge zuerst **
* Fix eines N+1-Query-Problems beim Öffnen der Dokumentketten-Breadcrumb (bis zu einem Einzel-Read pro Beleg der Transaktion) **

## Dokumentkette & Belegfluss
* Neuer Kostenvoranschlag-/Preceding-Offer-Flow: aus einem unbestätigten Auftrag lässt sich rückwirkend ein Angebot als Ursprung der Dokumentkette erzeugen **
* Kundenbezogene Duplizier-Modi für Angebote (gleicher Kunde/Revision, neuer Kunde, Blanko-Artikelkopie per Ctrl+Klick) **
* Fixes für veraltete Kunden-/Datumsreferenzen beim Duplizieren von Angeboten **
* Verbesserte Behandlung von Nummernkreis-Konflikten bei Belegnummern **
* Fix für fehlende Artikelnummer beim Duplizieren von Produkten **
* Fix für die Zahlungsübernahme bei Folgedokumenten **

## Lagerbestand
* Neues produktbezogenes Flag „Lagerbestand verwalten“ (`stockManaged`), um einzelne Produkte gezielt von der automatischen Bestandsführung auszunehmen, inkl. DB-Migration, CSV-Export und Tests **

## Datenbank
Neuer, umfangreicher Liquibase-Changelog `db.changelog-2.2.1.xml` (Model-Bundle), abgesichert mit `indexExists`/`columnExists`/`tableExists`-Preconditions, damit bereits manuell nachgezogene Instanzen nicht brechen:

* Rund 25 zuvor nur manuell auf der Produktivdatenbank vorhandene Performance-Indizes (u. a. auf `FKT_ADDRESS`, `FKT_CONTACT`, `FKT_DOCUMENT`, `FKT_DOCUMENTITEM`, `FKT_PRODUCT`, `FKT_DOCUMENTRECEIVER`) nachträglich in den Changelog aufgenommen, damit auch Neuinstallationen sie erhalten **
* Neuer Index für `FKT_DOCUMENT.TRANSACTIONID`/`BILLINGTYPE`, zuvor unindiziert trotz Nutzung in mehreren `DocumentsDAO`-Abfragen **
* Index-Bereinigung passend zum neuen serverseitigen Paging: doppelter `idx_product_itemnumber_only`-Index entfernt, die auf `DTYPE` verankerten (und dadurch wirkungslos gewordenen) Indizes `IDX_DOC_LIST_DATE`/`IDX_DOC_LIST_NAME` durch `DELETED`-verankerte Ersatzindizes ausgetauscht **
* Neue Spalte `FKT_PRODUCT.STOCKMANAGED` (NOT NULL, Default `true`), rückwirkend auf `false` gesetzt für Produkte ohne Bestandsmenge **
* `DELETED` (in rund 24 Tabellen) sowie `PRINTED`/`OPTIONAL`/`NOVAT` (`FKT_DOCUMENT`/`FKT_DOCUMENTITEM`) von nullable auf `NOT NULL DEFAULT FALSE` verschärft — verifiziert gegen den Live-Datenbestand, damit nachgelagerte Reporting-Abfragen ohne `COALESCE(...)`-Wrapper (nicht sargable) auskommen **
* Neue BI-/KPI-Views `VW_BI_DOCUMENT`, `VW_BI_DOCUMENT_ITEM`, `VW_BI_DOCUMENT_FLOW`, `VW_BI_DOCUMENT_RECEIVER_STATS`, `VW_BI_INVOICE`, `VW_KPI_AR_AGING`, `VW_KPI_AR_SUMMARY`, `VW_KPI_DOCUMENT_PIPELINE_MONTHLY`, `VW_KPI_INVENTORY`, `VW_KPI_INVOICE_ITEM_ORIGIN`, `VW_KPI_ITEM_ORIGIN`, `VW_KPI_MANUAL_ITEM_SALES`, `VW_KPI_PAYMENT_BEHAVIOR`, `VW_KPI_PRODUCT_SALES`, `VW_KPI_SALES_MONTHLY` für externes Reporting/BI-Tooling nachgezogen (MySQL/MariaDB-only, auf HSQLDB/H2 übersprungen) **
* Zusätzliche View `VW_PRODUCT_PICTURE` für ein externes Tool, nur angelegt, wenn dessen Tabelle `FKT_PRODUCTPICTURES` bereits existiert **

## Externe Tool-Integration
* Neue JavaScript-Bridge (`window.FKT`) im eingebetteten Browser-Widget: Umgebungsinfos, Öffnen von Produkten/Dokumenten, Benachrichtigung über externe Änderungen; mit Origin-Prüfung und Secret-basiertem Login-Handoff statt Klartext-Passwort **

## Build & Auslieferung
* Linux-Build nach Architektur aufgeteilt, Startfehler unter Linux behoben **
* ODFDOM-Abhängigkeiten auf offiziell veröffentlichte Versionen inkl. Sicherheitsupdates umgestellt **
* App-/Installer-Icons und About-Dialog-Grafiken erneuert, About-Dialog-Resizing behoben **

\*\* Stand: Version 2.2.1