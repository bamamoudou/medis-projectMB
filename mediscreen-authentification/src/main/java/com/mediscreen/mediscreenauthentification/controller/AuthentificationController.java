package com.mediscreen.mediscreenauthentification.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mediscreen.mediscreenauthentification.model.Jwt;
import com.mediscreen.mediscreenauthentification.model.Login;
import com.mediscreen.mediscreenauthentification.service.SecurityService;

@RestController
public class AuthentificationController {
	@Autowired
	private SecurityService securityService;

	@PostMapping("/generate-token")
	public ResponseEntity<Jwt> generateToken(@Valid @RequestBody Login login) {
		return new ResponseEntity<>(securityService.logUser(login), HttpStatus.OK);
	}

	@GetMapping("/validate-token")
	public ResponseEntity<Void> validateToken(@RequestParam String token) {
		if (securityService.isLog(token))
			return ResponseEntity.status(HttpStatus.OK).build();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}