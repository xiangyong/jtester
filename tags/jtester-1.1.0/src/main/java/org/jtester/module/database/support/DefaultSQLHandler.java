/*
 * Copyright 2008,  Unitils.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jtester.module.database.support;

import static ext.jtester.org.apache.commons.dbutils.DbUtils.closeQuietly;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.jtester.exception.JTesterException;

/**
 * Class to which database updates and queries are passed. Is in fact a utility
 * class, but is a concrete instance to enable decorating it or switching it
 * with another implementation, allowing things like a dry run, creating a
 * script file or logging updates to a log file or database table.
 * 
 * @author Filip Neven
 * @author Tim Ducheyne
 */
public class DefaultSQLHandler implements SQLHandler {

	/* The logger instance for this class */
	private final static Logger log4j = Logger.getLogger(DefaultSQLHandler.class);

	/*
	 * The DataSource that provides access to the database, on which all queries
	 * and updates are executed
	 */
	private DataSource dataSource;

	/*
	 * Boolean that indicates whether database updates have to executed on the
	 * database or not. Setting this value to false can be useful when running
	 * in dry mode
	 */
	private boolean doExecuteUpdates;

	/**
	 * Constructs a new instance that connects to the given DataSource
	 * 
	 * @param dataSource
	 *            The data source, not null
	 */
	public DefaultSQLHandler(DataSource dataSource) {
		this(dataSource, true);
	}

	/**
	 * Constructs a new instance that connects to the given DataSource
	 * 
	 * @param dataSource
	 *            The data source, not null
	 * @param doExecuteUpdates
	 *            Boolean indicating whether updates should effectively be
	 *            executed on the underlying database
	 */
	public DefaultSQLHandler(DataSource dataSource, boolean doExecuteUpdates) {
		this.dataSource = dataSource;
		this.doExecuteUpdates = doExecuteUpdates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.unitils.core.dbsupport.SQLHandler#executeUpdate(java.lang.String)
	 */
	public int executeUpdate(String sql) {
		log4j.debug(sql);

		if (!doExecuteUpdates) {
			// skip update
			return 0;
		}
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			return statement.executeUpdate(sql);

		} catch (Throwable e) {
			throw new JTesterException("Error while performing database update: " + sql, e);
		} finally {
			closeQuietly(connection, statement, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.unitils.core.dbsupport.SQLHandler#executeQuery(java.lang.String)
	 */
	public void executeQuery(String sql) {
		log4j.debug(sql);

		if (!doExecuteUpdates) {
			// skip query
			return;
		}
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

		} catch (Throwable e) {
			throw new JTesterException("Error while performing database update: " + sql, e);
		} finally {
			closeQuietly(connection, statement, resultSet);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dbmaintain.dbsupport.SQLHandler#executeUpdateAndCommit(java.lang.
	 * String)
	 */
	public int executeUpdateAndCommit(String sql) {
		log4j.debug(sql);

		if (!doExecuteUpdates) {
			// skip update
			return 0;
		}
		Connection connection = null;
		Statement statement = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			int nbChanges = statement.executeUpdate(sql);
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			return nbChanges;

		} catch (Throwable e) {
			throw new JTesterException("Error while performing database update: " + sql, e);
		} finally {
			closeQuietly(connection, statement, null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.unitils.core.dbsupport.SQLHandler#getItemAsLong(java.lang.String)
	 */
	public long getItemAsLong(String sql) {
		log4j.debug(sql);

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		} catch (Throwable e) {
			throw new JTesterException("Error while executing statement: " + sql, e);
		} finally {
			closeQuietly(connection, statement, resultSet);
		}

		// in case no value was found, throw an exception
		throw new JTesterException("No item value found: " + sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.unitils.core.dbsupport.SQLHandler#getItemAsString(java.lang.String)
	 */
	public String getItemAsString(String sql) {
		log4j.debug(sql);

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				return resultSet.getString(1);
			}
		} catch (Throwable e) {
			throw new JTesterException("Error while executing statement: " + sql, e);
		} finally {
			closeQuietly(connection, statement, resultSet);
		}

		// in case no value was found, throw an exception
		throw new JTesterException("No item value found: " + sql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.unitils.core.dbsupport.SQLHandler#getItemsAsStringSet(java.lang.String
	 * )
	 */
	public Set<String> getItemsAsStringSet(String sql) {
		log4j.debug(sql);

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			Set<String> result = new HashSet<String>();
			while (resultSet.next()) {
				result.add(resultSet.getString(1));
			}
			return result;

		} catch (Throwable e) {
			throw new JTesterException("Error while executing statement: " + sql, e);
		} finally {
			closeQuietly(connection, statement, resultSet);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.unitils.core.dbsupport.SQLHandler#exists(java.lang.String)
	 */
	public boolean exists(String sql) {
		log4j.debug(sql);

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			return resultSet.next();

		} catch (Throwable e) {
			throw new JTesterException("Error while executing statement: " + sql, e);
		} finally {
			closeQuietly(connection, statement, resultSet);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.unitils.core.dbsupport.SQLHandler#getDataSource()
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.unitils.core.dbsupport.SQLHandler#isDoExecuteUpdates()
	 */
	public boolean isDoExecuteUpdates() {
		return doExecuteUpdates;
	}
}
