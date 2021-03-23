package com.mediscreen.mediscreenpatient.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
public class DatabaseConfig implements DatabaseConfigInterface {

	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Database host
	 */
	private String host;

	/**
	 * Database port
	 */
	private String port;

	/**
	 * Database database name
	 */
	private String database;

	/**
	 * Database username
	 */
	private String user;

	/**
	 * Database password
	 */
	private String password;

	/**
	 * Application properties
	 */
	private AppProperties appProperties;

	/**
	 * Constructor
	 * 
	 * @param appProperties
	 */
	public DatabaseConfig(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	/**
	 * @see com.mediscreen.mspatientadmin.interfaces.DatabaseConfigurationInterface
	 *      {@link #getConnection()}
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.host = appProperties.getHost();
		this.port = String.valueOf(appProperties.getPort());
		this.database = appProperties.getDatabase();
		this.user = appProperties.getUser();
		this.password = appProperties.getPassword();
		if ((this.host == null) || (this.port == null) || (this.database == null) || (this.user == null)
				|| (this.password == null)) {
			logger.error("Error fetching database properties");
			throw new NullPointerException("Error fetching database properties");
		}

		return DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
				this.user, this.password);
	}

	/**
	 * @see com.mediscreen.mediscreenpatient.config.DatabaseConfigInterface
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
	 * @see com.mediscreen.mediscreenpatient.config.DatabaseConfigInterface
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
	 * @see com.mediscreen.mediscreenpatient.config.DatabaseConfigInterface
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
	 * @see com.mediscreen.mediscreenpatient.config.DatabaseConfigInterface
	 *      {@link #closeSQLTransaction(Connection, Statement, ResultSet)}
	 */
	@Override
	public void closeSQLTransaction(Connection con, Statement ps, ResultSet rs) {
		closeConnection(con);
		closePreparedStatement(ps);
		closeResultSet(rs);
	}
}