package com.mediscreen.mediscreenclientui.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;

import com.mediscreen.mediscreenclientui.model.Patient;

public interface PatientService {
	/**
	 * Get patient by id from ms-patientAdmin
	 * 
	 * @param session
	 * @return
	 */
	List<Patient> getAllPatients(HttpSession session);

	/**
	 * Get All patients from ms-patientAdmin
	 * 
	 * @param session
	 * @param id
	 * @return
	 */
	Patient getPatient(HttpSession session, int id);

	/**
	 * Create patient to ms-patientAdmin
	 * 
	 * @param session
	 * @param patient
	 * @return
	 */
	Patient createPatient(HttpSession session, Patient patient);

	/**
	 * Update patient to ms-patientAdmin
	 * 
	 * @param session
	 * @param patient
	 * @return
	 */
	void updatePatient(HttpSession session, Patient patient);

	/**
	 * Delete patient to ms-patientAdmin
	 * 
	 * @param session
	 * @param id
	 * @return
	 */
	HttpStatus deletePatient(HttpSession session, int id);

	/**
	 * Search patient
	 * 
	 * @param session
	 * @param search
	 * @return
	 */
	List<Patient> searchPatient(HttpSession session, String search);
}