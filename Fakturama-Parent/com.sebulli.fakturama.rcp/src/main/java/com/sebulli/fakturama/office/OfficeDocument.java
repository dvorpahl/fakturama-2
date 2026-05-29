/*
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama.office;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Shell;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.sebulli.fakturama.Activator;
import com.sebulli.fakturama.calculate.DocumentSummaryCalculator;
import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.dto.DocumentSummary;
import com.sebulli.fakturama.exception.FakturamaException;
import com.sebulli.fakturama.exception.FakturamaStoringException;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.OSDependent;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.office.FileOrganizer.PathOption;
import com.sebulli.fakturama.parts.DocumentEditor;
import com.sebulli.fakturama.util.DocumentTypeUtil;

public class OfficeDocument {

    /** The UniDataSet document, that is used to fill the OpenOffice document */
    private Document document;

    @Inject
    private IPreferenceStore preferences;

    @Inject
    protected IEclipseContext context;

    @Inject
    protected DocumentsDAO documentsDAO;

    @Inject
    private ILogger log;

    @Inject
    @Translation
    protected Messages msg;

    /**
     * Event Broker for sending update events to the list table
     */
    @Inject
    protected IEventBroker evtBroker;

    @Inject
    UISynchronize sync;

    private FileOrganizer fileOrganizer;
    private Shell shell;

    /**
     * background processing, default is FALSE
     */
    private boolean silentMode = false;

    //    private Path documentPath;

    //    private Path generatedPdf;

    @PostConstruct
    public void init(@Named(IServiceConstants.ACTIVE_SHELL) final Shell shell) {
        fileOrganizer = ContextInjectionFactory.make(FileOrganizer.class, context);
        this.shell = shell;
    }

    public void createDocument(Path template, final Document document, final boolean forceRecreation) throws FakturamaStoringException {
        //Open an existing document instead of creating a new one
        boolean openExisting = false;

        this.document = document;

        // Try to generate the OpenOffice document
        try {

            // Check whether there is already a document then do not 
            // generate one by the data, but open the existing one.
            if (testOpenAsExisting(document) && !forceRecreation) {
                openExisting = true;
                final Set<PathOption> pathOptions = Stream.of(PathOption.values()).collect(Collectors.toSet());
                template = fileOrganizer.getDocumentPath(pathOptions, TargetFormat.ODT, document);
            }

            // Stop here and do not fill the document's placeholders, if it's an existing document
            if (openExisting && document.getPdfPath() != null) {
                openDocument();
                return;
            }

            // remove previously created images            
            cleanup();

            //            // Get the VAT summary of the UniDataSet document
            //            VatSummarySetManager vatSummarySetManager = ContextInjectionFactory.make(VatSummarySetManager.class, context);
            //            vatSummarySetManager.add(this.document, Double.valueOf(1.0));

            // Recalculate the sum of the document before exporting
            final DocumentSummaryCalculator documentSummaryCalculator = ContextInjectionFactory.make(DocumentSummaryCalculator.class, context);
            final DocumentSummary documentSummary = documentSummaryCalculator.calculate(this.document);

            /*
             * Get the placeholders of the OpenOffice template.
             * The scanning of all placeholders to find the item and the vat
             * table
             * is also done here.
             */
            final OdfTextDocument textdoc = org.odftoolkit.odfdom.doc.OdfTextDocument.loadDocument(template.toFile());
            textdoc.changeMode(OdfTextDocument.OdfMediaType.TEXT);

            final TemplateProcessor templateProcessor = ContextInjectionFactory.make(TemplateProcessor.class, context);
            templateProcessor.processTemplate(textdoc, document, documentSummary);

            // Save the document
            if (saveOODocument(textdoc, template)) {
                openDocument();
            }

        } catch (final Exception e) {
            log.error(e, "Error starting OpenOffice with " + template.getFileName());
            throw new FakturamaStoringException("Error starting OpenOffice with " + template.getFileName(), e);
        }
    }

    /**
     * Opens the finally created document(s). Depends on preferences (ODT, PDF
     * or both of them).
     */
    private void openDocument() {
        final List<String> messages = new ArrayList<>();
        if (!silentMode) {
            if (preferences.getBoolean(Constants.PREFERENCES_OPENOFFICE_SAVE_ODT)) {
                if (preferences.getBoolean(Constants.PREFERENCES_OPENOFFICE_START_IN_NEW_THREAD) && document.getOdtPath() != null) {
                    sync.asyncExec(() -> {
                        if (!Program.launch(document.getOdtPath())) {
                            MessageDialog.openError(shell, msg.dialogMessageboxTitleError,
                                    "Document was created but can't find a viewer for OpenOffice document.");
                        }
                    });
                } else {
                    messages.add(msg.dialogPrintooSuccessful);
                }
            }

            if (document.getPdfPath() != null) {
                if (preferences.getBoolean(Constants.PREFERENCES_OPENPDF)) {
                    sync.asyncExec(() -> {
                        final String pdfProgramCall = OSDependent.getPDFProgramCall(document.getPdfPath());
                        final Program programForPdf = Program.findProgram(".pdf");
                        if (programForPdf == null || !programForPdf.execute(pdfProgramCall)) {
                            MessageDialog.openError(shell, msg.dialogMessageboxTitleError, "Document was created but can't find a viewer for PDF.");
                        }
                    });
                } else {
                    messages.add(msg.dialogPrintooPdfsuccessful);
                }
            }

            if (!messages.isEmpty() && !preferences.getString(Constants.DISPLAY_SUCCESSFUL_PRINTING).contentEquals(MessageDialogWithToggle.ALWAYS)) {
                MessageDialogWithToggle.openInformation(shell, msg.dialogMessageboxTitleInfo, String.join("\n", messages), null, false, preferences,
                        Constants.DISPLAY_SUCCESSFUL_PRINTING);
            }
        }
    }

    /**
     * Save an OpenOffice document as *.odt and as *.pdf
     * 
     * @param textdoc
     *            The document
     * @throws FakturamaException
     */
    private boolean saveOODocument(final OdfTextDocument textdoc, final Path template) throws FakturamaStoringException, FakturamaException {
        Path generatedPdf = null;
        final Set<PathOption> pathOptions = new HashSet<>(Arrays.asList(PathOption.values()));

        final boolean wasSaved = false;
        textdoc.getOfficeMetadata().setCreator(msg.applicationName);
        textdoc.getOfficeMetadata().setTitle(String.format("%s - %s",
                msg.getMessageFromKey(DocumentTypeUtil.findByBillingType(document.getBillingType()).getSingularKey()), document.getName()));
        textdoc.getOfficeMetadata().setCreator(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER));
        textdoc.getOfficeMetadata().setCreationDate(Calendar.getInstance());

        final Path targetOdtDocumentPath = fileOrganizer.getDocumentPath(pathOptions, TargetFormat.ODT, document);
        final Path origOdtFileName = targetOdtDocumentPath.getFileName();

        // create a temporary document for further processing
        OutputStream fileStream = null;
        Path tmpDocumentPath;
        try {
            tmpDocumentPath = Files.createTempFile(null, null);
            tmpDocumentPath.toFile().deleteOnExit();
            fileStream = Files.newOutputStream(tmpDocumentPath);
            // Save the document
            textdoc.save(fileStream);
        } catch (final Exception e) {
            log.error(e, "Error saving the OpenOffice document");
            throw new FakturamaStoringException(
                    "Error saving the temporary OpenOffice document with template " + template.getFileName() + ". Check if target file is opened.", e);
        } finally {
            if (fileStream != null) {
                try {
                    fileStream.close();
                } catch (final IOException e) {
                    log.error(e, "Error closing temporary OpenOffice file");
                }
            }
        }

        // create PDF, this is the single point of fail. If this does not work, the file is not marked printed
        generatedPdf = createPdf(tmpDocumentPath, origOdtFileName, TargetFormat.PDF);

        // if generation was not successful, cancel here
        if (generatedPdf == null) {
            return false;
        }

        // copy tempfile to ODT Output
        if (preferences.getBoolean(Constants.PREFERENCES_OPENOFFICE_SAVE_ODT)) {
            try {
                createOutputDirectory(targetOdtDocumentPath.getParent());
                Files.copy(tmpDocumentPath, targetOdtDocumentPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (final Exception e) {
                log.error(e);
                return false;
            }
        }

        // copy the PDF to the additional directory
        if (!preferences.getString(Constants.PREFERENCES_ADDITIONAL_OPENOFFICE_PDF_PATH_FORMAT).isEmpty()) {
            final Path additionalDocumentPath = fileOrganizer.getDocumentPath(pathOptions, TargetFormat.ADDITIONAL_PDF, document);
            try {
                Files.createDirectories(additionalDocumentPath.getParent());
                Files.copy(generatedPdf, additionalDocumentPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (final IOException e) {
                log.error(e);
            }
        }

        // Mark the document as printed, if it was saved as ODT and/or PDF
        // Mark the document as "printed"
        document.setPrinted(Boolean.TRUE);
        document.setPrintTemplate(template.toString());

        if (preferences.getBoolean(Constants.PREFERENCES_OPENOFFICE_SAVE_ODT)) {
            document.setOdtPath(targetOdtDocumentPath.toString());
        }

        // Update the document entry "pdfpath"
        document.setPdfPath(generatedPdf.toString());
        document = documentsDAO.save(document);

        // run PDF post processors, if any, return true if success, false if an error happened
        final boolean result = postProcess();
        if (!result) {
            throw new FakturamaException("Error while post processing invoice");
        }
        // Refresh the table view of all documents
        evtBroker.post(DocumentEditor.EDITOR_ID, "update");

        // if all was ok until here, document was saved successful
        return true;

    }

    private boolean postProcess() {
        boolean result = true;
        if (document.getPdfPath() != null && Files.exists(Paths.get(document.getPdfPath()))) {
            try {
                final Collection<ServiceReference<IPdfPostProcessor>> serviceReferences = Activator.getContext().getServiceReferences(IPdfPostProcessor.class,
                        null);
                if (serviceReferences.isEmpty()) {
                    log.info("no post processors found");
                }

                context.set(Shell.class, shell);
                final List<ServiceReference<IPdfPostProcessor>> sortedList = serviceReferences.stream().sorted(new ServiceRefComparator()).toList();

                for (final ServiceReference<IPdfPostProcessor> serviceReference : sortedList) {

                    // enrich post processor service with available Eclipse services
                    final IPdfPostProcessor currentProcessor = Activator.getContext().getService(serviceReference);
                    ContextInjectionFactory.inject(currentProcessor, context);
                    if (result && document instanceof final Invoice invoice && currentProcessor.canProcess(Optional.ofNullable(invoice))) {
                        result &= currentProcessor.processPdf(Optional.ofNullable(invoice));
                    }
                }
            } catch (final InvalidSyntaxException e) {
                log.error(String.format("PDF post processor couldn't be started. Reason: %s", e.getMessage()));
                return false;
            } catch (final FakturamaException e) {
                log.error("error while processing invoice: {}", e);
                return false;
            }
        }
        return result; // so that a message is displayed that something was wrong
    }

    private void cleanup() throws IOException {
        // remove temp images
        final PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:"
                + StringUtils.appendIfMissing(preferences.getString(Constants.GENERAL_WORKSPACE), String.valueOf(File.separatorChar)).replaceAll("\\\\", "/")
                + "tmpImage*");

        Files.walkFileTree(Paths.get(preferences.getString(Constants.GENERAL_WORKSPACE)), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs) throws IOException {
                if (pathMatcher.matches(path)) {
                    try {
                        Files.deleteIfExists(path);
                    } catch (final FileSystemException e) {
                        log.warn(String.format("temporary file couldn't be deleted! %s", e.getMessage()));
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                return FileVisitResult.SKIP_SIBLINGS;
            }
        });

    }

    /**
     * Creates the PDF.
     *
     * @param documentPath
     *            the path to the ODT document (which will be converted)
     * @param origFileName
     * @param targetFormat
     * @return <code>true</code> if the creation was successful
     * @throws FakturamaStoringException
     */
    private Path createPdf(final Path documentPath, final Path origFileName, final TargetFormat targetFormat) throws FakturamaStoringException {
        Path pdfFilename = null;

        // Create the directories, if they don't exist.
        createOutputDirectory(targetFormat);

        try {

            // Save the document
            final OfficeStarter ooStarter = ContextInjectionFactory.make(OfficeStarter.class, context);
            final Path ooPath = ooStarter.getCheckedOOPath(silentMode);
            if (ooPath != null) {

                // now, if the file name templates are different, we have to
                // rename the pdf
                final Set<PathOption> pathOptions = Stream.of(PathOption.values()).collect(Collectors.toSet());
                pdfFilename = fileOrganizer.getDocumentPath(pathOptions, targetFormat, document);

                try (RandomAccessFile raf = new RandomAccessFile(pdfFilename.toFile(), "rw");
                        FileChannel channel = raf.getChannel();
                        FileLock lock = channel.tryLock()) {
                    if (lock == null) {
                        if (!silentMode) {
                            MessageDialog.openError(shell, msg.dialogMessageboxTitleError, msg.dialogPrintooPdfwritererror);
                        }
                        log.warn("PDF file {} cannot be opened for writing, is it open already?", pdfFilename);
                        return null;
                    }
                } catch (final IOException ex) {
                    if (!silentMode) {
                        MessageDialog.openError(shell, msg.dialogMessageboxTitleError, msg.dialogPrintooPdfwritererror);
                    }
                    log.warn("PDF file {} cannot be opened for writing, is it open already?", pdfFilename);
                    return null;
                }

                final ProcessBuilder pb = new ProcessBuilder(ooPath.toString(), "--headless", "--convert-to", "pdf:writer_pdf_Export", "--outdir",
                        pdfFilename.getParent().toString(), // this is the PDF path
                        documentPath.toAbsolutePath().toString());

                final Process p = pb.start();
                p.waitFor();

                // if we convert a temporary document the suffix is changing to ".PDF", therefore
                // we have to change the document name here
                // create a temporary filename as it would be created by PDF writer process
                final Path tmpPdf = Paths.get(pdfFilename.getParent().toString(),
                        documentPath.getFileName().toString().replaceAll("\\.ODT$|\\.odt$|.tmp$", TargetFormat.PDF.getExtension()));

                if (/* !Files.exists(pdfFilename) && */Files.exists(tmpPdf)) {
                    pdfFilename = Files.move(tmpPdf, pdfFilename, StandardCopyOption.REPLACE_EXISTING);
                }
            } else {
                showError();
            }
        } catch (final FileSystemException e) {
            pdfFilename = null;
            throw new FakturamaStoringException("kann PDF nicht schreiben. Ist die Datei evtl. geöffnet?", e);
        } catch (final IOException e) {
            pdfFilename = null;
            showError();
            log.error(e, "Error moving the PDF document");
        } catch (final InterruptedException e) {
            // from ProcessBuilder
            log.error(e, "InterruptedException");
        }
        return pdfFilename;
    }

    private void showError() {
        if (!silentMode) {
            MessageDialog.openError(shell, msg.dialogMessageboxTitleError, "Can't create PDF!");
        }
        log.warn("Can't create PDF! Did you set the right OpenOffice path?");
    }

    private void createOutputDirectory(final Path directory) {
        if (Files.notExists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (final IOException e) {
                log.error(e, "could not create output directory: " + directory.toString());
            }
        }
    }

    /**
     * Creates the output directory, if necessary.
     *
     * @param targetFormat
     *            the target document format
     * @return the path that was created
     */
    private Path createOutputDirectory(final TargetFormat targetFormat) {
        final Set<PathOption> pathOptions = Collections.emptySet();
        final Path directory = fileOrganizer.getDocumentPath(pathOptions, targetFormat, document);
        createOutputDirectory(directory);
        return directory;
    }

    /**
     * Check whether there is already a document then do not generate one by the
     * data, but open the existing one.
     */
    public boolean testOpenAsExisting(final Document document) {
        final Set<PathOption> pathOptions = Stream.of(PathOption.values()).collect(Collectors.toSet());
        final Path oODocumentFile = fileOrganizer.getDocumentPath(pathOptions, TargetFormat.PDF, document);

        return (Files.exists(oODocumentFile) && document.getPrinted());//
        //                && filesAreEqual(document.getPrintTemplate(), template));
    }

    /**
     * Tests if 2 filenames are equal. Tests only the relative path and use the
     * parameter "folder" to separate the relative path from the absolute one.
     * 
     * @param fileName1
     * @param fileName2
     * @param folder
     *            The folder name to separate the relative path
     * @return <code>true</code>, if both are equal
     */
    private boolean filesAreEqual(String fileName1, final Path fileName2, final String folder) {

        int pos;
        String otherFileName = fileName2.toString();
        pos = fileName1.indexOf(folder);
        if (pos >= 0) {
            fileName1 = fileName1.substring(pos);
        }

        pos = fileName2.toString().indexOf(folder);
        if (pos >= 0) {
            otherFileName = fileName2.toString().substring(pos);
        }

        return fileName1.equals(otherFileName);
    }

    /**
     * Tests if 2 template filenames are equal. The absolute path is ignored.
     * 
     * @param fileName1
     * @param template
     * 
     * @return True, if both filenames are equal
     */
    private boolean filesAreEqual(final String fileName1, final Path template) {

        if (fileName1 == null) {
            return false;
        }

        // Test, if also the absolute path is equal
        if (fileName1.equals(template.toString())) {
            return true;
        }

        // If not, use the unlocalized folder names
        if (filesAreEqual(fileName1, template, "/Templates/")) {
            return true;
        }

        // Use the localized folder names
        if (filesAreEqual(fileName1, template, "/" + msg.configWorkspaceTemplatesName + "/")) {
            return true;
        }

        return false;
    }

    /**
     * @return the silentMode
     */
    public boolean isSilentMode() {
        return silentMode;
    }

    /**
     * @param silentMode
     *            the silentMode to set
     */
    public void setSilentMode(final boolean silentMode) {
        this.silentMode = silentMode;
    }

    class ServiceRefComparator implements Comparator<ServiceReference<IPdfPostProcessor>> {

        @Override
        public int compare(final ServiceReference<IPdfPostProcessor> ref1, final ServiceReference<IPdfPostProcessor> ref2) {
            final IPdfPostProcessor service1 = Activator.getContext().getService(ref1);
            final IPdfPostProcessor service2 = Activator.getContext().getService(ref2);

            if (service1 == null && service2 == null) {
                return 0;
            }
            if (service1 == null) {
                return -1;
            }
            if (service2 == null) {
                return 1;
            }

            return Integer.compare(service1.getPriority(), service2.getPriority());
        }
    }
}
