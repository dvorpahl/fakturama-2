/**
 * 
 */
package com.sebulli.fakturama.model;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.texo.converter.ObjectCopier;
import org.eclipse.emf.texo.model.ModelObject;
import org.eclipse.emf.texo.model.ModelResolver;
import org.eclipse.emf.texo.model.ModelResolver.ModelDescriptor;

/**
 * Helper class for duplicating model objects. Since not all references have to
 * be duplicated (e.g., VAT or Shipping objects), the duplicating process has to
 * be more intelligent than only stupid copying attributes and references.
 *
 */
public class ObjectDuplicator {

    public void einTest(final Document invoice) {

        //        FakturamaModelPackage fakturamaModelPackage = FakturamaModelPackage.INSTANCE;
        ModelDescriptor modelDescriptor = ModelResolver.getInstance().getModelDescriptor(invoice.getClass(), true);
        //        modelDescriptor.setModelPackage(fakturamaModelPackage);
        //        EClassifier eClassifier = fakturamaModelPackage.getEPackage().getEClassifiers().get(FakturamaModelPackage.DOCUMENT_CLASSIFIER_ID);
        //        modelDescriptor.setEClassifier(eClassifier);
        //      productAttributes = ((EClass) FakturamaModelPackage.INSTANCE.getEPackage().getEClassifiers().get(FakturamaModelPackage.PRODUCT_CLASSIFIER_ID)) //
        //      .getEAllAttributes().stream() //
        //      .filter(f -> !f.isMany()) //
        //      .sorted(Comparator.comparing(EStructuralFeature::getName))
        //      .collect(Collectors.toList());

        ModelObject<?> adapter = modelDescriptor.createAdapter(invoice);
        EStructuralFeature eStructuralFeature = adapter.eClass().getEStructuralFeature(FakturamaModelPackage.INVOICE_CUSTOMERREF_FEATURE_ID);
        Object feat = adapter.eGet(eStructuralFeature);
        System.out.println(feat);
    }

    /**
     * Controls what happens to the customer/address link and the document-chain
     * reference when a {@link Document} is duplicated via {@link #duplicateDocument}.
     */
    public enum DuplicateMode {
        /**
         * "Neues Angebot": the copy is treated as an unrelated, brand-new document -
         * the customer/address back-references are cleared (the user has to
         * (re-)pick an address) and no link to the source document is kept.
         */
        NEW_DOCUMENT,
        /**
         * "Zweites Angebot (gleicher Kunde)": the copy keeps the same
         * customer/address and is linked back to the document it was duplicated
         * from via {@link Document#setSourceDocument}, so it shows up together
         * with it in the document chain.
         */
        SAME_CUSTOMER
    }

    /**
     * Duplicates a document, always clearing the customer/address link (see
     * {@link DuplicateMode#NEW_DOCUMENT}). Kept for backward compatibility.
     *
     * @see #duplicateDocument(Document, DuplicateMode)
     */
    public <T extends Document> T duplicateDocument(final T document) {
        return duplicateDocument(document, DuplicateMode.NEW_DOCUMENT);
    }

    @SuppressWarnings("unchecked")
    public <T extends Document> T duplicateDocument(final T document, final DuplicateMode mode) {
        T clonedDocument = null;
        if (document != null) {
            ObjectCopier objectCopier = new ObjectCopier();
            objectCopier.setCopyChildren(true);
            objectCopier.setCopyReferences(true);
            clonedDocument = (T) objectCopier.copy(document);

            /*
             * Modify some references. Note that VAT, Payment and the like are entities
             * which are always referenced only. But entities like DocumentReceiver or
             * DocumentItem have to be created newly for each copy of a document. Therefore
             * we iterate through all relevant entity collections and set their item's id to
             * 0. This causes the Entity Manager to store the complete list as new entities.
             */
            // VAT, Shipment, Payment can be left unchanged
            if (clonedDocument != null) {
                // every "fresh" timestamp on the clone uses this single instant,
                // set directly (not left null for EntityListener#aboutToInsert to fill in)
                // since dateAdded/validFrom are never null on a real document and we don't
                // want to depend on whether persist() vs. merge() actually fires @PrePersist
                // for these EMF-Texo-copied child entities
                final Date now = Calendar.getInstance().getTime();
                final boolean keepCustomer = mode == DuplicateMode.SAME_CUSTOMER;

                // reset some attributes
                clonedDocument.getAdditionalInfo().setId(0);
                clonedDocument.setInvoiceReference(null);
                // link back to where this copy came from, unless it's meant to be
                // a completely unrelated new document
                clonedDocument.setSourceDocument(keepCustomer ? document : null);
                clonedDocument.setTransactionId(null);
                clonedDocument.setVersion(Integer.valueOf(1));

                // set DocumentReceiver to new; clear the origin contact/address
                // back-references unless the copy is meant to keep the same customer
                clonedDocument.getReceiver().forEach(r -> {
                    r.setId(0);
                    if (!keepCustomer) {
                        r.setOriginContactId(null);
                        r.setOriginAddressId(null);
                    }
                    r.setDateAdded(now);
                    r.setValidFrom(now);
                });

                // set DocumentItems to new
                clonedDocument.getItems().forEach(r -> {
                    r.setId(0);
                    r.setDateAdded(now);
                    r.setValidFrom(now);
                });

                // set date to actual date
                clonedDocument.setDocumentDate(now);
                clonedDocument.setServiceDate(now);
                clonedDocument.setOrderDate(now);

                // reset paid values, if any
                clonedDocument.setPaid(Boolean.FALSE);
                clonedDocument.setPayDate(null);
                clonedDocument.setPaidValue(null);

                // the document itself must also get a fresh dateAdded/validFrom
                clonedDocument.setDateAdded(now);
                clonedDocument.setValidFrom(now);

                // make the new object really "new" :-)
                clonedDocument.setId(0);
            }
        }
        return clonedDocument;
    }

    public Product duplicateProduct(final Product product) {
        Product clonedProduct = null;
        if (product != null) {
            clonedProduct = product.clone();
            ObjectCopier objectCopier = new ObjectCopier();
            objectCopier.setCopyChildren(true);
            objectCopier.setCopyReferences(true);
            clonedProduct = (Product) objectCopier.copy(product);
            // TODO set options for the product to new ones
            clonedProduct.setId(0);
        }
        return clonedProduct;
    }

}
