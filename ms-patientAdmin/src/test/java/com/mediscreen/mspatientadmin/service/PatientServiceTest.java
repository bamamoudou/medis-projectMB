package com.mediscreen.mspatientadmin.service;

import com.mediscreen.mspatientadmin.dao.PatientDaoImpl;
import com.mediscreen.mspatientadmin.exception.NotFoundException;
import com.mediscreen.mspatientadmin.model.Patient;
import com.mediscreen.mspatientadmin.serviceImpl.PatientServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
	private PatientServiceImpl patientService;
	private List<Patient> patientList;
	private Patient patient_1;
	private Patient patient_2;

	@Mock
	private static PatientDaoImpl patientDao;

	@BeforeEach
	void init_test() {
		patientService = new PatientServiceImpl(patientDao);

		patient_1 = new Patient(1, "firstname_1", "lastname_1", "sexe_1", LocalDate.now(), "address_1", "email_1",
				"phone_1", "country_1");

		patient_2 = new Patient(2, "firstname_2", "lastname_2", "sexe_2", LocalDate.now(), "address_2", "email_2",
				"phone_2", "country_2");

		patientList = new ArrayList<>();
		patientList.add(patient_1);
		patientList.add(patient_2);
	}

	@Tag("PatientServiceTest")
	@Test
	void getPatientById_test() {
		when(patientDao.getPatientById(any(Integer.class))).thenReturn(patient_1);
		assertThat(patientService.getPatientById(null)).isNull();
		assertThat(patientService.getPatientById(-1)).isNull();
		assertThat(patientService.getPatientById(0)).isNull();
		assertThat(patientService.getPatientById(1)).isEqualTo(patient_1);
	}

	@Tag("PatientServiceTest")
	@Test
	void getAllPatient_test() {
		when(patientDao.getAllPatient()).thenReturn(patientList);
		assertThat(patientService.getAllPatient()).isEqualTo(patientList);
	}

	@Tag("PatientServiceTest")
	@Test
	void updatePatient_test() {
		when(patientDao.updatePatient(any(Patient.class))).thenReturn(patient_2);
		assertThat(patientService.updatePatient(patient_1)).isEqualTo(patient_2);

		patient_1.setId(null);
		assertThat(patientService.updatePatient(patient_1)).isNull();

		patient_1.setId(0);
		assertThat(patientService.updatePatient(patient_1)).isNull();

		patient_1.setId(-1);
		assertThat(patientService.updatePatient(patient_1)).isNull();
	}

	@Tag("PatientServiceTest")
	@Test
	void createPatient_test() {
		when(patientDao.createPatient(any(Patient.class))).thenReturn(patient_2);
		assertThat(patientService.createPatient(patient_1)).isEqualTo(patient_2);
	}

	@Tag("PatientServiceTest")
	@Test
	void deletePatientById_test() {
		assertThat(patientService.deletePatientById(null)).isFalse();
		assertThat(patientService.deletePatientById(-1)).isFalse();
		assertThat(patientService.deletePatientById(0)).isFalse();
	}

	@Tag("PatientServiceTest")
	@Test
	void deletePatientById_test_unknownPatient() {
		when(patientDao.getPatientById(any(Integer.class))).thenReturn(null);
		assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> patientService.deletePatientById(1));
	}

	@Tag("PatientServiceTest")
	@Test
	void searchPatient_test() {
		when(patientDao.searchPatients(any(String.class))).thenReturn(patientList);
		assertThat(patientService.searchPatient(null)).isNull();
		assertThat(patientService.searchPatient("")).isNull();
		assertThat(patientService.searchPatient("c")).isEqualTo(patientList);
	}
}