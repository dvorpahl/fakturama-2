package org.fakturama.database.mariadb;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.mariadb.jdbc.MariaDbDataSource;
import org.mariadb.jdbc.MariaDbPoolDataSource;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

public class Activator implements BundleActivator {

    // Register data source factory service under the driver class name
    public static final String MARIADB_DRIVER_CLASS = "org.mariadb.jdbc.Driver";

    // All MySQL DataSourceFactory services will have their
    // DataSourceFactory.OSGI_JDBC_DRIVER_NAME service property set to this driver name
    public static final String MARIADB_DRIVER_NAME = "MariaDB";

    private static BundleContext context;
    private ServiceRegistration<?> dsfService;

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
        Hashtable<String, String> props = new Hashtable<>();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, MARIADB_DRIVER_NAME);
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, MARIADB_DRIVER_CLASS);
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
            return new MariaDbPoolDataSource();
        }

        @Override
        public XADataSource createXADataSource(final Properties props) throws SQLException {
            return new MariaDbDataSource();
        }

        @Override
        public Driver createDriver(final Properties props) throws SQLException {
            return new org.mariadb.jdbc.Driver();
        }

        @Override
        public DataSource createDataSource(final Properties props) throws SQLException {
            return new MariaDbDataSource();

        }
    }

}
