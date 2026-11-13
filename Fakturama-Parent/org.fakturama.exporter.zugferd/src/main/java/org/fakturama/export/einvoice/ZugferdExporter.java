/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2014 Ralf Heydenreich
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Ralf Heydenreich - initial API and implementation
 */
package org.fakturama.export.einvoice;

import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.fakturama.export.facturx.XRechnungCreator;
import org.osgi.service.component.annotations.Component;

import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.exception.FakturamaException;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.Debitor;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.office.IPdfPostProcessor;

/**
 * This is the main class for the exporter interface for the ZUGFeRD invoice. At
 * the moment, a COMFORT level ZUGFeRD document is generated. This will be
 * changed in future (more flexible).
 * 
 * NOTE: The injection of all the services has to be done by caller (because
 * this is an OSGi service, but the injected services are from Eclipse context).
 * 
 */
@Component()
public class ZugferdExporter implements IPdfPostProcessor {

    private static final byte PROFILE_ZUGFERD = 1;
    private static final byte PROFILE_XRECHNUNG = 2;

    @Inject
    @org.eclipse.e4.core.di.annotations.Optional
    @Preference
    private IEclipsePreferences eclipsePrefs;

    @Inject
    private IPreferenceStore preferences;

    @Inject
    @org.eclipse.e4.core.di.annotations.Optional
    public Shell shell;

    @Inject
    @Translation
    protected ZFMessages msg;

    @Inject
    private IEclipseContext eclipseContext;

    @Inject
    protected IDocumentAddressManager addressManager;

    @Inject
    protected ContactsDAO contactsDAO;

    enum FinancialRole {
        DEBTOR, CREDITOR
    }

    @Override
    public int getPriority() {
        return 30;
    }

    @Override
    public boolean canProcess(final Optional<Invoice> inputDocument) {
        /*
         * Zunächst muß geprüft werden, ob OO/LO auch PDF/A erzeugt. Dazu muß
         * man in der Datei
         * d:\Programme\LibreOffice 5\share\registry\main.xcd
         * den Schlüssel
         * 
         * <oor:data>
         * <oor:component-schema oor:package="org.openoffice.Office"
         * oor:name="Common" xml:lang="en-US"><component>
         * <group oor:name="Filter"><group oor:name="PDF"><group
         * oor:name="Export">
         * <prop oor:name="SelectPdfVersion" oor:type="xs:int"
         * oor:nillable="false"><value>0</value></prop>
         * 
         * prüfen. Der Wert muß auf "1" stehen. Siehe dazu
         * https://wiki.openoffice.org/wiki/API/Tutorials/PDF_export
         * Idee: Vor dem Speichern den Wert umsetzen und am Schluß wieder
         * zurücksetzen.
         */
        final boolean result = eclipsePrefs.getBoolean(ZFConstants.PREFERENCES_ZUGFERD_ACTIVE, Boolean.FALSE) && inputDocument.isPresent();
        if (!result) {
            return false;
        }
        final DocumentReceiver billingAddress = addressManager.getBillingAdress(inputDocument.get());
        if (billingAddress == null || getRequestedFormats(billingAddress) == 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean processPdf(final Optional<Invoice> invoice) throws FakturamaException {

        boolean result = checkSettings();
        if (result && invoice.isPresent() && invoice.get().getBillingType().isINVOICE()) {
            final DocumentReceiver receiver = addressManager.getBillingAdress(invoice.get());
            final byte requestedFormats = getRequestedFormats(receiver);
            final IEinvoiceCreator invoiceCreator = ContextInjectionFactory.make(XRechnungCreator.class, eclipseContext);
            result = true;
            if ((requestedFormats & PROFILE_ZUGFERD) != 0) {
                result &= invoiceCreator.createEInvoice(invoice, ConformanceLevel.ZUGFERD_V2_EN16931);
            }
            if ((requestedFormats & PROFILE_XRECHNUNG) != 0) {
                result &= invoiceCreator.createEInvoice(invoice, ConformanceLevel.XRECHNUNG);
            }
            if (!result) {
                // Display an error message
                MessageDialog.openError(shell, msg.zugferdExportCommandTitle, msg.zugferdExportErrorCancelled);
            }
        }
        return result;
    }

    private byte getRequestedFormats(final DocumentReceiver receiver) {
        if (receiver == null) {
            return 0;
        }
        // The export format is a current customer preference, including when
        // printing an old invoice. Never use the historical address snapshot.
        if (receiver.getOriginContactId() != null) {
            final Contact contact = contactsDAO.findById(receiver.getOriginContactId());
            if (contact instanceof Debitor debitor) {
                return debitor.getEInvoiceFormat();
            }
        }
        return 0;
    }

    /**
     * Check if PDFs can be created
     */
    private boolean checkSettings() {
        boolean result = true;
        if (preferences.getString(Constants.PREFERENCES_OPENOFFICE_PDF_PATH_FORMAT).isEmpty()) {
            result = false;
            MessageDialog.openError(shell, msg.zugferdExportCommandTitle, msg.zugferdExportErrorNopdfpath);
        }

        // TODO Check required company settings
        return result;
    }

}
