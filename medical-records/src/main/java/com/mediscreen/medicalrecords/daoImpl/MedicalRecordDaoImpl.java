package com.mediscreen.medicalrecords.daoImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mediscreen.medicalrecords.config.DatabaseConfigurationInterface;
import com.mediscreen.medicalrecords.dao.MedicalRecordDao;
import com.mediscreen.medicalrecords.model.MedicalRecord;

public class MedicalRecordDaoImpl implements MedicalRecordDao {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Database configuration
	 */
	private DatabaseConfigurationInterface databaseConfiguration;

	/**
	 * Constructor
	 * 
	 * @param databaseConfiguration
	 */
	public MedicalRecordDaoImpl(DatabaseConfigurationInterface databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
	}

	/**
	 * @see MedicalRecordDaoInterface {@link #getAllPatientMedicalRecords(Integer)}
	 */
	@Override
	public List<MedicalRecord> getAllPatientMedicalRecords(Integer patientId) {
		List<MedicalRecord> result = new ArrayList<>();
		result.add(new MedicalRecord(1, patientId, "Dr. Doc", LocalDate.now(), "content", true));
		result.add(new MedicalRecord(2, patientId, "Dr. Doc", LocalDate.now(), "content", false));
		result.add(new MedicalRecord(3, patientId, "Dr. Doc", LocalDate.now(), "content", true));
		result.add(new MedicalRecord(4, patientId, "Dr. Doc", LocalDate.now(), "content", true));
		result.add(new MedicalRecord(5, patientId, "Dr. Doc", LocalDate.now(), "content", false));
		result.add(new MedicalRecord(6, patientId, "Dr. Doc", LocalDate.now(), "content", true));

		return result;
	}

	/**
	 * @see MedicalRecordDaoInterface {@link #createMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecord;
	}

	/**
	 * @see MedicalRecordDaoInterface {@link #updateMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		return medicalRecord;
	}

}
