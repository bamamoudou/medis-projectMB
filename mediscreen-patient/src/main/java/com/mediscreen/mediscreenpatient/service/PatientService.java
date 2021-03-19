package com.mediscreen.mediscreenpatient.service;

import java.util.List;

import com.mediscreen.mediscreenpatient.model.Patient;

public interface PatientService {
	/**
	 * Get patient profile with his id
	 * 
	 * @param id
	 * @return
	 */
	Patient getPatientById(Integer id);

	/**
	 * Get all patient list
	 * 
	 * @return
	 */
	List<Patient> getAllPatient();

	/**
	 * Upadte patient profile
	 * 
	 * @param patient
	 * @return
	 */
	Patient updatePatient(Patient patient);

	/**
	 * Create new patient profile
	 * 
	 * @param patient
	 * @return
	 */
	Patient createPatient(Patient patient);

	/**
	 * Delete patient with his id
	 * 
	 * @param id
	 * @return
	 */
	boolean deletePatientById(Integer id);
}