package com.mediscreen.medicalrecords.config;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public interface DatabaseConfigurationInterface {
	/**
	 * Get MongoDB connexion
	 * 
	 * @return
	 */
	MongoClient getConnexion();

	/**
	 * Get MongoDB database
	 * 
	 * @param connexion
	 * @return
	 */
	MongoDatabase getDatabase(MongoClient connexion);

	/**
	 * Close MongoDB connexion
	 * 
	 * @param connexion
	 */
	void closeConnexion(MongoClient connexion);

	/**
	 * Get collection of database
	 * 
	 * @param database
	 * @param collectionName
	 * @return
	 */
	MongoCollection<Document> getCollection(MongoDatabase database, String collectionName);

	/**
	 * Create Collection
	 * 
	 * @param database
	 * @param collectionName
	 */
	void createCollection(MongoDatabase database, String collectionName);

}
