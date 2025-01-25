/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2014, 2020 Ralf Heydenreich
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Ralf Heydenreich - initial API and implementation
 */
package org.fakturama.export.einvoice;

/**
 * Abbildung der Levels für die einzelnen ZUGFeRD-Ausbaustufen
 * 
 * 
 */
public enum ConformanceLevel {

    ZUGFERD_V2_EN16931("urn:cen.eu:en16931:2017", "EN16931"), //
    XRECHNUNG("urn:cen.eu:en16931:2017#compliant#urn:xeinkauf.de:kosit:xrechnung_3.0", "COMFORT"), //

    FACTURX_EN16931("urn:cen.eu:en16931:2017", "EN16931"); //

    private String urn;
    private String descriptor;

    /**
     * @param urn
     */
    ConformanceLevel(final String urn, final String descriptor) {
        this.urn = urn;
        this.descriptor = descriptor;
    }

    /**
     * @return the urn
     */
    public final String getUrn() {
        return urn;
    }

    public String getDescriptor() {
        return descriptor;
    }

}
