package com.mediscreen.msmedicalreport.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MedicalRecord {
	private String id;

	@NotNull(message = "Patient id is mandatory")
	private Integer patientId;
	private String doctorName;
	private LocalDateTime createDate;
	private LocalDateTime lastChangeDate;
	private String content;

	@NotNull(message = "Active state is mandatory")
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
	public MedicalRecord(String id, Integer patientId, String doctorName, LocalDateTime createDate, String content,
			boolean isActive) {
		this.id = id;
		this.patientId = patientId;
		this.doctorName = doctorName;
		this.createDate = createDate;
		this.content = content;
		this.isActive = isActive;
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param patientId
	 * @param doctorName
	 * @param createDate
	 * @param lastChangeDate
	 * @param content
	 * @param isActive
	 */
	public MedicalRecord(String id, Integer patientId, String doctorName, LocalDateTime createDate,
			LocalDateTime lastChangeDate, String content, boolean isActive) {
		this.id = id;
		this.patientId = patientId;
		this.doctorName = doctorName;
		this.createDate = createDate;
		this.lastChangeDate = lastChangeDate;
		this.content = content;
		this.isActive = isActive;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getLastChangeDate() {
		return lastChangeDate;
	}

	public void setLastChangeDate(LocalDateTime lastChangeDate) {
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