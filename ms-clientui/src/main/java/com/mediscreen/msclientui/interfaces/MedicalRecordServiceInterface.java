package com.mediscreen.msclientui.interfaces;

import com.mediscreen.msclientui.model.MedicalRecord;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MedicalRecordServiceInterface {
    /**
     * Get all patient medical records with their id
     * @param session
     * @param patientId
     * @return
     */
    List<MedicalRecord> getAllPatientMedicalRecords(HttpSession session, int patientId);

    /**
     * Create new Medical record
     * @param session
     * @param medicalRecord
     * @return
     */
    MedicalRecord createMedicalRecord(HttpSession session, MedicalRecord medicalRecord);

    /**
     * Update Medical record
     * @param session
     * @param id
     * @param patientId
     * @param active
     * @return
     */
    MedicalRecord updateMedicalRecord(HttpSession session, String id, Integer patientId, Boolean active);
}
