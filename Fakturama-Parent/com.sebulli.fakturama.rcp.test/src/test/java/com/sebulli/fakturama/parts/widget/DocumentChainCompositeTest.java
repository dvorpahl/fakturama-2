package com.sebulli.fakturama.parts.widget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.FakturamaModelPackage;

class DocumentChainCompositeTest {

    @Test
    void filtersDeletedAndUnsupportedDocumentsAndUsesBusinessOrder() {
        final Document credit = document(BillingType.CREDIT, 5, 500);
        final Document delivery = document(BillingType.DELIVERY, 4, 400);
        final Document invoice = document(BillingType.INVOICE, 3, 300);
        final Document deletedInvoice = document(BillingType.INVOICE, 6, 600);
        deletedInvoice.setDeleted(Boolean.TRUE);
        final Document order = document(BillingType.ORDER, 2, 200);
        final Document offer = document(BillingType.OFFER, 1, 100);
        final Document confirmation = document(BillingType.CONFIRMATION, 7, 700);

        final List<Document> result = DocumentChainSupport.prepareDocuments(
                List.of(credit, deletedInvoice, delivery, confirmation, invoice, order, offer), invoice);

        assertEquals(List.of(offer, order, invoice, delivery, credit), result);
    }

    @Test
    void keepsAllDocumentsOfOneTypeAndSortsByDatesThenId() {
        final Document laterDocumentDate = document(BillingType.INVOICE, 30, 300);
        final Document earlierDateLaterAdded = document(BillingType.INVOICE, 20, 100);
        earlierDateLaterAdded.setDateAdded(new Date(300));
        final Document earlierDateEarlierAddedHigherId = document(BillingType.INVOICE, 11, 100);
        earlierDateEarlierAddedHigherId.setDateAdded(new Date(200));
        final Document earlierDateEarlierAddedLowerId = document(BillingType.INVOICE, 10, 100);
        earlierDateEarlierAddedLowerId.setDateAdded(new Date(200));

        final List<Document> result = DocumentChainSupport.prepareDocuments(
                List.of(laterDocumentDate, earlierDateLaterAdded, earlierDateEarlierAddedHigherId, earlierDateEarlierAddedLowerId), laterDocumentDate);

        assertEquals(List.of(earlierDateEarlierAddedLowerId, earlierDateEarlierAddedHigherId, earlierDateLaterAdded, laterDocumentDate), result);
    }

    @Test
    void addsCurrentDocumentWhenTransactionLookupHasNoResult() {
        final Document current = document(BillingType.ORDER, 0, 100);

        final List<Document> result = DocumentChainSupport.prepareDocuments(null, current);

        assertEquals(1, result.size());
        assertSame(current, result.get(0));
    }

    @Test
    void doesNotDuplicateCurrentDocumentReturnedAsAnotherManagedInstance() {
        final Document fromTransaction = document(BillingType.ORDER, 42, 100);
        final Document current = document(BillingType.ORDER, 42, 100);

        final List<Document> result = DocumentChainSupport.prepareDocuments(List.of(fromTransaction), current);

        assertEquals(List.of(fromTransaction), result);
    }

    private static Document document(final BillingType billingType, final long id, final long documentDate) {
        final Document document;
        switch (billingType) {
            case OFFER:
                document = FakturamaModelPackage.MODELFACTORY.createOffer();
                break;
            case ORDER:
                document = FakturamaModelPackage.MODELFACTORY.createOrder();
                break;
            case INVOICE:
                document = FakturamaModelPackage.MODELFACTORY.createInvoice();
                break;
            case DELIVERY:
                document = FakturamaModelPackage.MODELFACTORY.createDelivery();
                break;
            case CREDIT:
                document = FakturamaModelPackage.MODELFACTORY.createCredit();
                break;
            case CONFIRMATION:
                document = FakturamaModelPackage.MODELFACTORY.createConfirmation();
                break;
            default:
                throw new IllegalArgumentException(billingType.toString());
        }
        document.setBillingType(billingType);
        document.setId(id);
        document.setDocumentDate(new Date(documentDate));
        document.setDeleted(Boolean.FALSE);
        return document;
    }
}
