package com.mediscreen.mspatientadmin.interfaces;

import com.mediscreen.mspatientadmin.models.Patient;

import java.util.List;

public interface PatientServiceInterface {

    /**
     * Get patient profile with his id
     * @param id
     * @return
     */
    Patient getPatientById(Integer id);

    /**
     * Get all patient list
     * @return
     */
    List<Patient> getAllPatient();

    /**
     * Upadte patient profile
     * @param patient
     * @return
     */
    Patient updatePatient(Patient patient);

    /**
     * Create new patient profile
     * @param patient
     * @return
     */
    Patient createPatient(Patient patient);

    /**
     * Delete patient with his id
     * @param id
     * @return
     */
    boolean deletePatientById(Integer id);

    /**
     * Search patient
     * @param search
     * @return
     */
    List<Patient> searchPatient(String search);
}
