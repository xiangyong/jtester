//package org.jtester.module.tracer.jdbc;
//
//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.SQLWarning;
//import java.sql.Savepoint;
//import java.sql.Statement;
//import java.util.Map;
//
///**
// * 连接代理,主要作用是返回statment的代理
// */
//@SuppressWarnings({ "rawtypes", "unchecked" })
//public class ProxyConnection implements Connection {
//	private Connection connection;
//
//	protected boolean hasClosed = false;
//
//	public ProxyConnection(Connection connection) {
//		this.connection = connection;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void close() throws SQLException {
//		if (connection != null) {
//			connection.close();
//			hasClosed = true;
//		}
//		connection = null;
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean isClosed() throws SQLException {
//		return this.hasClosed || connection == null;
//	}
//
//	protected void checkOpen() throws SQLException {
//		if (this.hasClosed || connection == null) {
//			throw new SQLException("Connection is closed.");
//		}
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public Statement createStatement() throws SQLException {
//		checkOpen();
//		return new ProxyStatement(this, connection.createStatement());
//	}
//
//	/**
//	 * 返回Statement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public Statement createStatement(int resultSetType, int resultSetConcurrency)
//			throws SQLException {
//		checkOpen();
//		return new ProxyStatement(this, connection.createStatement(
//				resultSetType, resultSetConcurrency));
//	}
//
//	/**
//	 * 返回Statement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public Statement createStatement(int resultSetType,
//			int resultSetConcurrency, int resultSetHoldability)
//			throws SQLException {
//		checkOpen();
//		return new ProxyStatement(this, connection.createStatement(
//				resultSetType, resultSetConcurrency, resultSetHoldability));
//	}
//
//	/**
//	 * 返回PreparedStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public PreparedStatement prepareStatement(String sql) throws SQLException {
//		checkOpen();
//		return new ProxyPreparedStatement(this,
//				connection.prepareStatement(sql), sql);
//	}
//
//	/**
//	 * 返回PreparedStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
//			throws SQLException {
//		checkOpen();
//		return new ProxyPreparedStatement(this, connection.prepareStatement(
//				sql, autoGeneratedKeys), sql);
//	}
//
//	/**
//	 * 返回PreparedStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public PreparedStatement prepareStatement(String sql, int resultSetType,
//			int resultSetConcurrency) throws SQLException {
//		checkOpen();
//		return new ProxyPreparedStatement(this, connection.prepareStatement(
//				sql, resultSetType, resultSetConcurrency), sql);
//	}
//
//	/**
//	 * 返回PreparedStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public PreparedStatement prepareStatement(String sql, int resultSetType,
//			int resultSetConcurrency, int resultSetHoldability)
//			throws SQLException {
//		checkOpen();
//		return new ProxyPreparedStatement(this,
//				connection.prepareStatement(sql, resultSetType,
//						resultSetConcurrency, resultSetHoldability), sql);
//	}
//
//	/**
//	 * 返回PreparedStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
//			throws SQLException {
//		checkOpen();
//		return new ProxyPreparedStatement(this, connection.prepareStatement(
//				sql, columnIndexes), sql);
//	}
//
//	/**
//	 * 返回CallableStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public CallableStatement prepareCall(String sql) throws SQLException {
//		checkOpen();
//		return new ProxyCallableStatement(this, connection.prepareCall(sql),
//				sql);
//	}
//
//	/**
//	 * 返回CallableStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public CallableStatement prepareCall(String sql, int resultSetType,
//			int resultSetConcurrency) throws SQLException {
//		checkOpen();
//		return new ProxyCallableStatement(this, connection.prepareCall(sql,
//				resultSetType, resultSetConcurrency), sql);
//	}
//
//	/**
//	 * 返回CallableStatement代理类<br>
//	 * <br> {@inheritDoc}
//	 */
//	public CallableStatement prepareCall(String sql, int resultSetType,
//			int resultSetConcurrency, int resultSetHoldability)
//			throws SQLException {
//		checkOpen();
//		return new ProxyCallableStatement(this, connection.prepareCall(sql,
//				resultSetType, resultSetConcurrency, resultSetHoldability), sql);
//
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getHoldability() throws SQLException {
//		checkOpen();
//		return connection.getHoldability();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public int getTransactionIsolation() throws SQLException {
//		checkOpen();
//		return connection.getTransactionIsolation();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void clearWarnings() throws SQLException {
//		checkOpen();
//		connection.clearWarnings();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void commit() throws SQLException {
//		checkOpen();
//		connection.commit();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void rollback() throws SQLException {
//		checkOpen();
//		connection.rollback();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean getAutoCommit() throws SQLException {
//		checkOpen();
//		return connection.getAutoCommit();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public boolean isReadOnly() throws SQLException {
//		checkOpen();
//		return connection.isReadOnly();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setHoldability(int holdability) throws SQLException {
//		checkOpen();
//		connection.setHoldability(holdability);
//	}
//
//	public void setTransactionIsolation(int level) throws SQLException {
//		checkOpen();
//		connection.setTransactionIsolation(level);
//
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setAutoCommit(boolean autoCommit) throws SQLException {
//		checkOpen();
//		connection.setAutoCommit(autoCommit);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setReadOnly(boolean readOnly) throws SQLException {
//		checkOpen();
//		connection.setReadOnly(readOnly);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public String getCatalog() throws SQLException {
//		checkOpen();
//		return connection.getCatalog();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setCatalog(String catalog) throws SQLException {
//		checkOpen();
//		connection.setCatalog(catalog);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public DatabaseMetaData getMetaData() throws SQLException {
//		checkOpen();
//		return connection.getMetaData();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public SQLWarning getWarnings() throws SQLException {
//		checkOpen();
//		return connection.getWarnings();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public Savepoint setSavepoint() throws SQLException {
//		checkOpen();
//		return connection.setSavepoint();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
//		checkOpen();
//		connection.releaseSavepoint(savepoint);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void rollback(Savepoint savepoint) throws SQLException {
//		checkOpen();
//		connection.rollback(savepoint);
//
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public Map getTypeMap() throws SQLException {
//		checkOpen();
//		return connection.getTypeMap();
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public void setTypeMap(Map map) throws SQLException {
//		checkOpen();
//		connection.setTypeMap(map);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public String nativeSQL(String sql) throws SQLException {
//		checkOpen();
//		return connection.nativeSQL(sql);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public Savepoint setSavepoint(String name) throws SQLException {
//		checkOpen();
//		return connection.setSavepoint(name);
//	}
//
//	/**
//	 * {@inheritDoc}
//	 */
//	public PreparedStatement prepareStatement(String sql, String[] columnNames)
//			throws SQLException {
//		checkOpen();
//		return connection.prepareStatement(sql, columnNames);
//	}
//}
