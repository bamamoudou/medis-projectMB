package com.mediscreen.msmedicalrecord.configuration;

import com.mediscreen.msmedicalrecord.interfaces.DatabaseConfigurationInterface;
import com.mediscreen.msmedicalrecord.model.DBConnection;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * Database configuration
 * @author MorganCpn
 */
@Singleton
public class DatabaseConfiguration implements DatabaseConfigurationInterface {
    /**
     * Logger log4j2
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Database connection informations
     */
    private DBConnection dbConnection;

    /**
     * Constructor
     * @param appProperties
     */
    public DatabaseConfiguration(AppProperties appProperties) {
        this.dbConnection = new DBConnection(
                appProperties.getHost(),
                appProperties.getPort(),
                appProperties.getDatabase(),
                appProperties.getUser(),
                appProperties.getPassword()
        );
    }

    /**
     * Constructor
     * @param dbConnection
     */
    public DatabaseConfiguration(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * @see DatabaseConfigurationInterface {@link #getConnexion()}
     */
    @Override
    public MongoClient getConnexion(){
        if ((this.dbConnection.getHost() == null) || (this.dbConnection.getPort() == null) || (this.dbConnection.getDatabase() == null) || (this.dbConnection.getUser() == null) || (this.dbConnection.getPassword() == null)) {
            logger.error("DatabaseConfiguration.getMongoClient() : Error fetching database properties");
            throw new NullPointerException("DatabaseConfiguration.getMongoClient() : Error fetching database properties");
        }
        return new MongoClient(new MongoClientURI("mongodb://" + this.dbConnection.getUser() + ":" + this.dbConnection.getPassword() + "@" + this.dbConnection.getHost() + ":" + this.dbConnection.getPort() + "/" + this.dbConnection.getDatabase() + "?authSource=admin"));
    }

    /**
     * @see DatabaseConfigurationInterface {@link #closeConnexion(MongoClient)}
     */
    @Override
    public void closeConnexion(MongoClient connexion){
        if (connexion != null) {
            try {
                connexion.close();
            } catch (Exception e) {
                logger.error("DatabaseConfiguration.closeConnexion() : Error while closing connexion (" + e + ")");
            }
        }
    }

    /**
     * @see DatabaseConfigurationInterface {@link #getDatabase(MongoClient)}
     */
    @Override
    public MongoDatabase getDatabase(MongoClient connexion){
        MongoDatabase database = null;
        if(StringUtils.isBlank(this.dbConnection.getDatabase())) throw new NullPointerException("DatabaseConfiguration.getDatabase() : database is mandatory");
        try {
            database = connexion.getDatabase(this.dbConnection.getDatabase());
        } catch (Exception e) {
            logger.error("DatabaseConfiguration.getDatabase() : Error while getting database (" + e + ")");
        }
        return database;
    }

    /**
     * @see DatabaseConfigurationInterface {@link #getCollection(MongoDatabase, String)}
     */
    @Override
    public MongoCollection<Document> getCollection(MongoDatabase database, String collectionName){
        MongoCollection<Document> collection = null;
        if(StringUtils.isBlank(collectionName)) throw new NullPointerException("DatabaseConfiguration.getCollection() : collection is mandatory");
        try {
            collection = database.getCollection(collectionName);
        } catch (Exception e) {
            logger.error("DatabaseConfiguration.getCollection() : Error while getting collection (" + e + ")");
        }
        return collection;
    }

    /**
     * @see DatabaseConfigurationInterface {@link #createCollection(MongoDatabase, String)}
     */
    @Override
    public void createCollection(MongoDatabase database, String collectionName){
        if(StringUtils.isBlank(collectionName)) throw new NullPointerException("DatabaseConfiguration.createCollection() : collection is mandatory");
        try {
            database.createCollection(collectionName);
        } catch (Exception e) {
            logger.error("DatabaseConfiguration.createCollection() : Error while creating collection (" + e + ")");
        }
    }
}
