package com.mediscreen.msmedicalrecord.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MedicalRecordTest {
	private MedicalRecord medicalRecord;
	private LocalDateTime createDate = LocalDateTime.now();
	private LocalDateTime lastChangeDate = LocalDateTime.now();

	@BeforeEach
	void initTest() {
		medicalRecord = new MedicalRecord("5ffb399c57b9e94b6053c8ac", 1, "doctor", createDate, lastChangeDate,
				"content", true);
	}

	@Tag("MedicalRecordTest")
	@Test
	void get_test() {
		assertThat(medicalRecord.getId()).isEqualTo("5ffb399c57b9e94b6053c8ac");
		assertThat(medicalRecord.getPatientId()).isEqualTo(1);
		assertThat(medicalRecord.getDoctorName()).isEqualTo("doctor");
		assertThat(medicalRecord.getCreateDate()).isEqualTo(createDate);
		assertThat(medicalRecord.getLastChangeDate()).isEqualTo(lastChangeDate);
		assertThat(medicalRecord.getContent()).isEqualTo("content");
		assertThat(medicalRecord.isActive()).isEqualTo(true);
	}

	@Tag("MedicalRecordTest")
	@Test
	void set_test() {
		assertThat(medicalRecord.getId()).isEqualTo("5ffb399c57b9e94b6053c8ac");
		assertThat(medicalRecord.getPatientId()).isEqualTo(1);
		assertThat(medicalRecord.getDoctorName()).isEqualTo("doctor");
		assertThat(medicalRecord.getCreateDate()).isEqualTo(createDate);
		assertThat(medicalRecord.getLastChangeDate()).isEqualTo(lastChangeDate);
		assertThat(medicalRecord.getContent()).isEqualTo("content");
		assertThat(medicalRecord.isActive()).isEqualTo(true);

		LocalDateTime newCreateDate = LocalDateTime.now();
		LocalDateTime newLastChangeDate = LocalDateTime.now();

		medicalRecord.setId("newId");
		medicalRecord.setPatientId(2);
		medicalRecord.setDoctorName("newDoctor");
		medicalRecord.setCreateDate(newCreateDate);
		medicalRecord.setLastChangeDate(newLastChangeDate);
		medicalRecord.setContent("newContent");
		medicalRecord.setActive(false);

		assertThat(medicalRecord.getId()).isEqualTo("newId");
		assertThat(medicalRecord.getPatientId()).isEqualTo(2);
		assertThat(medicalRecord.getDoctorName()).isEqualTo("newDoctor");
		assertThat(medicalRecord.getCreateDate()).isEqualTo(newCreateDate);
		assertThat(medicalRecord.getLastChangeDate()).isEqualTo(newLastChangeDate);
		assertThat(medicalRecord.getContent()).isEqualTo("newContent");
		assertThat(medicalRecord.isActive()).isEqualTo(false);
	}
}