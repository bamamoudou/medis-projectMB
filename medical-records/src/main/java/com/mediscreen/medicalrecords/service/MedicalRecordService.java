package com.mediscreen.medicalrecords.service;

import java.util.List;

import com.mediscreen.medicalrecords.model.MedicalRecord;

public interface MedicalRecordService {
	/**
	 * Get all patient medical records
	 * 
	 * @param id
	 * @return
	 */
	List<MedicalRecord> getPatientMedicalRecords(Integer id);

	/**
	 * Update Medical Record
	 * 
	 * @param medicalRecord
	 * @return
	 */
	MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

	/**
	 * Create Medical Record
	 * 
	 * @param medicalRecord
	 * @return
	 */
	MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);
}