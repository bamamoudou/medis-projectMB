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
	// mediscreen-authentication
	@PostMapping("/mediscreen-authentification/generate-token")
	ResponseEntity<Jwt> msAuthentication_generateToken(@Valid @RequestBody Login login);

	@GetMapping("/mediscreen-authentification/validate-token")
	ResponseEntity<Void> msAuthentication_validateToken(@RequestParam("token") String token);

	// mediscreen-patient
	@GetMapping("/mediscreen-patient/patient/getAll")
	List<Patient> msPatientAdmin_getAllPatients(@RequestHeader("token") String token);

	@GetMapping("/mediscreen-patient/patient/search")
	List<Patient> searchPatients(@RequestHeader("token") String token, @RequestParam(required = true) String search);

	@GetMapping("/mediscreen-patient/patient/get/{id}")
	Patient msPatient_getPatient(@RequestHeader("token") String token, @PathVariable int id);

	@PostMapping("/mediscreen-patient/patient/create")
	ResponseEntity<Patient> msPatient_createPatient(@RequestHeader("token") String token,
			@Valid @RequestBody Patient patient);

	@PutMapping("/mediscreen-patient/patient/update")
	ResponseEntity<Patient> msPatient_updatePatient(@RequestHeader("token") String token,
			@Valid @RequestBody Patient patient);

	@DeleteMapping("/mediscreen-patient/patient/delete/{id}")
	ResponseEntity<Void> msPatient_deletePatient(@RequestHeader("token") String token, @PathVariable int id);
}