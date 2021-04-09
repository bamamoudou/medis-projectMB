package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.model.MedicalReport;

import javax.servlet.http.HttpSession;

public interface MedicalReportService {
	/**
	 * Get report of patient
	 * 
	 * @param session
	 * @param patientId
	 * @return
	 */
	MedicalReport getMedicalReport(HttpSession session, Integer patientId);
}