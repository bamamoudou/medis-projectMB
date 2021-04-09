package com.mediscreen.msmedicalreport.controller;

import com.mediscreen.msmedicalreport.exception.NotFoundException;
import com.mediscreen.msmedicalreport.model.MedicalReport;
import com.mediscreen.msmedicalreport.service.MedicalReportService;
import com.mediscreen.msmedicalreport.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MedicalReportController {
	@Autowired
	private SecurityService securityService;

	@Autowired
	private MedicalReportService medicalReportService;

	@GetMapping("/medical-report/generate-report/{id}")
	public MedicalReport generateMedicalReport(@RequestHeader("token") String token, @PathVariable int id) {
		return this.generate(token, id, null);
	}

	@PostMapping("/assess/id")
	public MedicalReport generateMedicalReportById(@RequestHeader("token") String token,
			@RequestParam(required = true) Map<String, Object> body) {
		return this.generate(token, Integer.valueOf((String) body.get("patId")), null);
	}

	@PostMapping("/assess/familyName")
	public MedicalReport generateMedicalReportByName(@RequestHeader("token") String token,
			@RequestParam(required = true) Map<String, Object> body) {
		return this.generate(token, null, (String) body.get("familyName"));
	}

	private MedicalReport generate(String token, Integer id, String name) {
		securityService.authenticationCheck(token);
		MedicalReport medicalReport = medicalReportService.generateReport(token, id, name);
		if (medicalReport == null)
			throw new NotFoundException("No report available");
		return medicalReport;
	}
}