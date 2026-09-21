package org.fakturama.addon.purchaseorders.example.model;

import java.util.Locale;

/** Status values used by fakturama-tool's PART_BESTELLUNG table. */
public enum PurchaseOrderStatus {
    OPEN("offen"),
    ORDERED("bestellt"),
    PARTIALLY_DELIVERED("teilgeliefert"),
    COMPLETED("abgeschlossen");

    private final String databaseValue;

    PurchaseOrderStatus(String databaseValue) {
        this.databaseValue = databaseValue;
    }

    public String databaseValue() {
        return databaseValue;
    }

    public static PurchaseOrderStatus fromDatabaseValue(Object value) {
        String normalized = String.valueOf(value).toLowerCase(Locale.ROOT);
        for (PurchaseOrderStatus status : values()) {
            if (status.databaseValue.equals(normalized)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown purchase-order status: " + value);
    }
}
