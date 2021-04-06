package com.mediscreen.medicalrecords.daoImpl;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mediscreen.medicalrecords.config.DatabaseConfigurationInterface;
import com.mediscreen.medicalrecords.dao.MedicalRecordDao;
import com.mediscreen.medicalrecords.model.MedicalRecord;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
		this.connexion = dbConfiguration.getConnexion();
	}

	/**
	 * return patients-medicalRecords collection
	 * 
	 * @return
	 */
	private MongoCollection<Document> getMedicalRecordsCollection() {
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
				jsonObject.getString("doctorName"),
				LocalDateTime.parse(jsonObject.getString("createDate"), this.dateFormatter),
				(!StringUtils.isBlank(jsonObject.getString("lastChangeDate")))
						? LocalDateTime.parse(jsonObject.getString("lastChangeDate"), this.dateFormatter)
						: null,
				jsonObject.getString("content"), jsonObject.getBoolean("isActive"));
	}

	/**
	 * @see MedicalRecordDaoInterface {@link #getAllPatientMedicalRecords(Integer)}
	 */
	@Override
	public List<MedicalRecord> getAllPatientMedicalRecords(Integer patientId) {
		List<MedicalRecord> result = null;

		MongoCollection<Document> collection = this.getMedicalRecordsCollection();

		for (Document document : collection.find(eq("patientId", patientId))) {
			if (result == null) {
				result = new ArrayList<>();
			}
			result.add(parseJsonToMedicalRecord(new JSONObject(document.toJson())));
		}
		return result;
	}

	/**
	 * @see MedicalRecordDaoInterface {@link #createMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		MongoCollection<Document> collection = this.getMedicalRecordsCollection();

		Document newDocument = new Document("patientId", medicalRecord.getPatientId())
				.append("doctorName", medicalRecord.getDoctorName())
				.append("createDate", LocalDateTime.now().format(this.dateFormatter)).append("lastChangeDate", "")
				.append("content", medicalRecord.getContent()).append("isActive", medicalRecord.isActive());

		collection.insertOne(newDocument);

		MedicalRecord result = null;

		for (Document document : collection.find(eq("patientId", medicalRecord.getPatientId()))) {
			MedicalRecord currentRecord = parseJsonToMedicalRecord(new JSONObject(document.toJson()));
			result = (result == null || result.getCreateDate().isBefore(currentRecord.getCreateDate())) ? currentRecord
					: result;
		}

		return result;
	}

	/**
	 * @see MedicalRecordDaoInterface {@link #updateMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		MongoCollection<Document> collection = this.getMedicalRecordsCollection();

		collection.updateOne(eq("_id", new ObjectId(medicalRecord.getId())),
				combine(set("isActive", medicalRecord.isActive()),
						set("lastChangeDate", LocalDateTime.now().format(this.dateFormatter))));

		MedicalRecord result = null;

		for (Document document : collection.find(eq("_id", new ObjectId(medicalRecord.getId())))) {
			MedicalRecord currentRecord = parseJsonToMedicalRecord(new JSONObject(document.toJson()));
			result = (result == null || result.getCreateDate().isBefore(currentRecord.getCreateDate())) ? currentRecord
					: result;
		}

		return result;
	}
}
