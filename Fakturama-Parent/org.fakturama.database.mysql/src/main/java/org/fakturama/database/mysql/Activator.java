package org.fakturama.database.mysql;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.MysqlXADataSource;

public class Activator implements BundleActivator {

    // Register data source factory service under the driver class name
    public static final String MYSQL_DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    // All MySQL DataSourceFactory services will have their
    // DataSourceFactory.OSGI_JDBC_DRIVER_NAME service property set to this driver name
    public static final String MYSQL_DRIVER_NAME = "MySQL";

    private static BundleContext context;
    private ServiceRegistration<?> dsfService;

    static BundleContext getContext() {
        return context;
    }

    @Override
    public void start(final BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;
        Hashtable<String, String> props = new Hashtable<>();
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME, MYSQL_DRIVER_NAME);
        props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS, MYSQL_DRIVER_CLASS);
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
            return new MysqlConnectionPoolDataSource();
        }

        @Override
        public XADataSource createXADataSource(final Properties props) throws SQLException {
            return new MysqlXADataSource();
        }

        @Override
        public Driver createDriver(final Properties props) throws SQLException {
            return new com.mysql.cj.jdbc.Driver();
        }

        @Override
        public DataSource createDataSource(final Properties props) throws SQLException {
            return new MysqlDataSource();

        }
    }

}
