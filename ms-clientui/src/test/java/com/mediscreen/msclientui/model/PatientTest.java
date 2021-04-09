package com.mediscreen.msclientui.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {
    private Patient patient;
    private LocalDate birthday = LocalDate.now();

    @BeforeEach
    void init_test(){
        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        medicalRecordList.add(new MedicalRecord(
                "5ffb399c57b9e94b6053c8ac",
                1,
                "doctor",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "content",
                true
        ));
        patient = new Patient(
            1,
            "firstname",
            "lastname",
            "sexe",
            birthday,
            "address",
            "email",
            "phone",
            "country",
            medicalRecordList
        );
    }

    @Tag("PatientTest")
    @Test
    void get_test(){
        assertThat(patient.getId()).isEqualTo(1);
        assertThat(patient.getFirstname()).isEqualTo("firstname");
        assertThat(patient.getLastname()).isEqualTo("lastname");
        assertThat(patient.getSexe()).isEqualTo("sexe");
        assertThat(patient.getBirthday()).isEqualTo(birthday);
        assertThat(patient.getAddress()).isEqualTo("address");
        assertThat(patient.getEmail()).isEqualTo("email");
        assertThat(patient.getPhone()).isEqualTo("phone");
        assertThat(patient.getCountry()).isEqualTo("country");
        assertThat(patient.getMedicalRecordList().size()).isEqualTo(1);
    }

    @Tag("PatientTest")
    @Test
    void set_test(){
        assertThat(patient.getId()).isEqualTo(1);
        assertThat(patient.getFirstname()).isEqualTo("firstname");
        assertThat(patient.getLastname()).isEqualTo("lastname");
        assertThat(patient.getSexe()).isEqualTo("sexe");
        assertThat(patient.getBirthday()).isEqualTo(birthday);
        assertThat(patient.getAddress()).isEqualTo("address");
        assertThat(patient.getEmail()).isEqualTo("email");
        assertThat(patient.getPhone()).isEqualTo("phone");
        assertThat(patient.getCountry()).isEqualTo("country");
        assertThat(patient.getMedicalRecordList().size()).isEqualTo(1);

        LocalDate newBirthday = LocalDate.now();

        patient.setId(2);
        patient.setFirstname("newFirstname");
        patient.setLastname("newLastname");
        patient.setSexe("newSexe");
        patient.setBirthday(newBirthday);
        patient.setAddress("newAddress");
        patient.setEmail("newEmail");
        patient.setPhone("newPhone");
        patient.setCountry("newCountry");
        patient.setMedicalRecordList(new ArrayList<>());

        assertThat(patient.getId()).isEqualTo(2);
        assertThat(patient.getFirstname()).isEqualTo("newFirstname");
        assertThat(patient.getLastname()).isEqualTo("newLastname");
        assertThat(patient.getSexe()).isEqualTo("newSexe");
        assertThat(patient.getBirthday()).isEqualTo(newBirthday);
        assertThat(patient.getAddress()).isEqualTo("newAddress");
        assertThat(patient.getEmail()).isEqualTo("newEmail");
        assertThat(patient.getPhone()).isEqualTo("newPhone");
        assertThat(patient.getCountry()).isEqualTo("newCountry");
        assertThat(patient.getMedicalRecordList().size()).isEqualTo(0);
    }

}