package com.mediscreen.mspatientadmin.dao;

import com.mediscreen.mspatientadmin.configuration.DatabaseConfiguration;
import com.mediscreen.mspatientadmin.exception.InternalServerErrorException;
import com.mediscreen.mspatientadmin.exception.NotFoundException;
import com.mediscreen.mspatientadmin.interfaces.DatabaseConfigurationInterface;
import com.mediscreen.mspatientadmin.model.DBConnection;
import com.mediscreen.mspatientadmin.model.Patient;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class PatientDaoTest {
    private PatientDao dao;
    private Patient patient;
    private DatabaseConfigurationInterface databaseConfiguration;

    @BeforeEach
    void init_test(){
        databaseConfiguration = new DatabaseConfiguration(new DBConnection(
                "localhost",
                3307,
                "mediscreen_patientadmin_test_oc_mc",
                "root",
                "password"
        ));

        this.resetDB();

        patient = new Patient(
                5,
                "firstname",
                "lastname",
                "S",
                LocalDate.now(),
                "address",
                "email",
                "phone",
                "country"
        );

        this.dao = new PatientDao(databaseConfiguration);
    }

    @Tag("PatientDaoTest")
    @Test
    void getPatientById_test() {
        assertThat(this.dao.getPatientById(10)).isNull();

        Patient patient = this.dao.getPatientById(1);

        assertThat(patient.getId()).isEqualTo(1);
        assertThat(patient.getFirstname()).isEqualTo("test");
        assertThat(patient.getLastname()).isEqualTo("testNone");
        assertThat(patient.getSexe()).isEqualTo("F");
        assertThat(patient.getBirthday()).isEqualTo(LocalDate.parse("1966-12-31"));
        assertThat(patient.getAddress()).isEqualTo("1 Brookside St");
        assertThat(patient.getEmail()).isEqualTo("EmailTestNone");
        assertThat(patient.getPhone()).isEqualTo("100-222-3333");
        assertThat(patient.getCountry()).isEqualTo("CountryTestNone");

        this.dao = null;
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> this.dao.getPatientById(1));
    }

    @Tag("PatientDaoTest")
    @Test
    void getAllPatient_test() {
        List<Patient> patientList = this.dao.getAllPatient();
        assertThat(patientList).isNotNull();
        assertThat(patientList.size()).isEqualTo(4);
        for(Patient patient : patientList){
            assertThat(patient).isNotNull();
            assertThat(patient.getFirstname()).isNotBlank();
            assertThat(patient.getSexe()).isNotBlank();
        }

        this.dao = null;
        assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> this.dao.getAllPatient());
    }

    @Tag("PatientDaoTest")
    @Test
    void CRUD_Patient_test() {
        List<Patient> patientList;

        // CREATE
        this.dao.createPatient(this.patient);

        patientList = this.dao.getAllPatient();
        assertThat(patientList).isNotNull();
        assertThat(patientList.size()).isEqualTo(5);

        Patient p_create = this.dao.getPatientById(5);
        assertThat(p_create.getId()).isEqualTo(patient.getId());
        assertThat(p_create.getFirstname()).isEqualTo(patient.getFirstname());
        assertThat(p_create.getLastname()).isEqualTo(patient.getLastname());
        assertThat(p_create.getSexe()).isEqualTo(patient.getSexe());
        assertThat(p_create.getBirthday()).isEqualTo(patient.getBirthday());
        assertThat(p_create.getAddress()).isEqualTo(patient.getAddress());
        assertThat(p_create.getEmail()).isEqualTo(patient.getEmail());
        assertThat(p_create.getPhone()).isEqualTo(patient.getPhone());
        assertThat(p_create.getCountry()).isEqualTo(patient.getCountry());


        // UPDATE
        patient.setLastname("testBorderline");

        this.dao.updatePatient(patient);

        Patient p_update = this.dao.getPatientById(5);
        assertThat(p_update.getId()).isEqualTo(patient.getId());
        assertThat(p_update.getLastname()).isEqualTo(patient.getLastname());


        // DELETE
        this.dao.deletePatientById(5);

        patientList = this.dao.getAllPatient();
        assertThat(patientList).isNotNull();
        assertThat(patientList.size()).isEqualTo(4);
    }

    @Tag("PatientDaoTest")
    @Test
    void searchPatients_test() {
        List<Patient> patientList = this.dao.searchPatients("testInDanger");

        assertThat(patientList).isNotNull();
        assertThat(patientList.size()).isEqualTo(1);
        assertThat(patientList.get(0).getLastname()).isEqualTo("testInDanger");
    }

    private void resetDB(){
        Connection connection = null;

        try {
            connection = databaseConfiguration.getConnection();

            //Clear patient table;
            connection.prepareStatement("TRUNCATE TABLE patient").execute();

            //Insert Data
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO patient(id, firstname, lastname, sexe, birthday, address, email, phone, country)");
            sql.append("VALUES");
            sql.append("('1', 'test', 'testNone', 'F', '1966-12-31', '1 Brookside St', 'EmailTestNone', '100-222-3333', 'CountryTestNone'),");
            sql.append("('2', 'test', 'testBorderline', 'M', '1945-06-24', '2 High St', 'EmailTestBorderline', '200-333-4444', 'CountryTestBorderline'),");
            sql.append("('3', 'test', 'testInDanger', 'M', '2004-06-18', '3 Club Road', 'EmailTestInDanger', '300-444-5555', 'CountryTestInDanger'),");
            sql.append("('4', 'test', 'testEarlyOnset', 'F', '2002-06-28', '4 Valley Dr', 'EmailTestEarlyOnset', '400-555-6666', 'CountryTestEarlyOnset')");
            connection.prepareStatement(sql.toString()).execute();

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            databaseConfiguration.closeConnection(connection);
        }
    }
}