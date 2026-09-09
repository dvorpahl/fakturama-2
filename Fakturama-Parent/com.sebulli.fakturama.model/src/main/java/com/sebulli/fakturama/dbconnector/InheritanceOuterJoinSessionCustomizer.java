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
        //
        // Derived from the mappings themselves rather than a hardcoded attribute-name list, so a
        // future relation added to Document is covered automatically instead of silently falling
        // back to the one-extra-SELECT-per-row behavior this customizer exists to eliminate.
        // Excludes:
        //  - collection-valued mappings (@OneToMany/@ManyToMany, e.g. the item lists): outer-
        //    joining a to-many relation multiplies the parent row per child instead of fetching
        //    it cheaply, the opposite of what this customizer is for.
        //  - relations whose reference class is Document or a subclass (sourceDocument,
        //    invoiceReference): both are self-referencing (Document -> Document), so joining them
        //    by default would make building *every* Document also eagerly build its whole
        //    referenced-document chain - see DocumentsDAO#fetchDocumentRelations's javadoc for
        //    the thousands-of-reads case that caused. Those stay lazy-on-access (still a single
        //    outer-joined query each, just not fetched up front).
        final ClassDescriptor documentDescriptor = session.getDescriptor(Document.class);
        if (documentDescriptor != null) {
            for (final DatabaseMapping mapping : documentDescriptor.getMappings()) {
                if (!(mapping instanceof ForeignReferenceMapping) || mapping.isCollectionMapping()) {
                    continue;
                }
                final ForeignReferenceMapping refMapping = (ForeignReferenceMapping) mapping;
                final Class<?> referenceClass = refMapping.getReferenceClass();
                if (referenceClass != null && Document.class.isAssignableFrom(referenceClass)) {
                    continue;
                }
                refMapping.setJoinFetch(ForeignReferenceMapping.OUTER_JOIN);
            }
        }
    }
}
