/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2021 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.connectors.mail;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.angus.mail.smtp.SMTPTransport;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.office.IPdfPostProcessor;
import com.sebulli.fakturama.office.TemplateFinder;
import com.sebulli.fakturama.office.TemplateProcessor;
import com.sebulli.fakturama.util.DocumentTypeUtil;

import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.NoSuchProviderException;
import jakarta.mail.Part;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.internet.MimeUtility;

/**
 * The Mail Service class is an {@link IPdfPostProcessor} for sending mails
 * after successful creation of a PDF.
 */
@Component()
public class MailService implements IPdfPostProcessor {

    @Inject
    private IDocumentAddressManager addressManager;

    @Inject
    @Preference
    private IEclipsePreferences prefs;

    @Inject
    private IEclipseContext ctx;

    @Inject
    private ILogger log;
    @Inject
    private UISynchronize uiSync;

    @Inject
    @Translation
    protected Messages msg;

    @Inject
    @Translation
    protected MailServiceMessages mailServiceMessages;

    private MailInfoDialog mailInfoDialog;
    public static final int MULTIPART_MODE_MIXED_RELATED = 3;

    private static final String MULTIPART_SUBTYPE_ALTERNATIVE = "alternative";

    private static final String CONTENT_TYPE_ALTERNATIVE = "text/alternative";

    private static final String CONTENT_TYPE_HTML = "text/html";

    private static final String CONTENT_TYPE_CHARSET_SUFFIX = ";charset=";

    @Override
    public int getPriority() {
        return 50;
    }

    @Override
    public boolean canProcess() {
        return prefs.getBoolean(MailServiceConstants.PREFERENCES_MAIL_ACTIVE, false);
    }

    @Override
    public boolean processPdf(final Optional<Invoice> inputDocument) {
        if (!inputDocument.isPresent()) {
            return true;
        }

        final DocumentReceiver billingAdress = addressManager.getBillingAdress(inputDocument.get());
        if (StringUtils.isAllBlank(billingAdress.getEmail())) {
            // ignore silently...
            return true;
        }

        // Collect some settings...
        final MailSettings settings = createSettings(inputDocument.get());

        // check settings
        if (!settings.isValid()) {
            MessageDialog.openError(ctx.get(Shell.class), msg.dialogMessageboxTitleError, mailServiceMessages.mailserviceSettingsInvalid);
            return false;
        }

        //... and open the mail dialog for examining the mail to send
        // (only if user wants to see the dialog)
        if (prefs.getBoolean("WANNA_SHOW_SENDMAIL_DIALOG", true)) { // setting isn't available at the moment!!!
            ctx.set(MailService.class, this);
            loadMailModal(settings);
        } else {
            sendMail(settings);
        }
        return true;
    }

    public void loadMailModal(final MailSettings settings) {
        // Ensure UI operations are performed on the UI thread
        final Shell shell = new Shell(Display.getCurrent());
        uiSync.asyncExec(() -> {
            ctx.set(MailSettings.class, settings);
            mailInfoDialog = new MailInfoDialog(shell);
            ContextInjectionFactory.inject(mailInfoDialog, this.ctx);

            mailInfoDialog.open();
        });
    }

    private MailSettings createSettings(final Invoice invoice) {

        final TemplateProcessor templateProcessor = ContextInjectionFactory.make(TemplateProcessor.class, ctx);
        final DocumentReceiver billingAdress = addressManager.getBillingAdress(invoice);
        final String rcpBundleName = FrameworkUtil.getBundle(IPdfPostProcessor.class).getSymbolicName();
        final String rcpBundlePrefsNodeName = String.format("/%s/%s", InstanceScope.SCOPE, rcpBundleName);

        final MailSettings settings = new MailSettings().withSender(prefs.node(rcpBundlePrefsNodeName).get(Constants.PREFERENCES_YOURCOMPANY_EMAIL, ""))
                .withSenderName(prefs.node(rcpBundlePrefsNodeName).get(Constants.PREFERENCES_YOURCOMPANY_NAME, ""))
                .withUser(prefs.get(MailServiceConstants.PREFERENCES_MAIL_USER, "")).withPassword(prefs.get(MailServiceConstants.PREFERENCES_MAIL_PASSWORD, ""))
                .withHost(prefs.get(MailServiceConstants.PREFERENCES_MAIL_HOST, "")).withReceiversTo(billingAdress.getEmail())
                .withReceiversCC(prefs.get(MailServiceConstants.PREFERENCES_MAIL_CC_FIX, "").split(MailSettings.ADDRESS_SEPARATOR_CHAR))
                .withReceiversBCC(prefs.get(MailServiceConstants.PREFERENCES_MAIL_BCC_FIX, "").split(MailSettings.ADDRESS_SEPARATOR_CHAR))
                .withSubject(createMailSubject(invoice, templateProcessor));

        settings.setBody(createBodyFromTemplate(invoice, templateProcessor));

        final List<String> additionalDocs = collectAdditionalDocs(invoice);
        settings.addToAdditionalDocs(additionalDocs);
        return settings;
    }

    private List<String> collectAdditionalDocs(final Invoice invoice) {
        final List<String> retList = new ArrayList<>();

        // the PDF is always an attachment
        retList.add(invoice.getPdfPath());

        // optional documents found in additional path
        final String additionalFilesPath = prefs.get(MailServiceConstants.PREFERENCES_MAIL_ADDITIONAL_DOCUMENTS_PATH, "");
        if (!additionalFilesPath.isBlank()) {
            final Path templatePath1 = Paths.get(additionalFilesPath);
            final List<String> templates = scanPathForadditionalFiles(templatePath1);
            retList.addAll(templates);
        }
        return retList;
    }

    /**
     * Scans the additional files path for all templates. If an additional file
     * exists, add it to the list of available additional files
     * 
     * @param additionalFilePath
     *            path which is scanned
     */
    private List<String> scanPathForadditionalFiles(final Path additionalFilePath) {
        List<String> additionalFiles = new ArrayList<>();
        try {
            if (Files.exists(additionalFilePath)) {
                additionalFiles = Files.list(additionalFilePath).sorted(Comparator.comparing(p -> p.getFileName().toString().toLowerCase()))
                        .map(p -> p.toAbsolutePath().toString()).toList();
            }
        } catch (final IOException e) {
            log.error(e, "Error while scanning the additional files directory: " + additionalFilePath.toString());
        }
        return additionalFiles;
    }

    private String createMailSubject(final Document invoice, final TemplateProcessor templateProcessor) {
        String prefDescriptor;
        switch (invoice.getBillingType()) {
            case INVOICE:
                prefDescriptor = MailServiceConstants.PREFERENCES_MAIL_SUBJECT_INVOICE;
                break;
            case DELIVERY:
                prefDescriptor = MailServiceConstants.PREFERENCES_MAIL_SUBJECT_DELIVERY;
                break;
            case OFFER:
                prefDescriptor = MailServiceConstants.PREFERENCES_MAIL_SUBJECT_OFFER;
                break;
            case DUNNING:
                prefDescriptor = MailServiceConstants.PREFERENCES_MAIL_SUBJECT_DUNNING;
                break;
            case ORDER:
                prefDescriptor = MailServiceConstants.PREFERENCES_MAIL_SUBJECT_ORDER;
                break;
            case PROFORMA:
                prefDescriptor = MailServiceConstants.PREFERENCES_MAIL_SUBJECT_PROFORMA;
                break;
            case CREDIT:
                prefDescriptor = MailServiceConstants.PREFERENCES_MAIL_SUBJECT_CREDIT;
                break;
            default:
                prefDescriptor = "";
                break;
        }
        return templateProcessor.fill(invoice, Optional.empty(), prefs.get(prefDescriptor, "<no subject>"));
    }

    private String createBodyFromTemplate(final Document invoice, final TemplateProcessor templateProcessor) {
        String templateString = "";
        final TemplateFinder templateFinder = ContextInjectionFactory.make(TemplateFinder.class, ctx);
        final List<Path> templates = templateFinder.collectTemplates(DocumentTypeUtil.findByBillingType(invoice.getBillingType()),
                TemplateFinder.TXT_TEMPLATE_FILEEXTENSION);

        if (templates != null && !templates.isEmpty()) {
            final Path mailTemplatePath = templates.get(0);
            if (Files.exists(mailTemplatePath)) {
                try {
                    templateString = Files.readString(mailTemplatePath);
                } catch (final IOException e) {
                    log.error(e, "mail template can't be processed: " + mailTemplatePath.getFileName());
                }
            }
        }

        return templateProcessor.fill(invoice, Optional.empty(), templateString);
    }

    public void sendMail(final MailSettings settings) {
        // create some properties and get the default Session
        final Properties props = System.getProperties();
        props.put(MailServiceConstants.MAIL_SMTP_HOST, settings.getHost());
        props.put(MailServiceConstants.MAIL_SMTP_AUTH, "true");
        final boolean useSSL = prefs.getBoolean(MailServiceConstants.PREFERENCES_MAIL_USESSL, false);
        props.put(MailServiceConstants.MAIL_SMTP_STARTTLS_ENABLE, !useSSL);
        props.put(MailServiceConstants.MAIL_SMTP_SSLTLS_ENABLE, useSSL);
        props.put(MailServiceConstants.MAIL_SMTP_PORT, MailServiceConstants.MAIL_SMTP_DEFAULT_PORT);
        props.put("mail.smtp.ssl.trust", '*');
        final Authenticator authenticator = new Authenticator() {
            final PasswordAuthentication authentication = new PasswordAuthentication(settings.getUser(), settings.getPassword());

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return authentication;
            }
        };

        final Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);
        boolean gotError = false;
        try {
            final MimeMessage message = createMessage(settings, session);

            SMTPTransport transport = null;
            try {
                transport = connectTransport(settings, session);
                transport.sendMessage(message, message.getAllRecipients());
            } catch (final MessagingException e) {
                gotError = true;
                log.error(e, "can't send mail");
            }

        } catch (final MessagingException mex) {
            gotError = true;
            log.error(mex, "can't send mail");
            Exception ex = null;
            if ((ex = mex.getNextException()) != null) {
                log.error(ex, "can't send mail");
            }
        } catch (final UnsupportedEncodingException ex) {
            gotError = true;
            log.error(ex, "can't send mail");
        } finally {
            if (!gotError && mailInfoDialog != null) {
                mailInfoDialog.close();
                mailInfoDialog = null;
            } else {
                MessageDialog.openError(ctx.get(Shell.class), msg.dialogMessageboxTitleError, mailServiceMessages.mailserviceSendFailed);
            }
        }
    }

    /**
     * @param settings
     * @return
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     */
    private MimeMessage createMessage(final MailSettings settings, final Session session) throws UnsupportedEncodingException, MessagingException {
        // create a message
        final MimeMessage message = new MimeMessage(session);

        //set From email field
        final InternetAddress senderAddr = new InternetAddress(settings.getSender());
        senderAddr.setPersonal(settings.getSenderName());
        message.setFrom(senderAddr);
        message.setSender(senderAddr);
        message.setSubject(settings.getSubject());

        message.setRecipients(Message.RecipientType.TO, settings.getReceiversTo());
        message.setRecipients(Message.RecipientType.CC, settings.getReceiversCC());
        message.setRecipients(Message.RecipientType.BCC, settings.getReceiversBCC());

        // create and fill the first message part
        // PLAIN TEXT
        final MimeMultipart mimeMultipart = new MimeMultipart("mixed");
        final MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(mimeBodyPart, CONTENT_TYPE_ALTERNATIVE);

        final MimeBodyPart plainTextPart = new MimeBodyPart();
        plainTextPart.setText(settings.getBody(), StandardCharsets.UTF_8.name());
        mimeMultipart.addBodyPart(plainTextPart);

        final MimeBodyPart htmlTextPart = new MimeBodyPart();
        htmlTextPart.setContent(settings.getBody(), CONTENT_TYPE_HTML + CONTENT_TYPE_CHARSET_SUFFIX + StandardCharsets.UTF_8.name());
        mimeMultipart.addBodyPart(htmlTextPart);

        // add attachments
        settings.getAdditionalDocs().stream().forEach(p -> {
            try {
                final MimeBodyPart mimePart = createMimePart(p);
                mimeMultipart.addBodyPart(mimePart);
            } catch (final MessagingException e) {
                log.error(e, "can't add mime body");
            }
        });

        // add the Multipart to the message
        message.setContent(mimeMultipart);

        // set the Date: header
        message.setSentDate(new Date());

        return message;
    }

    private MimeBodyPart createMimePart(final String file) throws MessagingException {
        try {
            final MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setDisposition(Part.ATTACHMENT);
            final Path filePath = Path.of(file);
            mimeBodyPart.setFileName(MimeUtility.encodeText(filePath.getFileName().toString()));
            final FileDataSource fileDataSource = new FileDataSource(filePath.toFile());

            final DataHandler datahandler = new DataHandler(fileDataSource);
            mimeBodyPart.setDataHandler(datahandler);
            return mimeBodyPart;
        } catch (final UnsupportedEncodingException ex) {
            throw new MessagingException("Failed to set attachment for message", ex);
        }
    }

    protected SMTPTransport connectTransport(final MailSettings settings, final Session session) throws MessagingException {
        String username = settings.getUser();
        String password = settings.getPassword();
        if ("".equals(username)) { // probably from a placeholder
            username = null;
            if ("".equals(password)) { // in conjunction with "" username, this means no password to use
                password = null;
            }
        }

        final SMTPTransport transport = getTransport(session);
        transport.connect(settings.getHost(), settings.getPort(), username, password);
        return transport;
    }

    protected SMTPTransport getTransport(final Session session) throws NoSuchProviderException {
        final boolean useSSL = prefs.getBoolean(MailServiceConstants.PREFERENCES_MAIL_USESSL, false);
        return (SMTPTransport) session.getTransport(useSSL ? "smtps" : "smtp");
    }

}
