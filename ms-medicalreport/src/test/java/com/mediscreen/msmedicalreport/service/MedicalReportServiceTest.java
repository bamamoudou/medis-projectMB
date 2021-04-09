package com.mediscreen.msmedicalreport.service;

import com.mediscreen.msmedicalreport.exception.EmptyDataException;
import com.mediscreen.msmedicalreport.exception.NotFoundException;
import com.mediscreen.msmedicalreport.model.MedicalRecord;
import com.mediscreen.msmedicalreport.model.MedicalReport;
import com.mediscreen.msmedicalreport.model.Patient;
import com.mediscreen.msmedicalreport.proxy.MSZuulProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalReportServiceTest {
    private MedicalReportService medicalReportService;
    MedicalRecord record = new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "hemoglobine A1C", true);

    @Mock
    private static MSZuulProxy msZuulProxy;

    @BeforeEach
    void init_test(){
        medicalReportService = new MedicalReportService(msZuulProxy);
    }

    private void testReport(Patient patient, MedicalReport report, MedicalReport.ReportResult result) {
        assertThat(report.getPatientId()).isEqualTo(patient.getId());
        assertThat(report.getDate()).isNotNull();
        assertThat(report.getResult()).isEqualTo(result);
        assertThat(report.getContent()).isNotBlank();
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_countTrigger(){
        Integer matchRemoveCount = 0;
        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "hemoglobine A1C", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "microalbumine", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "taille", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "poids", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "fumeur", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "anormal", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "cholesterol", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "vertige", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "rechute", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "reaction", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "anticorps", true));
        assertThat(medicalReportService.countTrigger(medicalRecordList)).isEqualTo(medicalRecordList.size());

        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "hémoglobine A1C", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "cholestérol", true));
        assertThat(medicalReportService.countTrigger(medicalRecordList)).isEqualTo(medicalRecordList.size());

        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "Poids", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "Fumeur", true));
        assertThat(medicalReportService.countTrigger(medicalRecordList)).isEqualTo(medicalRecordList.size());

        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "taille", false));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "poids", false));
        matchRemoveCount += 2;
        assertThat(medicalReportService.countTrigger(medicalRecordList)).isEqualTo(medicalRecordList.size() - matchRemoveCount);

        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "unknown", false));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "unknown 2", false));
        matchRemoveCount += 2;
        assertThat(medicalReportService.countTrigger(medicalRecordList)).isEqualTo(medicalRecordList.size() - matchRemoveCount);

        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "reaction anticorps", true));
        medicalRecordList.add(new MedicalRecord("_id", 1, "doctor", LocalDateTime.now(), null, "reactionanticorps", true));
        matchRemoveCount -= 2;
        assertThat(medicalReportService.countTrigger(medicalRecordList)).isEqualTo(medicalRecordList.size() - matchRemoveCount);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_argEmptyOrNull(){
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> medicalReportService.generateReport(null, 1, null));
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> medicalReportService.generateReport("", 1, null));
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> medicalReportService.generateReport("token", 0, null));
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> medicalReportService.generateReport("token", -1, null));
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> medicalReportService.generateReport("token", null, null));
        assertThatExceptionOfType(EmptyDataException.class).isThrownBy(() -> medicalReportService.generateReport("token", null, ""));
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_patientUnknown(){
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(null);
        when(msZuulProxy.msPatientAdmin_getAllPatients(anyString())).thenReturn(null);
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> medicalReportService.generateReport("token", 1, null));
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> medicalReportService.generateReport("token", null, "name"));
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_medicalRecordsNull(){
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "sexe",
                LocalDate.now(),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(null);
        testReport(patient, medicalReportService.generateReport("token", 1, null), MedicalReport.ReportResult.NONE);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_noMedicalRecords(){
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "sexe",
                LocalDate.now(),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(new ArrayList<>());
        testReport(patient, medicalReportService.generateReport("token", 1, null), MedicalReport.ReportResult.NONE);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_triggerCountLess2_ageLess30(){
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "sexe",
                LocalDate.of(LocalDate.now().getYear() - 20, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);
        testReport(patient, medicalReportService.generateReport("token", 1, null), MedicalReport.ReportResult.NONE);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_ageLess30(){
        MedicalReport medicalReport;
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "sexe",
                LocalDate.of(LocalDate.now().getYear() - 20, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        // triggerCount : 1
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 2
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 3
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_ageEqual30(){
        MedicalReport medicalReport;
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "sexe",
                LocalDate.of(LocalDate.now().getYear() - 30, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        // triggerCount : 1
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 2
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.BORDERLINE);

        // triggerCount : 3
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.BORDERLINE);

        // triggerCount : 6
        medicalRecordList.add(record);
        medicalRecordList.add(record);
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.IN_DANGER);

        // triggerCount : 8
        medicalRecordList.add(record);
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.EARLY_ONSET);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_ageGreater30(){
        MedicalReport medicalReport;
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "sexe",
                LocalDate.of(LocalDate.now().getYear() - 35, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        // triggerCount : 1
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 2
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.BORDERLINE);

        // triggerCount : 3
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.BORDERLINE);

        // triggerCount : 6
        medicalRecordList.add(record);
        medicalRecordList.add(record);
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.IN_DANGER);

        // triggerCount : 8
        medicalRecordList.add(record);
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.EARLY_ONSET);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_Male_ageLess30(){
        MedicalReport medicalReport;
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "M",
                LocalDate.of(LocalDate.now().getYear() - 20, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        // triggerCount : 1
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 2
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 3
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.IN_DANGER);

        // triggerCount : 4
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.IN_DANGER);

        // triggerCount : 5
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.EARLY_ONSET);

        // triggerCount : 6
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.EARLY_ONSET);
    }

    @Tag("MedicalReportServiceTest")
    @Test
    void generateReport_test_Female_ageLess30(){
        MedicalReport medicalReport;
        Patient patient = new Patient(
                1,
                "firstname",
                "lastname",
                "F",
                LocalDate.of(LocalDate.now().getYear() - 20, LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth()),
                "address",
                "email",
                "phone",
                "country"
        );
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(patient);

        List<MedicalRecord> medicalRecordList = new ArrayList<>();

        // triggerCount : 1
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 2
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 3
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.NONE);

        // triggerCount : 4
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.IN_DANGER);

        // triggerCount : 5
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.IN_DANGER);

        // triggerCount : 6
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.IN_DANGER);

        // triggerCount : 7
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.EARLY_ONSET);

        // triggerCount : 8
        medicalRecordList.add(record);
        when(msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords(anyString(), anyInt())).thenReturn(medicalRecordList);

        medicalReport = medicalReportService.generateReport("token", 1, null);
        testReport(patient, medicalReport, MedicalReport.ReportResult.EARLY_ONSET);
    }
}