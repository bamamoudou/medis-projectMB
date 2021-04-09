package com.mediscreen.msmedicalreport.serviceImpl;

import com.mediscreen.msmedicalreport.exception.EmptyDataException;
import com.mediscreen.msmedicalreport.exception.NotFoundException;
import com.mediscreen.msmedicalreport.model.MedicalRecord;
import com.mediscreen.msmedicalreport.model.MedicalReport;
import com.mediscreen.msmedicalreport.model.Patient;
import com.mediscreen.msmedicalreport.proxy.MSZuulProxy;
import com.mediscreen.msmedicalreport.service.MedicalReportService;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicalReportServiceImpl implements MedicalReportService {
	/**
	 * Logger4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * ems-zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	/**
	 * Constructor
	 * 
	 * @param msZuulProxy
	 */
	public MedicalReportServiceImpl(MSZuulProxy msZuulProxy) {
		this.msZuulProxy = msZuulProxy;
	}

	/**
	 * Constructor
	 */
	public MedicalReportServiceImpl() {
	}

	/**
	 * Get patient profile by id
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	private Patient getPatientProfileById(String token, Integer id) {
		Patient patient = null;
		if (id != null && id > 0) {
			patient = msZuulProxy.msPatientAdminGetPatient(token, id);
			if (patient == null) {
				logger.error("Unknown patient with id : " + id);
				throw new NotFoundException("Unknown patient with id : " + id);
			}
		}
		return patient;
	}

	/**
	 * Get patient profile by name
	 * 
	 * @param token
	 * @param name
	 * @return
	 */
	private Patient getPatientProfileByName(String token, String name) {
		Patient patient = null;
		if (!StringUtils.isBlank(name)) {
			List<Patient> patientList = msZuulProxy.msPatientAdminGetAllPatients(token);
			if ((patientList != null) && (patientList.size() > 0)) {
				for (Patient p : patientList) {
					if (p.getLastname().equalsIgnoreCase(name))
						patient = p;
				}
			}
			if (patient == null) {
				logger.error("Unknown patient with name : " + name);
				throw new NotFoundException("Unknown patient with name : " + name);
			}
		}
		return patient;
	}

	/**
	 * Get all patient medical records
	 * 
	 * @param token
	 * @param id
	 * @return
	 */
	private List<MedicalRecord> getMedicalRecordList(String token, Integer id) {
		List<MedicalRecord> medicalRecordList = null;
		if (id != null && id > 0) {
			medicalRecordList = msZuulProxy.msMedicalRecordGetAllPatientMedicalRecords(token, id);
		}
		return medicalRecordList;
	}

	/**
	 * Count médical trigger from medical records list
	 * 
	 * @param medicalRecordList
	 * @return
	 */
	public Integer countTrigger(List<MedicalRecord> medicalRecordList) {
		Integer triggerCount = 0;
		List<String> triggerList = new ArrayList<>();
		triggerList.add("microalbumine");
		triggerList.add("taille");
		triggerList.add("poids");
		triggerList.add("fumeur");
		triggerList.add("anormal");
		triggerList.add("cholest[eé]rol");
		triggerList.add("vertige");
		triggerList.add("rechute");
		triggerList.add("reaction");
		triggerList.add("anticorps");

		if (medicalRecordList != null && medicalRecordList.size() > 0) {
			for (MedicalRecord medicalRecord : medicalRecordList) {
				if (medicalRecord.isActive() && !StringUtils.isBlank(medicalRecord.getContent())) {
					if (medicalRecord.getContent().matches("(?i:.*h[eé]moglobine A1C.*)"))
						triggerCount++;
					String contentSplit[] = medicalRecord.getContent().split(" ");
					for (String str : contentSplit) {
						for (String trigger : triggerList) {
							if (str.matches("(?i:.*" + trigger + ".*)"))
								triggerCount++;
						}
					}
				}
			}
		}
		return triggerCount;
	}

	/**
	 * @see MedicalReportService {@link #generateReport(String, Integer, String)}
	 */
	@Override
	public MedicalReport generateReport(String token, Integer id, String name) {
		if (StringUtils.isBlank(token) || (name == null && (id == null || id < 1))
				|| (id == null && StringUtils.isBlank(name))) {
			logger.error("MedicalReportService : id or name and token are mandatory");
			throw new EmptyDataException("MedicalReportService : id or name and token are mandatory");
		}

		MedicalReport medicalReport = null;
		Patient patient = (id != null) ? this.getPatientProfileById(token, id)
				: this.getPatientProfileByName(token, name);
		if (patient != null) {
			Integer triggerCount = this.countTrigger(this.getMedicalRecordList(token, id));

			medicalReport = new MedicalReport();
			medicalReport.setPatientId(patient.getId());
			medicalReport.setDate(LocalDateTime.now());
			medicalReport.setResult(null);

			// BORDERLINE
			if (triggerCount >= 2 && patient.getAge() >= 30) {
				medicalReport.setResult(MedicalReport.ReportResult.BORDERLINE);
			}

			// IN DANGER
			if ((triggerCount >= 3 && patient.getAge() < 30 && patient.getSexe().equals("M"))
					|| (triggerCount >= 4 && patient.getAge() < 30 && patient.getSexe().equals("F"))
					|| (triggerCount >= 6 && patient.getAge() >= 30)) {
				medicalReport.setResult(MedicalReport.ReportResult.IN_DANGER);
			}

			// EARLY ONSET
			if ((triggerCount >= 5 && patient.getAge() < 30 && patient.getSexe().equals("M"))
					|| (triggerCount >= 7 && patient.getAge() < 30 && patient.getSexe().equals("F"))
					|| (triggerCount >= 8 && patient.getAge() >= 30)) {
				medicalReport.setResult(MedicalReport.ReportResult.EARLY_ONSET);
			}

			// NONE
			if (medicalReport.getResult() == null) {
				medicalReport.setResult(MedicalReport.ReportResult.NONE);
			}

			StringBuffer reportContent = new StringBuffer();
			reportContent.append("Patient (id : ").append(patient.getId()).append(") : ");
			if (patient.getFirstname() != null) {
				reportContent.append(patient.getFirstname()).append(" ");
			}
			if (patient.getLastname() != null) {
				reportContent.append(patient.getLastname());
			}
			reportContent.append(" (").append(patient.getAge()).append("y)");

			medicalReport.setContent(reportContent.toString());
		}
		return medicalReport;
	}
}