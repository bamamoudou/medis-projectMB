package com.mediscreen.msmedicalreport.interfaces;

import com.mediscreen.msmedicalreport.model.MedicalReport;

public interface MedicalReportServiceInterface {
    /**
     * Generate report
     * @param token
     * @param id
     * @param name
     * @return
     */
    MedicalReport generateReport(String token, Integer id, String name);
}
