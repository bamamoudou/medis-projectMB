package com.mediscreen.msmedicalrecord.service;

import com.mediscreen.msmedicalrecord.dao.MedicalRecordDao;
import com.mediscreen.msmedicalrecord.exception.NotFoundException;
import com.mediscreen.msmedicalrecord.model.MedicalRecord;
import com.mediscreen.msmedicalrecord.model.Patient;
import com.mediscreen.msmedicalrecord.proxy.MSZuulProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceTest {
    private MedicalRecordService medicalRecordService;
    private List<MedicalRecord> medicalRecordList;
    private MedicalRecord medicalRecord_1;
    private MedicalRecord medicalRecord_2;

    @Mock
    private static MSZuulProxy msZuulProxy;

    @Mock
    private static MedicalRecordDao medicalRecordDao;

    @BeforeEach
    void init_test(){
        medicalRecordService = new MedicalRecordService(medicalRecordDao, msZuulProxy);

        medicalRecord_1 = new MedicalRecord(
            "id_1",
            1,
            "doctor_1",
            LocalDateTime.now(),
            "content_1",
            true
        );

        medicalRecord_1 = new MedicalRecord(
            "id_2",
            1,
            "doctor_2",
            LocalDateTime.now(),
            "content_2",
            true
        );

        medicalRecordList = new ArrayList<>();
        medicalRecordList.add(medicalRecord_1);
        medicalRecordList.add(medicalRecord_2);
    }

    @Tag("MedicalRecordServiceTest")
    @Test
    void getPatientMedicalRecords_test(){
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(new Patient());
        when(medicalRecordDao.getAllPatientMedicalRecords(anyInt())).thenReturn(medicalRecordList);
        assertThat(medicalRecordService.getPatientMedicalRecords("token", 1)).isEqualTo(medicalRecordList);
        assertThat(medicalRecordService.getPatientMedicalRecords("token", 0)).isNull();
        assertThat(medicalRecordService.getPatientMedicalRecords("token", null)).isNull();
        assertThat(medicalRecordService.getPatientMedicalRecords("token", -1)).isNull();
    }

    @Tag("MedicalRecordServiceTest")
    @Test
    void getPatientMedicalRecords_test_unknowPatient(){
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(null);
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> medicalRecordService.getPatientMedicalRecords("token", 1));
    }

    @Tag("MedicalRecordServiceTest")
    @Test
    void createMedicalRecord_test(){
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(new Patient());
        when(medicalRecordDao.createMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord_2);
        assertThat(medicalRecordService.createMedicalRecord("token", medicalRecord_1)).isEqualTo(medicalRecord_2);


        medicalRecord_1.setPatientId(0);
        assertThat(medicalRecordService.createMedicalRecord("token", medicalRecord_1)).isNull();

        medicalRecord_1.setPatientId(-1);
        assertThat(medicalRecordService.createMedicalRecord("token", medicalRecord_1)).isNull();

        medicalRecord_1.setPatientId(null);
        assertThat(medicalRecordService.createMedicalRecord("token", medicalRecord_1)).isNull();
    }

    @Tag("MedicalRecordServiceTest")
    @Test
    void createMedicalRecord_test_unknowPatient(){
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(null);
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> medicalRecordService.getPatientMedicalRecords("token", 1));
    }

    @Tag("MedicalRecordServiceTest")
    @Test
    void updateMedicalRecord_test(){
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(new Patient());
        when(medicalRecordDao.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord_2);
        assertThat(medicalRecordService.updateMedicalRecord("token", medicalRecord_1)).isEqualTo(medicalRecord_2);


        medicalRecord_1.setPatientId(0);
        assertThat(medicalRecordService.updateMedicalRecord("token", medicalRecord_1)).isNull();

        medicalRecord_1.setPatientId(-1);
        assertThat(medicalRecordService.updateMedicalRecord("token", medicalRecord_1)).isNull();

        medicalRecord_1.setPatientId(null);
        assertThat(medicalRecordService.updateMedicalRecord("token", medicalRecord_1)).isNull();
    }

    @Tag("MedicalRecordServiceTest")
    @Test
    void updateMedicalRecord_test_unknowPatient(){
        when(msZuulProxy.msPatientAdmin_getPatient(anyString(), anyInt())).thenReturn(null);
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> medicalRecordService.getPatientMedicalRecords("token", 1));
    }
}