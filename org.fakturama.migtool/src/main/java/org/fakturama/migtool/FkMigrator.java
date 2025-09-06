/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2024 www.fakturama.org
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
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Scope;
import liquibase.change.core.AddForeignKeyConstraintChange;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.command.CommandScope;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.serializer.core.xml.XMLChangeLogSerializer;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.core.ForeignKey;

public class FkMigrator {
    private static final Logger log = LogManager.getLogger(FkMigrator.class);

    private static String formatedDate = new SimpleDateFormat("ddMMyyyy_hhmmss").format(new Date());

    public static List<ForeignKey> getForeignKeys(final Database database) throws DatabaseException, InvalidExampleException {
        DatabaseSnapshot snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(database.getDefaultSchema(), database, new SnapshotControl(database));

        return new ArrayList<>(snapshot.get(ForeignKey.class));
    }

    public static DatabaseChangeLog generateDropForeignKeyChangeLog(final List<ForeignKey> foreignKeys) {
        Path path = Paths.get("dropFk.xml");
        log.info("Path is {}", path);
        DatabaseChangeLog changeLog = new DatabaseChangeLog(path.toString());
        ChangeSet changeSet = new ChangeSet("DropFK-Migration-" + formatedDate, LiquibaseMigration.MIGRATION_AUTHOR, false, false, "", "", "", true, changeLog);
        for (ForeignKey foreignKey : foreignKeys) {
            DropForeignKeyConstraintChange dropFkChange = new DropForeignKeyConstraintChange();
            dropFkChange.setBaseTableName(foreignKey.getForeignKeyTable().getName());
            dropFkChange.setConstraintName(foreignKey.getName());
            changeSet.addChange(dropFkChange);
        }
        changeLog.addChangeSet(changeSet);

        serializeChangelog(path, changeLog);

        return changeLog;
    }

    private static void serializeChangelog(final Path path, final DatabaseChangeLog changeLog) {
        // Serialize the DatabaseChangeLog to an XML file
        XMLChangeLogSerializer serializer = new XMLChangeLogSerializer();
        try (OutputStream out = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            serializer.write(changeLog.getChangeSets(), out);
        } catch (IOException e) {
            log.error("Error writing changelog", e);
        }
    }

    public static DatabaseChangeLog generateCreateForeignKeyChangeLog(final List<ForeignKey> foreignKeys) {
        Path path = Paths.get("recreateFK.xml");
        log.info("Path is {}", path);
        DatabaseChangeLog changeLog = new DatabaseChangeLog(path.toString());

        ChangeSet changeSet = new ChangeSet("ReInsertFK-Migration-" + formatedDate, LiquibaseMigration.MIGRATION_AUTHOR, false, false, "", "", "", true,
                changeLog);
        for (ForeignKey foreignKey : foreignKeys) {
            AddForeignKeyConstraintChange addFkChange = new AddForeignKeyConstraintChange();
            addFkChange.setBaseTableName(foreignKey.getForeignKeyTable().getName());
            addFkChange.setConstraintName(foreignKey.getName());
            addFkChange.setBaseColumnNames(foreignKey.getForeignKeyColumns().get(0).getName());
            addFkChange.setReferencedTableName(foreignKey.getPrimaryKeyTable().getName());
            addFkChange.setReferencedColumnNames(foreignKey.getPrimaryKeyColumns().get(0).getName());
            addFkChange.setOnDelete(foreignKey.getDeleteRule());
            addFkChange.setOnUpdate(foreignKey.getUpdateRule());
            addFkChange.setDeferrable(foreignKey.isDeferrable());

            changeSet.addChange(addFkChange);
        }
        changeLog.addChangeSet(changeSet);

        serializeChangelog(path, changeLog);
        return changeLog;
    }

    public void executeChangeLog(final DatabaseChangeLog changeLog, final Database database) throws Exception {
        Map<String, Object> args = new HashMap<>();
        args.put("changeLogFile", changeLog.getFilePath());
        args.put("contexts", new Contexts().toString());
        args.put("labelFilter", new LabelExpression().toString());

        Scope.child(args, () -> {
            new CommandScope("update").addArgumentValue("changeLogFile", changeLog).execute();
        });
    }
}
