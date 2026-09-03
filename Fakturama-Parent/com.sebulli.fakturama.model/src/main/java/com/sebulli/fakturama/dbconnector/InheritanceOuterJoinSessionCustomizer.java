package com.sebulli.fakturama.dbconnector;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.InheritancePolicy;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.ForeignReferenceMapping;
import org.eclipse.persistence.sessions.Session;

import com.sebulli.fakturama.model.Document;

/**
 * {@code Document} uses {@code InheritanceType.JOINED} across many subclass tables (Invoice,
 * Offer, Order, Delivery, ...). Weaving isn't enabled in this OSGi launch, so none of its
 * relations can be made truly lazy - EclipseLink always resolves them eagerly one way or another.
 * Passing {@code QueryHints.INHERITANCE_OUTER_JOIN} on individual queries only covers the
 * outermost query: as soon as EclipseLink needs to build a nested related {@code Document} (e.g.
 * resolving {@code sourceDocument}/{@code invoiceReference} while building the object graph for a
 * fetch-joined row), that inner build uses a fresh {@code ReadObjectQuery} with no hint attached,
 * which falls back to {@code InheritancePolicy#selectOneRowUsingMultipleTableSubclassRead} - one
 * extra single-row SELECT per nested Document, cascading through however long the
 * sourceDocument/invoiceReference chain is. Verified via a thread dump taken mid-burst: switching
 * the document list to a category with long reference chains fired ~2000 of these in under 20
 * seconds. Setting this at the descriptor level makes outer-joined subclass reads the default for
 * every read of Document, everywhere, not just the queries we remembered to add the hint to.
 */
public class InheritanceOuterJoinSessionCustomizer implements SessionCustomizer {
    /**
     * {@code Document}'s eager, non-self-referencing @ManyToOne relations - none of these can be
     * made truly {@code LAZY} either (same weaving restriction), so every access resolves them one
     * way or another. Deliberately excludes {@code sourceDocument}/{@code invoiceReference}: both
     * are self-referencing ({@code Document -> Document}), so joining them by default would make
     * building *every* Document also eagerly build its whole referenced-document chain - see
     * {@code DocumentsDAO#fetchDocumentRelations}'s javadoc for the thousands-of-reads case that
     * caused. Those two stay lazy-on-access (still a single outer-joined query each, just not
     * fetched up front); this only covers the four relations that are cheap and finite either way.
     */
    private static final String[] DOCUMENT_JOINED_ATTRIBUTES = { "additionalInfo", "payment", "shipping", "noVatReference" };

    @Override
    public void customize(final Session session) {
        for (final ClassDescriptor descriptor : session.getDescriptors().values()) {
            final InheritancePolicy inheritancePolicy = descriptor.hasInheritance() ? descriptor.getInheritancePolicy() : null;
            if (inheritancePolicy != null && inheritancePolicy.isJoinedStrategy()) {
                inheritancePolicy.setShouldOuterJoinSubclasses(true);
            }
        }

        // Same reasoning as the subclass-join fix above, extended to the relations it doesn't
        // cover: QueryHints.INHERITANCE_OUTER_JOIN / the explicit fetch joins in
        // DocumentsDAO#fetchDocumentRelations only apply to the outermost query. As soon as
        // EclipseLink needs to build a *nested* Document - e.g. resolving sourceDocument while
        // building a fetch-joined row, or any other relation navigated to a Document from outside
        // this package - that inner build uses a fresh, hint-less ReadObjectQuery and falls back
        // to one extra single-row SELECT per relation per Document. Setting the join at the
        // descriptor level makes it the default for every read of Document, everywhere, not just
        // the call sites we remembered to fetch-join explicitly.
        final ClassDescriptor documentDescriptor = session.getDescriptor(Document.class);
        if (documentDescriptor != null) {
            for (final String attribute : DOCUMENT_JOINED_ATTRIBUTES) {
                final DatabaseMapping mapping = documentDescriptor.getMappingForAttributeName(attribute);
                if (mapping instanceof ForeignReferenceMapping) {
                    ((ForeignReferenceMapping) mapping).setJoinFetch(ForeignReferenceMapping.OUTER_JOIN);
                }
            }
        }
    }
}
