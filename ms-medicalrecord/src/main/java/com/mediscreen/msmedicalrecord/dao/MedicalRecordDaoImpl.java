package com.mediscreen.msmedicalrecord.dao;

import com.mediscreen.msmedicalrecord.configuration.DatabaseConfigurationInterface;
import com.mediscreen.msmedicalrecord.exception.NotFoundException;
import com.mediscreen.msmedicalrecord.model.MedicalRecord;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class MedicalRecordDaoImpl implements MedicalRecordDao {
	/**
	 * Date formatter
	 */
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	/**
	 * MongoDB Connexion
	 */
	private MongoClient connexion;

	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Database configuration
	 */
	private DatabaseConfigurationInterface dbConfiguration;

	/**
	 * Constructor
	 * 
	 * @param databaseConfiguration
	 */
	public MedicalRecordDaoImpl(DatabaseConfigurationInterface databaseConfiguration) {
		this.dbConfiguration = databaseConfiguration;
	}

	/**
	 * return patients-medicalRecords collection
	 * 
	 * @return
	 */
	private MongoCollection<Document> getMedicalRecordsCollection() {
		this.connexion = dbConfiguration.getConnexion();
		MongoDatabase db = dbConfiguration.getDatabase(this.connexion);
		return dbConfiguration.getCollection(db, "patientsMedicalRecords");
	}

	/**
	 * Construct MedicalRecord from JSON
	 * 
	 * @param jsonObject
	 * @return
	 */
	private MedicalRecord parseJsonToMedicalRecord(JSONObject jsonObject) {
		return new MedicalRecord(jsonObject.getJSONObject("_id").getString("$oid"), jsonObject.getInt("patientId"),
				(!jsonObject.isNull("doctorName") && !StringUtils.isBlank(jsonObject.getString("doctorName")))
						? (String) jsonObject.get("doctorName")
						: null,
				(!jsonObject.isNull("createDate") && !StringUtils.isBlank(jsonObject.getString("createDate")))
						? LocalDateTime.parse(jsonObject.getString("createDate"), this.dateFormatter)
						: null,
				(!jsonObject.isNull("lastChangeDate") && !StringUtils.isBlank(jsonObject.getString("lastChangeDate")))
						? LocalDateTime.parse(jsonObject.getString("lastChangeDate"), this.dateFormatter)
						: null,
				(!jsonObject.isNull("content") && !StringUtils.isBlank(jsonObject.getString("content")))
						? (String) jsonObject.get("content")
						: null,
				jsonObject.getBoolean("isActive"));
	}

	/**
	 * @see MedicalRecordDao {@link #getAllPatientMedicalRecords(Integer)}
	 */
	@Override
	public List<MedicalRecord> getAllPatientMedicalRecords(Integer patientId) {
		boolean added = false;
		List<MedicalRecord> result = null;

		try {
			MongoCollection<Document> collection = this.getMedicalRecordsCollection();

			for (Document document : collection.find(eq("patientId", patientId)).sort(Sorts.descending("createDate"))) {
				if (result == null) {
					result = new ArrayList<>();
				}
				MedicalRecord medicalRecord = parseJsonToMedicalRecord(new JSONObject(document.toJson()));
				for (int i = 0; i < result.size(); i++) {
					if (medicalRecord.getCreateDate().isAfter(result.get(0).getCreateDate())) {
						result.add(0, medicalRecord);
						added = true;
						break;
					}
				}
				if (!added) {
					result.add(medicalRecord);
				}
				added = false;
			}
		} catch (Exception e) {
			logger.error("Error DAO : " + e);
		} finally {
			if (connexion != null)
				connexion.close();
		}
		return result;
	}

	/**
	 * @see MedicalRecordDao {@link #createMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		MedicalRecord result = null;
		try {
			MongoCollection<Document> collection = this.getMedicalRecordsCollection();

			Document newDocument = new Document("patientId", medicalRecord.getPatientId())
					.append("doctorName", medicalRecord.getDoctorName())
					.append("createDate", LocalDateTime.now().format(this.dateFormatter)).append("lastChangeDate", null)
					.append("content", medicalRecord.getContent()).append("isActive", medicalRecord.isActive());

			collection.insertOne(newDocument);

			for (Document document : collection.find(eq("patientId", medicalRecord.getPatientId()))) {
				MedicalRecord currentRecord = parseJsonToMedicalRecord(new JSONObject(document.toJson()));
				result = (result == null || result.getCreateDate().isBefore(currentRecord.getCreateDate()))
						? currentRecord
						: result;
			}
		} catch (Exception e) {
			logger.error("Error DAO : " + e);
		} finally {
			if (connexion != null)
				connexion.close();
		}

		return result;
	}

	/**
	 * @see MedicalRecordDao {@link #updateMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		MedicalRecord result = null;
		try {
			MongoCollection<Document> collection = this.getMedicalRecordsCollection();

			if (!collection.find(eq("_id", new ObjectId(medicalRecord.getId()))).cursor().hasNext()) {
				logger.error("Unknown medical record with id : " + medicalRecord.getId());
				throw new NotFoundException("Unknown medical record with id : " + medicalRecord.getId());
			}

			collection.updateOne(eq("_id", new ObjectId(medicalRecord.getId())),
					combine(set("isActive", medicalRecord.isActive()),
							set("lastChangeDate", LocalDateTime.now().format(this.dateFormatter))));

			for (Document document : collection.find(eq("_id", new ObjectId(medicalRecord.getId())))) {
				MedicalRecord currentRecord = parseJsonToMedicalRecord(new JSONObject(document.toJson()));
				result = (result == null || result.getCreateDate().isBefore(currentRecord.getCreateDate()))
						? currentRecord
						: result;
			}
		} catch (Exception e) {
			logger.error("Error DAO : " + e);
		} finally {
			if (connexion != null)
				connexion.close();
		}
		return result;
	}
}