package HibernateUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.jboss.logging.Logger;

public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider, ServiceRegistryAwareService {

	private DriverManagerConnectionProviderImpl provider = new DriverManagerConnectionProviderImpl();

	private Logger log = Logger.getLogger(MultiTenantConnectionProviderImpl.class.getName());

	public boolean isUnwrappableAs(Class arg0) {
		return provider.isUnwrappableAs(arg0);
	}

	public <T> T unwrap(Class<T> arg0) {
		return provider.unwrap(arg0);
	}

	public Connection getAnyConnection() throws SQLException {
		return provider.getConnection();
	}

	public Connection getConnection(String tenantId) throws SQLException {
		Connection con = getAnyConnection();
		try {
			con.createStatement().execute("use " + tenantId);
			log.info("Using " + tenantId + " as database schema");
		} catch (SQLException ex) {
			throw new HibernateException("Could not alter connection for specific schema");
		}
		return con;
	}

	public void releaseAnyConnection(Connection con) throws SQLException {
		provider.closeConnection(con);

	}

	public void releaseConnection(String tenantId, Connection con) throws SQLException {
		try {
			con.createStatement().execute("USE mysql");
			System.out.println("Now, released " + tenantId);
		} catch (SQLException ex) {
			throw new HibernateException("Unable to reset");
		}
		provider.closeConnection(con);

	}

	public boolean supportsAggressiveRelease() {
		return false;
	}

	public void injectServices(ServiceRegistryImplementor registry) {
		Map settings = registry.getService(ConfigurationService.class).getSettings();
		provider.configure(settings);
		provider.injectServices(registry);

	}
}