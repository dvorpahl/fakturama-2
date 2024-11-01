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
 * Contributors: Gerd Bartelt - initial API and implementation Gerd Schrick -
 * 202301 major rework
 */

package com.sebulli.fakturama.office;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.odftoolkit.helper.common.navigation.PlaceholderNavigation;
import org.odftoolkit.helper.common.navigation.PlaceholderNode;
import org.odftoolkit.helper.common.navigation.PlaceholderNode.PlaceholderNodeType;
import org.odftoolkit.helper.common.navigation.PlaceholderNode.PlaceholderTableType;
import org.odftoolkit.helper.common.navigation.PlaceholderParameters;
import org.odftoolkit.helper.common.navigation.TemplateParameter;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.doc.table.OdfTableCell;
import org.odftoolkit.odfdom.doc.table.OdfTableRow;
import org.odftoolkit.odfdom.dom.element.table.TableCoveredTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElementBase;
import org.odftoolkit.odfdom.dom.element.table.TableTableRowElement;
import org.odftoolkit.odfdom.dom.element.text.TextPlaceholderElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sebulli.fakturama.Activator;
import com.sebulli.fakturama.calculate.DocumentSummaryCalculator;
import com.sebulli.fakturama.converter.CommonConverter;
import com.sebulli.fakturama.dao.AddressDAO;
import com.sebulli.fakturama.dao.ContactsDAO;
import com.sebulli.fakturama.dao.DocumentReceiverDAO;
import com.sebulli.fakturama.dto.DocumentSummary;
import com.sebulli.fakturama.dto.Price;
import com.sebulli.fakturama.dto.Transaction;
import com.sebulli.fakturama.dto.VatSummaryItem;
import com.sebulli.fakturama.i18n.ILocaleService;
import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.log.ILogger;
import com.sebulli.fakturama.misc.Constants;
import com.sebulli.fakturama.misc.DataUtils;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.misc.IDateFormatterService;
import com.sebulli.fakturama.misc.INumberFormatterService;
import com.sebulli.fakturama.model.Address;
import com.sebulli.fakturama.model.BankAccount;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.model.ContactType;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.model.DocumentItem;
import com.sebulli.fakturama.model.DocumentReceiver;
import com.sebulli.fakturama.model.Dunning;
import com.sebulli.fakturama.model.IDocumentAddressManager;
import com.sebulli.fakturama.model.Invoice;
import com.sebulli.fakturama.model.Payment;
import com.sebulli.fakturama.model.Product;
import com.sebulli.fakturama.qrcode.QRCodeService;
import com.sebulli.fakturama.util.ContactUtil;
import com.sebulli.fakturama.util.DocumentTypeUtil;

/**
 * This class fills an OpenOffice template and replaces all the placeholders
 * with the document data.
 */
public class TemplateProcessor {

    public static final int COUNT_OF_LAST_SHOWN_DIGITS = 3;

    @Inject
    @Translation
    protected Messages msg;

    @Inject
    private IEclipseContext context;

    @Inject
    private ContactsDAO contactsDAO;

    @Inject
    private AddressDAO addressDAO;

    @Inject
    private ILogger log;

    @Inject
    private DocumentReceiverDAO documentReceiverDao;

    @Inject
    private IDateFormatterService dateFormatterService;

    @Inject
    private INumberFormatterService numberFormatterService;

    @Inject
    private ILocaleService localeUtil;

    @Inject
    private IPreferenceStore preferences;

    @Inject
    private IDocumentAddressManager addressManager;

    private QRCodeService qrCodeService;

    /**
     * Checks if the current editor uses sales equalization tax (this is only
     * needed for some customers).
     */
    private boolean useSET = false;

    private static NumberFormat localizedNumberFormat = NumberFormat.getInstance(Locale.getDefault());

    private ContactUtil contactUtil;

    @PostConstruct
    public void init() throws InvalidSyntaxException {
        contactUtil = ContextInjectionFactory.make(ContactUtil.class, context);
        final ServiceReference<QRCodeService> serviceReference = Activator.getContext().getServiceReference(QRCodeService.class);
        if (serviceReference != null) {
            qrCodeService = Activator.getContext().getService(serviceReference);
            ContextInjectionFactory.inject(qrCodeService, context);
        }
    }

    /**
     * Replaces all line breaks by a "-"
     * 
     * @param s
     *            The string in multiple lines
     * @param replacement
     *            The replacement
     * @return The string in one line, seperated by a "-"
     */
    private String StringInOneLine(String s, final String replacement) {
        // Convert CRLF to LF
        s = DataUtils.getInstance().convertCRLF2LF(s).trim();
        // Replace line feeds by a " - "
        s = s.replaceAll("\\n", replacement);
        return s;
    }

    /**
     * Trims the String and removes the quotation marks (if any)
     * 
     * @param s
     *            The string with quotation marks
     * @return The string without them
     */
    private String removeQuotationMarks(final String s) {
        return StringUtils.removeStart(StringUtils.removeEnd(s.trim(), "\""), "\"");
    }

    /**
     * Replace the placeholder values by a value in a list
     * 
     * @param replacements
     *            A list of replacements, separated by a ";" e.g.:
     *            {"Belgien","BEL";"Dänemark","DNK"}
     * @param value
     *            The input value
     * @return The modified value
     */
    private String replaceValues(String replacements, String value) {
        replacements = StringUtils.removeEnd(StringUtils.removeStart(replacements.trim(), "{"), "}");
        final String parts[] = replacements.split(";");

        // Nothing to do
        if (parts.length < 1) {
            return value;
        }

        // get all parts
        for (final String part : parts) {
            final String[] twoStrings = part.split(",");
            if (twoStrings.length == 2) {

                // Escape sequences...
                twoStrings[0] = TemplateProcessorHelper.decodeEntities(removeQuotationMarks(twoStrings[0]));

                // Replace the value, if it is equal to the entry
                if (DataUtils.getInstance().replaceAllAccentedChars(TemplateProcessorHelper.decodeEntities(value))
                        .equalsIgnoreCase(DataUtils.getInstance().replaceAllAccentedChars(twoStrings[0]))) {
                    value = removeQuotationMarks(twoStrings[1]);
                    return value;
                }
            }
        }

        return value;
    }

    /**
     * Replace the placeholder value by use of Java's
     * {@link String#replaceAll(String, String)}
     * 
     * @param replacement
     *            format: "regex", "replacement"
     * @param value
     *            The input value
     * @return The modified value
     */
    private String replaceRegex(final String replacement, final String value) {
        // split (comma may be used in the parameters)
        final String parts[] = replacement.split("\"\\s*,\\s*\"");

        // prepare params
        final String regex = TemplateProcessorHelper.decodeEntities(parts[0]);
        if (regex.length() < 1) {
            // nothing to do
            return value;
        }
        String replaceBy = parts.length >= 2 ? parts[1] : "";
        replaceBy = TemplateProcessorHelper.decodeEntities(replaceBy);

        try {
            return value.replaceAll(regex, replaceBy);
        } catch (final Exception e) {
            log.error("\nException in $REPLACEREGEX:" + replacement + "\nReason: " + e);
            return value;
        }
    }

    /**
     * Applies the parameters to the value</br>
     * (parameters in placeholder can be chained/repeated)
     * 
     * @param value
     *            The value the parameters are applied to
     * @param params
     *            the parameters container object
     * @return The value modified by the parameters</br>
     *         or the unmodified value if <code>params.isEmpty() == true</code>
     */
    String applyParameters(final String value, final PlaceholderParameters params) {
        String retval = value;
        if (!params.isEmpty()) {
            for (final TemplateParameter param : params.getParameters()) {
                if (StringUtils.isNotBlank(param.getKey())) {
                    // process the parameter
                    //   note: currently there are no (valid) params with an empty body
                    if (StringUtils.isNotEmpty(param.getBody())) {
                        if (StringUtils.isEmpty(retval)) {
                            // params operating on an empty value
                            //   note: this switch is currently not really needed, just in place for easy extension
                            switch (param.getKey()) {
                            case "EMPTY":
                                retval = param.getBody();
                                break;
                            default:
                                break; // do nothing
                            }
                        } else {
                            // params operating on a non-empty value
                            switch (param.getKey()) {
                            case "PRE":
                                retval = param.getBody() + retval;
                                break;
                            case "POST":
                                retval += param.getBody();
                                break;
                            case "INONELINE":
                                retval = StringInOneLine(retval, param.getBody());
                                break;
                            case "REPLACE":
                                retval = replaceValues(param.getBody(), retval);
                                break;
                            case "REPLACEREGEX": // GS/ [ADD TemplateParameters]
                                retval = replaceRegex(param.getBody(), retval);
                                break;
                            case "FORMAT":
                                try {
                                    final Double parsedDouble = localizedNumberFormat.parse(retval).doubleValue();
                                    retval = numberFormatterService.DoubleToDecimalFormatedValue(parsedDouble, param.getBody());
                                } catch (final ParseException e) {
                                    retval = "### NVL ###";
                                }
                                break;
                            case "DFORMAT":
                                try {
                                    final GregorianCalendar checkDate = dateFormatterService.getCalendarFromDateString(retval);
                                    final SimpleDateFormat sdf = new SimpleDateFormat(param.getBody());
                                    retval = sdf.format(checkDate.getTime());
                                } catch (final IllegalArgumentException e) {
                                    retval = "### NVL ###";
                                }
                                break;
                            case "FIRST":
                                final Integer lengthFIRST = TemplateProcessorHelper.parseInteger(param.getBody(), retval.length());
                                if (lengthFIRST.compareTo(Integer.valueOf(0)) >= 0) {
                                    final int len = lengthFIRST.compareTo(retval.length()) < 0 ? lengthFIRST : retval.length();
                                    retval = retval.substring(0, len);
                                }
                                break;
                            case "LAST":
                                final Integer lengthLAST = TemplateProcessorHelper.parseInteger(param.getBody(), retval.length());
                                if (lengthLAST.compareTo(Integer.valueOf(0)) >= 0) {
                                    final int len = lengthLAST.compareTo(retval.length()) < 0 ? lengthLAST : retval.length();
                                    retval = retval.substring(retval.length() - len);
                                }
                                break;
                            case "RANGE":
                                final String[] boundariesRANGE = param.getBody().split(",");
                                if (boundariesRANGE.length == 2) {
                                    // for customer convenience we start counting from 1
                                    final Integer start = TemplateProcessorHelper.parseInteger(boundariesRANGE[0], 0) - 1;
                                    final Integer end = TemplateProcessorHelper.parseInteger(boundariesRANGE[1], retval.length());
                                    if (end.compareTo(Integer.valueOf(0)) >= 0) {
                                        final int len = end.compareTo(retval.length()) < 0 ? end : retval.length();
                                        retval = len == 0 ? "" : retval.substring(start, len);
                                    }
                                }
                                break;
                            case "EXRANGE":
                                if (!param.getBody().isEmpty()) {
                                    final String[] boundariesEXRANGE = param.getBody().split(",");
                                    if (boundariesEXRANGE.length == 2) {
                                        // for customer convenience we start counting from 1
                                        final Integer start = TemplateProcessorHelper.parseInteger(boundariesEXRANGE[0], 0) - 1;
                                        final Integer end = TemplateProcessorHelper.parseInteger(boundariesEXRANGE[1], retval.length());
                                        if (end.compareTo(Integer.valueOf(0)) >= 0) {
                                            final int len = end.compareTo(retval.length()) < 0 ? end : retval.length();
                                            if (len == 0) {
                                                retval = "";
                                            } else {
                                                final String first = retval.substring(0, Math.max(0, start));
                                                final String last = retval.substring(len, retval.length());
                                                retval = first + last;
                                            }
                                        }
                                    }
                                }
                                break;
                            default:
                                break; // do nothing
                            }
                            // intermediate encode special entities that may have been added by a parameter processing
                            // so the retval is ready ('clean') for more processing (or return)
                            retval = TemplateProcessorHelper.decodeEntities(retval);
                        }
                    }
                }
            }
        }
        return retval;
    }

    /**
     * GS/ [TPR] legacy - only called in TemplateProcessorTest (shall be
     * updated)
     * 
     * @param placeholder
     *            The placeholder with parameter(s)
     * @param value
     *            The value
     * @return The value modified by the parameters
     */
    String applyParameters(final String placeholder, final String pValue) {
        return applyParameters(pValue, PlaceholderParameters.of(placeholder));
    }

    /**
     * Extract the placeholder values from a given document
     * 
     * @param document
     *            The document with all the values
     * @param placeholder
     *            The placeholder to extract
     * @return The extracted value
     */
    public String getDocumentInfo(final Document document, final Optional<DocumentSummary> documentSummary, final Placeholder placeholder) {
        final String value = getDocumentInfoByPlaceholder(document, documentSummary, placeholder);
        // GS/20221209 totally useless as <placeholder.getKey()> never contains a parameter !!!
        //		return interpretParameters(placeholder.getKey(), value);
        return value;
    }

    public String fill(final Document document, final Optional<DocumentSummary> documentSummaryOpt, final String template) {
        int startOfPlaceholder = template.indexOf(PlaceholderNavigation.PLACEHOLDER_PREFIX);
        if (startOfPlaceholder > -1) {
            final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // enable summaries in (mail) template
            final DocumentSummary documentSummary = documentSummaryOpt.orElseGet(() -> {
                final DocumentSummaryCalculator documentSummaryCalculator = ContextInjectionFactory.make(DocumentSummaryCalculator.class, context);
                return documentSummaryCalculator.calculate(document);
            });

            try {
                final DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
                final org.w3c.dom.Document doc = documentBuilder.newDocument();
                int endIndex = -1;
                String placeholderRaw = null;
                final StringBuffer result = new StringBuffer();
                while (startOfPlaceholder > -1) {
                    // transfer non-placeholder data
                    if (startOfPlaceholder > endIndex + 1) {
                        // at least one char
                        result.append(template.substring(endIndex + 1, startOfPlaceholder));
                    }
                    endIndex = template.indexOf(PlaceholderNavigation.PLACEHOLDER_SUFFIX, startOfPlaceholder);
                    if (endIndex > -1) {
                        // now we have start AND end pos of placeholder,
                        // get placeholder w/o < and >
                        placeholderRaw = template.substring(startOfPlaceholder, endIndex + 1);
                        // now handle the placeholder = replace with text
                        final PlaceholderNode placeholderNode = new PlaceholderNode(doc.createTextNode(placeholderRaw));
                        result.append(getTextForPlaceholder(placeholderNode, document, Optional.of(documentSummary)));
                        startOfPlaceholder = template.indexOf(PlaceholderNavigation.PLACEHOLDER_PREFIX, endIndex);
                    } else {
                        // now we have start but NO end pos, so it's not a placeholder
                        // and no more placeholder can follow, so add rest to result and finish
                        result.append(template.substring(startOfPlaceholder));
                        startOfPlaceholder = -1;
                    }
                }
                return result.toString();
            } catch (final ParserConfigurationException pcE) {
                log.error(pcE, "Cannot parse template " + StringUtils.wrapIfMissing(template, "'"));
                return template + "\nParserConfigurationException:\n" + pcE;
            }
        } else {
            // no placeholder, template = result document
            return template;
        }
    }

    /**
     * Fill a template document with values
     * 
     * @param textdoc
     * @param document
     * @param documentSummary
     */
    public void processTemplate(final OdfTextDocument textdoc, final Document document, final DocumentSummary documentSummary) {
        // check if we have to use sales equalization tax
        setUseSalesEquationTaxForDocument(documentReceiverDao.isSETEnabled(document));

        final PlaceholderNavigation navi = new PlaceholderNavigation().of(textdoc).withDelimiters(true)
                .withTableIdentifiers(PlaceholderTableType.ITEMS_TABLE, PlaceholderTableType.VATLIST_TABLE, PlaceholderTableType.SALESEQUALIZATIONTAX_TABLE)
                .withImageIdentifiers(Placeholder.INVOICE_SWISSCODE.getKey(), Placeholder.INVOICE_GIROCODE.getKey(), Placeholder.YOURCOMPANY_QRVCARD.getKey())
                .build();
        final List<PlaceholderNode> placeholderNodes = Collections.unmodifiableList(navi.getPlaceHolders());

        // A reference to the item and vat table
        final Set<String> processedTables = new HashSet<>();

        // Get the items of the UniDataSet document
        final List<DocumentItem> itemDataSets = document.getItems().stream().sorted((i1, i2) -> i1.getPosNr().compareTo(i2.getPosNr()))
                .collect(Collectors.toList());
        final Set<Node> nodesMarkedForRemoving = new HashSet<>();
        // Get the sign of this document ( + or -)
        final int sign = DocumentTypeUtil.findByBillingType(document.getBillingType()).getSign();

        for (final PlaceholderNode placeholderNode : placeholderNodes) {
            if (!placeholderNode.isPlaceholder()) {
                continue;
            }
            switch (placeholderNode.getNodeType()) {
            case NORMAL_NODE:
                // Remove the discount cells, if there is no discount set
                // Remove the Deposit & the Finalpayment Row if there is no Deposit
                if (StringUtils.startsWith(placeholderNode.getPlaceholderKey(), PlaceholderTableType.DISCOUNT_TABLE.getKey())
                        && documentSummary.getDiscountNet().isZero()
                        || StringUtils.startsWith(placeholderNode.getPlaceholderKey(), PlaceholderTableType.DEPOSIT_TABLE.getKey())
                                && documentSummary.getDeposit().isZero()) {
                    // store parent node for later removing
                    // we have to remember the parent node since the current node is replaced (could
                    // be orphaned)
                    final TableTableRowElement row = (TableTableRowElement) placeholderNode.findParentNode(TableTableRowElement.ELEMENT_NAME.getQName(),
                            placeholderNode.getNode());

                    // ah, but wait: sometimes the DEPOSIT placeholder isn't placed in a table...
                    if (row != null) {
                        nodesMarkedForRemoving.add(row);
                    }
                }
                // Replace all other placeholders
                placeholderNode.replaceWith(getTextForPlaceholder(placeholderNode, document, Optional.ofNullable(documentSummary)));
                break;
            case IMAGE_NODE:
                final String text = getTextForPlaceholder(placeholderNode, document, Optional.ofNullable(documentSummary));
                if (StringUtils.isNotBlank(text)) {
                    final URI imageFileURI = Path.of(text).toUri();
                    final Pair<Integer, Integer> widthHeight = getCustomImageSize(imageFileURI, placeholderNode);
                    placeholderNode.replaceWith(imageFileURI, widthHeight.getLeft(), widthHeight.getRight());
                }
                break;
            case TABLE_NODE:
                // process only if that table wasn't processed
                // but wait: a table (e.g., "ITEM" table) could occur more than once!
                if (!processedTables.contains(placeholderNode.getNode().getUserData("TABLE_ID"))) {
                    // get the complete row with placeholders and store it as a template
                    final OdfTableRow pRowTemplate = navi.getTableRow(placeholderNode);
                    final OdfTable pTable = pRowTemplate.getTable();
                    // for each item from items list create a row and replace the placeholders
                    // which table?
                    switch (placeholderNode.getTableType()) {
                    case ITEMS_TABLE:
                        // Fill the item table with the items
                        /*
                         * Attention: Not only the current placeholderNode is replaced in this step, but
                         * also *all* other placeholders belonging to this item table! Therefore we have
                         * to skip all the other items placeholder in this table template. We
                         * distinguish the placeholders for a certain table by its user data field.
                         */
                        fillItemTableWithData(itemDataSets, pTable, pRowTemplate, sign);
                        break;
                    case VATLIST_TABLE:
                        fillVatTableWithData(documentSummary, pTable, pRowTemplate, placeholderNode.getTableType(), false);
                        break;
                    case SALESEQUALIZATIONTAX_TABLE:
                        fillVatTableWithData(documentSummary, pTable, pRowTemplate, placeholderNode.getTableType(), true);
                        break;
                    default:
                        break;
                    }

                    // delete the template row from table
                    pTable.removeRowsByIndex(pRowTemplate.getRowIndex(), 1);

                    // determine type of this table and store it
                    processedTables.add((String) placeholderNode.getNode().getUserData("TABLE_ID"));
                }
                break;

            default:
                break;
            }
        }

        //        for (Node removeNode : nodesMarkedForRemoving) {
        //            removeNode.getParentNode().removeChild(removeNode);
        //        }

    }

    /**
     * Get the text (replacement) for a placeholder<br>
     * from the property in the property list.<br>
     * The text is 'ready-to-use' (= parameters already applied)
     * 
     * @param placeholder
     *            The complete placeholder (incl. parameters, if any) from the
     *            document
     * @return the text for the placeholder
     */
    private String getTextForPlaceholder(final PlaceholderNode placeholderNode, final Document document, final Optional<DocumentSummary> documentSummary) {
        final Placeholder p = Placeholder.valueOfKey(placeholderNode.getPlaceholderKey());
        String text = null;
        if (p != null) {
            text = getDocumentInfoByPlaceholder(document, documentSummary, p);
            // If the String is non empty, replace the OS new line with the OpenOffice new line
            if (StringUtils.isNotBlank(text)) {
                text = text.replaceAll("\n", "\r");
            }
            if (placeholderNode.getNodeType() == PlaceholderNodeType.NORMAL_NODE) {
                text = applyParameters(text, placeholderNode.getParameters());
            }
        }
        return text;
    }

    /**
     * Get Information from document. If there is no reference to a customer,
     * use the address field to Extract the address
     * 
     * @param document
     *            The document
     * @param key
     *            The key to extract
     * @return The extracted result
     */
    private String getDocumentInfoByPlaceholder(final Document document, final Optional<DocumentSummary> documentSummary, final Placeholder placeholder) {
        final String key = placeholder.getKey();

        if (key.startsWith("YOURCOMPANY")) {
            // Get the company information from the preferences
            final String owner = preferences.getString(Constants.PREFERENCES_YOURCOMPANY_OWNER);
            final String streetWithNo = preferences.getString(Constants.PREFERENCES_YOURCOMPANY_STREET);
            switch (placeholder) {
            case YOURCOMPANY_COMPANY:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_NAME);
            case YOURCOMPANY_OWNER:
                return owner;
            case YOURCOMPANY_OWNER_FIRSTNAME:
                return contactUtil.getFirstName(owner);
            case YOURCOMPANY_OWNER_LASTNAME:
                return contactUtil.getLastName(owner);

            case YOURCOMPANY_STREET:
                return streetWithNo;
            case YOURCOMPANY_STREETNAME:
                return contactUtil.getStreetName(streetWithNo);
            case YOURCOMPANY_STREETNO:
                return contactUtil.getStreetNo(streetWithNo);

            case YOURCOMPANY_ZIP:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_ZIP);
            case YOURCOMPANY_CITY:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CITY);
            case YOURCOMPANY_COUNTRY:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_COUNTRY);
            case YOURCOMPANY_EMAIL:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_EMAIL);
            case YOURCOMPANY_PHONE:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TEL);
            case YOURCOMPANY_PHONE_PRE:
                return TemplateProcessorHelper.getTelPrePost(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TEL), true);
            case YOURCOMPANY_PHONE_POST:
                return TemplateProcessorHelper.getTelPrePost(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TEL), false);
            case YOURCOMPANY_MOBILE:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_MOBILE);
            case YOURCOMPANY_FAX:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_FAX);
            case YOURCOMPANY_FAX_PRE:
                return TemplateProcessorHelper.getTelPrePost(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_FAX), true);
            case YOURCOMPANY_FAX_POST:
                return TemplateProcessorHelper.getTelPrePost(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_FAX), false);
            case YOURCOMPANY_WEBSITE:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_WEBSITE);
            case YOURCOMPANY_VATNR:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_VATNR);
            case YOURCOMPANY_TAXNR:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TAXNR);
            case YOURCOMPANY_TAXOFFICE:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_TAXOFFICE);
            case YOURCOMPANY_BANKACCOUNTNR:
                return preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR");
            case YOURCOMPANY_BANKCODE:
                return preferences.getString("YOURCOMPANY_COMPANY_BANKCODE");
            case YOURCOMPANY_BANK:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BANK);
            case YOURCOMPANY_IBAN:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN);
            case YOURCOMPANY_BIC:
                return preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BIC);
            case YOURCOMPANY_QRVCARD:
                return createImageFile(qrCodeService.createVCardQRCode(document), "png").toString();
            default:
                break;
            }
        }

        if (document == null) {
            return null;
        }

        if (key.equals("DOCUMENT.DATE")) {
            return dateFormatterService.getFormattedLocalizedDate(document.getDocumentDate());
        }
        if (key.equals("DOCUMENT.ADDRESSES.EQUAL")) {
            return (contactUtil.deliveryAddressEqualsBillingAddress(document)).toString();
        }

        // Get address and delivery address
        // with option "DIFFERENT" and without
        String deliverystring;
        String differentstring;
        // address and delivery address
        final DocumentReceiver billingAdress = addressManager.getBillingAdress(document);
        final DocumentReceiver deliveryAdress = addressManager.getDeliveryAdress(document);
        for (int i = 0; i < 2; i++) {
            String s;
            deliverystring = i == 1 ? "delivery" : "";
            if (i == 1) {
                s = contactUtil.getAddressAsString(deliveryAdress);
            } else {
                s = contactUtil.getAddressAsString(billingAdress);
            }

            //  with option "DIFFERENT" and without
            for (int ii = 0; ii < 2; ii++) {
                differentstring = ii == 1 ? ".DIFFERENT" : "";
                if (ii == 1 && contactUtil.deliveryAddressEqualsBillingAddress(document)) {
                    s = "";
                }
                if (key.equals("DOCUMENT" + differentstring + "." + deliverystring.toUpperCase() + "ADDRESS")) {
                    return s;
                }
            }
        }

        // Get information from the document
        if (key.equals("DOCUMENT.TYPE")) {
            return msg.getMessageFromKey(DocumentTypeUtil.findByBillingType(document.getBillingType()).getSingularKey());
        }
        if (key.equals("DOCUMENT.NAME")) {
            return document.getName();
        }
        if (key.equals("DOCUMENT.CUSTOMERREF")) {
            return document.getCustomerRef();
        }
        if (key.equals("DOCUMENT.CONSULTANT")) {
            return billingAdress.getConsultant();
        }
        if (key.equals("DOCUMENT.SERVICEDATE")) {
            return dateFormatterService.getFormattedLocalizedDate(document.getServiceDate());
        }
        if (key.equals("DOCUMENT.MESSAGE")) {
            return document.getMessage();
        }
        if (key.equals("DOCUMENT.MESSAGE1")) {
            return document.getMessage();
        }
        if (key.equals("DOCUMENT.MESSAGE2")) {
            return document.getMessage2();
        }
        if (key.equals("DOCUMENT.MESSAGE3")) {
            return document.getMessage3();
        }
        if (key.equals("DOCUMENT.TRANSACTION")) {
            return Optional.ofNullable(document.getTransactionId()).orElse(Integer.valueOf(0)).toString();
        }
        if (key.equals("DOCUMENT.INVOICE")) {
            return document.getInvoiceReference() != null ? document.getInvoiceReference().getName() : "";
        }
        if (key.equals("DOCUMENT.WEBSHOP.ID")) {
            return document.getWebshopId();
        }
        if (key.equals("DOCUMENT.WEBSHOP.DATE")) {
            return dateFormatterService.getFormattedLocalizedDate(document.getWebshopDate());
        }
        if (key.equals("DOCUMENT.ORDER.DATE")) {
            return dateFormatterService.getFormattedLocalizedDate(document.getOrderDate());
        }
        if (key.equals("DOCUMENT.VESTINGPERIOD.START")) {
            return dateFormatterService.getFormattedLocalizedDate(document.getVestingPeriodStart());
        }
        if (key.equals("DOCUMENT.VESTINGPERIOD.END")) {
            return dateFormatterService.getFormattedLocalizedDate(document.getVestingPeriodEnd());
        }
        if (key.equals("DOCUMENT.ITEMS.GROSS")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getItemsGross()) : "";
        }

        if (key.equals("DOCUMENT.ITEMS.NET")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getItemsNet()) : "";
        }

        // FAK-432
        // discount is negative
        if (key.equals("DOCUMENT.ITEMS.NET.DISCOUNTED")) {
            return documentSummary.isPresent()
                    ? numberFormatterService.formatCurrency(documentSummary.get().getItemsNet().add(documentSummary.get().getDiscountNet()))
                    : "";
        }
        if (key.equals("DOCUMENT.TOTAL.NET")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalNet()) : "";
        }
        if (key.equals("DOCUMENT.TOTAL.VAT")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalVat()) : "";
        }
        if (key.equals("DOCUMENT.TOTAL.GROSS")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalGross()) : "";
        }
        if (key.equals("DOCUMENT.TOTAL.QUANTITY")) {
            return documentSummary.isPresent() ? Double.toString(documentSummary.get().getTotalQuantity()) : ""; // FAK-410
        }
        if (key.equals("DOCUMENT.ITEMS.COUNT")) {
            return String.format("%d", document.getItems().size());
        }

        try {
            if (key.equals("INVOICE.SWISSCODE")) {
                if (document instanceof Invoice) {
                    final Path imageFile = createImageFile(qrCodeService.createSwissCodeQR((Invoice) document), "png");
                    return imageFile != null ? imageFile.toString() : "";
                } else {
                    return "";
                }
            }

            if (key.equals("INVOICE.GIROCODE")) {
                if (document instanceof Invoice) {
                    final Path imageFile = createImageFile(qrCodeService.createGiroCode((Invoice) document), "png");
                    return imageFile != null ? imageFile.toString() : "";
                } else {
                    return "";
                }
            }
        } catch (final InvalidParameterException e) {
            final StringBuilder msgDetail = new StringBuilder("Bei der Erstellung des SWISS-QR-Codes sind folgende Fehler aufgetreten:\n\n");

            Arrays.stream(StringUtils.split(e.getMessage(), '\n')).forEach(m -> addDetailError(msgDetail, m));

            MessageDialog.openError(null, msg.dialogMessageboxTitleError, msgDetail.toString());
            return "";
        }

        if (key.startsWith("DOCUMENT.WEIGHT")) {
            if (key.equals("DOCUMENT.WEIGHT.TARA")) {
                return numberFormatterService.doubleToFormattedQuantity(document.getTara());
            }
            final double netWeightValue = document.getItems().stream().mapToDouble(d -> d.getWeight() != null ? d.getWeight() : Double.valueOf(0.0)).sum();
            if (key.equals("DOCUMENT.WEIGHT.NET")) {
                return numberFormatterService.doubleToFormattedQuantity(netWeightValue);
            }
            final Double taraValue = document.getTara() != null ? document.getTara() : Double.valueOf(0.0);
            if (key.equals("DOCUMENT.WEIGHT.TOTAL")) {
                return numberFormatterService.doubleToFormattedQuantity(netWeightValue + taraValue);
            }
        }

        if (key.equals("DOCUMENT.DEPOSIT.DEPOSIT")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getDeposit()) : "";
        }
        if (key.equals("DOCUMENT.DEPOSIT.FINALPAYMENT")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getFinalPayment()) : "";
        }
        if (key.equals("DOCUMENT.DEPOSIT.DEP_TEXT")) {
            return preferences.getString(Constants.PREFERENCES_DEPOSIT_TEXT);
        }
        if (key.equals("DOCUMENT.DEPOSIT.FINALPMT_TEXT")) {
            return preferences.getString(Constants.PREFERENCES_FINALPAYMENT_TEXT);
        }

        if (key.equals("ITEMS.DISCOUNT.PERCENT")
                && Optional.ofNullable(document.getItemsRebate()).orElse(NumberUtils.DOUBLE_ZERO).compareTo(NumberUtils.DOUBLE_ZERO) != 0) {
            Double itemsRebate = document.getItemsRebate();
            if (itemsRebate != null && itemsRebate < NumberUtils.DOUBLE_ZERO) {
                itemsRebate *= NumberUtils.DOUBLE_MINUS_ONE; // make rebate positive (see https://bugs.fakturama.info/view.php?id=937)
            }

            return numberFormatterService.DoubleToFormatedPercent(itemsRebate);
        }
        if (key.equals("ITEMS.DISCOUNT.NET")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getDiscountNet()) : "";
        }
        if (key.equals("ITEMS.DISCOUNT.GROSS")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getDiscountGross()) : "";
        }

        if (document.getPayment() != null) {
            if (key.equals("ITEMS.DISCOUNT.DAYS")) {
                return document.getPayment().getDiscountDays().toString();
            }
            if (key.equals("ITEMS.DISCOUNT.DUEDATE")) {
                return getDiscountDueDate(document);
            }
            final double percent = document.getPayment().getDiscountValue();
            if (key.equals("ITEMS.DISCOUNT.DISCOUNTPERCENT")) {
                return numberFormatterService.DoubleToFormatedPercent(percent);
            }
            if (key.equals("ITEMS.DISCOUNT.VALUE")) {
                return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalGross().multiply(1 - percent)) : "";
            }
            if (key.equals("ITEMS.DISCOUNT.NETVALUE")) {
                return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalNet().multiply(1 - percent)) : "";
            }
            if (key.equals("ITEMS.DISCOUNT.TARAVALUE")) {
                return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalVat().multiply(1 - percent)) : "";
            }

            if (key.equals("PAYMENT.TEXT")) {

                // Replace the placeholders in the payment text
                return createPaymentText(document, documentSummary, percent);
            }
        }

        if (key.equals("SHIPPING.NET")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getShippingNet()) : "";
        }
        if (key.equals("SHIPPING.VAT")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getShippingVat()) : "";
        }
        if (key.equals("SHIPPING.GROSS")) {
            return documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getShippingGross()) : "";
        }
        if (key.equals("SHIPPING.NAME")) {
            return document.getShipping() != null ? document.getShipping().getName() : "";
        }
        if (key.equals("SHIPPING.DESCRIPTION")) {
            return document.getShipping() != null ? document.getShipping().getDescription() : document.getAdditionalInfo().getShippingDescription();
        }
        if (key.equals("SHIPPING.VAT.DESCRIPTION")) {
            return document.getShipping() != null ? document.getShipping().getShippingVat().getDescription() : "";
        }
        if (key.equals("DOCUMENT.DUNNING.LEVEL") && document.getBillingType() == BillingType.DUNNING) {
            return ((Dunning) document).getDunningLevel().toString();
        }

        // Get the reference string to other documents
        if (key.startsWith("DOCUMENT.REFERENCE.")) {
            final Transaction transaction = ContextInjectionFactory.make(Transaction.class, context).of(document);
            if (transaction != null) {
                switch (key) {
                case "DOCUMENT.REFERENCE.OFFER":
                    return transaction.getReference(DocumentType.OFFER);
                case "DOCUMENT.REFERENCE.ORDER":
                    return transaction.getReference(DocumentType.ORDER);
                case "DOCUMENT.REFERENCE.CONFIRMATION":
                    return transaction.getReference(DocumentType.CONFIRMATION);
                case "DOCUMENT.REFERENCE.INVOICE":
                    return transaction.getReference(DocumentType.INVOICE);
                case "DOCUMENT.REFERENCE.INVOICE.DATE":
                    return transaction.getFirstReferencedDocumentDate(DocumentType.INVOICE);
                //				case "DOCUMENT.REFERENCE.INVOICE.DUEDATE":
                //					return transaction.getFirstReferencedDocumentDueDate(DocumentType.INVOICE);
                case "DOCUMENT.REFERENCE.DELIVERY":
                    return transaction.getReference(DocumentType.DELIVERY);
                case "DOCUMENT.REFERENCE.CREDIT":
                    return transaction.getReference(DocumentType.CREDIT);
                case "DOCUMENT.REFERENCE.DUNNING":
                    return transaction.getReference(DocumentType.DUNNING);
                case "DOCUMENT.REFERENCE.PROFORMA":
                    return transaction.getReference(DocumentType.PROFORMA);
                }
            }
        }

        //setProperty("PAYMENT.NAME", document.getStringValueByKey("paymentname"));
        if (key.equals("PAYMENT.DESCRIPTION")) {
            return document.getPayment() != null ? document.getPayment().getDescription() : document.getAdditionalInfo().getPaymentDescription();
        }
        if (key.equals("PAYMENT.PAID.VALUE")) {
            return numberFormatterService.DoubleToFormatedPriceRound(document.getPaidValue());
        }
        if (key.equals("PAYMENT.PAID.DATE")) {
            return dateFormatterService.getFormattedLocalizedDate(document.getPayDate());
        }
        if (key.equals("PAYMENT.DUE.DAYS")) {
            return Integer.toString(document.getDueDays());
        }
        if (key.equals("PAYMENT.DUE.DATE")) {
            final LocalDateTime newDate = DataUtils.getInstance().addToDate(document.getDocumentDate(), document.getDueDays());
            return newDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        }
        if (key.equals("PAYMENT.PAID")) {
            return BooleanUtils.toStringTrueFalse(document.getPaid());
        }

        String key2;
        String addressField;

        final String deliveryPrefix = "DELIVERY.";
        if (key.startsWith(deliveryPrefix)) {
            key2 = key.substring(deliveryPrefix.length());
            addressField = deliveryAdress != null ? contactUtil.getAddressAsString(deliveryAdress) : contactUtil.getAddressAsString(billingAdress);
        } else {
            key2 = key;
            addressField = contactUtil.getAddressAsString(billingAdress);
        }

        if (key2.equals("ADDRESS.FIRSTLINE")) {
            return document.getAddressFirstLine();
        }

        // Get the contact of the UniDataSet document
        DocumentReceiver contact = billingAdress;
        // There is a reference to a contact. Use this (but only if it's a valid contact!)
        if (contact != null && (key.startsWith("ADDRESS") || key.startsWith(deliveryPrefix))) {
            Optional<String> checked = checkAddressPlaceholders(contact, key, ContactType.BILLING);
            if (checked.isPresent()) {
                return checked.get();
            }

            // now switch to delivery contact, if any
            if (deliveryAdress != null) {
                contact = deliveryAdress;
                // if no delivery contact is available, use billing contact
            }
            checked = checkAddressPlaceholders(contact, key, ContactType.DELIVERY);
            if (checked.isPresent()) {
                return checked.get();
            }
        }
        // There is no reference - Try to get the information from the address field
        else {
            if (key2.equals("ADDRESS.FIRSTLINE")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_ADDRESSFIRSTLINE);
            }
            if (key2.equals("ADDRESS.NAME")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_NAME);
            }
            if (key2.equals("ADDRESS.FIRSTNAME")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_FIRSTNAME);
            }
            if (key2.equals("ADDRESS.LASTNAME")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_LASTNAME);
            }
            if (key2.equals("ADDRESS.COMPANY")) {
                return contactUtil.getDataFromAddressField(addressField, "company");
            }
            if (key2.equals("ADDRESS.STREET")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_STREET);
            }
            if (key2.equals("ADDRESS.STREETNAME")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_STREETNAME);
            }
            if (key2.equals("ADDRESS.STREETNO")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_STREETNO);
            }
            if (key2.equals("ADDRESS.ZIP")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_ZIP);
            }
            if (key2.equals("ADDRESS.CITY")) {
                return contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_CITY);
            }
            final String country = contactUtil.getDataFromAddressField(addressField, ContactUtil.KEY_COUNTY);
            if (key2.equals("ADDRESS.COUNTRY")) {
                return country;
            }

            final Optional<Locale> locale = localeUtil.findLocaleByDisplayCountry(country);
            if (key2.equals("ADDRESS.COUNTRY.CODE2")) {
                return locale.orElseGet(() -> localeUtil.getDefaultLocale()).getCountry();
            }
            if (key2.equals("ADDRESS.COUNTRY.CODE3")) {
                return locale.orElseGet(() -> localeUtil.getDefaultLocale()).getISO3Country();
            }

            if (key2.equals("ADDRESS.GREETING")) {
                return contactUtil.getCommonGreeting();
            }

            // indeterminable fields in this case
            if (key.equals("ADDRESS.BANK.ACCOUNT.HOLDER") || key.equals("ADDRESS.BANK.ACCOUNT") || key.equals("ADDRESS.BANK.CODE")
                    || key.equals("ADDRESS.BANK.NAME") || key.equals("ADDRESS.BANK.IBAN") || key.equals("ADDRESS.BANK.BIC") || key.equals("ADDRESS.NR")
                    || key.equals("ADDRESS.PHONE") || key.equals("ADDRESS.PHONE.PRE") || key.equals("ADDRESS.PHONE.POST") || key.equals("ADDRESS.FAX")
                    || key.equals("ADDRESS.FAX.PRE") || key.equals("ADDRESS.FAX.POST") || key.equals("ADDRESS.MOBILE") || key.equals("ADDRESS.MOBILE.PRE")
                    || key.equals("ADDRESS.MOBILE.POST") || key.equals("ADDRESS.EMAIL") || key.equals("ADDRESS.WEBSITE") || key.equals("ADDRESS.VATNR")
                    || key.equals("ADDRESS.NOTE") || key.equals("ADDRESS.GENDER") || key.equals("ADDRESS.TITLE") || key.equals("ADDRESS.DISCOUNT")) {
                return "";
            }
        }

        return null;
    }

    private void addDetailError(final StringBuilder msgDetail, final String errorMessage) {
        if (errorMessage.contains(":")) {
            final String[] splittedString = errorMessage.split(":");
            msgDetail.append(getMsgKey(splittedString[0])).append(": ").append(getMessageDetail(splittedString[1])).append('\n');
        }
    }

    private String getMsgKey(final String messageDetail) {
        switch (messageDetail.trim()) {
        case "account":
            return msg.commonFieldAccount;
        case "creditor.addressLine1":
            return "creditor address first line";
        case "creditor.postalCode":
            return msg.commonFieldZipcode;
        case "creditor.town":
            return msg.commonFieldCity;
        default:
            return messageDetail;
        }
    }

    private String getMessageDetail(final String messageKey) {
        switch (messageKey.trim()) {
        case "account_iban_invalid":
            return "invalid IBAN";
        case "address_type_conflict":
            return "wrong address type";
        default:
            return messageKey;
        }
    }

    private Optional<String> checkAddressPlaceholders(final DocumentReceiver contact, String key, final ContactType billing) {
        if (key.startsWith(billing.getName())) {
            key = key.replaceAll(billing.getName() + "\\.", "");
        }
        if (key.equals("ADDRESS")) {
            return Optional.ofNullable(contactUtil.getAddressAsString(contact));
        }
        if (key.equals("ADDRESS.GENDER")) {
            return Optional.ofNullable(contactUtil.getGenderString(contact));
        }
        if (key.equals("ADDRESS.GREETING")) {
            return Optional.ofNullable(contactUtil.getGreeting(contact));
        }
        if (key.equals("ADDRESS.TITLE")) {
            return Optional.ofNullable(contact.getTitle());
        }
        if (key.equals("ADDRESS.NAME")) {
            return Optional.ofNullable(contactUtil.getFirstAndLastName(contact));
        }
        if (key.equals("ADDRESS.NAMEWITHCOMPANY")) {
            return Optional.ofNullable(contactUtil.getNameWithCompany(contact));
        }
        if (key.equals("ADDRESS.FIRSTANDLASTNAME")) {
            return Optional.ofNullable(contactUtil.getFirstAndLastName(contact));
        }
        if (key.equals("ADDRESS.FIRSTNAME")) {
            return Optional.ofNullable(contact.getFirstName());
        }
        if (key.equals("ADDRESS.LASTNAME")) {
            return Optional.ofNullable(contact.getName());
        }
        if (key.equals("ADDRESS.COMPANY")) {
            return Optional.ofNullable(contact.getCompany());
        }

        if (key.equals("ADDRESS.STREET")) {
            return Optional.ofNullable(contact.getStreet());
        }
        if (key.equals("ADDRESS.STREETNAME")) {
            return Optional.ofNullable(contactUtil.getStreetName(contact.getStreet()));
        }
        if (key.equals("ADDRESS.STREETNO")) {
            return Optional.ofNullable(contactUtil.getStreetNo(contact.getStreet()));
        }
        if (key.equals("ADDRESS.ZIP")) {
            return Optional.ofNullable(contact.getZip());
        }
        if (key.equals("ADDRESS.CITY")) {
            return Optional.ofNullable(contact.getCity());
        }
        if (key.equals("ADDRESS.COUNTRY.CODE2")) {
            return Optional.ofNullable(contact.getCountryCode());
        }

        final Optional<Locale> locale = localeUtil.findByCode(contact.getCountryCode());
        if (key.equals("ADDRESS.COUNTRY")) {
            return Optional.ofNullable(locale.isPresent() ? locale.get().getDisplayCountry() : "??");
        }
        if (key.equals("ADDRESS.COUNTRY.CODE3")) {
            return Optional.ofNullable(locale.isPresent() ? locale.get().getISO3Country() : "???");
        }

        Contact originContact;
        originContact = contact.getOriginContactId() != null ? contactsDAO.findById(contact.getOriginContactId()) : null;
        if (originContact != null) {
            switch (key) {
            case "ADDRESS.ALIAS":
                return Optional.ofNullable(originContact.getAlias());
            case "ADDRESS.REGISTERNUMBER":
                return Optional.ofNullable(originContact.getRegisterNumber());
            case "ADDRESS.WEBSITE":
                return Optional.ofNullable(originContact.getWebsite());
            case "ADDRESS.WEBSHOPUSER":
                return Optional.ofNullable(originContact.getWebshopName());
            case "ADDRESS.VATNR":
                return Optional.ofNullable(originContact.getVatNumber());
            case "ADDRESS.NOTE":
                return Optional.ofNullable(originContact.getNote());
            case "ADDRESS.RELIABLILITY":
                return Optional.ofNullable(contactUtil.getReliabilityString(originContact.getReliability()));
            case "ADDRESS.PAYMENT":
                return originContact.getPayment() != null ? Optional.ofNullable(originContact.getPayment().getName()) : Optional.empty();
            case "ADDRESS.HASSALESEQTAX":
                return Optional.ofNullable(BooleanUtils.toStringTrueFalse(originContact.getUseSalesEqualizationTax()));

            // to be continued...
            default:
                break;
            }
            if (key.equals("ADDRESS.BIRTHDAY")) {
                return Optional
                        .ofNullable(originContact.getBirthday() == null ? "" : dateFormatterService.getFormattedLocalizedDate(originContact.getBirthday()));
            }

            if (key.equals("ADDRESS.DISCOUNT")) {
                return Optional
                        .ofNullable(numberFormatterService.DoubleToFormatedPercent(Optional.ofNullable(originContact.getDiscount()).orElse(Double.valueOf(0))));
            }
            if (key.equals("ADDRESS.MANDATEREFERENCE")) {
                return Optional.ofNullable(originContact.getMandateReference());
            }

            if (contact.getOriginAddressId() != null) {
                final Address address = addressDAO.findById(contact.getOriginAddressId());
                if (address != null) {
                    switch (key) {
                    case "ADDRESS.NAMESUFFIX":
                    case "ADDRESS.NAMEADDON":
                        return Optional.ofNullable(address.getName());
                    case "ADDRESS.CITYADDON":
                        return Optional.ofNullable(address.getCityAddon());
                    case "ADDRESS.ADDRESSADDON":
                        return Optional.ofNullable(address.getAddressAddon());
                    case "ADDRESS.PHONE2":
                        return Optional.ofNullable(address.getAdditionalPhone());
                    case "ADDRESS.LOCALCONSULTANT":
                        return Optional.ofNullable(address.getLocalConsultant());
                    case "ADDRESS.EMAIL":
                        return Optional.ofNullable(address.getEmail());
                    case "ADDRESS.PHONE":
                        return Optional.ofNullable(address.getPhone());
                    case "ADDRESS.PHONE.PRE":
                        return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(address.getPhone(), true));
                    case "ADDRESS.PHONE.POST":
                        return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(address.getPhone(), false));
                    case "ADDRESS.FAX":
                        return Optional.ofNullable(address.getFax());
                    case "ADDRESS.FAX.PRE":
                        return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(address.getFax(), true));
                    case "ADDRESS.FAX.POST":
                        return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(address.getFax(), false));
                    case "ADDRESS.MOBILE":
                        return Optional.ofNullable(address.getMobile());
                    case "ADDRESS.MOBILE.PRE":
                        return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(address.getMobile(), true));
                    case "ADDRESS.MOBILE.POST":
                        return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(address.getMobile(), false));
                    default:
                        break;
                    }
                }
            } else {
                // use OLD contact fields (deprecated!)
                if (key.equals("ADDRESS.PHONE")) {
                    return Optional.ofNullable(contact.getPhone());
                }
                if (key.equals("ADDRESS.PHONE.PRE")) {
                    return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(contact.getPhone(), true));
                }
                if (key.equals("ADDRESS.PHONE.POST")) {
                    return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(contact.getPhone(), false));
                }
                if (key.equals("ADDRESS.FAX")) {
                    return Optional.ofNullable(contact.getFax());
                }
                if (key.equals("ADDRESS.FAX.PRE")) {
                    return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(contact.getFax(), true));
                }
                if (key.equals("ADDRESS.FAX.POST")) {
                    return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(contact.getFax(), false));
                }
                if (key.equals("ADDRESS.MOBILE")) {
                    return Optional.ofNullable(contact.getMobile());
                }
                if (key.equals("ADDRESS.MOBILE.PRE")) {
                    return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(contact.getMobile(), true));
                }
                if (key.equals("ADDRESS.MOBILE.POST")) {
                    return Optional.ofNullable(TemplateProcessorHelper.getTelPrePost(contact.getMobile(), false));
                }
                if (key.equals("ADDRESS.EMAIL")) {
                    return Optional.ofNullable(contact.getEmail());
                }
            }
            final BankAccount bankAccount = originContact.getBankAccount();
            if (bankAccount != null) {
                switch (key) {
                case "ADDRESS.BANK.ACCOUNT.HOLDER":
                    return Optional.ofNullable(bankAccount.getAccountHolder());
                case "ADDRESS.BANK.ACCOUNT":
                    return Optional.ofNullable(bankAccount.getName());
                case "ADDRESS.BANK.CODE":
                    return Optional.ofNullable(Optional.ofNullable(bankAccount.getBankCode()).orElse(Integer.valueOf(0)).toString());
                case "ADDRESS.BANK.NAME":
                    return Optional.ofNullable(bankAccount.getBankName());
                case "ADDRESS.BANK.IBAN":
                    return Optional.ofNullable(bankAccount.getIban());
                case "ADDRESS.BANK.BIC":
                    return Optional.ofNullable(bankAccount.getBic());
                default:
                    break;
                }
            }
        }
        if (key.equals("ADDRESS.NR")) {
            return Optional.ofNullable(contact.getCustomerNumber());
        }
        if (key.equals("ADDRESS.SUPPLIER.NUMBER")) {
            return Objects.isNull(originContact) ? Optional.empty() : Optional.ofNullable(originContact.getSupplierNumber());
        }
        if (key.equals("ADDRESS.GLN")) {
            return Optional.ofNullable(Optional.ofNullable(contact.getGln()).orElse(Long.valueOf(0)).toString());
        }
        return Optional.empty();
    }

    /**
     * Creates the {@link Payment} text according to the document infos.
     * 
     * @param document
     *            the {@link Document} which contains the {@link Payment}
     *            information
     * @param documentSummary
     *            the {@link DocumentSummary} for some additional information
     * @param percent
     *            the discount, if any
     * @return fully formatted {@link Payment} text
     */
    public String createPaymentText(final Document document, final Optional<DocumentSummary> documentSummary, final double percent) {
        // String paymenttext = document.getPayment().getPaidText();
        String paymenttext = document.getAdditionalInfo().getPaymentText();
        if (paymenttext == null && document.getPayment() != null) {
            // try to get the default payment text from payment entry, if one exists
            paymenttext = BooleanUtils.toBoolean(document.getPaid()) ? document.getPayment().getPaidText() : document.getPayment().getUnpaidText();
        }
        paymenttext = StringUtils.replaceEach(paymenttext, new String[] { "<PAID.VALUE>", "<PAID.DATE>", "<DUE.DAYS>" },
                new String[] { numberFormatterService.DoubleToFormatedPriceRound(document.getPaidValue()),
                        dateFormatterService.getFormattedLocalizedDate(document.getPayDate()), Integer.toString(document.getDueDays()) });
        final LocalDateTime dueDate = DataUtils.getInstance().addToDate(document.getDocumentDate(), document.getDueDays());
        paymenttext = StringUtils.replace(paymenttext, "<DUE.DATE>", dueDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));

        paymenttext = StringUtils.replace(paymenttext, "<DUE.DISCOUNT.PERCENT>",
                numberFormatterService.DoubleToFormatedPercent(document.getPayment().getDiscountValue()));
        paymenttext = StringUtils.replace(paymenttext, "<DUE.DISCOUNT.DAYS>", document.getPayment().getDiscountDays().toString());
        paymenttext = StringUtils.replace(paymenttext, "<DUE.DISCOUNT.VALUE>",
                documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalGross().multiply(1 - percent)) : "");
        paymenttext = StringUtils.replace(paymenttext, "<DUE.DISCOUNT.DATE>", getDiscountDueDate(document));

        paymenttext = StringUtils.replace(paymenttext, "<BANK.ACCOUNT>", preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR"));
        paymenttext = StringUtils.replace(paymenttext, "<BANK.IBAN>", preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN));
        paymenttext = StringUtils.replace(paymenttext, "<BANK.BIC>", preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BIC));
        paymenttext = StringUtils.replace(paymenttext, "<BANK.NAME>", preferences.getString(Constants.PREFERENCES_YOURCOMPANY_BANK));
        paymenttext = StringUtils.replace(paymenttext, "<BANK.CODE>", preferences.getString("YOURCOMPANY_COMPANY_BANKCODE"));
        paymenttext = StringUtils.replace(paymenttext, "<YOURCOMPANY.CREDITORID>", preferences.getString(Constants.PREFERENCES_YOURCOMPANY_CREDITORID));

        // Additional placeholder for censored bank account
        String censoredAccount = censorAccountNumber(preferences.getString("YOURCOMPANY_COMPANY_BANKACCOUNTNR"));
        paymenttext = StringUtils.replace(paymenttext, "<BANK.ACCOUNT.CENSORED>", censoredAccount);
        censoredAccount = censorAccountNumber(preferences.getString(Constants.PREFERENCES_YOURCOMPANY_IBAN));
        paymenttext = StringUtils.replace(paymenttext, "<BANK.IBAN.CENSORED>", censoredAccount);

        final DocumentReceiver documentReceiver = addressManager.getBillingAdress(document);
        if (documentReceiver != null && documentReceiver.getOriginContactId() != null) {
            final Contact contact = contactsDAO.findById(documentReceiver.getOriginContactId());
            if (contact != null && contact.getBankAccount() != null) {
                // debitor's bank account
                paymenttext = StringUtils.replace(paymenttext, "<DEBITOR.BANK.ACCOUNT.HOLDER>", Objects.toString(contact.getBankAccount().getAccountHolder(), ""));
                paymenttext = StringUtils.replace(paymenttext, "<DEBITOR.BANK.IBAN>", Objects.toString(contact.getBankAccount().getIban(), ""));
                paymenttext = StringUtils.replace(paymenttext, "<DEBITOR.BANK.BIC>", Objects.toString(contact.getBankAccount().getBic(), ""));
                paymenttext = StringUtils.replace(paymenttext, "<DEBITOR.BANK.NAME>", Objects.toString(contact.getBankAccount().getBankName(), ""));
                paymenttext = StringUtils.replace(paymenttext, "<DEBITOR.MANDATREF>", Objects.toString(contact.getMandateReference(), ""));
                // Additional placeholder for censored bank account
                censoredAccount = censorAccountNumber(contact.getBankAccount().getIban());
                paymenttext = StringUtils.replace(paymenttext, "<DEBITOR.BANK.IBAN.CENSORED>", censoredAccount);
            }
        }

        // placeholder for total sum
        paymenttext = StringUtils.replace(paymenttext, "<DOCUMENT.TOTAL>",
                documentSummary.isPresent() ? numberFormatterService.formatCurrency(documentSummary.get().getTotalGross()) : "");
        paymenttext = StringUtils.replace(paymenttext, "<DOCUMENT.NAME>", document.getName());
        return paymenttext;
    }

    /**
     * @param paymenttext
     * @param bankAccountLength
     * @return
     */
    private String censorAccountNumber(final String accountNumber) {
        String retval = "";
        if (accountNumber != null) {
            final Integer bankAccountLength = accountNumber.length();
            // Only set placeholder if bank account exists
            if (bankAccountLength > COUNT_OF_LAST_SHOWN_DIGITS) {
                // Show only the last COUNT_OF_LAST_SHOWN_DIGITS digits
                final Integer bankAccountCensoredLength = bankAccountLength - COUNT_OF_LAST_SHOWN_DIGITS;
                retval = StringUtils.leftPad(accountNumber.substring(bankAccountCensoredLength), bankAccountCensoredLength, "*");
            } else {
                retval = "***";
            }
        }
        return retval;
    }

    /**
     * Fill vat table with data.
     *
     * @param vatSummarySetManager
     *            the vat summary set manager
     * @param pTable
     *            the p table
     * @param pRowTemplate
     *            the p row template
     * @param placeholderTableType
     *            current placeholder type
     * @param skipIfEmpty
     *            skips the row creation if value of sales equalization tax is
     *            empty (only for this case!)
     */
    private void fillVatTableWithData(final DocumentSummary documentSummary, final OdfTable pTable, final OdfTableRow pRowTemplate,
            final PlaceholderTableType placeholderTableType, final boolean skipIfEmpty) {
        // Get all items
        final int cellCount = pRowTemplate.getCellCount();

        final Iterator<VatSummaryItem> it = documentSummary.getVatSummary().iterator();
        while (it.hasNext()) {
            final VatSummaryItem vatSummaryItem = it.next();

            if (skipIfEmpty
                    && (!this.useSET || vatSummaryItem.getSalesEqTaxPercent() == null || vatSummaryItem.getSalesEqTaxPercent().equals(Double.valueOf(0.0)))) { // skip empty rows
                continue;
            }

            // clone one row from template
            final TableTableRowElement newRowElement = (TableTableRowElement) pRowTemplate.getOdfElement().cloneNode(true);
            // we always insert only ONE row to the table
            final OdfTableRow tmpRow = pTable.insertRowsBefore(pRowTemplate.getRowIndex(), 1).get(0);
            //          Row tmpRow = pTable.appendRow();  // don't know yet why the row was appended instead of inserted...
            pTable.getOdfElement().replaceChild(newRowElement, tmpRow.getOdfElement());
            final OdfTableRow newRow = OdfTableRow.getInstance(newRowElement);
            // find all placeholders within row
            for (int j = 0; j < cellCount; j++) {
                // System.out.print(".");
                // a template cell
                OdfTableCell currentCell;

                // temp index for columns
                int tmpIdx = j;
                do {
                    // Attention: Skip covered (spanned) cells!
                    currentCell = newRow.getCellByIndex(tmpIdx++);
                } while (currentCell.getOdfElement() instanceof TableCoveredTableCellElement);
                // correct for later use
                tmpIdx--;

                // make a copy of the template cell
                final Element cellNode = (TableTableCellElementBase) currentCell.getOdfElement().cloneNode(true);

                // find all placeholders in a cell
                final NodeList cellPlaceholders = cellNode.getElementsByTagName(TextPlaceholderElement.ELEMENT_NAME.getQName());

                /*
                 * The appended row only has default cells (without styles
                 * etc.). Therefore we have to take the template cell and
                 * replace the current cell (the real cell!) with it.
                 */
                newRow.getOdfElement().replaceChild(cellNode, newRow.getCellByIndex(tmpIdx).getOdfElement());
                // replace placeholders in this cell with current content
                final int countOfPlaceholders = cellPlaceholders.getLength();
                for (int k = 0; k < countOfPlaceholders; k++) {
                    final Node item = cellPlaceholders.item(0);
                    final PlaceholderNode cellPlaceholder = new PlaceholderNode(item);
                    switch (placeholderTableType) {
                    case VATLIST_TABLE:
                        fillVatTableWithData(vatSummaryItem, cellPlaceholder);
                        break;
                    case SALESEQUALIZATIONTAX_TABLE:
                        fillSalesEqualizationTaxTableWithData(vatSummaryItem, cellPlaceholder);
                    default:
                        break;
                    }
                }
            }
        }
    }

    /**
     * Fill the cell of the VAT table with the VAT data
     * 
     * @param placeholderDisplayText
     *            Column header
     * @param key
     *            VAT key (VAT description)
     * @param value
     *            VAT value
     * @param iText
     *            The Text that is set
     * @param index
     *            Index of the VAT entry
     * @param cellText
     *            The cell's text.
     * @return
     */
    private Node fillVatTableWithData(final VatSummaryItem vatSummaryItem, final PlaceholderNode cellPlaceholder) {
        final String key = vatSummaryItem.getVatName();
        final String value = numberFormatterService.formatCurrency(vatSummaryItem.getVat());
        // Get the text of the column. This is to determine if it is the column
        // with the VAT description or with the VAT value
        String textValue = "";

        // It's the VAT description
        if (cellPlaceholder.isPlaceholder("VATLIST.DESCRIPTIONS")) {
            textValue = key;
        }
        // It's the VAT value
        else if (cellPlaceholder.isPlaceholder("VATLIST.VALUES")) {
            textValue = value;
        } else if (cellPlaceholder.isPlaceholder("VATLIST.PERCENT")) {
            textValue = numberFormatterService.DoubleToFormatedPercent(vatSummaryItem.getVatPercent());
        } else if (cellPlaceholder.isPlaceholder("VATLIST.VATSUBTOTAL")) {
            textValue = numberFormatterService.formatCurrency(vatSummaryItem.getNet());
        } else {
            return null;
        }

        // Set the text
        return cellPlaceholder.replaceWith(textValue);

    }

    private Node fillSalesEqualizationTaxTableWithData(final VatSummaryItem vatSummaryItem, final PlaceholderNode cellPlaceholder) {
        // Get the text of the column. This is to determine if it is the column
        // with the VAT description or with the VAT value
        String textValue = "";

        if (this.useSET && vatSummaryItem.getSalesEqTax() != null) {
            if (cellPlaceholder.isPlaceholder("SALESEQUALIZATIONTAX.VALUES")) {
                textValue = numberFormatterService.formatCurrency(vatSummaryItem.getSalesEqTax());
            } else if (cellPlaceholder.isPlaceholder("SALESEQUALIZATIONTAX.PERCENT")) {
                textValue = numberFormatterService.DoubleToFormatedPercent(vatSummaryItem.getSalesEqTaxPercent());
            } else if (cellPlaceholder.isPlaceholder("SALESEQUALIZATIONTAX.SUBTOTAL")) {
                textValue = numberFormatterService.formatCurrency(vatSummaryItem.getNet());
            } else {
                return null;
            }
        }

        // Set the text
        return cellPlaceholder.replaceWith(textValue);
    }

    /**
     * Fill all cells of the item table with the item data
     * 
     * @param column
     *            The index of the column
     * @param itemDataSets
     *            Item data
     * @param itemsTable
     *            The item table
     * @param lastTemplateRow
     *            Counts the last row of the table
     * @param cellText
     *            The cell's text.
     */
    private void fillItemTableWithData(final List<DocumentItem> itemDataSets, final OdfTable pTable, final OdfTableRow pRowTemplate, final int sign) {
        // Get all items
        for (int row = 0; row < itemDataSets.size(); row++) {
            // clone one row from template
            final TableTableRowElement newRowElement = (TableTableRowElement) pRowTemplate.getOdfElement().cloneNode(true);
            // we always insert only ONE row to the table
            final OdfTableRow tmpRow = pTable.insertRowsBefore(pRowTemplate.getRowIndex(), 1).get(0);
            pTable.getOdfElement().replaceChild(newRowElement, tmpRow.getOdfElement());
            final OdfTableRow newRow = OdfTableRow.getInstance(newRowElement);
            // find all placeholders within row
            final int cellCount = newRowElement.getChildNodes().getLength();
            for (int j = 0; j < cellCount; j++) {
                // a template cell
                final OdfTableCell currentCell = newRow.getCellByIndex(j);

                // skip unnecessary cells
                if (currentCell.getOdfElement() instanceof TableCoveredTableCellElement) {
                    continue;
                }

                // make a copy of the template cell
                final Element cellNode = (TableTableCellElementBase) currentCell.getOdfElement().cloneNode(true);

                // find all placeholders in a cell
                final NodeList cellPlaceholders = cellNode.getElementsByTagName(TextPlaceholderElement.ELEMENT_NAME.getQName());

                /*
                 * The appended row only has default cells (without styles etc.). Therefore we
                 * have to take the template cell and replace the current cell with it.
                 */
                newRow.getOdfElement().replaceChild(cellNode, newRow.getCellByIndex(j).getOdfElement());
                // replace placeholders in this cell with current content
                final int countOfPlaceholders = cellPlaceholders.getLength();
                for (int k = 0; k < countOfPlaceholders; k++) {
                    final Node item = cellPlaceholders.item(0);
                    final PlaceholderNode cellPlaceholder = new PlaceholderNode(item);

                    final OdfTextDocument mDocument = (OdfTextDocument) ((OdfFileDom) (pTable.getOdfElement().getOwnerDocument())).getDocument();
                    cellPlaceholder.setOwnerDocument(mDocument);
                    fillItemTableWithData(itemDataSets.get(row), cellPlaceholder, sign);
                }
            }
        }
    }

    /**
     * Fill the cell of the item table with the item data
     * 
     * @param item
     * @param index
     *            Index of the item entry
     * @param cellPlaceholder
     *            The cell's placeholder.
     * @return
     */
    private void fillItemTableWithData(final DocumentItem item, final PlaceholderNode cellPlaceholder, final int sign) {
        String value = "";
        String placeholderDisplayText = cellPlaceholder.getNodeText();
        final String key = cellPlaceholder.getPlaceholderKey();
        final Price price = new Price(item, useSET, sign);
        final boolean isReplaceOptionalPrice = item.getOptional() && preferences.getBoolean(Constants.PREFERENCES_OPTIONALITEMS_REPLACE_PRICE);

        // Get the item quantity
        if (key.equals("ITEM.QUANTITY")) {
            final NumberFormat numberInstance = NumberFormat.getNumberInstance(localeUtil.getDefaultLocale());
            numberInstance.setMaximumFractionDigits(10);
            value = numberInstance.format(item.getQuantity());
        }

        // The position
        else if (key.equals("ITEM.POS")) {
            value = item.getPosNr().toString();
        }

        // The text for optional items
        else if (key.equals("ITEM.OPTIONAL.TEXT")) {
            if (item.getOptional()) {
                value = preferences.getString(Constants.PREFERENCES_OPTIONALITEMS_OPTIONALITEM_TEXT);
                value = value.replaceAll("<br>", "\n");
            }
        }

        // Get the item name
        else if (key.equals("ITEM.NAME")) {
            value = item.getName();
        }

        // Get the item number
        else if (key.equals("ITEM.NR")) {
            value = item.getItemNumber();
        }

        // Get the supplier item number
        else if (key.equals("ITEM.SUPPLIERNUMBER")) {
            value = item.getSupplierItemNumber();
        }

        // Get the quanity unit
        else if (key.equals("ITEM.QUANTITYUNIT")) {
            value = item.getQuantityUnit();
        }

        // Get the item weight
        else if (key.equals("ITEM.WEIGHT")) {
            value = item.getWeight() != null ? item.getWeight().toString() : "";
        }

        // Get the item weight
        else if (key.equals("ITEM.GTIN")) {
            value = item.getGtin() != null ? item.getGtin().toString() : "";
        }

        // vesting period
        else if (key.equals("ITEM.VESTINGPERIOD.START")) {
            value = item.getVestingPeriodStart() != null ? dateFormatterService.getFormattedLocalizedDate(item.getVestingPeriodStart()) : "";
        } else if (key.equals("ITEM.VESTINGPERIOD.END")) {
            value = item.getVestingPeriodEnd() != null ? dateFormatterService.getFormattedLocalizedDate(item.getVestingPeriodEnd()) : "";
        }

        // Get the item description
        else if (key.equals("ITEM.DESCRIPTION")) {
            value = item.getDescription();
            // Remove pre linebreak if description is empty to avoid empty lines
            if (StringUtils.defaultString(value).isEmpty()) {
                placeholderDisplayText = placeholderDisplayText.replaceFirst("\n<ITEM.DESCRIPTION>", "<ITEM.DESCRIPTION>");
            }
        }

        // Get the item discount in percent
        else if (key.equals("ITEM.DISCOUNT.PERCENT")) {
            Double itemRebate = item.getItemRebate();
            if (itemRebate != null && itemRebate < NumberUtils.DOUBLE_ZERO) {
                itemRebate *= NumberUtils.DOUBLE_MINUS_ONE; // make rebate positive (see https://bugs.fakturama.info/view.php?id=937)
            }
            value = numberFormatterService.DoubleToFormatedPercent(itemRebate);
        }

        // Get the absolute item discount (gross=
        else if (key.equals("ITEM.GROSS.DISCOUNT.VALUE")) {
            value = numberFormatterService.formatCurrency(price.getUnitGrossDiscountedRounded());
        }

        else if (key.equals("ITEM.SALESEQUALIZATIONTAX.PERCENT") && this.useSET) {
            value = numberFormatterService.DoubleToFormatedPercent(item.getItemVat().getSalesEqualizationTax());
        }

        // Get the item's VAT name
        else if (key.equals("ITEM.VAT.NAME")) {
            value = item.getItemVat().getName();
        }

        // Get the item's VAT description
        else if (key.equals("ITEM.VAT.DESCRIPTION")) {
            value = item.getItemVat().getDescription();
        }

        // Get the item net value
        else if (key.equals("ITEM.UNIT.NET")) {
            value = numberFormatterService.formatCurrency(price.getUnitNetRounded());
        }

        // Get the item VAT
        else if (key.equals("ITEM.UNIT.VAT")) {
            value = numberFormatterService.formatCurrency(price.getUnitVatRounded());
        }

        // Get the item gross value
        else if (key.equals("ITEM.UNIT.GROSS")) {
            value = numberFormatterService.formatCurrency(price.getUnitGrossRounded());
        }

        // Get the discounted item net value
        else if (key.equals("ITEM.UNIT.NET.DISCOUNTED")) {
            value = numberFormatterService.formatCurrency(price.getUnitNetDiscountedRounded());
        }

        // Get the discounted item VAT
        else if (key.equals("ITEM.UNIT.VAT.DISCOUNTED")) {
            value = numberFormatterService.formatCurrency(price.getUnitVatDiscountedRounded());
        }

        // Get the discounted item gross value
        else if (key.equals("ITEM.UNIT.GROSS.DISCOUNTED")) {
            value = numberFormatterService.formatCurrency(price.getUnitGrossDiscountedRounded());
        }

        // Get the total net value
        else if (key.equals("ITEM.TOTAL.NET")) {
            if (isReplaceOptionalPrice) {
                value = preferences.getString(Constants.PREFERENCES_OPTIONALITEMS_PRICE_REPLACEMENT);
                if (value.contains("{}")) {
                    value = value.replaceAll("\\{\\}", numberFormatterService.formatCurrency(price.getUnitNetDiscounted().multiply(item.getQuantity())));
                }
            } else {
                value = numberFormatterService.formatCurrency(price.getTotalNetRounded());
            }
        }

        // Get the total VAT
        else if (key.equals("ITEM.TOTAL.VAT")) {
            if (isReplaceOptionalPrice) {
                value = preferences.getString(Constants.PREFERENCES_OPTIONALITEMS_PRICE_REPLACEMENT);
                if (value.contains("{}")) {
                    value = value.replaceAll("\\{\\}", numberFormatterService.formatCurrency(price.getUnitVatDiscounted().multiply(item.getQuantity())));
                }
            } else {
                value = numberFormatterService.formatCurrency(price.getTotalVatRounded());
            }
        }

        // Get the total gross value
        else if (key.equals("ITEM.TOTAL.GROSS")) {
            if (isReplaceOptionalPrice) {
                value = preferences.getString(Constants.PREFERENCES_OPTIONALITEMS_PRICE_REPLACEMENT);
                if (value.contains("{}")) {
                    value = value.replaceAll("\\{\\}", numberFormatterService.formatCurrency(price.getUnitGrossDiscounted().multiply(item.getQuantity())));
                }
            } else {
                value = numberFormatterService.formatCurrency(price.getTotalGrossRounded());
            }
        }

        // Get the absolute item discount (net)
        else if (key.equals("ITEM.NET.DISCOUNT.VALUE")) {
            if (isReplaceOptionalPrice) {
                value = preferences.getString(Constants.PREFERENCES_OPTIONALITEMS_PRICE_REPLACEMENT);
                if (value.contains("{}")) {
                    value = value.replaceAll("\\{\\}", numberFormatterService.formatCurrency(price.getUnitNet().subtract(price.getUnitNetDiscounted())));
                }
            } else {
                value = numberFormatterService.formatCurrency(price.getUnitNet().subtract(price.getUnitNetDiscounted()));
            }
        }

        // Get the absolute item discount (gross)
        else if (key.equals("ITEM.GROSS.DISCOUNT.VALUE")) {
            if (isReplaceOptionalPrice) {
                value = preferences.getString(Constants.PREFERENCES_OPTIONALITEMS_PRICE_REPLACEMENT);
                if (value.contains("{}")) {
                    value = value.replaceAll("\\{\\}",
                            numberFormatterService.formatCurrency(price.getUnitGross().subtract(price.getUnitGrossDiscountedRounded())));
                }
            } else {
                value = numberFormatterService.formatCurrency(price.getUnitGross().subtract(price.getUnitGrossDiscountedRounded()));
            }
        }

        // Get the item's VAT
        else if (key.equals("ITEM.VAT.PERCENT")) {
            value = numberFormatterService.DoubleToFormatedPercent(item.getItemVat().getTaxValue());
        }

        // Get product picture
        else if (key.startsWith("ITEM.PICTURE")) {

            if (item.getPicture() != null) {

                final Pair<Integer, Integer> widthHeight = getCustomImageSize(item.getPicture(), cellPlaceholder);
                final Path imageFile = createImageFile(item.getPicture(), "JPG");

                if (imageFile != null) {
                    // replace the placeholder
                    cellPlaceholder.replaceWith(imageFile.toUri(), widthHeight.getLeft(), widthHeight.getRight());
                }
                return;
            }

            value = "";
        } else if (key.startsWith("ITEM.BARCODE")) {

            if (item.getItemNumber() != null) {

                final byte[] imageBytes = qrCodeService.createEANCode(item.getItemNumber());
                final Path imageFile = createImageFile(imageBytes, "JPG");

                if (imageFile != null) {
                    final Pair<Integer, Integer> widthHeight = getCustomImageSize(imageBytes, cellPlaceholder);
                    // replace the placeholder
                    cellPlaceholder.replaceWith(imageFile.toUri(), widthHeight.getLeft(), widthHeight.getRight());
                }
                return;
            }

            value = "";
        }

        else if (item.getProduct() != null) {
            final Product product = item.getProduct();
            // Get the item's category
            if (key.equals("ITEM.UNIT.CATEGORY")) {
                value = CommonConverter.getCategoryName(product.getCategories(), "/");
            } else if (key.equals("ITEM.UNIT.UDF01")) {
                value = product.getCdf01();
            } else if (key.equals("ITEM.UNIT.UDF02")) {
                value = product.getCdf02();
            } else if (key.equals("ITEM.UNIT.UDF03")) {
                value = product.getCdf03();
            } else if (key.equals("ITEM.UNIT.COSTPRICE")) {
                value = numberFormatterService.DoubleToFormatedPriceRound(Optional.ofNullable(product.getCostPrice()).orElse(Double.valueOf(0.0)));
            } else {
                value = "";
            }
        } else {
            value = "";
        }

        // Interpret all parameters
        value = applyParameters(value, cellPlaceholder.getParameters());

        // Convert CRLF to LF 
        value = DataUtils.getInstance().convertCRLF2LF(value);

        // Set the text of the cell
        cellPlaceholder.replaceWith(value);
    }

    private Pair<Integer, Integer> getCustomImageSize(final byte[] imageBytes, final PlaceholderNode placeholder) {
        // get height and with from parameters (if set), defaults to 0
        int pixelWidth = TemplateProcessorHelper.parseInt(placeholder.getParameter("WIDTH"), 0);
        int pixelHeight = TemplateProcessorHelper.parseInt(placeholder.getParameter("HEIGHT"), 0);

        // Use default values
        try (ByteArrayInputStream imgStream = new ByteArrayInputStream(imageBytes);) {

            final BufferedImage image = ImageIO.read(imgStream);

            if (image != null) {
                final int pictureHeight = image.getHeight();
                final int pictureWidth = image.getWidth();

                if (pixelWidth == 0 && pixelHeight == 0) {
                    pixelWidth = pictureWidth;
                    pixelHeight = pictureHeight;
                } else {
                    if (pixelHeight <= 0 && pictureWidth > 0) {
                        pixelHeight = pictureHeight * pixelWidth / pictureWidth;
                    }
                    if (pixelWidth <= 0 && pictureHeight > 0) {
                        pixelWidth = pictureWidth * pixelHeight / pictureHeight;
                    }
                }
            } else {
                log.error("Can't create image file.");
            }
        } catch (final IOException e) {
            log.error("Can't get size from temporary image file. Reason: " + e);
        }

        return ImmutablePair.of(pixelWidth, pixelHeight);
    }

    private Pair<Integer, Integer> getCustomImageSize(final URI imageFileURI, final PlaceholderNode placeholder) {
        // get height and with from parameters (if set), defaults to 0
        int pixelWidth = TemplateProcessorHelper.parseInt(placeholder.getParameter("WIDTH"), 0);
        int pixelHeight = TemplateProcessorHelper.parseInt(placeholder.getParameter("HEIGHT"), 0);

        // Use default values
        try {
            final BufferedImage image = ImageIO.read(imageFileURI.toURL());
            final int pictureHeight = image.getHeight();
            final int pictureWidth = image.getWidth();

            if (pixelWidth == 0 && pixelHeight == 0) {
                pixelWidth = pictureWidth;
                pixelHeight = pictureHeight;
            } else {
                if (pixelHeight <= 0 && pictureWidth > 0) {
                    pixelHeight = pictureHeight * pixelWidth / pictureWidth;
                }
                if (pixelWidth <= 0 && pictureHeight > 0) {
                    pixelWidth = pictureWidth * pixelHeight / pictureHeight;
                }
            }
        } catch (final IOException e) {
            log.error("Can't get size from temporary image file. Reason: " + e);
        }

        return ImmutablePair.of(pixelWidth, pixelHeight);
    }

    private Path createImageFile(final byte[] imageBytes, String formatName) {
        Path imageFile = null;

        if (imageBytes == null || imageBytes.length == 0) {
            return null;
        }

        // Read the image a first time to get width and height
        try (ByteArrayInputStream imgStream = new ByteArrayInputStream(imageBytes);) {

            final BufferedImage image = ImageIO.read(imgStream);
            
            if(image != null) {

                // Generate the image
                final String imageName = "tmpImage" + RandomStringUtils.randomAlphanumeric(8);
    
                /*
                 * Workaround: As long as the ODF toolkit can't handle images from a ByteStream
                 * we have to convert it to a temporary image and insert that into the document.
                 */
                imageFile = Paths.get(preferences.getString(Constants.GENERAL_WORKSPACE), imageName);
    
                final ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes));
                if (iis != null) {
                    final Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
                    if (!iter.hasNext()) {
                        throw new IOException("cannot determine image format");
                    }
                    // get the first reader
                    final ImageReader reader = iter.next();
                    formatName = reader.getFormatName();
                    reader.dispose();
                    iis.close();
                }
    
                ImageIO.write(image, formatName, imageFile.toFile());
            } else {
                log.error("Can't create image file.");
            }
        } catch (IOException | IllegalArgumentException e) {
            log.error("Can't create temporary image file. Reason: " + e);
        }
        return imageFile;
    }

    private void setUseSalesEquationTaxForDocument(final boolean isSET) {
        this.useSET = isSET;
    }

    /**
     * @param document
     * @return
     */
    private String getDiscountDueDate(final Document document) {
        LocalDateTime date = LocalDateTime.ofInstant(document.getDocumentDate().toInstant(), ZoneId.systemDefault());
        date = date.plusDays(document.getPayment().getDiscountDays());
        // if another ULocale than the system's default ULocale should be used then we
        // have to write "DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(...))" here 
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    /**
     * Test, if the name is in the list of all placeholders
     * 
     * @param testPlaceholder
     *            The placeholder to test
     * @return TRUE if the placeholder is in the list
     */
    public boolean isPlaceholder(final String testPlaceholder) {
        final String placeholderKey = TemplateProcessorHelper.extractPlaceholderKey(testPlaceholder);

        // Test all placeholders
        return Placeholder.valueOfKey(placeholderKey) != null;
    }

}
