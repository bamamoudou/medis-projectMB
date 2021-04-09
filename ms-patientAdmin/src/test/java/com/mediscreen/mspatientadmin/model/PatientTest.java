package com.mediscreen.mspatientadmin.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PatientTest {
	private Patient patient;
	private LocalDate birthday = LocalDate.now();

	@BeforeEach
	void init_test() {
		patient = new Patient(1, "firstname", "lastname", "sexe", birthday, "address", "email", "phone", "country");
	}

	@Tag("PatientTest")
	@Test
	void get_test() {
		assertThat(patient.getId()).isEqualTo(1);
		assertThat(patient.getFirstname()).isEqualTo("firstname");
		assertThat(patient.getLastname()).isEqualTo("lastname");
		assertThat(patient.getSexe()).isEqualTo("sexe");
		assertThat(patient.getBirthday()).isEqualTo(birthday);
		assertThat(patient.getAddress()).isEqualTo("address");
		assertThat(patient.getEmail()).isEqualTo("email");
		assertThat(patient.getPhone()).isEqualTo("phone");
		assertThat(patient.getCountry()).isEqualTo("country");
	}

	@Tag("PatientTest")
	@Test
	void set_test() {
		assertThat(patient.getId()).isEqualTo(1);
		assertThat(patient.getFirstname()).isEqualTo("firstname");
		assertThat(patient.getLastname()).isEqualTo("lastname");
		assertThat(patient.getSexe()).isEqualTo("sexe");
		assertThat(patient.getBirthday()).isEqualTo(birthday);
		assertThat(patient.getAddress()).isEqualTo("address");
		assertThat(patient.getEmail()).isEqualTo("email");
		assertThat(patient.getPhone()).isEqualTo("phone");
		assertThat(patient.getCountry()).isEqualTo("country");

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

		assertThat(patient.getId()).isEqualTo(2);
		assertThat(patient.getFirstname()).isEqualTo("newFirstname");
		assertThat(patient.getLastname()).isEqualTo("newLastname");
		assertThat(patient.getSexe()).isEqualTo("newSexe");
		assertThat(patient.getBirthday()).isEqualTo(newBirthday);
		assertThat(patient.getAddress()).isEqualTo("newAddress");
		assertThat(patient.getEmail()).isEqualTo("newEmail");
		assertThat(patient.getPhone()).isEqualTo("newPhone");
		assertThat(patient.getCountry()).isEqualTo("newCountry");
	}
}