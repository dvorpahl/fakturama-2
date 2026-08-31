package com.sebulli.fakturama.calculate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.FakturamaModelPackage;
import com.sebulli.fakturama.model.Invoice;

class CustomerStatisticsTest {

    @Test
    void separatesPaidAndOpenInvoicesAndUsesOutstandingMonetaryValue() throws Exception {
        final Contact contact = FakturamaModelPackage.MODELFACTORY.createDebitor();
        contact.setId(42);
        final Invoice newestPaid = invoice(true, 1200.0, 1200.0, 2025, Calendar.AUGUST, 19);
        final Invoice olderPaid = invoice(true, 500.0, 500.0, 2024, Calendar.JANUARY, 10);
        final Invoice partiallyPaid = invoice(false, 900.0, 300.0, 2025, Calendar.JULY, 1);
        final Invoice unpaid = invoice(null, 250.0, 0.0, 2025, Calendar.JUNE, 1);

        final DocumentsDAO documentsDAO = mock(DocumentsDAO.class);
        when(documentsDAO.findInvoicesForContact(contact)).thenReturn(List.of(newestPaid, olderPaid, partiallyPaid, unpaid));
        final CustomerStatistics statistics = new CustomerStatistics(contact, null);
        injectDocumentsDAO(statistics, documentsDAO);

        statistics.makeStatistics(true);

        assertEquals(2, statistics.getOrdersCount());
        assertEquals(2, statistics.getOpenInvoicesCount());
        assertEquals(1700.0, statistics.getTotal());
        assertEquals(850.0, statistics.getOpenTotal());
        assertEquals("08/2025", statistics.getLastOrderMonth());
    }

    private static Invoice invoice(final Boolean paid, final double totalValue, final double paidValue, final int year, final int month,
            final int day) {
        final Invoice invoice = FakturamaModelPackage.MODELFACTORY.createInvoice();
        invoice.setPaid(paid);
        invoice.setTotalValue(totalValue);
        invoice.setPaidValue(paidValue);
        invoice.setOrderDate(new GregorianCalendar(year, month, day).getTime());
        invoice.setDeleted(Boolean.FALSE);
        return invoice;
    }

    private static void injectDocumentsDAO(final CustomerStatistics statistics, final DocumentsDAO documentsDAO) throws Exception {
        final Field field = CustomerStatistics.class.getDeclaredField("documentsDAO");
        field.setAccessible(true);
        field.set(statistics, documentsDAO);
    }
}
