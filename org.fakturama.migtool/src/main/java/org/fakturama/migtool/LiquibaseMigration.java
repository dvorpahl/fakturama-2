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

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;

/**
 * 
 */
public class LiquibaseMigration {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        String diffPath = "./diffOutput.xml";
        //        Files.deleteIfExists(Path.of(diffPath));
        String hsqlConnectionString = "jdbc:hsqldb:file:C:\\Users\\Karsten\\fakturama_development\\fakturama_dirk\\Database/Database";
        String mariaConnectionString = "jdbc:mariadb://localhost:3306/fakturama";

        Connection con = DriverManager.getConnection(hsqlConnectionString, "sa", "");
        Connection conMaria = DriverManager.getConnection(mariaConnectionString, "root", "password");
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(con));
        Database databaseMaria = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conMaria));

        Liquibase liquibaseInit = new Liquibase("changelog/db.changelog-master.xml", new ClassLoaderResourceAccessor(), databaseMaria);
        liquibaseInit.update(new Contexts());

        //        new CommandScope(GenerateChangelogCommandStep.COMMAND_NAME[0]) //
        //                .addArgumentValue(GenerateChangelogCommandStep.AUTHOR_ARG, "Fakturama Migration Tool") //
        //                .addArgumentValue(GenerateChangelogCommandStep.CHANGELOG_FILE_ARG, diffPath) //
        //                .addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database) //
        //                //        .addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DATABASE_ARG, database) //
        //                .addArgumentValue("diff-types", "data") //
        //                .addArgumentValue("exclude-objects", "FKT_DOCUMENT,FKT_ADDRESS.FK_CONTACT") //
        //                .setOutput(System.out) //
        //                .addArgumentValue(GenerateChangelogCommandStep.OVERWRITE_OUTPUT_FILE_ARG, true) //
        //                .addArgumentValue(GlobalConfiguration.OUTPUT_FILE_ENCODING.getKey(), "UTF-8") //
        //                .addArgumentValue("log-level", "SEVERE") //
        //                .execute();
        //        new CommandScope(DiffChangelogCommandStep.COMMAND_NAME[0]) //
        //                .addArgumentValue(DiffChangelogCommandStep.AUTHOR_ARG, "Fakturama Migration Tool") //
        //                .addArgumentValue(DiffChangelogCommandStep.CHANGELOG_FILE_ARG, diffPath) //
        //                .addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, databaseMaria) //
        //                .addArgumentValue(ReferenceDbUrlConnectionCommandStep.REFERENCE_DATABASE_ARG, database) //
        //                .addArgumentValue("diff-types", "table,data") //
        //                .setOutput(System.out) //
        //                .addArgumentValue(GenerateChangelogCommandStep.OVERWRITE_OUTPUT_FILE_ARG, true) //
        //                .execute();

        Liquibase liquibase = new Liquibase(diffPath, new DirectoryResourceAccessor(Paths.get(".")), databaseMaria);
        liquibase.update();
        //        liquibaseInit.close();
        liquibase.close();
    }

}
