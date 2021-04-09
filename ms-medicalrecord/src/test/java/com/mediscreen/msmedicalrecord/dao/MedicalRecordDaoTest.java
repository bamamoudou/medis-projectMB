package com.mediscreen.msmedicalrecord.dao;

import com.mediscreen.msmedicalrecord.configuration.DatabaseConfiguration;
import com.mediscreen.msmedicalrecord.interfaces.DatabaseConfigurationInterface;
import com.mediscreen.msmedicalrecord.model.DBConnection;
import com.mediscreen.msmedicalrecord.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MedicalRecordDaoTest {
    private DatabaseConfigurationInterface databaseConfiguration;
    private MedicalRecordDao dao;
    private MedicalRecord medicalRecord = new MedicalRecord(
            null,
            2,
            "doctorName",
            LocalDateTime.now(),
            "content",
            false
    );

    @BeforeEach
    void init_test(){
        databaseConfiguration = new DatabaseConfiguration(new DBConnection(
                "localhost",
                27018,
                "mediscreen_test_oc_mc",
                "root",
                "password"
        ));

        this.dao = new MedicalRecordDao(databaseConfiguration);
    }

    @Test
    void getAllPatientMedicalRecords_test() {
        List<MedicalRecord> medicalRecordList = this.dao.getAllPatientMedicalRecords(1);
        assertThat(medicalRecordList).isNotNull();
        assertThat(medicalRecordList.size()).isGreaterThanOrEqualTo(3);

        assertThat(this.dao.getAllPatientMedicalRecords(50)).isNull();
    }

    @Test
    void create_update_medicalRecord_test() {
        MedicalRecord medicalRecordCreated = this.dao.createMedicalRecord(medicalRecord);

        assertThat(medicalRecordCreated.getId()).isNotNull();
        assertThat(medicalRecordCreated.getPatientId()).isEqualTo(medicalRecord.getPatientId());
        assertThat(medicalRecordCreated.getDoctorName()).isEqualTo(medicalRecord.getDoctorName());
        assertThat(medicalRecordCreated.getCreateDate()).isBefore(LocalDateTime.now());
        assertThat(medicalRecordCreated.getLastChangeDate()).isNull();
        assertThat(medicalRecordCreated.getContent()).isEqualTo(medicalRecord.getContent());
        assertThat(medicalRecordCreated.isActive()).isEqualTo(medicalRecord.isActive());

        medicalRecord.setId(medicalRecordCreated.getId());

        medicalRecord.setActive(!medicalRecord.isActive());
        MedicalRecord medicalRecordUpdated = this.dao.updateMedicalRecord(medicalRecord);

        assertThat(medicalRecordUpdated.getId()).isEqualTo(medicalRecord.getId());
        assertThat(medicalRecordUpdated.getLastChangeDate()).isNotNull();
        assertThat(medicalRecordUpdated.isActive()).isEqualTo(medicalRecord.isActive());
    }
}