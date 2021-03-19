package com.mediscreen.mediscreenpatient.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.mediscreenpatient.exception.NotFoundException;
import com.mediscreen.mediscreenpatient.model.Patient;
import com.mediscreen.mediscreenpatient.service.PatientService;

@RestController
public class PatientController {
	@Autowired
	private PatientService patientService;

	@GetMapping("/patient/getAll")
	public List<Patient> getPatient() {
		List<Patient> patientList = patientService.getAllPatient();
		if (patientList == null)
			throw new NotFoundException("No data found");
		return patientList;
	}

	@GetMapping("/patient/get/{id}")
	public Patient getPatient(@PathVariable int id) {
		Patient patient = patientService.getPatientById(id);
		if (patient == null)
			throw new NotFoundException("Unknown patient with id : " + id);
		return patient;
	}

	@PostMapping("/patient/create")
	public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
		Patient newPatient = patientService.createPatient(patient);
		if (newPatient == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
	}

	@PutMapping("/patient/update")
	public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient) {
		Patient updatedPatient = patientService.updatePatient(patient);
		if (updatedPatient == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
	}

	@DeleteMapping("/patient/delete/{id}")
	public ResponseEntity<Void> deletePatient(@PathVariable int id) {
		if (!patientService.deletePatientById(id))
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok().build();
	}
}