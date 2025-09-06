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

package org.fakturama.migtool;

import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class LiquibaseMigration {

    public static final Pattern UNSUPPORTED_BLOB = Pattern.compile(",\\s*(?:\\r\\n?|\\n)\\s*\"valueComputed\":\\s*\"UNSUPPORTED FOR DIFF: BINARY DATA\"\n");
    public static final String MIGRATION_AUTHOR = "Fakturama Migration Tool";

    private static final Logger log = LogManager.getLogger(LiquibaseMigration.class);

    /**
     * @param args
     * @throws IOException
     * @throws Exception
     */
    public static void main(final String[] args) throws IOException {
        log.info("Starting migration... ");
        new MigrationWorker().run();
        log.info("Finished migration... ");
    }
}
