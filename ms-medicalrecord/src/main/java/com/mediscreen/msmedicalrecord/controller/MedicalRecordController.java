package com.mediscreen.msmedicalrecord.controller;

import com.mediscreen.msmedicalrecord.model.MedicalRecord;
import com.mediscreen.msmedicalrecord.service.MedicalRecordService;
import com.mediscreen.msmedicalrecord.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class MedicalRecordController {
	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private SecurityService securityService;

	@GetMapping("/medical-record/getAll/{id}")
	public List<MedicalRecord> getAllPatientMedicalRecords(@RequestHeader("token") String token, @PathVariable int id) {
		// securityService.authenticationCheck(token);
		return medicalRecordService.getPatientMedicalRecords(token, id);
	}

	@PostMapping("/patHistory/add")
	public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestHeader("token") String token,
			@RequestParam(required = true) Map<String, Object> body) {
		securityService.authenticationCheck(token);
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setPatientId(Integer.valueOf((String) body.get("patId")));
		medicalRecord.setContent((String) body.get("e"));
		medicalRecord.setActive(true);
		MedicalRecord newMedicalRecord = medicalRecordService.createMedicalRecord(token, medicalRecord);
		if (newMedicalRecord == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<>(newMedicalRecord, HttpStatus.CREATED);
	}

	@PostMapping("/medical-record/create")
	public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestHeader("token") String token,
			@Valid @RequestBody MedicalRecord medicalRecord) {
		securityService.authenticationCheck(token);
		MedicalRecord newMedicalRecord = medicalRecordService.createMedicalRecord(token, medicalRecord);
		if (newMedicalRecord == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<>(newMedicalRecord, HttpStatus.CREATED);
	}

	@PutMapping("/medical-record/update")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestHeader("token") String token,
			@Valid @RequestBody MedicalRecord medicalRecord) {
		securityService.authenticationCheck(token);
		MedicalRecord updateMedicalRecord = medicalRecordService.updateMedicalRecord(token, medicalRecord);
		if (updateMedicalRecord == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<>(updateMedicalRecord, HttpStatus.OK);
	}
}