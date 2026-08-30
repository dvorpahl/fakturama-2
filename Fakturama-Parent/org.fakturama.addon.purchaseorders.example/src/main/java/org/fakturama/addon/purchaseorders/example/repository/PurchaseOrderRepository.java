package org.fakturama.addon.purchaseorders.example.repository;

import java.util.List;

import org.fakturama.addon.purchaseorders.example.model.PurchaseOrder;

/** Read-only boundary for purchase-order data owned by this add-on. */
public interface PurchaseOrderRepository {
    List<PurchaseOrder> findAll();
}
