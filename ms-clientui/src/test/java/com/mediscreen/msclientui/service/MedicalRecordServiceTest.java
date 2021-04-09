package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.JWTTest;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.model.MedicalRecord;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import com.mediscreen.msclientui.service.MedicalRecordService;
import com.mediscreen.msclientui.service.SecurityService;
import com.mediscreen.msclientui.serviceImpl.MedicalRecordServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
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
	private MedicalRecord medicalRecord;
	private List<MedicalRecord> medicalRecordList;

	@Mock
	private static SecurityService securityService;

	@Mock
	private static MSZuulProxy msZuulProxy;

	@BeforeEach
	void init_test() {
		medicalRecord = new MedicalRecord("5ffb399c57b9e94b6053c8ac", 1, "doctor", LocalDateTime.now(), null,
				"\ncontent\n", true);

		medicalRecordList = new ArrayList<>();
		medicalRecordList.add(medicalRecord);

		this.medicalRecordService = new MedicalRecordServiceImpl(msZuulProxy, securityService);
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void getAllPatientMedicalRecords_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msMedicalRecordGetAllPatientMedicalRecords(null, 0)).thenReturn(medicalRecordList);

		List<MedicalRecord> result = medicalRecordService.getAllPatientMedicalRecords(JWTTest.session, 0);

		assertThat(result).isNotNull();
		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getContent()).isEqualTo("</br>content</br>");
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void getAllPatientMedicalRecords_test_nullList() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msMedicalRecordGetAllPatientMedicalRecords(null, 0)).thenReturn(null);
		assertThat(medicalRecordService.getAllPatientMedicalRecords(JWTTest.session, 0)).isNull();
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void getAllPatientMedicalRecords_test_emptyList() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msMedicalRecordGetAllPatientMedicalRecords(null, 0)).thenReturn(new ArrayList<>());
		assertThat(medicalRecordService.getAllPatientMedicalRecords(JWTTest.session, 0)).isNotNull();
		assertThat(medicalRecordService.getAllPatientMedicalRecords(JWTTest.session, 0).size()).isEqualTo(0);
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void getAllPatientMedicalRecords_test_isNotLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> medicalRecordService.getAllPatientMedicalRecords(JWTTest.session, 0));
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void createMedicalRecord_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msMedicalRecordsCreateMedicalRecord(null, medicalRecord))
				.thenReturn(new ResponseEntity<>(medicalRecord, HttpStatus.OK));
		assertThat(medicalRecordService.createMedicalRecord(JWTTest.session, medicalRecord)).isEqualTo(medicalRecord);
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void createMedicalRecord_test_nullResult() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msMedicalRecordsCreateMedicalRecord(null, medicalRecord))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));
		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> medicalRecordService.createMedicalRecord(JWTTest.session, medicalRecord));
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void createMedicalRecord_test_isNotLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> medicalRecordService.createMedicalRecord(JWTTest.session, medicalRecord));
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void updateMedicalRecord_test() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msMedicalRecordsUpdateMedicalRecord(anyObject(), any(MedicalRecord.class)))
				.thenReturn(new ResponseEntity<>(medicalRecord, HttpStatus.OK));
		assertThat(medicalRecordService.updateMedicalRecord(JWTTest.session, "_id", 0, true)).isEqualTo(medicalRecord);
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void updateMedicalRecord_test_nullResult() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(true);
		when(msZuulProxy.msMedicalRecordsUpdateMedicalRecord(anyObject(), any(MedicalRecord.class)))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));
		assertThatExceptionOfType(NotFoundException.class)
				.isThrownBy(() -> medicalRecordService.updateMedicalRecord(JWTTest.session, "_id", 0, true));
	}

	@Tag("MedicalRecordServiceTest")
	@Test
	void updateMedicalRecord_test_isNotLog() {
		when(securityService.isLog(any(HttpSession.class))).thenReturn(false);
		assertThatExceptionOfType(NotAllowedException.class)
				.isThrownBy(() -> medicalRecordService.updateMedicalRecord(JWTTest.session, "_id", 0, true));
	}
}