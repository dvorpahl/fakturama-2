/* 
 * Fakturama - Free Invoicing Software - http://fakturama.sebulli.com
 * 
 * Copyright (C) 2012 Gerd Bartelt
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Gerd Bartelt - initial API and implementation
 */

package com.sebulli.fakturama.calculate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.dao.DocumentsDAO;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.util.ContactUtil;

/**
 * This class can generate a customer statistic
 *  
 * @author Gerd Bartelt
 */
public class CustomerStatistics {
    
    @Inject
    private DocumentsDAO documentsDAO;
    
    @Inject
    private IDocumentAddressManager addressManager;
    
    @Inject
    private IEclipseContext context;
    
    @Inject
    private ContactsDAO contactsDAO;

	/**
     * @param contact the contact to set
     */
    public void setContact(DocumentReceiver documentReceiver) {
		if(documentReceiver.getOriginContactId() != null) {
			Contact contact = contactsDAO.findById(documentReceiver.getOriginContactId());
			this.contact = contact;
		}
    }

    public void setContact(final Contact contact) {
        this.contact = contact;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    // The customer has already ordered something
	private boolean isRegularCustomer = false;
	
	// How many orders
	private Integer ordersCount = 0;

	// How many invoices are not completely paid
	private Integer openInvoicesCount = 0;
	
	// The last date
	private Calendar lastOrderDate = null;

	// Some of the invoices
	private String invoices = "";
	
	// The total volume
	private Double total = Double.valueOf(0.0);

	// Remaining value of all invoices which are not completely paid
	private Double openTotal = Double.valueOf(0.0);
	
	// Customer to test
	private Contact contact = null;
	private String address = "";
	
	public CustomerStatistics() {}
	
	/**
	 * Constructor
	 * 		Generates a statistic
	 * @param 
	 * 		contactID of the customer
	 */
	public CustomerStatistics (DocumentReceiver documentReceiver) {
		this(documentReceiver, null);
	}
	
	/**
	 * Constructor
	 * 		Generates a statistic
	 * @param 
	 * 		contactID of the customer
	 * @param 
	 * 		firstAddressLine of the customer
	 */
	public CustomerStatistics (Contact contact, String address) {
		this.contact = contact;
		this.address = address;
	}
	
	public CustomerStatistics (DocumentReceiver documentReceiver, String address) {
		if(documentReceiver.getOriginContactId() != null) {
			Contact contact = contactsDAO.findById(documentReceiver.getOriginContactId());
			this.contact = contact;
		}
		this.address = address;
	}
	

	/**
	 * Make the Statistics. Search for other documents from this customer
	 * 
	 * @param 
	 * 		byID <code>true</code>:  Compare contact ID <br />
	 * 		     <code>false</code>: Compare also first line of address
	 */
	public void makeStatistics(boolean byID) {
		resetStatistics();
		// Get all undeleted documents
		// Paid and open invoices are needed for the compact customer summary.
	    // Compare the customer ID
		List<Invoice> documents = byID ? documentsDAO.findInvoicesForContact(contact) : documentsDAO.findPaidInvoices();
			ContactUtil contactUtil = byID ? null : ContextInjectionFactory.make(ContactUtil.class, context);

		// Export the document data
		for (Invoice document : documents) {

			boolean customerFound = false;

			if (byID) {
			    // in this case we found the document through documentsDAO, therefore it's always true
				customerFound = true;
			}
			JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();
			// Compare the the address
            if (!byID && address.length() > 10 && 
            		jaroWinklerDistance.apply(contactUtil.getAddressAsString(addressManager.getBillingAdress(document)), address) > 0.7) {
				customerFound = true;
			}
			
				if (customerFound) {
					if (!Boolean.TRUE.equals(document.getPaid())) {
						openInvoicesCount++;
						final double invoiceTotal = document.getTotalValue() != null ? document.getTotalValue() : 0.0;
						final double alreadyPaid = document.getPaidValue() != null ? document.getPaidValue() : 0.0;
						openTotal += Math.max(0.0, invoiceTotal - alreadyPaid);
						continue;
					}

					// It's a regular customer
					isRegularCustomer = true;

				// Add the invoice number to the list of invoices
				// Add maximum 4 invoices
				if (ordersCount < 4) {
					if (!invoices.isEmpty())
						invoices += ", ";
					invoices += document.getName();
				}
				else if (ordersCount == 4) {
					invoices += ", ...";
				}
				
				// Increment the count of orders
				ordersCount ++;
				
				// Increase the total
					total += document.getPaidValue() != null ? document.getPaidValue() : 0.0;
				
				// Get the date of the document and convert it to a
				// GregorianCalendar object.
				GregorianCalendar documentDate = new GregorianCalendar();
                // Use date 
                Date expenditureDateString = document.getOrderDate();

                // Do only parse non empty strings
                if (expenditureDateString != null) {
                	documentDate.setTime(expenditureDateString);

                	// Set the last order date
	                	if (lastOrderDate == null || documentDate.after(lastOrderDate)) {
	                		lastOrderDate = documentDate;
	                	}
                }
				}
			}
		}

		private void resetStatistics() {
			isRegularCustomer = false;
			ordersCount = 0;
			openInvoicesCount = 0;
			lastOrderDate = null;
			invoices = "";
			total = 0.0;
			openTotal = 0.0;
		}
	
	/**
	 * Returns whether the customer has already paid invoices
	 * 
	 * @return
	 * 		<code>true</code> if there are some paid invoices
	 */
	public boolean hasPaidInvoices() {
		return isRegularCustomer;
	}
	
	/**
	 * Returns how often the customer has paid an invoice
	 * 
	 * @return
	 * 		The number of the paid invoices
	 */
	public Integer getOrdersCount () {
		return ordersCount;
	}

	public Integer getOpenInvoicesCount() {
		return openInvoicesCount;
	}
	
	/**
	 * Returns the total value
	 * 
	 * @return
	 * 		The total value
	 */
	public Double getTotal () {
		return total;
	}

	public Double getOpenTotal() {
		return openTotal;
	}
	
	/**
	 * Returns the last date
	 * 
	 * @return
	 * 		The date of the last order
	 */
	public String getLastOrderDate() {
		if (lastOrderDate != null) {
		    Instant instant = lastOrderDate.toInstant();
		    LocalDate date = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate();
//		    LocalDate date = LocalDate.from(lastOrderDate.toInstant());
		    return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
			// DataUtils.getInstance().getDateTimeAsLocalString((GregorianCalendar) lastOrderDate);
		} else {
			return "-";
		}
	}

	public String getLastOrderMonth() {
		if (lastOrderDate == null) {
			return "-";
		}
		final LocalDate date = LocalDateTime.ofInstant(lastOrderDate.toInstant(), ZoneOffset.UTC).toLocalDate();
		return date.format(DateTimeFormatter.ofPattern("MM/yyyy"));
	}
	
	/**
	 * Returns the string with some of the invoices
	 * 
	 * @return
	 * 	String with invoice numbers
	 */
	public String getInvoices () {
		return invoices;
	}
}
