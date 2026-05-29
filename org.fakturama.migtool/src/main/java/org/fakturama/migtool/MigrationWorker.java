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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.RegExUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fakturama.migtool.model.Destination;
import org.fakturama.migtool.model.Settings;
import org.yaml.snakeyaml.Yaml;

import liquibase.CatalogAndSchema;
import liquibase.Contexts;
import liquibase.GlobalConfiguration;
import liquibase.LabelExpression;
import liquibase.Scope;
import liquibase.UpdateSummaryEnum;
import liquibase.UpdateSummaryOutputEnum;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.command.CommandScope;
import liquibase.command.core.GenerateChangelogCommandStep;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.ChangeExecListenerCommandStep;
import liquibase.command.core.helpers.DatabaseChangelogCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionArgumentsCommandStep;
import liquibase.command.core.helpers.PreCompareCommandStep;
import liquibase.command.core.helpers.ShowSummaryArgument;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.DiffGeneratorFactory;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.exception.CommandExecutionException;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.DirectoryResourceAccessor;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Table;

public class MigrationWorker {

    /**
     * 
     */
    private static final String APPLICATION_YML = "application.yml";

    private static final Logger log = LogManager.getLogger(MigrationWorker.class);

    private static final String FAKTURAMA_CHANGELOG = "changelog/db.changelog-master.xml";
    Connection connectionOld = null;
    Database databaseOld = null;
    Connection connectionNew = null;
    Database databaseNew = null;

    private final Settings settings;

    public MigrationWorker() throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream;
        if (Files.exists(Paths.get(MigrationWorker.APPLICATION_YML))) {
            inputStream = Files.newInputStream(Paths.get(MigrationWorker.APPLICATION_YML), StandardOpenOption.READ);
        } else {
            inputStream = this.getClass().getClassLoader().getResourceAsStream(MigrationWorker.APPLICATION_YML);
        }
        settings = yaml.loadAs(inputStream, Settings.class);

    }

    public void run() {
        try {
            String diffPath = "./diffOutput.json";
            String diffPathOut = "./diffOutput_changed.json";
            Files.deleteIfExists(Path.of(diffPath));
            Files.deleteIfExists(Path.of(diffPathOut));

            runDefaultScriptsAgainstBothDatabases();

            checkForColumnDiffs("./someShittyDiff.json");

            List<ForeignKey> foreignKeys = FkMigrator.getForeignKeys(databaseNew);
            DatabaseChangeLog dropFkChangeLog = FkMigrator.generateDropForeignKeyChangeLog(foreignKeys);
            DatabaseChangeLog createFkChangeLog = FkMigrator.generateCreateForeignKeyChangeLog(foreignKeys);

            log.info("drop foreign keys in new database");
            insertDataIntoDb(dropFkChangeLog.getFilePath(), databaseNew, new DirectoryResourceAccessor(Paths.get(".")));

            dumpDataFromOldDb(diffPath, diffPathOut);

            insertDataIntoDb(diffPathOut, databaseNew, new DirectoryResourceAccessor(Paths.get(".")));

            updatePicturesFromOldDb();

            log.info("recreate foreign keys in new database");
            insertDataIntoDb(createFkChangeLog.getFilePath(), databaseNew, new DirectoryResourceAccessor(Paths.get(".")));
        } catch (Exception e) {
            log.error("Error while migration", e);
        }
    }

    private void runDefaultScriptsAgainstBothDatabases() throws SQLException, DatabaseException, LiquibaseException, FileNotFoundException {
        getConOld();
        getConNew();

        // start Fakturama Changes on all Databases first
        Resource res = null;
        try (ClassLoaderResourceAccessor cp = new ClassLoaderResourceAccessor()) {
            res = cp.get(FAKTURAMA_CHANGELOG);
            if (res.exists()) {
                log.info("running default scripts against old database");
                insertDataIntoDb(res.getPath(), databaseOld, new ClassLoaderResourceAccessor());
                log.info("running default scripts against new database");
                insertDataIntoDb(res.getPath(), databaseNew, new ClassLoaderResourceAccessor());
            } else {
                throw new RuntimeException("Cannot find Fakturama Changelog");
            }
        } catch (Exception e) {
            log.error("Something weird", e);
        }
    }

    private void runChangelogObjectOnNewDb(final DatabaseChangeLog changeLog) throws LiquibaseException, FileNotFoundException {
        runInScope(() -> {

            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, databaseNew);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_ARG, changeLog);
            updateCommand.addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, null);
            updateCommand.addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, null);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, null);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, UpdateSummaryOutputEnum.LOG);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, UpdateSummaryEnum.SUMMARY);

            updateCommand.execute();
        }, databaseNew, new DirectoryResourceAccessor(Paths.get(".")));
    }

    private void dumpDataFromOldDb(final String diffPath, final String diffPathOut)
            throws SQLException, DatabaseException, CommandExecutionException, IOException {
        getConOld();
        log.info("running dump of data from old database");
        new CommandScope(GenerateChangelogCommandStep.COMMAND_NAME[0]) //
                .addArgumentValue(GenerateChangelogCommandStep.AUTHOR_ARG, LiquibaseMigration.MIGRATION_AUTHOR) //
                .addArgumentValue(GenerateChangelogCommandStep.CHANGELOG_FILE_ARG, diffPath) //
                .addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, databaseOld) //
                .addArgumentValue(PreCompareCommandStep.DIFF_TYPES_ARG, "data") //
                .addArgumentValue(GenerateChangelogCommandStep.OVERWRITE_OUTPUT_FILE_ARG, true) //
                .addArgumentValue(GlobalConfiguration.OUTPUT_FILE_ENCODING.getKey(), "UTF-8") //
                .addArgumentValue("log-level", "SEVERE") //
                .execute();

        // since this code does not filter blob properly (thanks liquibase), we remove it from json output
        String text = Files.readString(Paths.get(diffPath), StandardCharsets.UTF_8);
        String result = RegExUtils.removeAll(text, LiquibaseMigration.UNSUPPORTED_BLOB);
        Files.writeString(Paths.get(diffPathOut), result, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    private void checkForColumnDiffs(final String diffPath) throws SQLException, IOException, LiquibaseException {
        Files.deleteIfExists(Paths.get(diffPath));
        getConOld();
        getConNew();

        HashSet<Class<? extends DatabaseObject>> set = new HashSet<>();
        set.add(Column.class);
        CompareControl compareControl = new CompareControl(set);
        compareControl.addSuppressedField(Column.class, "type");
        compareControl.addSuppressedField(Column.class, "defaultValue");
        compareControl.addSuppressedField(Column.class, "order");
        compareControl.addSuppressedField(Column.class, "remarks");

        SnapshotControl snapshotControl = new SnapshotControl(databaseOld, new Class[] { Table.class, Column.class });

        SnapshotGeneratorFactory.resetAll();
        DatabaseSnapshot snapshotOld = SnapshotGeneratorFactory.getInstance().createSnapshot(new CatalogAndSchema(null, settings.getSource().getCatalog()),
                databaseOld, snapshotControl);

        SnapshotGeneratorFactory.resetAll();
        DatabaseSnapshot snapshotNew = SnapshotGeneratorFactory.getInstance().createSnapshot(new CatalogAndSchema(null, settings.getDestination().getCatalog()),
                databaseNew, snapshotControl);

        DiffResult diffResult = DiffGeneratorFactory.getInstance().compare(snapshotNew, snapshotOld, compareControl);
        Set<Column> missing = new HashSet<>(diffResult.getMissingObjects(Column.class));
        Set<Column> unexpected = new HashSet<>(diffResult.getUnexpectedObjects(Column.class));
        missing.removeIf(c -> filterName(c.getRelation().getName()));
        unexpected.removeIf(c -> filterName(c.getRelation().getName()));

        if (!missing.isEmpty() || !unexpected.isEmpty()) {
            log.error("Some Columns need to be fixed: Missing {}, Unexpected {}", missing, unexpected);
            System.exit(1);
        }
        log.info("running dump of data from old database");
    }

    private boolean filterName(final String name) {
        return (name.startsWith("old_") || name.startsWith("OLD_") || name.startsWith("tmp_") || name.startsWith("TMP_"));
    }

    private void insertDataIntoDb(final String diffPathOut, final Database database, final ResourceAccessor resourceAccessor)
            throws LiquibaseException, SQLException {
        log.info("running insert of data into database");
        getConOld();
        getConNew();

        final ChangeLogParameters changeLogParameters = new ChangeLogParameters(databaseNew);
        runInScope(() -> {

            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionArgumentsCommandStep.DATABASE_ARG, database);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, diffPathOut);
            updateCommand.addArgumentValue(UpdateCommandStep.CONTEXTS_ARG, null);
            updateCommand.addArgumentValue(UpdateCommandStep.LABEL_FILTER_ARG, null);
            updateCommand.addArgumentValue(ChangeExecListenerCommandStep.CHANGE_EXEC_LISTENER_ARG, null);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY_OUTPUT, UpdateSummaryOutputEnum.LOG);
            updateCommand.addArgumentValue(DatabaseChangelogCommandStep.CHANGELOG_PARAMETERS, changeLogParameters);
            updateCommand.addArgumentValue(ShowSummaryArgument.SHOW_SUMMARY, UpdateSummaryEnum.SUMMARY);
            updateCommand.addArgumentValue(PreCompareCommandStep.EXCLUDE_OBJECTS_ARG, "column:PICTURE");

            updateCommand.execute();
        }, database, resourceAccessor);
    }

    private void updatePicturesFromOldDb() throws SQLException, DatabaseException {
        log.info("running blob migration for pictures");
        getConOld();
        getConNew();

        insertPicturesIntoNewDb("FKT_DOCUMENTITEM");
        connectionNew.commit();
        insertPicturesIntoNewDb("FKT_PRODUCT");
        connectionNew.commit();
    }

    /**
     * @param connectionNew
     * @param databaseNew
     */
    private void getConNew() throws SQLException, DatabaseException {
        Destination destination = settings.getDestination();
        connectionNew = DriverManager.getConnection(destination.getUrl(), destination.getUser(), destination.getPassword());
        connectionNew.setCatalog(destination.getCatalog());
        databaseNew = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connectionNew));
        databaseNew.setAutoCommit(true);
    }

    /**
     * @param connectionOld
     * @param databaseOld
     * @throws SQLException
     * @throws DatabaseException
     */
    private void getConOld() throws SQLException, DatabaseException {
        Destination destination = settings.getSource();
        connectionOld = DriverManager.getConnection(destination.getUrl(), destination.getUser(), destination.getPassword());
        connectionOld.setCatalog(destination.getCatalog());
        databaseOld = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connectionOld));
    }

    /**
     * @param connectionOld
     * @param table
     * @throws SQLException
     */
    private void insertPicturesIntoNewDb(final String table) throws SQLException {
        String[] colNames = { "ID", "PICTURE" };
        try (PreparedStatement psOldDocumentItem = connectionOld.prepareStatement("Select id, picture from " + table + " where picture is not null", colNames);
                PreparedStatement psNewDocumentItem = connectionNew.prepareStatement("UPDATE " + table + " SET PICTURE=? WHERE ID=?");) {
            ResultSet resultOldDocumentItem = psOldDocumentItem.executeQuery();
            while (resultOldDocumentItem.next()) {
                Long id = resultOldDocumentItem.getLong(colNames[0]);
                byte[] picture = resultOldDocumentItem.getBytes(colNames[1]);
                psNewDocumentItem.setBytes(1, picture);
                psNewDocumentItem.setLong(2, id);
                int updated = psNewDocumentItem.executeUpdate();
                log.info("Updated id {} with {} rows", id, updated);
            }
            connectionNew.commit();
        }
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

    private void runInScope(final Scope.ScopedRunner scopedRunner, final Database database, final ResourceAccessor resourceAccessor) throws LiquibaseException {
        Map<String, Object> scopeObjects = new HashMap<>();
        scopeObjects.put(Scope.Attr.database.name(), database);
        scopeObjects.put(Scope.Attr.resourceAccessor.name(), resourceAccessor);

        try {
            Scope.child(scopeObjects, scopedRunner);
        } catch (LiquibaseException e) {
            throw e;
        } catch (Exception e) {
            throw new LiquibaseException(e);
        }
    }
}