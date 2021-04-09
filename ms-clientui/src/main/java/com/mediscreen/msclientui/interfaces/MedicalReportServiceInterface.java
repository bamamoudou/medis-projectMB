package com.mediscreen.msclientui.interfaces;

import com.mediscreen.msclientui.model.MedicalReport;

import javax.servlet.http.HttpSession;

public interface MedicalReportServiceInterface {
    /**
     * Get report of patient
     * @param session
     * @param patientId
     * @return
     */
    MedicalReport getMedicalReport(HttpSession session, Integer patientId);
}
