/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2020 Ralf Heydenreich
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Ralf Heydenreich - initial API and implementation
 */
package org.fakturama.export.facturx;

import java.io.Serializable;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.preference.IPreferenceStore;
import org.fakturama.export.einvoice.AbstractEInvoiceCreator;
import org.fakturama.export.einvoice.ConformanceLevel;
import org.fakturama.export.einvoice.IEinvoice;
import org.fakturama.export.einvoice.IPdfHelper;
import org.fakturama.export.einvoice.converter.EInvoiceConverter;
import org.fakturama.export.einvoice.converter.InvoiceConverterException;
import org.fakturama.export.einvoice.model.EInvoice;

import com.sebulli.fakturama.exception.FakturamaException;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.util.ContactUtil;

/**
 * Generator class for XRechnung /Factur-x files.
 * 
 * <p>
 * HINTS (from German documentation):<br/>
 * <ul>
 * <li>Bei Dezimalzahlen müssen die Nachkommastellen durch einen Dezimalpunkt
 * getrennt sein.
 * <li>Die Codelisten werden analog zu CEN/TS 16931-3-3 definiert
 * <li>Bis zum Profil EN 16931 (COMFORT) gelten die Design-Prinzipien der Norm,
 * dass sich eine Rechnung immer nur auf genau eine Bestellung und genau eine
 * Lieferung beziehen darf.
 * <li>Nettopreis als verbindliche Preisinformation
 * <li>Der Nettopreis des Artikels in diesem Zusammenhang ist der Preis eines
 * Artikels ohne Umsatzsteuer nach Abzug des Nachlasses auf den Artikelpreis.
 * Der Nettobetrag der Rechnungsposition ist der „Netto“-Betrag d. h. ohne die
 * Umsatzsteuer, aber einschließlich aller für die Positionsebene geltenden Zu-
 * und Abschläge sowie sonstiger anfallender Steuern.
 * <li>Die EN 16931-1 unterstützt nur Nachlass auf den Bruttopreis des Artikels.
 * </ul>
 * </p>
 *
 */
public class XRechnungCreator extends AbstractEInvoiceCreator {

    @Inject
    private IEclipseContext context;

    private IPdfHelper pdfHelper;

    @Inject // node: org.fakturama.export.zugferd
    protected IPreferenceStore preferences;

    @Inject
    private ILogger log;

    @Inject
    @Translation
    protected Messages msg;

    @Override
    public boolean createEInvoice(final Optional<Invoice> invoice, final ConformanceLevel zugferdProfile) throws FakturamaException {
        // return if invoice is not given
        if (invoice.isEmpty()) {
            return false;
        }

        // transform Invoice into standard Model for electronic invoices

        // now use new model to create further xml files
        Serializable invoiceXml;
        // 2. create XML file
        final IEinvoice eInvoiceImpl;
        final ContactUtil contactUtil = ContextInjectionFactory.make(ContactUtil.class, eclipseContext);
        final EInvoiceConverter converter = new EInvoiceConverter(eclipseContext, preferences, contactsDAO, contactUtil, addressManager, msg, localeUtil,
                measureUnits, zfMsg);
        EInvoice eInvoice;
        try {
            eInvoice = converter.convertInvoice(invoice.orElseThrow());
            // set vars related to profile
            converter.postProcess(eInvoice, zugferdProfile);
        } catch (final InvoiceConverterException e) {
            log.error(e, "Error converting invoice to EInvoice");
            return false;
        }
        switch (zugferdProfile) {
            case ZUGFERD_V2_EN16931, FACTURX_EN16931, XRECHNUNG:
                eInvoiceImpl = ContextInjectionFactory.make(XRechnung.class, context);
                invoiceXml = eInvoiceImpl.getInvoiceXml(eInvoice);
                break;
            default:
                // if we have another profile... exit with error
                return false;
        }

        // 3. merge XML & PDF/A-1 to PDF/A-3
        return createPdf(invoice.get(), () -> invoiceXml, zugferdProfile);
    }

    @Override
    protected IPdfHelper getPdfHelper() {
        if (pdfHelper == null) {
            pdfHelper = new FacturXHelper();
        }
        return pdfHelper;
    }

}
