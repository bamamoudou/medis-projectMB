package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.JWTTest;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.interfaces.MedicalReportServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.MedicalReport;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalReportServiceTest {
    private MedicalReportServiceInterface medicalReportService;

    @Mock
    private static SecurityServiceInterface securityService;

    @Mock
    private static MSZuulProxy msZuulProxy;

    @BeforeEach
    void init_test(){
        medicalReportService = new MedicalReportService(msZuulProxy, securityService);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void getMedicalReport_test_isNotLog(){
        when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
        assertThatExceptionOfType(NotAllowedException.class).isThrownBy(() -> medicalReportService.getMedicalReport(JWTTest.session, 0));
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void getMedicalReport_test_nullReport(){
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        when(msZuulProxy.msMedicalReport_generateMedicalReport(anyObject(), anyInt())).thenReturn(null);
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> medicalReportService.getMedicalReport(JWTTest.session, 0));
    }



    @Tag("MedicalReportServiceTest")
    @Test
    void getMedicalReport_test(){
        MedicalReport medicalReport = new MedicalReport(0, LocalDateTime.now(), "content", MedicalReport.ReportResult.NONE);
        when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
        when(msZuulProxy.msMedicalReport_generateMedicalReport(null, 0)).thenReturn(medicalReport);
        HttpSession session = JWTTest.session;
        session.setAttribute("token", JWTTest.token);
        MedicalReport medicalReportResponse = medicalReportService.getMedicalReport(session, 0);

        assertThat(medicalReportResponse.getPatientId()).isEqualTo(medicalReport.getPatientId());
        assertThat(medicalReportResponse.getDate()).isEqualTo(medicalReport.getDate());
        assertThat(medicalReportResponse.getContent()).isEqualTo(medicalReport.getContent());
        assertThat(medicalReportResponse.getResult()).isEqualTo(medicalReport.getResult());
    }
}