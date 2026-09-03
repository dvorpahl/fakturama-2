package com.sebulli.fakturama.dbconnector;

import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.descriptors.InheritancePolicy;
import org.eclipse.persistence.sessions.Session;

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
    }
}
