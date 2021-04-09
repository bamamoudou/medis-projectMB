package com.mediscreen.mspatientadmin.interfaces;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database configuration
 * @author MorganCpn
 */
public interface DatabaseConfigurationInterface {

    /**
     * Open Connection on DB
     * @return Connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    Connection getConnection() throws ClassNotFoundException, SQLException;

    /**
     * Close Connection
     * @param con Active connection
     */
    void closeConnection(Connection con);

    /**
     * Close Prepared Statement
     * @param ps Open statement
     */
    void closePreparedStatement(Statement ps);

    /**
     * Close ResultSet
     * @param rs Open ResultSet
     */
    void closeResultSet(ResultSet rs);

    /**
     * Close all connection elements
     * @param con
     * @param ps
     * @param rs
     */
    void closeSQLTransaction(Connection con, Statement ps, ResultSet rs);
}
