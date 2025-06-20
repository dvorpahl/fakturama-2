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
package org.fakturama.export.einvoice;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.xmpbox.schema.XmpSchemaException;
import org.apache.xmpbox.xml.XmpParsingException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.fakturama.export.facturx.AbstractEInvoice;

import com.sebulli.fakturama.dao.CEFACTCodeDAO;
import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.IDateFormatterService;
import com.sebulli.fakturama.misc.INumberFormatterService;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.office.FileOrganizer;
import com.sebulli.fakturama.office.FileOrganizer.PathOption;
import com.sebulli.fakturama.office.TargetFormat;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

public abstract class AbstractEInvoiceCreator implements IEinvoiceCreator {
    /**
     * This is for distinguishing the different contact entries.
     *
     */
    public enum ContactType {
        SELLER, BUYER
    }

    //  enum InvoiceeTradeParty { DERIVED, }
    public enum PriceType {
        GROSS_PRICE, NET_PRICE, NET_PRICE_DISCOUNTED
    }

    @Inject
    @Translation
    protected ZFMessages zfMsg;

    @Inject
    @org.eclipse.e4.core.di.annotations.Optional
    @Preference(nodePath = "org.fakturama.exporter.zugferd")
    protected IEclipsePreferences eclipsePrefs;

    @Inject
    protected IEclipseContext eclipseContext;

    @Inject
    ILogger log;

    @Inject
    protected CEFACTCodeDAO measureUnits;

    @Inject
    protected ILocaleService localeUtil;

    @Inject
    protected ContactsDAO contactsDAO;

    @Inject
    protected INumberFormatterService numberFormatterService;

    @Inject
    protected IDateFormatterService dateFormatterService;

    @Inject
    protected IDocumentAddressManager addressManager;

    @Inject
    @org.eclipse.e4.core.di.annotations.Optional
    protected Shell shell;

    /** The Constant DEFAULT_PRICE_SCALE. */
    protected static final int DEFAULT_AMOUNT_SCALE = 4;

    protected final SimpleDateFormat sdfDest = new SimpleDateFormat("yyyyMMdd");
    protected Map<String, MonetaryAmount> netPricesPerVat = new HashMap<>();

    /**
     * Erzeugt aus einem bereits gedruckten PDF-Dokument (PDF/A-1) und einem
     * XML-Eingabestream eine ZUGFeRD-Datei (PDF/A-3). Bei XRechnung wird nur
     * das XML-File in den vorgegebenen Ordner geschrieben.
     * 
     * @param invoice
     * @param invoiceXmlJaxb
     * @param zugferdProfile
     */
    protected boolean createPdf(final Invoice invoice, final Supplier<? extends Serializable> invoiceXmlJaxb, final ConformanceLevel zugferdProfile) {
        boolean retval = true;
        final String pdfFile = invoice.getPdfPath();
        PDDocument pdfa3 = null;

        netPricesPerVat.clear();

        if (zugferdProfile == ConformanceLevel.XRECHNUNG) {
            try {
                final IPreferenceStore defaultValuesNode = ZFPreferenceStoreProvider.getInstance().getPreferenceStore();
                final FileOrganizer fo = ContextInjectionFactory.make(FileOrganizer.class, eclipseContext);
                final Set<PathOption> pathOptions = Stream.of(PathOption.values()).collect(Collectors.toSet());
                final Path path = fo.getDocumentPath(pathOptions, TargetFormat.XML,
                        eclipsePrefs.get(ZFConstants.PREFERENCES_ZUGFERD_PATH, defaultValuesNode.getDefaultString(ZFConstants.PREFERENCES_ZUGFERD_PATH)),
                        invoice);
                // only to be on the safe side...
                Files.deleteIfExists(path);
                createXmlFile(invoiceXmlJaxb, path);

            } catch (final Exception exception) {
                log.error(exception, "can't delete old XRechnung document: " + exception.getMessage());
            }
        } else {
            // this is Zugferd only
            try (ByteArrayOutputStream buffo = new ByteArrayOutputStream()) {
                // create XML from structure              
                final JAXBContext context = org.eclipse.persistence.jaxb.JAXBContextFactory.createContext("org.fakturama.export.facturx.modelgen",
                        this.getClass().getClassLoader(), null);
                final Path file = Files.createTempFile("fakxml", "xml");
                final OutputStream outputStream = Files.newOutputStream(file);

                context.createMarshaller().marshal(invoiceXmlJaxb.get(), outputStream);
                outputStream.flush();
                outputStream.close();

                printXMLDocument(new StreamSource(file.toFile()), new StreamResult(buffo));
                final PDDocument retvalPDFA3 = getPdfHelper().makeA3Acompliant(pdfFile,
                        zugferdProfile/* , zugferdXml, invoice.getName() */);

                // embed XML
                pdfa3 = getPdfHelper().attachZugferdFile(retvalPDFA3, buffo);

                if (pdfFile != null) {
                    pdfa3.save(Paths.get(pdfFile + ".pdf").toFile());
                } else { // dialog cancelled
                    retval = false;
                }
            } catch (JAXBException | IOException | TransformerException | XmpParsingException | XmpSchemaException exception) {
                log.error(exception, "error creating ZUGFeRD document: " + exception.getMessage());
                retval = false;
            } finally {
                if (pdfa3 != null) {
                    try {
                        pdfa3.close();
                    } catch (final IOException ioex) {
                        log.error(ioex, "error closing ZUGFeRD PDF document: " + ioex.getMessage());
                    }
                }
            }

            try {
                Files.move(Paths.get(pdfFile + ".pdf"), Paths.get(pdfFile), StandardCopyOption.REPLACE_EXISTING);
            } catch (final IOException e) {
                log.error("Error editing document", e);
            }
        }
        return retval;
    }

    protected abstract IPdfHelper getPdfHelper();

    private void printXMLDocument(final StreamSource streamSource, final StreamResult streamResult) throws IOException, TransformerException {
        final TransformerFactory tf = TransformerFactory.newInstance();
        final Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, ZFConstants.CHARSET_UTF8_KEY);
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(streamSource, streamResult);
    }

    /**
     * create the generated export file (only for debugging purposes)
     * 
     * @param root
     *            the document
     */
    protected void createXmlFile(final Supplier<? extends Serializable> root, final Path path) {
        // create directory if it doesn't exist
        createOutputDirectory(path.getParent());

        try (BufferedWriter newBufferedWriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE);) {
            final Path file = Files.createTempFile("fakxml", "xml");
            final OutputStream outputStream = Files.newOutputStream(file);
            final JAXBContext testContext = org.eclipse.persistence.jaxb.JAXBContextFactory
                    .createContext("org.fakturama.export.facturx.modelgen:org.fakturama.export.zugferd.modelgen", this.getClass().getClassLoader(), null);
            testContext.createMarshaller().marshal(root.get(), outputStream);
            outputStream.flush();
            outputStream.close();
            printXMLDocument(new StreamSource(file.toFile()), new StreamResult(newBufferedWriter));
        } catch (JAXBException | IOException | TransformerException e) {
            log.error(e);
        }
    }

    private void createOutputDirectory(final Path directory) {
        if (Files.notExists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (final IOException e) {
                log.error(e, "can't create output directory: " + directory.toString());
            }
        }
    }

    /**
     * @deprecated use {@link AbstractEInvoice} method
     * @param contact
     * @return
     */
    @Deprecated
    protected Contact getOriginContact(final DocumentReceiver contact) {
        if (contact.getOriginContactId() != null) {
            return contactsDAO.findById(contact.getOriginContactId());
        }
        return null;
    }

}