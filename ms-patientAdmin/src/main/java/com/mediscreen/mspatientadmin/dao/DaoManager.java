package com.mediscreen.mspatientadmin.dao;

import com.mediscreen.mspatientadmin.exception.InternalServerErrorException;
import com.mediscreen.mspatientadmin.interfaces.DatabaseConfigurationInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoManager {
    /**
     * Logger log4j2
     */
    protected final Logger logger;

    /**
     * Database configuration
     */
    protected DatabaseConfigurationInterface databaseConfiguration;

    /**
     * Constructor
     */
    public DaoManager(DatabaseConfigurationInterface databaseConfiguration, String className) {
        this.logger = LogManager.getLogger(className);
        this.databaseConfiguration = databaseConfiguration;
    }

    /**
     * Get last id of table
     * @param table
     * @return
     */
    public Integer getMaxId(String table){
        Integer result = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT max(id) as max_id FROM ").append(table);

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt("max_id");
            }
        } catch (ClassNotFoundException e){
            logger.error("DaoManager.getMaxId() -> Error getting max id : {O}", e);
            throw new InternalServerErrorException("ClassNotFoundException : " + e);
        } catch (SQLException e){
            logger.error("DaoManager.getMaxId() -> Error getting max id : {O}", e);
            throw new InternalServerErrorException("SQLException : " + e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, rs);
        }

        if (result != null) logger.error(this.getClass() + ".getMaxId() -> No record in table");
        return result;
    }

    /**
     * Delete record by id
     * @param table
     * @param id
     */
    public void deleteById(String table, Integer id) {
        Connection con = null;
        PreparedStatement ps = null;

        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ").append(table).append(" WHERE id = ?");

        try {
            con = databaseConfiguration.getConnection();
            ps = con.prepareStatement(sql.toString());
            ps.setInt(1, id);
            ps.execute();
            logger.info(this.getClass() + ".deleteById() -> Record delete with id : " + id);
        } catch (ClassNotFoundException e){
            logger.error("DaoManager.deleteById() -> Error delete : {O}", e);
            throw new InternalServerErrorException("ClassNotFoundException : " + e);
        } catch (SQLException e){
            logger.error("DaoManager.deleteById() -> Error delete : {O}", e);
            throw new InternalServerErrorException("SQLException : " + e);
        } finally {
            databaseConfiguration.closeSQLTransaction(con, ps, null);
        }
    }
}
