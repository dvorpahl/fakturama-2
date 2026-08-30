package org.fakturama.addon.purchaseorders.example.model;

import java.time.LocalDate;
import java.util.Objects;

/** One row from PART_BESTELLPOSITION. */
public record PurchaseOrderItem(
        long id,
        Long documentId,
        String itemNumber,
        double orderedQuantity,
        double receivedQuantity,
        LocalDate createdOn) {

    public PurchaseOrderItem {
        Objects.requireNonNull(itemNumber, "itemNumber");
        Objects.requireNonNull(createdOn, "createdOn");
    }

    public double outstandingQuantity() {
        return Math.max(0.0, orderedQuantity - receivedQuantity);
    }

    public boolean isComplete() {
        return receivedQuantity >= orderedQuantity;
    }
}
