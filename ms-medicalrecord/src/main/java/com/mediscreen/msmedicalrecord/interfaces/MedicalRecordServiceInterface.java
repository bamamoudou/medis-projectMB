package com.mediscreen.msmedicalrecord.interfaces;

import com.mediscreen.msmedicalrecord.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordServiceInterface {

    /**
     * Get all patient medical records
     * @param token
     * @param id
     * @return
     */
    List<MedicalRecord> getPatientMedicalRecords(String token, Integer id);

    /**
     * Update Medical Record
     * @param token
     * @param medicalRecord
     * @return
     */
    MedicalRecord updateMedicalRecord(String token, MedicalRecord medicalRecord);

    /**
     * Create Medical Record
     * @param token
     * @param medicalRecord
     * @return
     */
    MedicalRecord createMedicalRecord(String token, MedicalRecord medicalRecord);
}
