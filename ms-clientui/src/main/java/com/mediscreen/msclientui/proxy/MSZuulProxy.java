package com.mediscreen.msclientui.proxy;

import com.mediscreen.msclientui.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "ems-zuul")
public interface MSZuulProxy {
	// ms-authentication
	@PostMapping("/ms-authentication/generate-token")
	ResponseEntity<Jwt> msAuthenticationGenerateToken(@Valid @RequestBody Login login);

	@GetMapping("/ms-authentication/validate-token")
	ResponseEntity<Void> msAuthenticationValidateToken(@RequestParam("token") String token);

	// ms-patientAdmin
	@GetMapping("/ms-patientadmin/patient/getAll")
	List<Patient> msPatientAdminGetAllPatients(@RequestHeader("token") String token);

	@GetMapping("/ms-patientadmin/patient/search")
	List<Patient> searchPatients(@RequestHeader("token") String token, @RequestParam(required = true) String search);

	@GetMapping("/ms-patientadmin/patient/get/{id}")
	Patient msPatientAdminGetPatient(@RequestHeader("token") String token, @PathVariable int id);

	@PostMapping("/ms-patientadmin/patient/create")
	ResponseEntity<Patient> msPatientAdminCreatePatient(@RequestHeader("token") String token,
			@Valid @RequestBody Patient patient);

	@PutMapping("/ms-patientadmin/patient/update")
	ResponseEntity<Patient> msPatientAdminUpdatePatient(@RequestHeader("token") String token,
			@Valid @RequestBody Patient patient);

	@DeleteMapping("/ms-patientadmin/patient/delete/{id}")
	ResponseEntity<Void> msPatientAdminDeletePatient(@RequestHeader("token") String token, @PathVariable int id);

	// ms-medicalrecord
	@GetMapping("/ms-medicalrecord/medical-record/getAll/{id}")
	List<MedicalRecord> msMedicalRecordGetAllPatientMedicalRecords(@RequestHeader("token") String token,
			@PathVariable int id);

	@PostMapping("/ms-medicalrecord//medical-record/create")
	ResponseEntity<MedicalRecord> msMedicalRecordsCreateMedicalRecord(@RequestHeader("token") String token,
			@Valid @RequestBody MedicalRecord medicalRecord);

	@PutMapping("/ms-medicalrecord//medical-record/update")
	ResponseEntity<MedicalRecord> msMedicalRecordsUpdateMedicalRecord(@RequestHeader("token") String token,
			@Valid @RequestBody MedicalRecord medicalRecord);

	// ms-medicalreport
	@GetMapping("/ms-medicalreport/medical-report/generate-report/{id}")
	MedicalReport msMedicalReportGenerateMedicalReport(@RequestHeader("token") String token, @PathVariable int id);
}