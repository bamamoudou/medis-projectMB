package com.mediscreen.msmedicalreport.service;

import com.mediscreen.msmedicalreport.model.MedicalReport;

public interface MedicalReportService {
	/**
	 * Generate report
	 * 
	 * @param token
	 * @param id
	 * @param name
	 * @return
	 */
	MedicalReport generateReport(String token, Integer id, String name);
}