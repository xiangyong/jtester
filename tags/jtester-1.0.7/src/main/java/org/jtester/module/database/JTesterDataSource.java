package org.jtester.module.database;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.jtester.module.core.helper.ConfigurationHelper;
import org.jtester.module.tracer.jdbc.ProxyConnection;

/**
 * JTester DataSource
 * 
 * @author darui.wudr
 * 
 */
public class JTesterDataSource implements DataSource {
	private final static Logger log4j = Logger.getLogger(JTesterDataSource.class);
	private DataSource dataSource;

	private final DataSourceType type;
	private final String driver;
	private final String url;
	private final String username;
	private final String password;
	private final String schemaNames;

	public JTesterDataSource(DataSourceType type, String driver, String url, String schemaNames, String user,
			String pass) {
		this.type = type;
		this.driver = driver;
		this.url = url;
		this.schemaNames = schemaNames;
		this.username = user;
		this.password = pass;
		this.dataSource = this.createDataSource();
	}

	private DataSource createDataSource() {
		this.checkDoesTestDB();
		this.registerDriver();
		BasicDataSource dataSource = new BasicDataSource();
		log4j.info("Creating data source. Driver: " + driver + ", url: " + url + ", user: " + username
				+ ", password: <not shown>");
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;
	}

	private boolean proxy = false;// TODO

	/**
	 * {@inheritDoc}
	 */
	public Connection getConnection() throws SQLException {
		Connection conn = dataSource.getConnection();
		// 是否进行代理
		if (proxy) {
			return getProxy(conn);
		} else {
			return conn;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Connection getConnection(String arg0, String arg1) throws SQLException {
		Connection conn = dataSource.getConnection(arg0, arg1);
		// 是否进行代理
		if (proxy) {
			return getProxy(conn);
		} else {
			return conn;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public PrintWriter getLogWriter() throws SQLException {
		return dataSource.getLogWriter();
	}

	/**
	 * {@inheritDoc}
	 */
	public int getLoginTimeout() throws SQLException {
		return dataSource.getLoginTimeout();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLogWriter(PrintWriter arg0) throws SQLException {
		dataSource.setLogWriter(arg0);
	}

	/**
	 * {@inheritDoc}
	 */
	public void setLoginTimeout(int arg0) throws SQLException {
		dataSource.setLoginTimeout(arg0);
	}

	/**
	 * 判断是否本地数据库或者是test数据库<br>
	 * 如果不是返回RuntimeException
	 */
	private void checkDoesTestDB() {
		if (ConfigurationHelper.doesOnlyTestDatabase() == false) {
			return;
		}
		if (this.type.isMemoryDB()) {
			return;
		}

		if (url.contains("127.0.0.1") || url.toUpperCase().contains("LOCALHOST")) {
			return;
		}
		String[] schemas = this.schemaNames.split(";");
		for (String schema : schemas) {
			if (schema.trim().equals("")) {
				continue;
			}
			String temp = schema.toUpperCase();
			if (!temp.endsWith("TEST") && !temp.startsWith("TEST")) {
				throw new RuntimeException("only local db or test db will be allowed to connect,url:" + url
						+ ", schemas:" + this.schemaNames);
			}
		}
	}

	@Override
	public String toString() {
		return "JTesterDataSource [type=" + type + ", driver=" + driver + ", url=" + url + ", username=" + username
				+ ", password=" + password + ", schemaNames=" + schemaNames + "]";
	}

	/**
	 * 得到连接代理
	 */
	private static Connection getProxy(Connection conn) {
		if (conn instanceof ProxyConnection) {
			return conn;
		} else {
			return new ProxyConnection(conn);
		}
	}

	private final static Set<String> registered = new HashSet<String>();

	/**
	 * 注册数据库驱动
	 */
	private void registerDriver() {
		try {
			if (registered.contains(driver)) {
				return;
			}
			DriverManager.registerDriver((Driver) Class.forName(driver).newInstance());
			registered.add(driver);
		} catch (Exception e) {
			throw new RuntimeException("Cannot register SQL driver " + driver);
		}
	}

	// /**
	// * 是否需要去除数据库的外键约束和字段not null约束
	// *
	// * @param dataSource
	// */
	// protected void doesDisableDataSource(DataSource dataSource) {
	// if (!ConfigUtil.doesDisableConstraints()) {
	// return;
	// }
	// log.info("Disables all foreign key and not-null constraints on the configured schema's.");
	// SQLHandler handler = new DefaultSQLHandler(dataSource);
	// ConstraintsDisabler disabler =
	// DatabaseModuleConfigUtils.getConfiguredDatabaseTaskInstance(
	// ConstraintsDisabler.class, ConfigUtil.unitilscfg, handler);
	// disabler.disableConstraints();
	// }
}
