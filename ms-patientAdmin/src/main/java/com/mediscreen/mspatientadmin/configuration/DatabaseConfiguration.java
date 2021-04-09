package com.mediscreen.mspatientadmin.configuration;

import com.mediscreen.mspatientadmin.model.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Singleton;
import java.sql.*;

/**
 * Database configuration
 * 
 */
@Singleton
public class DatabaseConfiguration implements DatabaseConfigurationInterface {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * DB connection info to production DB
	 */
	private DBConnection dbConnection;

	/**
	 * Constructor
	 * 
	 * @param appProperties
	 */
	public DatabaseConfiguration(AppProperties appProperties) {
		this.dbConnection = new DBConnection(appProperties.getHost(), appProperties.getPort(),
				appProperties.getDatabase(), appProperties.getUser(), appProperties.getPassword());
	}

	/**
	 * Constructor
	 * 
	 * @param dbConnectionTest
	 */
	public DatabaseConfiguration(DBConnection dbConnection) {
		this.dbConnection = dbConnection;
	}

	/**
	 * @see com.mediscreen.mspatientadmin.configuration.DatabaseConfigurationInterface
	 *      {@link #getConnection()}
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		if ((this.dbConnection.getHost() == null) || (this.dbConnection.getPort() == null)
				|| (this.dbConnection.getDatabase() == null) || (this.dbConnection.getUser() == null)
				|| (this.dbConnection.getPassword() == null)) {
			logger.error("Error fetching database properties");
			throw new NullPointerException("Error fetching database properties");
		}

		return DriverManager.getConnection(
				"jdbc:mysql://" + this.dbConnection.getHost() + ":" + this.dbConnection.getPort() + "/"
						+ this.dbConnection.getDatabase(),
				this.dbConnection.getUser(), this.dbConnection.getPassword());
	}

	/**
	 * @see com.mediscreen.mspatientadmin.configuration.DatabaseConfigurationInterface
	 *      {@link #closeConnection(Connection)}
	 */
	@Override
	public void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("Error while closing connection", e);
			}
		}
	}

	/**
	 * @see com.mediscreen.mspatientadmin.configuration.DatabaseConfigurationInterface
	 *      {@link #closePreparedStatement(Statement)}
	 */
	@Override
	public void closePreparedStatement(Statement ps) {
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error("Error while closing prepared statement", e);
			}
		}
	}

	/**
	 * @see com.mediscreen.mspatientadmin.configuration.DatabaseConfigurationInterface
	 *      {@link #closeResultSet(ResultSet)}
	 */
	@Override
	public void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Error while closing result set", e);
			}
		}
	}

	/**
	 * @see com.mediscreen.mspatientadmin.configuration.DatabaseConfigurationInterface
	 *      {@link #closeSQLTransaction(Connection, Statement, ResultSet)}
	 */
	@Override
	public void closeSQLTransaction(Connection con, Statement ps, ResultSet rs) {
		closeConnection(con);
		closePreparedStatement(ps);
		closeResultSet(rs);
	}
}