package com.mediscreen.mediscreenpatient.service;

import java.util.List;

import com.mediscreen.mediscreenpatient.model.Patient;

public interface PatientService {
	/**
	 * GET patient profile with his id
	 * 
	 * @param id
	 * @return
	 */
	Patient getPatientById(Integer id);

	/**
	 * GET all patient list
	 * 
	 * @return
	 */
	List<Patient> getAllPatient();

	/**
	 * UPDATE patient profile
	 * 
	 * @param patient
	 * @return
	 */
	Patient updatePatient(Patient patient);

	/**
	 * CREATE new patient profile
	 * 
	 * @param patient
	 * @return
	 */
	Patient createPatient(Patient patient);

	/**
	 * DELETE patient with his id
	 * 
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