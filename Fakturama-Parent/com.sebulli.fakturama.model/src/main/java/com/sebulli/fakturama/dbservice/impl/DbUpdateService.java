/*
 * Fakturama - Free Invoicing Software - http://www.fakturama.org
 * 
 * Copyright (C) 2016 www.fakturama.org
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: The Fakturama Team - initial API and implementation
 */

package com.sebulli.fakturama.dbservice.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opcoach.e4.preferences.IPreferenceStoreProvider;
import com.sebulli.fakturama.dbconnector.IActivateDbServer;
import com.sebulli.fakturama.dbconnector.IDbConnection;
import com.sebulli.fakturama.dbservice.IDbUpdateService;
import com.sebulli.fakturama.misc.Constants;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.spi.PersistenceProvider;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.exception.ValidationFailedException;
import liquibase.resource.OSGiResourceAccessor;

/**
 * Implementation of {@link IDbUpdateService}.
 *
 */
public class DbUpdateService implements IDbUpdateService {
    private static final Logger log = LoggerFactory.getLogger(DbUpdateService.class);
    private static final String SYS_PROP_DATABASE_PORT = "hsql.database.port";
    private IPreferenceStore preferenceStore;
    private IActivateDbServer currentDbServer;

    /* (non-Javadoc)
     * @see com.sebulli.fakturama.dbservice.IDbUpdateService#updateDatabase()
     */
    @Override
    public boolean updateDatabase() {
        boolean retval = true;

        // get the preferences for this application
        Bundle bundle = FrameworkUtil.getBundle(DbUpdateService.class);
        BundleContext context = bundle.getBundleContext();
        ServiceReference<IPreferenceStoreProvider> serviceReference = context.getServiceReference(IPreferenceStoreProvider.class);
        preferenceStore = context.getService(serviceReference).getPreferenceStore();
        Liquibase liquibase = null;
        try (java.sql.Connection connection = openConnection(context);) {
            if (connection == null) {
                throw new SQLException("can't create database connection!");
            }

            // emergency switch: turn off this feature with NODBUPDATE=true
            if (Boolean.getBoolean("NODBUPDATE")) {
                return retval;
            }
            /*
             * Annoying Feature: Liquibase Hub.
             * https://docs.liquibase.com/tools-integrations/liquibase-hub/operations.html?Highlight=liquibase%20hub
             * you can set the level of data specifying SET JAVA_OPTS="-DLiquibaseHubMode=[all|meta|off]"
             * Alternatively, you can pass your API key as a runtime argument and run your commands as usual or you 
             * can specify it in your JAVA_OPTS as -Dliquibase.hub.apiKey. 
             */
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            liquibase = new liquibase.Liquibase("/changelog/db.changelog-master.xml", new OSGiResourceAccessor(bundle), database);

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (ValidationFailedException exc) {
            System.err.println("Database has not the correct version! " + exc.getMessage());
            retval = false;
        } catch (LiquibaseException | SQLException | NullPointerException ex) {
            System.err.println("Failed to create the database connection: " + ex);
            retval = false;
        } finally {
            if (liquibase != null) {
                try {
                    liquibase.close();
                } catch (Exception e) {
                    // ignore
                }
            }
        }
        return retval;
    }

    /**
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    private Connection openConnection(final BundleContext context) {
        Connection conn = null;
        try {
            ServiceReference<?>[] allServiceReferences = context.getAllServiceReferences(org.osgi.service.jdbc.DataSourceFactory.class.getName(),
                    String.format("(%s=%s)", DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, preferenceStore.getString(PersistenceUnitProperties.JDBC_DRIVER)));
            ServiceReference<DataSourceFactory> serviceReference;

            if (allServiceReferences != null && allServiceReferences.length > 0) {
                serviceReference = (ServiceReference<DataSourceFactory>) allServiceReferences[0];
            } else {
                serviceReference = null;
                System.err.println("No service reference found for database connection!");
            }
            Properties prop = new Properties();
            prop.put(DataSourceFactory.JDBC_URL, preferenceStore.getString(PersistenceUnitProperties.JDBC_URL));
            prop.put(DataSourceFactory.JDBC_USER, preferenceStore.getString(PersistenceUnitProperties.JDBC_USER));
            prop.put(DataSourceFactory.JDBC_PASSWORD, preferenceStore.getString(PersistenceUnitProperties.JDBC_PASSWORD));

            String dataSource = (String) prop.get(DataSourceFactory.JDBC_URL);
            /*
             * This part is for optimizing performance. Since most users don't want to create an HSQL server database,
             * we use the standard database (under working dir's Database directory) and start them in server mode.
             * This is fully transparent to the user. The database is closed after the application shut down.
             * The problem is, if the user switches the workspace, we have to switch the database, too.
             * Therefore we have to shutdown the old one and start the database in the correct working directory.
             * 
             * ONLY(!!!) important if we use HSQL in the standard way. All other possibilities use an extra database which is 
             * independent of working directory. 
             */
            if (dataSource.contains("hsqldb")) {
                allServiceReferences = context.getAllServiceReferences(IActivateDbServer.class.getName(), null);
                if (allServiceReferences != null && allServiceReferences.length > 0) {
                    ServiceReference<IActivateDbServer> currentDbServerRef = (ServiceReference<IActivateDbServer>) allServiceReferences[0];
                    String dataSourceName = preferenceStore.getString(DataSourceFactory.JDBC_DATASOURCE_NAME);

                    // check old setting (before v2.1.2)
                    if (StringUtils.isBlank(dataSourceName)) {
                        dataSourceName = preferenceStore.getString("hsqlfiledb");
                    }
                    prop.put(DataSourceFactory.JDBC_DATASOURCE_NAME, dataSourceName);

                    String sysPropPort = System.getProperty(SYS_PROP_DATABASE_PORT, preferenceStore.getString(DataSourceFactory.JDBC_PORT_NUMBER));
                    if (StringUtils.isNumeric(sysPropPort)) {
                        prop.put(DataSourceFactory.JDBC_PORT_NUMBER, sysPropPort);
                    }

                    prop.put("encoding", "UTF-8");
                    prop.put(Constants.GENERAL_WORKSPACE, preferenceStore.getString(Constants.GENERAL_WORKSPACE));

                    currentDbServer = context.getService(currentDbServerRef);
                    if (!isDbAlive()) {
                        Properties activateProps = currentDbServer.activateServer(prop);
                        preferenceStore.putValue(PersistenceUnitProperties.JDBC_URL, String.format("jdbc:hsqldb:hsql://localhost:%s/%s",
                                activateProps.get(DataSourceFactory.JDBC_PORT_NUMBER), activateProps.get(DataSourceFactory.JDBC_DATABASE_NAME)));
                        prop.put(DataSourceFactory.JDBC_URL, preferenceStore.getString(PersistenceUnitProperties.JDBC_URL));
                        preferenceStore.putValue(DataSourceFactory.JDBC_DATASOURCE_NAME, (String) activateProps.get(DataSourceFactory.JDBC_DATASOURCE_NAME));
                    } else {
                        log.info("database was already started");
                    }
                    ServiceReference<IDbConnection> dbConnectionRef = (ServiceReference<IDbConnection>) allServiceReferences[0];
                    IDbConnection dbConnection = context.getService(dbConnectionRef);
                    conn = dbConnection.getConnection();
                }
            }

            if (conn == null) {
                log.info("Creating Database connectionä ...");
                conn = context.getService(serviceReference).createDataSource(prop).getConnection();
            }

            if (conn != null) {
                log.info("Starting database link ...");
                allServiceReferences = context.getAllServiceReferences(PersistenceProvider.class.getName(), null);
                ServiceReference<PersistenceProvider> serviceReferencePP = (ServiceReference<PersistenceProvider>) allServiceReferences[0];
                PersistenceProvider pp = context.getService(serviceReferencePP);
                Map<String, Object> properties = new HashMap<>();
                properties.put(PersistenceUnitProperties.CLASSLOADER, this.getClass().getClassLoader());

                properties.put(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "persistence.xml");
                properties.put(PersistenceUnitProperties.JDBC_DRIVER, preferenceStore.getString(PersistenceUnitProperties.JDBC_DRIVER));//"org.hsqldb.jdbc.JDBCDriver");//prop.getProperty(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS)); //org.hsqldb.jdbc.JDBCDriver
                properties.put(PersistenceUnitProperties.JDBC_URL, prop.getProperty(DataSourceFactory.JDBC_URL));
                properties.put(PersistenceUnitProperties.JDBC_USER, prop.getProperty(DataSourceFactory.JDBC_USER));
                properties.put(PersistenceUnitProperties.JDBC_PASSWORD, prop.getProperty(DataSourceFactory.JDBC_PASSWORD));

                EntityManagerFactory emf = pp.createEntityManagerFactory("unconfigured2", properties);
                emf.createEntityManager();
                context.registerService(EntityManagerFactory.class, emf, null);
            }
        } catch (SQLException ex) {
            // handle any errors
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        } catch (InvalidSyntaxException e) {
            System.err.println("Invalid syntax: " + e.getMessage());
        }
        return conn;
    }

    @Override
    @PreDestroy
    public void shutDownDb() {
        if (currentDbServer != null) {
            try {
                currentDbServer.stopServer();
            } catch (Exception e) {
                // ignore any exception
            }
        }
    }

    @Override
    public boolean isDbAlive() {
        return currentDbServer != null && currentDbServer.isAlive();
    }

    @Override
    public ServiceRegistration initOldDaoEntityFaktory(final String oldJdbcUrl) {
        BundleContext context = FrameworkUtil.getBundle(getClass()).getBundleContext();

        try {
            ServiceReference<?>[] allServiceReferences = context.getAllServiceReferences(PersistenceProvider.class.getName(), null);
            ServiceReference<PersistenceProvider> serviceReferencePP = (ServiceReference<PersistenceProvider>) allServiceReferences[0];
            PersistenceProvider pp = context.getService(serviceReferencePP);
            Map<String, Object> properties = new HashMap<>();
            properties.put(PersistenceUnitProperties.CLASSLOADER, this.getClass().getClassLoader());

            log.info("Bundle State: {} with name {}", context.getBundle().getState(), context.getBundle().getSymbolicName());
            properties.put(PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML, "persistence.xml");
            properties.put(PersistenceUnitProperties.JDBC_DRIVER, "org.hsqldb.jdbc.JDBCDriver");
            properties.put(PersistenceUnitProperties.JDBC_URL, oldJdbcUrl);
            properties.put(PersistenceUnitProperties.JDBC_USER, "sa");
            properties.put(PersistenceUnitProperties.LOGGING_LEVEL, "INFO");
            properties.put(PersistenceUnitProperties.WEAVING, "false");
            properties.put(PersistenceUnitProperties.WEAVING_INTERNAL, "false");

            EntityManagerFactory emf = pp.createEntityManagerFactory("origin-datasource", properties);
            Hashtable<String, Object> emfProperties = new Hashtable<>();
            emfProperties.put("persistence.unit.name", "origin-datasource");
            return context.registerService(EntityManagerFactory.class, emf, emfProperties);
        } catch (Exception e) {
            return null;
        }

        //        @GeminiPersistenceContext(unitName = "origin-datasource", properties = {
        //                @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_URL, valuePref = @Preference("OLD_JDBC_URL")),
        //                @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_DRIVER, value = "org.hsqldb.jdbc.JDBCDriver"),
        //                @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_USER, value = "sa"),
        //                @GeminiPersistenceProperty(name = PersistenceUnitProperties.JDBC_PASSWORD, value = ""),
        //                @GeminiPersistenceProperty(name = PersistenceUnitProperties.LOGGING_LEVEL, value = "INFO"),
        //                @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING, value = "false"),
        //                @GeminiPersistenceProperty(name = PersistenceUnitProperties.WEAVING_INTERNAL, value = "false") })

    }
}
