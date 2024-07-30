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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * Container class for Mail Settings
 */
public class MailSettings {
    public static final String ADDRESS_SEPARATOR_CHAR = ";";
    private String user;
    private String password;
    private String host;

    private int port;
    private String templateText;
    private String subject;
    private String body;
    private String sender;
    private final List<String> receiversTo = new ArrayList<>();
    private final List<String> receiversCC = new ArrayList<>();
    private final List<String> receiversBCC = new ArrayList<>();
    private List<String> additionalDocs = new ArrayList<>();
    private String senderName;

    // field list
    public static final String FIELD_RECEIVERS_TO = "receiversTo";
    public static final String FIELD_RECEIVERS_CC = "receiversCC";
    public static final String FIELD_RECEIVERS_BCC = "receiversBCC";
    public static final String FIELD_RECEIVERS_SUBJECT = "subject";
    public static final String FIELD_RECEIVERS_BODY = "body";
    public static final String FIELD_RECEIVERS_ADDITIONALDOCS = "additionalDocs";

    /**
     * Checks if the mandatory fields are set
     * 
     * @return <code>true</code> if all necessary settings are set
     */
    public boolean isValid() {
        boolean receiversAreValid = areReceiversValid(FIELD_RECEIVERS_TO) && areReceiversValid(FIELD_RECEIVERS_CC) && areReceiversValid(FIELD_RECEIVERS_BCC);

        return StringUtils.isNoneEmpty(user, host, password, sender) && !receiversTo.isEmpty() && receiversAreValid;
    }

    public boolean areReceiversValid(final String fieldIdentifier) {
        List<String> receivers;
        switch (fieldIdentifier) {
        case FIELD_RECEIVERS_TO:
            receivers = receiversTo;
            break;
        case FIELD_RECEIVERS_CC:
            receivers = receiversCC;
            break;
        case FIELD_RECEIVERS_BCC:
            receivers = receiversBCC;
            break;
        default:
            receivers = Collections.<String> emptyList();
            break;
        }

        return receivers.stream().allMatch(e -> StringUtils.isBlank(e) || EmailValidator.getInstance().isValid(e));

    }

    public MailSettings withUser(final String user) {
        this.user = user;
        return this;
    }

    public MailSettings withPassword(final String pasword) {
        this.password = pasword;
        return this;
    }

    public MailSettings withSender(final String sender) {
        this.sender = sender;
        return this;
    }

    public MailSettings withSenderName(final String senderName) {
        this.senderName = senderName;
        return this;
    }

    public MailSettings withAdditionalDocs(final String... additionalDocs) {
        this.additionalDocs = Arrays.stream(additionalDocs).collect(Collectors.toList());
        return this;
    }

    public MailSettings withTemplateText(final String templateText) {
        this.templateText = templateText;
        return this;
    }

    public MailSettings withReceiversTo(final String... receiversTo) {
        this.receiversTo.addAll(Arrays.asList(receiversTo));
        return this;
    }

    public MailSettings withReceiversCC(final String... receiversCC) {
        this.receiversCC.addAll(Arrays.asList(receiversCC));
        return this;
    }

    public MailSettings withReceiversBCC(final String... receiversBCC) {
        this.receiversBCC.addAll(Arrays.asList(receiversBCC));
        return this;
    }

    public MailSettings withHost(final String host) {
        String[] splitted;
        if ((splitted = StringUtils.split(host, ":", 2)) != null && splitted.length == 2) {
            this.host = splitted[0];
            this.port = Integer.parseInt(splitted[1]);
        } else {
            this.host = host;
            this.port = MailServiceConstants.MAIL_SMTP_DEFAULT_PORT;
        }
        return this;
    }

    public MailSettings withSubject(final String subject) {
        this.subject = subject;
        return this;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String pasword) {
        this.password = pasword;
    }

    public List<String> getAdditionalDocs() {
        return additionalDocs;
    }

    public void addToAdditionalDocs(final String filter, final String... additionalDocs) {
        this.additionalDocs
                .addAll(Arrays.stream(additionalDocs).map(s -> !s.startsWith(filter) ? StringUtils.appendIfMissing(filter, File.separator) + s : s).toList());
    }

    public void addToAdditionalDocs(final List<String> additionalDocs) {
        this.additionalDocs.addAll(additionalDocs);
    }

    public void setAdditionalDocs(final List<String> additionalDocs) {
        this.additionalDocs = additionalDocs;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(final String templateText) {
        this.templateText = templateText;
    }

    public String getReceiversTo() {
        return String.join(ADDRESS_SEPARATOR_CHAR, receiversTo);
    }

    public void setReceiversTo(final String receivers) {
        if (receivers != null) {
            receiversTo.clear();
            Arrays.stream(receivers.split(ADDRESS_SEPARATOR_CHAR)).forEach(r -> receiversTo.add(StringUtils.trim(r)));
        }
    }

    public void setReceiversCC(final String receivers) {
        if (receivers != null) {
            receiversCC.clear();
            Arrays.stream(receivers.split(ADDRESS_SEPARATOR_CHAR)).forEach(r -> receiversCC.add(StringUtils.trim(r)));
        }
    }

    public void setReceiversBCC(final String receivers) {
        if (receivers != null) {
            receiversBCC.clear();
            Arrays.stream(receivers.split(ADDRESS_SEPARATOR_CHAR)).forEach(r -> receiversBCC.add(StringUtils.trim(r)));
        }
    }

    public String getReceiversCC() {
        return StringUtils.join(receiversCC, ADDRESS_SEPARATOR_CHAR);
    }

    public String getReceiversBCC() {
        return StringUtils.join(receiversBCC, ADDRESS_SEPARATOR_CHAR);
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getSender() {
        return sender;
    }

    public String getSenderWithName() {
        return String.format("%s <%s>", senderName, sender);
    }

    public String getSenderName() {
        return senderName;
    }

    public void removeFromAdditionalDocs(final String additionalDoc) {
        this.additionalDocs.remove(additionalDoc);
    }

    public String getBodyHtml() {
        return "<b>tbd</b>";
    }

}
