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

import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import com.sebulli.fakturama.i18n.Messages;
import com.sebulli.fakturama.model.Contact;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.resources.core.IconSize;

/**
 * Compact native customer navigation card with invoice statistics.
 */
public class CustomerSummaryComposite extends Composite {

    public static final int PREFERRED_WIDTH = 360;
    public static final int PREFERRED_HEIGHT = 58;

    private static final int ARC = 12;
    private static final int PADDING = 10;
    private static final int ICON_SIZE = 20;
    private static final int ICON_TEXT_GAP = 9;

    private final LocalResourceManager resourceManager;
    private final Image contactIcon;
    private final Contact contact;
    private final String statisticsText;
    private final String revenueText;
    private final String monthText;
    private final double paidTotal;
    private final double openTotal;
    private boolean hovered;

    public CustomerSummaryComposite(final Composite parent, final int style, final Messages messages, final Contact contact,
            final int paidInvoices, final int openInvoices, final String revenueText, final String openAmountText, final String monthText,
            final double paidTotal, final double openTotal, final Consumer<Contact> contactOpener) {
        super(parent, style);
        this.contact = Objects.requireNonNull(contact);
        this.revenueText = Objects.requireNonNull(revenueText);
        this.monthText = Objects.requireNonNull(monthText);
        this.paidTotal = paidTotal;
        this.openTotal = openTotal;
        this.statisticsText = paidInvoices + "/" + openInvoices + " | ";
        this.resourceManager = new LocalResourceManager(JFaceResources.getResources(), this);

        final Image source = Icon.COMMAND_CONTACT.getImage(IconSize.DefaultIconSize);
        contactIcon = resourceManager.createImage(ImageDescriptor.createFromImageData(source.getImageData().scaledTo(ICON_SIZE, ICON_SIZE)));

        setLayout(new FillLayout());
        final Canvas card = new Canvas(this, SWT.DOUBLE_BUFFERED);
        card.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_HAND));
        card.setToolTipText(createToolTip(messages, paidInvoices, openInvoices, openAmountText));
        card.addPaintListener(event -> paintCard(event.gc, card.getClientArea()));
        card.addMouseTrackListener(new org.eclipse.swt.events.MouseTrackAdapter() {
            @Override
            public void mouseEnter(final MouseEvent event) {
                hovered = true;
                card.redraw();
            }

            @Override
            public void mouseExit(final MouseEvent event) {
                hovered = false;
                card.redraw();
            }
        });
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseUp(final MouseEvent event) {
                if (event.button == 1) {
                    contactOpener.accept(contact);
                }
            }
        });
        card.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent event) {
                if (event.keyCode == SWT.CR || event.character == ' ') {
                    contactOpener.accept(contact);
                }
            }
        });
        card.getAccessible().addAccessibleListener(new AccessibleAdapter() {
            @Override
            public void getName(final AccessibleEvent event) {
                event.result = card.getToolTipText();
            }
        });
    }

    private void paintCard(final GC gc, final Rectangle bounds) {
        final Color background = getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
        final Color border = getDisplay().getSystemColor(hovered ? SWT.COLOR_LIST_SELECTION : SWT.COLOR_WIDGET_NORMAL_SHADOW);
        gc.setAntialias(SWT.ON);
        gc.setBackground(background);
        gc.setForeground(border);
        gc.setLineWidth(hovered ? 2 : 1);
        gc.fillRoundRectangle(1, 1, bounds.width - 3, bounds.height - 3, ARC, ARC);
        gc.drawRoundRectangle(1, 1, bounds.width - 3, bounds.height - 3, ARC, ARC);

        final int iconY = Math.max(PADDING, (bounds.height - ICON_SIZE) / 2);
        gc.drawImage(contactIcon, PADDING, iconY);

        final int textX = PADDING + ICON_SIZE + ICON_TEXT_GAP;
        final int maximumWidth = Math.max(1, bounds.width - textX - PADDING);
        final Font originalFont = gc.getFont();
        gc.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
        gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        gc.drawText(shorten(gc, contact.getName(), maximumWidth), textX, 7, true);

        gc.setFont(originalFont);
        final int textY = 7 + gc.getFontMetrics().getHeight() + 1;
        int currentX = textX;
        gc.drawText(statisticsText, currentX, textY, true);
        currentX += gc.textExtent(statisticsText).x;

        gc.setForeground(revenueColor());
        gc.drawText(revenueText, currentX, textY, true);
        currentX += gc.textExtent(revenueText).x;

        gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND));
        gc.drawText(" | " + monthText, currentX, textY, true);
    }

    private Color revenueColor() {
        if (openTotal <= 0.0) {
            return getDisplay().getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
        }
        return getDisplay().getSystemColor(openTotal > paidTotal ? SWT.COLOR_DARK_RED : SWT.COLOR_DARK_YELLOW);
    }

    private String createToolTip(final Messages messages, final int paidInvoices, final int openInvoices, final String openAmountText) {
        return contact.getName() + '\n' + messages.documentOrderStatePaid + ": " + paidInvoices + " | " + messages.documentOrderStateOpen + ": "
                + openInvoices + '\n' + messages.exporterDataVolume + ": " + revenueText + " | " + messages.documentOrderStateOpen + ": "
                + openAmountText;
    }

    private static String shorten(final GC gc, final String value, final int maximumWidth) {
        final String text = value != null ? value : "";
        if (gc.textExtent(text).x <= maximumWidth) {
            return text;
        }
        String shortened = text;
        while (!shortened.isEmpty() && gc.textExtent(shortened + "...").x > maximumWidth) {
            shortened = shortened.substring(0, shortened.length() - 1);
        }
        return shortened + "...";
    }
}
