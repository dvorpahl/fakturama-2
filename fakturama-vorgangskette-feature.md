# Implementierungsauftrag: Vorgangskette (Belegkette) im DocumentEditor anzeigen

## Kontext / Motivation

`Document.sourceDocument` (Spalte `FKT_DOCUMENT.FK_SRCDOCUMENT`, `@ManyToOne` in
`Document.java`) verweist bereits seit jeher auf das Dokument, aus dem das aktuelle
entstanden ist (Angebot → Auftragsbestätigung → Lieferschein → Rechnung → Gutschrift, in
welcher Reihenfolge auch immer die tatsächlich angelegt wurden). **Diese Beziehung wird
aber nirgends in der Desktop-UI angezeigt** - geprüft: `FK_SRCDOCUMENT`/`sourceDocument`
taucht im gesamten Java-Quellcode nur in der Modell-Klasse selbst auf, in keinem View,
keinem Editor, keinem Handler. Ein Bediener, der z.B. eine Rechnung offen hat und wissen
will "aus welchem Auftrag kam die?" oder "gibt es dazu schon einen Lieferschein?", muss
manuell suchen.

Der GEKO-Webserver (separates, parallel entwickeltes Web-Tool über dieselbe Datenbank,
`fakturama-tool`-Repo) hat genau das bereits gebaut und produktiv im Einsatz - als
Vorlage/Referenzimplementierung für dieses Ticket. Dort live: eine horizontale Kette aus
"Chips" (Angebot → Auftrag → Lieferschein → Rechnung, ...) oben auf der Beleg-Detailseite,
aktuelles Dokument hervorgehoben, andere Kettenglieder anklickbar.

## Datenlogik (verifiziert, direkt aus der Referenzimplementierung übernehmbar)

Referenz: `fakturama-tool/src/queries/angebote_auftraege_document_chain.sql` (SQL-CTE,
weil das Web-Tool ohne ORM arbeitet - in Java mit den bereits vorhandenen JPA-Relationen
sogar einfacher umsetzbar als dort).

Algorithmus, zweiphasig:

1. **Wurzel finden**: vom aktuell geöffneten Dokument aus wiederholt
   `document.getSourceDocument()` folgen, bis `null` erreicht ist. Das ist die Wurzel der
   Kette (meist das ursprüngliche Angebot, kann aber auch ein Auftrag ohne Angebot sein).
2. **Ganzen Familienbaum einsammeln**: von der Wurzel aus alle Dokumente sammeln, deren
   `sourceDocument` (transitiv) auf ein bereits eingesammeltes Dokument zeigt. **Wichtig:**
   es ist ein Baum, keine feste lineare Kette - ein Lieferschein kann sowohl eine Rechnung
   als auch (unabhängig) eine Gutschrift auslösen, und Lieferschein/Rechnung können sich
   in beiden Reihenfolgen gegenseitig auslösen. Nie eine feste Stufenfolge (Angebot →
   Auftrag → Lieferschein → Rechnung) annehmen, sondern dem echten `sourceDocument`-Graph
   folgen.
3. **Sortierung für die Anzeige**: chronologisch nach `documentDate` (Fallback: `id`) -
   ergibt eine sinnvolle Lese-Reihenfolge auch wenn der Graph technisch kein linearer Pfad
   ist.
4. **Dokumenttyp pro Kettenglied**: in der SQL-Referenz braucht das sechs `LEFT JOIN`s
   gegen die Joined-Table-Vererbungs-Tabellen (`FKT_OFFER`/`FKT_ORDER`/
   `FKT_CONFIRMATION`/`FKT_DELIVERY`/`FKT_INVOICE`/`FKT_CREDIT`), weil dort keine echte
   Polymorphie existiert. **In Java entfällt das** - `Document` ist über JPA
   `InheritanceType.JOINED` polymorph, der tatsächliche Laufzeittyp (`Offer`, `Order`,
   `Confirmation`, `Delivery`, `Invoice`, `Credit`) liefert direkt den Anzeigenamen (z.B.
   über `instanceof`-Prüfungen oder eine kleine `Map<Class<?>, String>`), keine
   zusätzliche Abfrage nötig.
5. **Nur anzeigen, wenn die Familie mehr als 1 Mitglied hat** - ein isoliertes Dokument
   ohne jede Verknüpfung soll keine leere/eingliedrige Kette zeigen.

### Vorgeschlagene neue Methode

`DocumentsDAO.java` (bestehende Klasse, `com.sebulli.fakturama.rcp/.../dao/
DocumentsDAO.java`, hat bereits diverse `findX(...)`-Methoden als Vorbild):

```java
/**
 * Liefert die vollständige Vorgangskette (Familie) eines Dokuments: von der Wurzel
 * (kein sourceDocument mehr) bis zu allen direkt und indirekt daraus abgeleiteten
 * Dokumenten, chronologisch sortiert. Leere/einelementige Liste, wenn das Dokument
 * isoliert ist.
 */
public List<Document> findDocumentFamily(Document document) {
    // Phase 1: zur Wurzel hochlaufen
    Document root = document;
    while (root.getSourceDocument() != null) {
        root = root.getSourceDocument();
    }

    // Phase 2: von der Wurzel abwärts den ganzen Baum einsammeln (BFS/DFS über
    // "findByFieldName(source, root)"-artige Query, oder eine kleine rekursive
    // Helper-Methode mit einer WHERE FK_SRCDOCUMENT IN (...)-Abfrage je Ebene)
    ...

    family.sort(Comparator.comparing(Document::getDocumentDate,
            Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(Document::getId));
    return family;
}
```

## UI / Darstellung

Referenz: `fakturama-tool/src/templates/angebote_auftraege/detail.html` (Zeilen ~67-102)
+ CSS in `fakturama-tool/src/templates/header.html` (`.chain-chip*`, Zeilen ~710-753).

- Horizontale Reihe aus "Chips", getrennt durch kleine Rechts-Pfeile (►).
- Jeder Chip: kleines, fett/uppercase Typ-Label oben (ANGEBOT/AUFTRAG/LIEFERSCHEIN/...),
  darunter die Belegnummer.
- Chip des **aktuell geöffneten** Dokuments: hervorgehoben (Rahmen + Hintergrundfarbe in
  der Akzentfarbe der Anwendung).
- Chips **anderer** Dokumente: klickbar, öffnen dieses Dokument in seinem eigenen Editor.
- Einbauort: oben im `DocumentEditor` (SWT-Composite), wo aktuell schon
  Auftragsnummer/Status stehen - vor den eigentlichen Formularfeldern.

### Klick-Navigation

`com.sebulli.fakturama.handlers.CallEditor` (bereits vorhandener Handler, öffnet
Dokument-Editoren über `@Named(PARAM_EDITOR_TYPE)` + `@Named(PARAM_OBJ_ID)`, siehe
`CallEditor.java:173-183`) ist genau der bestehende Mechanismus dafür - ein Chip-Klick
baut einen `ParameterizedCommand` mit der ID des Zieldokuments und führt ihn über
`commandService`/`handlerService` aus, exakt wie an anderer Stelle im Editor schon für
das Öffnen von Dokumenten verwendet. Keine neue Editor-Öffnen-Logik nötig.

## Aufwand / Schritte

1. `DocumentsDAO.findDocumentFamily(Document)` implementieren (s.o.).
2. Kleine SWT-Composite (`DocumentChainComposite` o.ä.) für die Chip-Reihe, oder inline in
   `DocumentEditor.java` (siehe `createFormContent()`/vergleichbare Methode, wo aktuell
   Kopfzeilen-Felder aufgebaut werden).
3. Chip-Klick → `CallEditor` über einen `ParameterizedCommand` aufrufen (Zieldokument-ID +
   Typ als Parameter, siehe oben).
4. Composite nur einblenden, wenn `findDocumentFamily(document).size() > 1`.
5. Lokalisierung: neue Typ-Label-Strings (ANGEBOT/AUFTRAG/...) existieren vermutlich schon
   als Konstanten irgendwo (Dokumenttyp-Anzeigenamen werden an vielen Stellen im Code
   gebraucht) - dort wiederverwenden statt neu zu übersetzen.

## Nicht Teil dieses Tickets

- Keine Änderung an der Datenbank/dem Modell - `FK_SRCDOCUMENT` existiert bereits
  vollständig und wird bereits beim Anlegen von Folgedokumenten korrekt gesetzt
  (verifiziert an echten Produktivdaten über den GEKO-Webserver).
- Keine Vereinheitlichung mit dem Web-Tool - beide Anzeigen bleiben unabhängige
  Implementierungen derselben, bereits vorhandenen Datenbeziehung.
