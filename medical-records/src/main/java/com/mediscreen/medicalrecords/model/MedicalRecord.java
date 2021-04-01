package com.mediscreen.medicalrecords.model;

import java.time.LocalDate;

public class MedicalRecord {
	private Integer id;
	private Integer patientId;
	private String doctorName;
	private LocalDate createDate;
	private LocalDate lastChangeDate;
	private String content;
	private boolean isActive;

	/**
	 * Constructor
	 */
	public MedicalRecord() {
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param patientId
	 * @param doctorName
	 * @param createDate
	 * @param content
	 * @param isActive
	 */
	public MedicalRecord(Integer id, Integer patientId, String doctorName, LocalDate createDate, String content,
			boolean isActive) {
		this.id = id;
		this.patientId = patientId;
		this.doctorName = doctorName;
		this.createDate = createDate;
		this.content = content;
		this.isActive = isActive;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public LocalDate getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}

	public LocalDate getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(LocalDate lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}
}