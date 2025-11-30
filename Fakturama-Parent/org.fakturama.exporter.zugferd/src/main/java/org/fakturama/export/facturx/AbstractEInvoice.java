/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2020 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * The Fakturama Team - initial API and implementation
 */

package org.fakturama.export.facturx;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.money.MonetaryAmount;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.fakturama.export.einvoice.IEinvoice;
import org.fakturama.export.einvoice.ZFMessages;
import org.fakturama.export.facturx.modelgen.ObjectFactory;

import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.DocumentReceiver;

/**
 *
 */
public abstract class AbstractEInvoice implements IEinvoice {
    /**
     * This is for distinguishing the different contact entries.
     *
     */
    public enum ContactType {
        SELLER, BUYER, DELIVERY
    }

    //  enum InvoiceeTradeParty { DERIVED, }
    public enum PriceType {
        GROSS_PRICE, NET_PRICE, NET_PRICE_DISCOUNTED
    }

    @Inject
    @Translation
    protected ZFMessages zfMsg;

    @Inject // node: org.fakturama.export.zugferd
    protected IPreferenceStore preferences;

    @Inject
    ILogger log;

    @Inject
    protected ILocaleService localeUtil;

    @Inject
    protected ContactsDAO contactsDAO;

    @Inject
    @org.eclipse.e4.core.di.annotations.Optional
    protected Shell shell;

    /** The Constant DEFAULT_PRICE_SCALE. */
    protected int customMoneyScale = 2;
    protected int customQuantityScale = 2;
    
    protected static DateTimeFormatter sdfDest = DateTimeFormatter.ofPattern("yyyyMMdd");
    protected Map<String, MonetaryAmount> netPricesPerVat = new HashMap<>();

    protected ObjectFactory factory;

    protected Contact getOriginContact(final DocumentReceiver contact) {
        if (contact.getOriginContactId() != null) {
            return contactsDAO.findById(contact.getOriginContactId());
        }
        return null;
    }

    @PostConstruct
    public void init() {
        customMoneyScale = preferences.getInt(Constants.PREFERENCES_GENERAL_CURRENCY_DECIMALPLACES);
        if (customMoneyScale < 0) {
            customMoneyScale = 2;
        }
        customQuantityScale = preferences.getInt(Constants.PREFERENCES_GENERAL_QUANTITY_DECIMALPLACES);
        if (customQuantityScale < 0) {
            customQuantityScale = 2;
        }
    }
}
