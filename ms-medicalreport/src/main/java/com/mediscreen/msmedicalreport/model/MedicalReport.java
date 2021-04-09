package com.mediscreen.msmedicalreport.model;

import java.time.LocalDateTime;

public class MedicalReport {
    private Integer patientId;
    private LocalDateTime date;
    private String content;
    private ReportResult result;

    public enum ReportResult {
        NONE("None"),
        BORDERLINE("Borderline"),
        IN_DANGER("In Danger"),
        EARLY_ONSET("Early onset");

        private String lib;

        /**
         * Constructor
         * @param lib
         */
        ReportResult(String lib) {
            this.lib = lib;
        }

        public String getLib(){
            return this.lib;
        }
    }

    /**
     * Constructor
     */
    public MedicalReport() {
    }

    /**
     * Constructor
     * @param patientId
     * @param date
     * @param content
     * @param result
     */
    public MedicalReport(Integer patientId, LocalDateTime date, String content, ReportResult result) {
        this.patientId = patientId;
        this.date = date;
        this.content = content;
        this.result = result;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ReportResult getResult() {
        return result;
    }

    public void setResult(ReportResult result) {
        this.result = result;
    }
}
