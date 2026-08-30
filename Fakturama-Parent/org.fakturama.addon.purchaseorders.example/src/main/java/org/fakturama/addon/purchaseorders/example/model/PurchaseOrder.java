package org.fakturama.addon.purchaseorders.example.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/** Aggregate assembled from PART_BESTELLUNG and its PART_BESTELLPOSITION rows. */
public record PurchaseOrder(
        long id,
        String supplier,
        PurchaseOrderStatus status,
        LocalDate createdOn,
        LocalDate orderedOn,
        LocalDate plannedReceiptOn,
        String note,
        List<PurchaseOrderItem> items) {

    public PurchaseOrder {
        Objects.requireNonNull(status, "status");
        Objects.requireNonNull(createdOn, "createdOn");
        items = List.copyOf(items);
    }

    public double orderedQuantity() {
        return items.stream().mapToDouble(PurchaseOrderItem::orderedQuantity).sum();
    }

    public double receivedQuantity() {
        return items.stream().mapToDouble(PurchaseOrderItem::receivedQuantity).sum();
    }

    public boolean isOverdue(LocalDate today) {
        return plannedReceiptOn != null
                && status != PurchaseOrderStatus.COMPLETED
                && plannedReceiptOn.isBefore(today);
    }
}
