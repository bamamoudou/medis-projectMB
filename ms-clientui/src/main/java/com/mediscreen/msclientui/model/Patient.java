package com.mediscreen.msclientui.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Patient {
	private Integer id;

	@Length(max = 60, message = "Max length : 60")
	private String firstname;

	@Length(max = 60, message = "Max length : 60")
	private String lastname;

	@Length(max = 1, message = "Max length : 1")
	private String sexe;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;

	@Length(max = 255, message = "Max length : 255")
	private String address;

	@Length(max = 100, message = "Max length : 100")
	private String email;

	@Length(max = 50, message = "Max length : 50")
	private String phone;

	private String country;

	private List<MedicalRecord> medicalRecordList;

	private MedicalReport medicalReport;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param firstname
	 * @param lastname
	 * @param sexe
	 * @param birthday
	 * @param address
	 * @param email
	 * @param phone
	 * @param country
	 * @param medicalRecordList
	 */
	public Patient(Integer id, String firstname, String lastname, String sexe, LocalDate birthday, String address,
			String email, String phone, String country, List<MedicalRecord> medicalRecordList) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.sexe = sexe;
		this.birthday = birthday;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.country = country;
		this.medicalRecordList = medicalRecordList;
	}

	public Patient() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<MedicalRecord> getMedicalRecordList() {
		return medicalRecordList;
	}

	public void setMedicalRecordList(List<MedicalRecord> medicalRecordList) {
		if (medicalRecordList == null) {
			this.medicalRecordList = new ArrayList<>();
		} else {
			this.medicalRecordList = medicalRecordList;
		}
	}

	public MedicalReport getMedicalReport() {
		return medicalReport;
	}

	public void setMedicalReport(MedicalReport medicalReport) {
		this.medicalReport = medicalReport;
	}

	public Integer getAge() {
		if (this.getBirthday() != null) {
			return Period.between(this.getBirthday(), LocalDate.now()).getYears();
		} else {
			return 0;
		}
	}
}