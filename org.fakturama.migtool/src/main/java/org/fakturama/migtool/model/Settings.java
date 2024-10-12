/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2023 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package org.fakturama.migtool.model;

public class Settings {
    private Destination source;
    private Destination destination;

    /**
     * @return the source
     */
    public Destination getSource() {
        return source;
    }

    /**
     * @param source
     *            the source to set
     */
    public void setSource(final Destination source) {
        this.source = source;
    }

    /**
     * @return the destination
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * @param destination
     *            the destination to set
     */
    public void setDestination(final Destination destination) {
        this.destination = destination;
    }

}
