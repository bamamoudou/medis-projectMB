package com.mediscreen.medicalrecords;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.medicalrecords.exception.NotFoundException;
import com.mediscreen.medicalrecords.model.MedicalRecord;
import com.mediscreen.medicalrecords.service.MedicalRecordService;
import com.mediscreen.medicalrecords.service.SecurityService;

@RestController
public class MedicalRecordController {
	@Autowired
	private MedicalRecordService medicalRecordService;

	@Autowired
	private SecurityService securityService;

	@GetMapping("/medical-record/getAll/{id}")
	public List<MedicalRecord> getAllPatientMedicalRecords(@RequestHeader("token") String token, @PathVariable int id) {
		securityService.authenticationCheck(token);
		List<MedicalRecord> medicalRecordList = medicalRecordService.getPatientMedicalRecords(id);
		if (medicalRecordList == null || medicalRecordList.size() == 0)
			throw new NotFoundException("No data found");
		return medicalRecordList;
	}

	@PostMapping("/medical-record/create")
	public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestHeader("token") String token,
			@Valid @RequestBody MedicalRecord medicalRecord) {
		securityService.authenticationCheck(token);
		MedicalRecord newMedicalRecord = medicalRecordService.createMedicalRecord(medicalRecord);
		if (newMedicalRecord == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<>(newMedicalRecord, HttpStatus.CREATED);
	}

	@PutMapping("/medical-record/update")
	public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestHeader("token") String token,
			@Valid @RequestBody MedicalRecord medicalRecord) {
		securityService.authenticationCheck(token);
		MedicalRecord updateMedicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord);
		if (updateMedicalRecord == null)
			return ResponseEntity.noContent().build();
		return new ResponseEntity<>(updateMedicalRecord, HttpStatus.OK);
	}
}