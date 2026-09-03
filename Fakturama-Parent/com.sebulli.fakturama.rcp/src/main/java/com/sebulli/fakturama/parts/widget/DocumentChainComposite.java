/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 *
 * Copyright (C) 2026 The Fakturama Team
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package com.sebulli.fakturama.parts.widget;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.model.BillingType;
import com.sebulli.fakturama.model.Document;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.resources.core.IconSize;
import com.sebulli.fakturama.util.DocumentTypeUtil;

/**
 * Compact native SWT representation of the documents belonging to one
 * transaction.
 */
public class DocumentChainComposite extends Composite {

    private static final int MINIMUM_HEIGHT = 64;
    private static final int CARD_WIDTH = 190;
    private static final int CARD_HEIGHT = 58;
    private static final int CARD_ARC = 12;
    private static final int CARD_PADDING = 10;
    private static final int ICON_SIZE = 20;
    private static final int ICON_TEXT_GAP = 9;

    private static final RGB ACTIVE_BACKGROUND_RGB = new RGB(225, 240, 252);
    private static final RGB ACTIVE_BORDER_RGB = new RGB(30, 96, 145);

    private final Messages messages;
    private final Consumer<Document> documentOpener;
    private final LocalResourceManager resourceManager;
    private final Map<BillingType, Image> cardIcons = new EnumMap<>(BillingType.class);
    private final ScrolledComposite scroller;
    private Composite chainContent;
    private Document activeDocument;

    public DocumentChainComposite(final Composite parent, final int style, final Messages messages, final Document activeDocument,
            final List<Document> documents, final Consumer<Document> documentOpener) {
        super(parent, style);
        this.messages = Objects.requireNonNull(messages);
        this.documentOpener = Objects.requireNonNull(documentOpener);
        this.resourceManager = new LocalResourceManager(JFaceResources.getResources(), this);
        this.activeDocument = activeDocument;

        setLayout(new FillLayout());
        scroller = new ScrolledComposite(this, SWT.H_SCROLL | SWT.DOUBLE_BUFFERED);
        scroller.setExpandHorizontal(true);
        scroller.setExpandVertical(true);
        scroller.addListener(SWT.Resize, event -> resizeContent());

        setDocuments(activeDocument, documents);
    }

    /**
     * Rebuilds the visual chain, for example after the active document was saved.
     */
    public final void setDocuments(final Document activeDocument, final List<Document> documents) {
        this.activeDocument = activeDocument;
        if (chainContent != null && !chainContent.isDisposed()) {
            chainContent.dispose();
        }

        final List<Document> chainDocuments = DocumentChainSupport.prepareDocuments(documents, activeDocument);
        chainContent = new Composite(scroller, SWT.NONE);
        final GridLayout layout = new GridLayout(Math.max(1, chainDocuments.size() * 2 - 1), false);
        layout.marginWidth = 8;
        layout.marginHeight = 3;
        layout.horizontalSpacing = 7;
        chainContent.setLayout(layout);

        for (int i = 0; i < chainDocuments.size(); i++) {
            if (i > 0) {
                createChevron(chainContent);
            }
            createCard(chainContent, chainDocuments.get(i));
        }

        scroller.setContent(chainContent);
        resizeContent();
        layout(true, true);
    }

    private void createCard(final Composite parent, final Document cardDocument) {
        final boolean active = isActive(cardDocument);
        final boolean navigable = !active && cardDocument.getId() > 0;
        final Canvas card = new Canvas(parent, SWT.DOUBLE_BUFFERED);
        card.setData(cardDocument);
        card.setToolTipText(createAccessibleText(cardDocument));
        card.setCursor(navigable ? getDisplay().getSystemCursor(SWT.CURSOR_HAND) : null);
        card.setBackground(parent.getBackground());
        card.addPaintListener(event -> paintCard(event.gc, card.getClientArea(), cardDocument, active));
        card.getAccessible().addAccessibleListener(new AccessibleAdapter() {
            @Override
            public void getName(final AccessibleEvent event) {
                event.result = createAccessibleText(cardDocument);
            }
        });
        if (navigable) {
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseUp(final MouseEvent event) {
                    if (event.button == 1) {
                        documentOpener.accept(cardDocument);
                    }
                }
            });
            card.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(final KeyEvent event) {
                    if (event.keyCode == SWT.CR || event.character == ' ') {
                        documentOpener.accept(cardDocument);
                    }
                }
            });
        }
        card.setLayoutData(new GridData(CARD_WIDTH, CARD_HEIGHT));
    }

    private void paintCard(final GC gc, final Rectangle bounds, final Document cardDocument, final boolean active) {
        final Color background = active ? activeBackground() : getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
        final Color border = active ? activeBorder() : getDisplay().getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
        gc.setAntialias(SWT.ON);
        gc.setBackground(background);
        gc.setForeground(border);
        gc.setLineWidth(active ? 2 : 1);
        gc.fillRoundRectangle(1, 1, bounds.width - 3, bounds.height - 3, CARD_ARC, CARD_ARC);
        gc.drawRoundRectangle(1, 1, bounds.width - 3, bounds.height - 3, CARD_ARC, CARD_ARC);

        final Image icon = cardIcon(cardDocument.getBillingType());
        final int iconX = CARD_PADDING;
        final int iconY = Math.max(CARD_PADDING, (bounds.height - ICON_SIZE) / 2);
        gc.drawImage(icon, iconX, iconY);

        final int textX = iconX + ICON_SIZE + ICON_TEXT_GAP;
        final int textWidth = Math.max(1, bounds.width - textX - CARD_PADDING);
        int textY = 7;
        final Font originalFont = gc.getFont();
        gc.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
        gc.setForeground(active ? activeBorder() : getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        gc.drawText(shorten(gc, cardTitle(cardDocument), textWidth), textX, textY, true);
        textY += gc.getFontMetrics().getHeight() + 1;

        gc.setFont(originalFont);
        gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        gc.drawText(shorten(gc, StringUtils.defaultString(cardDocument.getName()), textWidth), textX, textY, true);
    }

    private void createChevron(final Composite parent) {
        final Label chevron = new Label(parent, SWT.NONE);
        chevron.setText("\u203a");
        chevron.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW));
        chevron.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
    }

    private void resizeContent() {
        if (chainContent == null || chainContent.isDisposed()) {
            return;
        }
        final Point preferred = chainContent.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        final Rectangle clientArea = scroller.getClientArea();
        chainContent.setSize(Math.max(preferred.x, clientArea.width), Math.max(preferred.y, MINIMUM_HEIGHT));
        scroller.setMinSize(preferred);
    }

    private String translatedType(final Document cardDocument) {
        final DocumentType type = DocumentTypeUtil.findByBillingType(cardDocument.getBillingType());
        return messages.getMessageFromKey(type.getSingularKey());
    }

    private String cardTitle(final Document cardDocument) {
        return cardDocument.getBillingType() == BillingType.ORDER ? orderSource(cardDocument) : translatedType(cardDocument);
    }

    private String orderSource(final Document cardDocument) {
        return StringUtils.isNotEmpty(cardDocument.getWebshopId()) ? messages.editorDocumentChainWebshop : messages.editorDocumentChainDirectorder;
    }

    private String createAccessibleText(final Document cardDocument) {
        final StringBuilder text = new StringBuilder(cardTitle(cardDocument));
        if (StringUtils.isNotEmpty(cardDocument.getName())) {
            text.append(' ').append(cardDocument.getName());
        }
        return text.toString();
    }

    private boolean isActive(final Document cardDocument) {
        if (activeDocument == cardDocument) {
            return true;
        }
        return activeDocument != null && activeDocument.getId() > 0 && activeDocument.getId() == cardDocument.getId();
    }

    private Color activeBackground() {
        return resourceManager.createColor(ColorDescriptor.createFrom(ACTIVE_BACKGROUND_RGB));
    }

    private Color activeBorder() {
        return resourceManager.createColor(ColorDescriptor.createFrom(ACTIVE_BORDER_RGB));
    }

    private Image cardIcon(final BillingType billingType) {
        return cardIcons.computeIfAbsent(billingType, type -> {
            final Image source = iconFor(type).getImage(IconSize.DefaultIconSize);
            return resourceManager.createImage(ImageDescriptor.createFromImageData(source.getImageData().scaledTo(ICON_SIZE, ICON_SIZE)));
        });
    }

    private static String shorten(final GC gc, final String text, final int maximumWidth) {
        if (gc.textExtent(text).x <= maximumWidth) {
            return text;
        }
        String shortened = text;
        while (!shortened.isEmpty() && gc.textExtent(shortened + "...").x > maximumWidth) {
            shortened = shortened.substring(0, shortened.length() - 1);
        }
        return shortened + "...";
    }

    private static Icon iconFor(final BillingType billingType) {
        switch (billingType) {
            case OFFER:
                return Icon.COMMAND_OFFER;
            case ORDER:
                return Icon.COMMAND_ORDER;
            case CONFIRMATION:
                return Icon.COMMAND_CONFIRMATION;
            case INVOICE:
                return Icon.COMMAND_INVOICE;
            case DELIVERY:
                return Icon.COMMAND_DELIVERY;
            case CREDIT:
                return Icon.COMMAND_CREDIT;
            default:
                throw new IllegalArgumentException("Unsupported document type: " + billingType);
        }
    }

}
