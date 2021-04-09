package com.mediscreen.msmedicalrecord.interfaces;

import com.mediscreen.msmedicalrecord.model.MedicalRecord;

import java.util.List;

public interface MedicalRecordDaoInterface {
    /**
     * Get all patient medical records
     * @param patientId
     * @return
     */
    List<MedicalRecord> getAllPatientMedicalRecords(Integer patientId);

    /**
     * Update medical record
     * @param medicalRecord
     * @return
     */
    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord);

    /**
     * Create MedicalRecord for patient
     * @param medicalRecord
     * @return
     */
    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord);
}
