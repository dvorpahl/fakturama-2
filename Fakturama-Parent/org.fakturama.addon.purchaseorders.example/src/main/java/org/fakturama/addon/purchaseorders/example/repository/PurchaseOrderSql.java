package org.fakturama.addon.purchaseorders.example.repository;

final class PurchaseOrderSql {
    static final String SELECT_ORDERS = """
            SELECT
                ID,
                LIEFERANT,
                STATUS,
                ERSTELLT_AM,
                BESTELLT_AM,
                GEPLANTES_WE_DATUM,
                NOTIZ
            FROM PART_BESTELLUNG
            ORDER BY ERSTELLT_AM DESC, ID DESC
            """;

    static final String SELECT_ITEMS = """
            SELECT
                ID,
                FK_BESTELLUNG,
                FK_DOCUMENT,
                ITEMNUMBER,
                MENGE_BESTELLT,
                MENGE_EINGEGANGEN,
                ERSTELLT_AM
            FROM PART_BESTELLPOSITION
            ORDER BY FK_BESTELLUNG, ID
            """;

    private PurchaseOrderSql() {
    }
}
