package com.mediscreen.medicalrecords.dao;

import java.util.List;

import com.mediscreen.medicalrecords.model.MedicalRecord;

public interface MedicalRecordDao {
	/**
	 * Get all patient medical records
	 * 
	 * @param patientId
	 * @return
	 */
	List<MedicalRecord> getAllPatientMedicalRecords(Integer patientId);

	/**
	 * Update medical record
	 * 
	 * @param medicalRecord
	 * @return
	 */
	MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Create MedicalRecord for patient
	 * 
	 * @param medicalRecord
	 * @return
	 */
	MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);
}