package com.mediscreen.mspatientadmin.dao;

import com.mediscreen.mspatientadmin.model.Patient;

import java.util.List;


public interface PatientDao {
	/**
	 * Get patient with id
	 * 
	 * @param id
	 * @return Patient
	 */
	Patient getPatientById(Integer id);

	/**
	 * Get all patient
	 * 
	 * @return
	 */
	List<Patient> getAllPatient();

	/**
	 * Update patient
	 * 
	 * @param patient
	 */
	Patient updatePatient(Patient patient);

	/**
	 * Create new patient
	 * 
	 * @param patient
	 */
	Patient createPatient(Patient patient);

	/**
	 * Get list of patients with search
	 * 
	 * @param search
	 * @return
	 */
	List<Patient> searchPatients(String search);

	/**
	 * Delete patient
	 * 
	 * @param id
	 */
	void deletePatientById(Integer id);
}