    
    /*
     * Fakturama - Free Invoicing Software - http://www.fakturama.org
     * 
     * Copyright (C) 2014 www.fakturama.org
     * 
     * All rights reserved. This program and the accompanying materials are made
     * available under the terms of the Eclipse Public License v1.0 which
     * accompanies this distribution, and is available at
     * http://www.eclipse.org/legal/epl-v10.html
     * 
     * Contributors: The Fakturama Team - initial API and implementation
     */

    package com.sebulli.fakturama.views.datatable.documents;

import com.sebulli.fakturama.model.Document_;

    /**
     * Enum for describing a Contact list. This contains the name of the displayed
     * values, the position of the columns and a default width of each column
     * (copied from old ColumnWidth*PreferencePages).
     *
     */
    public enum DocumentListDescriptor {

        ICON("$documenttype", null, 0, 5), 
        ICON_WEBSHOP("$webshop", null, 1, 3),
        ICON_RENTAL("$rental", null, 2, 3),
        DOCUMENT(Document_.name.getName(), "common.field.document", 3, 10), 
        DATE(Document_.documentDate.getName(), "common.field.date", 4, 10), 
        NAME(Document_.addressFirstLine.getName(), "common.field.name", 5, 40),
        CUSTREF(Document_.customerRef.getName(), "editor.document.field.custref", 6, 40), 
        STATE("$status", "common.field.state", 7, 10),
        TOTAL(Document_.totalValue.getName(), "common.field.total", 8, 20),
        PRINTED("$printed", "common.field.printed", 9, 5),
        ;

        private String propertyName, messageKey;
        private int position, defaultWidth;

        /**
         * @param propertyName
         * @param position
         * @param defaultWidth
         */
        private DocumentListDescriptor(String propertyName, String messageKey, int position, int defaultWidth) {
            this.propertyName = propertyName;
            this.messageKey = messageKey;
            this.position = position;
            this.defaultWidth = defaultWidth;
        }

        /**
         * @return the propertyName
         */
        public final String getPropertyName() {
            return propertyName;
        }

        /**
         * @return the position
         */
        public final int getPosition() {
            return position;
        }

        /**
         * @return the defaultWidth
         */
        public final int getDefaultWidth() {
            return defaultWidth;
        }

        /**
         * @return the messageKey
         */
        public String getMessageKey() {
            return messageKey;
        }

        public static DocumentListDescriptor getDescriptorFromColumn(int columnIndex) {
            for (DocumentListDescriptor descriptor : values()) {
                if (descriptor.getPosition() == columnIndex) { return descriptor; }
            }
            return null;
        }

        /**
         * Gets all visible(!) properties of Contacts type.
         * 
         * @return properties of Contacts type
         */
        public static final String[] getDocumentPropertyNames() {
            return new String[] { 
                    DocumentListDescriptor.ICON.getPropertyName(), 
                    DocumentListDescriptor.ICON_WEBSHOP.getPropertyName(),
                    DocumentListDescriptor.ICON_RENTAL.getPropertyName(),
                    DocumentListDescriptor.DOCUMENT.getPropertyName(),
                    DocumentListDescriptor.DATE.getPropertyName(), 
                    DocumentListDescriptor.NAME.getPropertyName(),
                    DocumentListDescriptor.CUSTREF.getPropertyName(),
                    DocumentListDescriptor.STATE.getPropertyName(), 
                    DocumentListDescriptor.TOTAL.getPropertyName(), 
                    DocumentListDescriptor.PRINTED.getPropertyName(), 
            };
        }
    }

