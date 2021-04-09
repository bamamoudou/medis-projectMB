package com.mediscreen.msclientui.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MedicalReportTest {
    private MedicalReport medicalReport;
    private LocalDateTime dateTime;

    @BeforeEach
    void init_test(){
        medicalReport = new MedicalReport(
                1,
                dateTime,
                "content",
                MedicalReport.ReportResult.NONE
        );
    }

    @Tag("MedicalReportTest")
    @Test
    void get_test(){
        assertThat(medicalReport.getPatientId()).isEqualTo(1);
        assertThat(medicalReport.getDate()).isEqualTo(dateTime);
        assertThat(medicalReport.getContent()).isEqualTo("content");
        assertThat(medicalReport.getResult()).isEqualTo(MedicalReport.ReportResult.NONE);
    }

    @Tag("MedicalReportTest")
    @Test
    void set_test(){
        assertThat(medicalReport.getPatientId()).isEqualTo(1);
        assertThat(medicalReport.getDate()).isEqualTo(dateTime);
        assertThat(medicalReport.getContent()).isEqualTo("content");
        assertThat(medicalReport.getResult()).isEqualTo(MedicalReport.ReportResult.NONE);

        LocalDateTime newDateTime = LocalDateTime.now();

        medicalReport.setPatientId(2);
        medicalReport.setDate(newDateTime);
        medicalReport.setContent("new content");
        medicalReport.setResult(MedicalReport.ReportResult.EARLY_ONSET);

        assertThat(medicalReport.getPatientId()).isEqualTo(2);
        assertThat(medicalReport.getDate()).isEqualTo(newDateTime);
        assertThat(medicalReport.getContent()).isEqualTo("new content");
        assertThat(medicalReport.getResult()).isEqualTo(MedicalReport.ReportResult.EARLY_ONSET);
    }
}