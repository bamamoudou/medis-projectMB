package com.mediscreen.mediscreenclientui.proxy;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediscreen.mediscreenclientui.model.Jwt;
import com.mediscreen.mediscreenclientui.model.Login;
import com.mediscreen.mediscreenclientui.model.Patient;

@FeignClient(name = "zuul-server")
public interface MSZuulProxy {
	// ms-authentication
	@PostMapping("/ms-authentication/generate-token")
	ResponseEntity<Jwt> msAuthentication_generateToken(@Valid @RequestBody Login login);

	@GetMapping("/ms-authentication/validate-token")
	ResponseEntity<Void> msAuthentication_validateToken(@RequestParam("token") String token);

	// ms-patientAdmin
	@GetMapping("/ms-patientadmin/patient/getAll")
	List<Patient> msPatientAdmin_getAllPatients(@RequestHeader("token") String token);

	@GetMapping("/ms-patientadmin/patient/search")
	List<Patient> searchPatients(@RequestHeader("token") String token, @RequestParam(required = true) String search);

	@GetMapping("/ms-patientadmin/patient/get/{id}")
	Patient msPatientAdmin_getPatient(@RequestHeader("token") String token, @PathVariable int id);

	@PostMapping("/ms-patientadmin/patient/create")
	ResponseEntity<Patient> msPatientAdmin_createPatient(@RequestHeader("token") String token,
			@Valid @RequestBody Patient patient);

	@PutMapping("/ms-patientadmin/patient/update")
	ResponseEntity<Patient> msPatientAdmin_updatePatient(@RequestHeader("token") String token,
			@Valid @RequestBody Patient patient);

	@DeleteMapping("/ms-patientadmin/patient/delete/{id}")
	ResponseEntity<Void> msPatientAdmin_deletePatient(@RequestHeader("token") String token, @PathVariable int id);
}