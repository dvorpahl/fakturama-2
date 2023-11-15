package org.fakturama.database.hsql;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.hsqldb.jdbc.JDBCDriver;
import org.hsqldb.jdbc.pool.JDBCPooledDataSource;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

    private static final Logger log = LoggerFactory.getLogger(Activator.class);
    // Register data source factory service under the driver class name
    public static final String HSQL_DRIVER_CLASS = "org.hsqldb.jdbc.JDBCDriver";

    // All MySQL DataSourceFactory services will have their
    // DataSourceFactory.OSGI_JDBC_DRIVER_NAME service property set to this driver name
    public static final String HSQL_DRIVER_NAME = "HSQLDB";

    private static BundleContext context;
    private ServiceRegistration<?> dsfService;

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        log.info("Driver HSQL is loading... ");
        Activator.context = bundleContext;
        Hashtable<String, String> props = new Hashtable<>();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, HSQL_DRIVER_NAME);
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, HSQL_DRIVER_CLASS);
        DriverManager.registerDriver(new JDBCDriver());
        dsfService = context.registerService(DataSourceFactory.class.getName(), new CustomDataSourceFactory(), props);
    }

    @Override
    public void stop(final BundleContext bundleContext) throws Exception {
        Activator.context = null;
        if (dsfService != null) {
            dsfService.unregister();
        }
    }

    private static class CustomDataSourceFactory implements DataSourceFactory {

        public CustomDataSourceFactory() {
        }

        @Override
        public ConnectionPoolDataSource createConnectionPoolDataSource(final Properties props) throws SQLException {
            return new JDBCPooledDataSource();
        }

        @Override
        public XADataSource createXADataSource(final Properties props) throws SQLException {
            return new JDBCXADataSource();
        }

        @Override
        public Driver createDriver(final Properties props) throws SQLException {
            return new JDBCDriver();
        }

        @Override
        public DataSource createDataSource(final Properties props) throws SQLException {
            try {
                return JDBCDataSourceFactory.createDataSource(props);
            } catch (Exception e) {
                throw new SQLException(e.getMessage(), e);
            }

        }
    }

}
