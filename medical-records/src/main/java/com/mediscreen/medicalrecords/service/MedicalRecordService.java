package com.mediscreen.medicalrecords.service;

import java.util.List;

import com.mediscreen.medicalrecords.model.MedicalRecord;

public interface MedicalRecordService {
	/**
	 * Get all patient medical records
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	List<MedicalRecord> getPatientMedicalRecords(String token, Integer id);

	/**
	 * Update Medical Record
	 * 
	 * @param token
	 * @param medicalRecord
	 * @return
	 */
	MedicalRecord updateMedicalRecord(String token, MedicalRecord medicalRecord);

	/**
	 * Create Medical Record
	 * 
	 * @param token
	 * @param medicalRecord
	 * @return
	 */
	MedicalRecord createMedicalRecord(String token, MedicalRecord medicalRecord);
}