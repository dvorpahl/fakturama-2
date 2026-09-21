package org.fakturama.addon.purchaseorders.example.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.fakturama.addon.purchaseorders.example.model.PurchaseOrder;
import org.fakturama.addon.purchaseorders.example.model.PurchaseOrderItem;
import org.fakturama.addon.purchaseorders.example.model.PurchaseOrderStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 * Reads add-on-owned tables through Fakturama's configured persistence unit.
 * Native projections are intentional: the core persistence.xml has a fixed entity set.
 */
public final class JpaPurchaseOrderRepository implements PurchaseOrderRepository {
    private final EntityManagerFactory entityManagerFactory;

    public JpaPurchaseOrderRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<PurchaseOrder> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Map<Long, OrderBuilder> orders = readOrders(entityManager);
            readItems(entityManager, orders);
            return orders.values().stream().map(OrderBuilder::build).toList();
        } finally {
            entityManager.close();
        }
    }

    @SuppressWarnings("unchecked")
    private Map<Long, OrderBuilder> readOrders(EntityManager entityManager) {
        List<Object[]> rows = entityManager.createNativeQuery(PurchaseOrderSql.SELECT_ORDERS).getResultList();
        Map<Long, OrderBuilder> orders = new LinkedHashMap<>();
        for (Object[] row : rows) {
            long id = longValue(row[0]);
            orders.put(id, new OrderBuilder(
                    id,
                    stringValue(row[1]),
                    PurchaseOrderStatus.fromDatabaseValue(row[2]),
                    localDate(row[3]),
                    localDate(row[4]),
                    localDate(row[5]),
                    stringValue(row[6])));
        }
        return orders;
    }

    @SuppressWarnings("unchecked")
    private void readItems(EntityManager entityManager, Map<Long, OrderBuilder> orders) {
        List<Object[]> rows = entityManager.createNativeQuery(PurchaseOrderSql.SELECT_ITEMS).getResultList();
        for (Object[] row : rows) {
            OrderBuilder order = orders.get(longValue(row[1]));
            if (order != null) {
                order.items.add(new PurchaseOrderItem(
                        longValue(row[0]),
                        nullableLong(row[2]),
                        String.valueOf(row[3]),
                        doubleValue(row[4]),
                        doubleValue(row[5]),
                        localDate(row[6])));
            }
        }
    }

    private static long longValue(Object value) {
        return ((Number) value).longValue();
    }

    private static Long nullableLong(Object value) {
        return value == null ? null : longValue(value);
    }

    private static double doubleValue(Object value) {
        return ((Number) value).doubleValue();
    }

    private static String stringValue(Object value) {
        return value == null ? null : String.valueOf(value);
    }

    private static LocalDate localDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDate date) {
            return date;
        }
        if (value instanceof Date date) {
            return date.toLocalDate();
        }
        return LocalDate.parse(String.valueOf(value));
    }

    private static final class OrderBuilder {
        private final long id;
        private final String supplier;
        private final PurchaseOrderStatus status;
        private final LocalDate createdOn;
        private final LocalDate orderedOn;
        private final LocalDate plannedReceiptOn;
        private final String note;
        private final List<PurchaseOrderItem> items = new ArrayList<>();

        private OrderBuilder(long id, String supplier, PurchaseOrderStatus status, LocalDate createdOn,
                LocalDate orderedOn, LocalDate plannedReceiptOn, String note) {
            this.id = id;
            this.supplier = supplier;
            this.status = status;
            this.createdOn = createdOn;
            this.orderedOn = orderedOn;
            this.plannedReceiptOn = plannedReceiptOn;
            this.note = note;
        }

        private PurchaseOrder build() {
            return new PurchaseOrder(id, supplier, status, createdOn, orderedOn, plannedReceiptOn, note, items);
        }
    }
}
