/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 *
 * Copyright (C) 2026 The Fakturama Team
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.sebulli.fakturama.parts.widget;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Document;

/** Visual ordering and filtering used by {@link DocumentChainComposite}. */
final class DocumentChainSupport {

    private static final Map<BillingType, Integer> TYPE_ORDER = createTypeOrder();

    private DocumentChainSupport() {
    }

    static List<Document> prepareDocuments(final List<Document> documents, final Document activeDocument) {
        final List<Document> result = new ArrayList<>();
        if (documents != null) {
            result.addAll(documents);
        }
        if (activeDocument != null && result.stream().noneMatch(document -> sameDocument(document, activeDocument))) {
            result.add(activeDocument);
        }
        result.removeIf(document -> document == null || Boolean.TRUE.equals(document.getDeleted()) || !TYPE_ORDER.containsKey(document.getBillingType()));
        result.sort(documentComparator());
        return result;
    }

    private static Comparator<Document> documentComparator() {
        final Comparator<Date> dates = Comparator.nullsLast(Comparator.naturalOrder());
        return Comparator.comparingInt((Document document) -> TYPE_ORDER.get(document.getBillingType()))
                .thenComparing(Document::getDocumentDate, dates)
                .thenComparing(Document::getDateAdded, dates)
                .thenComparingLong(Document::getId);
    }

    private static boolean sameDocument(final Document first, final Document second) {
        return first == second || first != null && second != null && first.getId() > 0 && first.getId() == second.getId();
    }

    private static Map<BillingType, Integer> createTypeOrder() {
        final Map<BillingType, Integer> result = new EnumMap<>(BillingType.class);
        result.put(BillingType.OFFER, 0);
        result.put(BillingType.ORDER, 1);
        result.put(BillingType.CONFIRMATION, 2);
        result.put(BillingType.INVOICE, 3);
        result.put(BillingType.DELIVERY, 4);
        result.put(BillingType.CREDIT, 5);
        return result;
    }
}
