package com.mediscreen.msclientui.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.mediscreen.msclientui.exception.EmptyDataException;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.model.Patient;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import com.mediscreen.msclientui.service.MedicalRecordService;
import com.mediscreen.msclientui.service.MedicalReportService;
import com.mediscreen.msclientui.service.PatientService;
import com.mediscreen.msclientui.service.SecurityService;

public class PatientServiceImpl implements PatientService {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	/**
	 * Security service
	 */
	private SecurityService securityService;

	/**
	 * MedicalRecordService
	 */
	private MedicalRecordService medicalRecordService;
	/**
	 * MedicalReportService
	 */
	private MedicalReportService medicalReportService;

	/**
	 * Constructor
	 * 
	 * @param securityService
	 * @param medicalRecordService
	 * @param medicalReportService
	 */
	public PatientServiceImpl(SecurityService securityService, MedicalRecordService medicalRecordService,
			MedicalReportService medicalReportService) {
		this.securityService = securityService;
		this.medicalRecordService = medicalRecordService;
		this.medicalReportService = medicalReportService;
	}

	/**
	 * Constructor
	 */
	public PatientServiceImpl() {
	}

	/**
	 * Constructor
	 * 
	 * @param msZuulProxy
	 * @param securityService
	 * @param medicalRecordService
	 * @param medicalReportService
	 */
	public PatientServiceImpl(MSZuulProxy msZuulProxy, SecurityService securityService,
			MedicalRecordService medicalRecordService, MedicalReportService medicalReportService) {
		this.msZuulProxy = msZuulProxy;
		this.securityService = securityService;
		this.medicalRecordService = medicalRecordService;
		this.medicalReportService = medicalReportService;
	}

	/**
	 * @see PatientService {@link #getAllPatients(HttpSession)}
	 */
	@Override
	public List<Patient> getAllPatients(HttpSession session) {
		if (!securityService.isLog(session))
			throw new NotAllowedException("Permission denied");
		return msZuulProxy.msPatientAdminGetAllPatients((String) session.getAttribute("token"));
	}

	/**
	 * @see PatientService {@link #searchPatient(HttpSession, String)}
	 */
	@Override
	public List<Patient> searchPatient(HttpSession session, String search) {
		if (!securityService.isLog(session))
			throw new NotAllowedException("Permission denied");
		return msZuulProxy.searchPatients((String) session.getAttribute("token"), search);
	}

	/**
	 * @see PatientService {@link #getPatient(HttpSession, Integer)}
	 */
	@Override
	public Patient getPatient(HttpSession session, Integer id) {
		if (!securityService.isLog(session))
			throw new NotAllowedException("Permission denied");
		if (id == null || id < 1)
			throw new EmptyDataException("Id is mandatory");
		Patient patient = msZuulProxy.msPatientAdminGetPatient((String) session.getAttribute("token"), id);
		if (patient != null) {
			patient.setMedicalRecordList(medicalRecordService.getAllPatientMedicalRecords(session, patient.getId()));
			patient.setMedicalReport(medicalReportService.getMedicalReport(session, patient.getId()));
		}
		return patient;
	}

	/**
	 * @see PatientService {@link #createPatient(HttpSession, Patient)}
	 */
	@Override
	public Patient createPatient(HttpSession session, Patient patient) {
		if (!securityService.isLog(session))
			throw new NotAllowedException("Permission denied");
		Patient newPatient = msZuulProxy.msPatientAdminCreatePatient((String) session.getAttribute("token"), patient)
				.getBody();
		if (newPatient == null)
			throw new NotFoundException("Patient can't be create");
		return newPatient;
	}

	/**
	 * @see PatientService {@link #updatePatient(HttpSession, Patient)}
	 */
	@Override
	public void updatePatient(HttpSession session, Patient patient) {
		if (!securityService.isLog(session)) {
			logger.error("Permission denied");
			throw new NotAllowedException("Permission denied");
		}
		if (this.getPatient(session, patient.getId()) == null) {
			logger.error("Patient id unknown : " + patient.getId());
			throw new NotFoundException("Patient id unknown");
		}
		Patient updatePatient = msZuulProxy.msPatientAdminUpdatePatient((String) session.getAttribute("token"), patient)
				.getBody();
		if (updatePatient == null) {
			logger.error("Patient can't be updated");
			throw new NotFoundException("Patient can't be updated");
		}
	}

	/**
	 * @see PatientService {@link #deletePatient(HttpSession, Integer)}
	 */
	@Override
	public HttpStatus deletePatient(HttpSession session, Integer id) {
		if (!securityService.isLog(session)) {
			logger.error("Permission denied");
			throw new NotAllowedException("Permission denied");
		}

		if (id == null || id < 1)
			throw new EmptyDataException("Id is mandatory");

		if (this.getPatient(session, id) == null) {
			logger.error("Patient id unknown : " + id);
			throw new NotFoundException("Patient id unknown");
		}
		return msZuulProxy.msPatientAdminDeletePatient((String) session.getAttribute("token"), id).getStatusCode();
	}
}