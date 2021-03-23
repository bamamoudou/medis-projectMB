package com.mediscreen.mediscreenclientui.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mediscreen.mediscreenclientui.model.Patient;
import com.mediscreen.mediscreenclientui.proxy.MSZuulProxy;
import com.mediscreen.mediscreenclientui.service.PatientService;

public class PatientServiceImpl implements PatientService {
	/**
	 * Zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	@Override
	public List<Patient> getAllPatients(String token) {
		List<Patient> patientList = new ArrayList<>();
		patientList.add(new Patient(1, "juanita", "emard", "F", LocalDate.parse("1995-01-06"), "1 Brookside St",
				"juanita.emard@email.com", "100-222-3333", "France"));
		patientList.add(new Patient(2, "alexane", "collins", "F", LocalDate.parse("1989-11-22"), "2 High St",
				"alexane.collins@email.com", "200-333-4444", "United Kingdom of Great Britain and Northern Ireland"));
		patientList.add(new Patient(3, "ford", "bashirian", "M", LocalDate.parse("1997-09-13"), "3 Club Road",
				"ford.bashirian@email.com", "300-444-5555", "United States of America"));
		patientList.add(new Patient(4, "katrine", "lehner", "F", LocalDate.parse("2000-05-05"), "4 Valley Dr",
				"katrine.lehner@email.com", "400-555-6666", "Finland"));
		return patientList;
		// return msZuulProxy.msPatientAdmin_getAllPatients(token);
	}

	@Override
	public List<Patient> searchPatient(String token, String search) {
		/*
		 * if (!StringUtils.isBlank(search)) { return msZuulProxy.searchPatients(token,
		 * search); } else { return null; }
		 */
		List<Patient> patientList = new ArrayList<>();
		patientList.add(new Patient(1, "juanita", "emard", "F", LocalDate.parse("1995-01-06"), "1 Brookside St",
				"juanita.emard@email.com", "100-222-3333", "France"));
		patientList.add(new Patient(2, "alexane", "collins", "F", LocalDate.parse("1989-11-22"), "2 High St",
				"alexane.collins@email.com", "200-333-4444", "United Kingdom of Great Britain and Northern Ireland"));
		patientList.add(new Patient(3, "ford", "bashirian", "M", LocalDate.parse("1997-09-13"), "3 Club Road",
				"ford.bashirian@email.com", "300-444-5555", "United States of America"));
		patientList.add(new Patient(4, "katrine", "lehner", "F", LocalDate.parse("2000-05-05"), "4 Valley Dr",
				"katrine.lehner@email.com", "400-555-6666", "Finland"));
		return patientList;
	}

	@Override
	public Patient getPatient(String token, int id) {
		return new Patient(1, "juanita", "emard", "F", LocalDate.parse("1995-01-06"), "1 Brookside St",
				"juanita.emard@email.com", "100-222-3333", "France");
		// return msZuulProxy.msPatientAdmin_getPatient(token, id);
	}

	@Override
	public ResponseEntity<Patient> createPatient(String token, Patient patient) {
		return new ResponseEntity<Patient>(new Patient(1, "juanita", "emard", "F", LocalDate.parse("1995-01-06"),
				"1 Brookside St", "juanita.emard@email.com", "100-222-3333", "France"), HttpStatus.CREATED);
		// return msZuulProxy.msPatientAdmin_createPatient(token, patient);
	}

	@Override
	public ResponseEntity<Patient> updatePatient(String token, Patient patient) {
		return new ResponseEntity<Patient>(new Patient(1, "juanita", "emard", "F", LocalDate.parse("1995-01-06"),
				"1 Brookside St", "juanita.emard@email.com", "100-222-3333", "France"), HttpStatus.OK);
		// return msZuulProxy.msPatientAdmin_updatePatient(token, patient);
	}

	@Override
	public ResponseEntity<Void> deletePatient(String token, int id) {
		return new ResponseEntity<>(HttpStatus.OK);
		// return msZuulProxy.msPatientAdmin_deletePatient(token, id);
	}
}