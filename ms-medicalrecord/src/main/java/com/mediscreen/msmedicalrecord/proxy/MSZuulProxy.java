package com.mediscreen.msmedicalrecord.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.mediscreen.msmedicalrecord.model.Patient;

@FeignClient(name = "ems-zuul")
public interface MSZuulProxy {
	// ms-authentication
	@GetMapping("/ms-authentication/validate-token")
	ResponseEntity<Void> msAuthenticationValidateToken(@RequestParam("token") String token);

	// ms-patientAdmin
	@GetMapping("/ms-patientadmin/patient/get/{id}")
	Patient msPatientAdminGetPatient(@RequestHeader("token") String token, @PathVariable int id);
}