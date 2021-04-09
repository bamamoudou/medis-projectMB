package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.JWTTest;
import com.mediscreen.msclientui.exception.EmptyDataException;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.model.MedicalRecord;
import com.mediscreen.msclientui.model.MedicalReport;
import com.mediscreen.msclientui.model.Patient;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import com.mediscreen.msclientui.service.MedicalRecordService;
import com.mediscreen.msclientui.service.MedicalReportService;
import com.mediscreen.msclientui.service.PatientService;
import com.mediscreen.msclientui.service.SecurityService;
import com.mediscreen.msclientui.serviceImpl.PatientServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {
	private PatientService patientService;
	private List<Patient> patientList;
	private Patient patient;
	private MedicalReport medicalReport;
	private List<MedicalRecord> medicalRecordList;

	@Mock
	private static SecurityService securityService;

	@Mock
	private static MSZuulProxy msZuulProxy;

	@Mock
	private static MedicalRecordService medicalRecordService;

	@Mock
	private static MedicalReportService medicalReportService;

	@BeforeEach
	void init_test() {
		medicalRecordList = new ArrayList<>();
		medicalRecordList.add(new MedicalRecord("5ffb399c57b9e94b6053c8ac", 1, "doctor", LocalDateTime.now(),
				LocalDateTime.now(), "content", true));
		patient = new Patient(1, "firstname", "lastname", "sexe", LocalDate.now(), "address", "email", "phone",
				"country", medicalRecordList);

		medicalReport = new MedicalReport(0, LocalDateTime.now(), "content", MedicalReport.ReportResult.NONE);
		patient.setMedicalReport(medicalReport);

		patientList = new ArrayList<>();
		patientList.add(patient);

		patientService = new PatientServiceImpl(msZuulProxy, securityService, medicalRecordService,
				medicalReportService);
	}

	@Test
	void getAllPatients_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminGetAllPatients(anyObject())).thenReturn(patientList);
		assertThat(patientService.getAllPatients(JWTTest.session)).isNotNull();
		assertThat(patientService.getAllPatients(JWTTest.session).size()).isEqualTo(1);
	}

	@Test
	void getAllPatients_test_notLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> patientService.getAllPatients(JWTTest.session));
	}

	@Test
	void searchPatient_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.searchPatients(anyObject(), anyString())).thenReturn(patientList);
		assertThat(patientService.searchPatient(JWTTest.session, "search")).isNotNull();
		assertThat(patientService.searchPatient(JWTTest.session, "search").size()).isEqualTo(1);
	}

	@Test
	void searchPatient_test_notLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> patientService.searchPatient(JWTTest.session, "search"));
	}

	@Test
	void getPatient_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminGetPatient(anyObject(), anyInt())).thenReturn(patient);
		when(medicalRecordService.getAllPatientMedicalRecords(any(HttpSession.class), anyInt()))
				.thenReturn(medicalRecordList);
		when(medicalReportService.getMedicalReport(any(HttpSession.class), anyInt())).thenReturn(medicalReport);

		Patient result = patientService.getPatient(JWTTest.session, 1);

		assertThat(result).isNotNull();
		assertThat(result.getMedicalRecordList()).isEqualTo(medicalRecordList);
		assertThat(result.getMedicalReport()).isEqualTo(medicalReport);
	}

	@Test
	void getPatient_test_idNull() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		assertThatExceptionOfType(EmptyDataException.class)
				.isThrownBy(() -> patientService.getPatient(JWTTest.session, null));
	}

	@Test
	void getPatient_test_idZero() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		assertThatExceptionOfType(EmptyDataException.class)
				.isThrownBy(() -> patientService.getPatient(JWTTest.session, 0));
	}

	@Test
	void getPatient_test_notLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> patientService.getPatient(JWTTest.session, 0));
	}

	@Test
	void createPatient_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminCreatePatient(anyObject(), any(Patient.class)))
				.thenReturn(new ResponseEntity<>(patient, HttpStatus.OK));

		assertThat(patientService.createPatient(JWTTest.session, patient)).isEqualTo(patient);
	}

	@Test
	void createPatient_test_nullResult() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminCreatePatient(anyObject(), any(Patient.class)))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));
		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> patientService.createPatient(JWTTest.session, patient));
	}

	@Test
	void createPatient_test_notLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> patientService.createPatient(JWTTest.session, patient));
	}

	@Test
	void updatePatient_test_nullResult() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminGetPatient(anyObject(), anyInt())).thenReturn(patient);
		when(medicalRecordService.getAllPatientMedicalRecords(any(HttpSession.class), anyInt()))
				.thenReturn(medicalRecordList);
		when(medicalReportService.getMedicalReport(any(HttpSession.class), anyInt())).thenReturn(medicalReport);
		when(msZuulProxy.msPatientAdminUpdatePatient(anyObject(), any(Patient.class)))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));

		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> patientService.updatePatient(JWTTest.session, patient));
	}

	@Test
	void updatePatient_test_unknownPatient() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminGetPatient(anyObject(), anyInt())).thenReturn(null);
		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> patientService.updatePatient(JWTTest.session, patient));
	}

	@Test
	void updatePatient_test_notLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> patientService.updatePatient(JWTTest.session, patient));
	}

	@Test
	void deletePatient_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminGetPatient(anyObject(), anyInt())).thenReturn(patient);
		when(medicalRecordService.getAllPatientMedicalRecords(any(HttpSession.class), anyInt()))
				.thenReturn(medicalRecordList);
		when(medicalReportService.getMedicalReport(any(HttpSession.class), anyInt())).thenReturn(medicalReport);
		when(msZuulProxy.msPatientAdminDeletePatient(anyObject(), anyInt()))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));

		assertThat(patientService.deletePatient(JWTTest.session, 1)).isEqualTo(HttpStatus.OK);
	}

	@Test
	void deletePatient_test_unknownPatient() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msPatientAdminGetPatient(anyObject(), anyInt())).thenReturn(null);
		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> patientService.deletePatient(JWTTest.session, 1));
	}

	@Test
	void deletePatient_test_idNull() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		assertThatExceptionOfType(EmptyDataException.class)
				.isThrownBy(() -> patientService.deletePatient(JWTTest.session, null));
	}

	@Test
	void deletePatient_test_idZero() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		assertThatExceptionOfType(EmptyDataException.class)
				.isThrownBy(() -> patientService.deletePatient(JWTTest.session, 0));
	}

	@Test
	void deletePatient_test_notLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> patientService.deletePatient(JWTTest.session, 0));
	}
}