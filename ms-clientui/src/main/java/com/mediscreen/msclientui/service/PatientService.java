package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.exception.EmptyDataException;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msclientui.interfaces.MedicalReportServiceInterface;
import com.mediscreen.msclientui.interfaces.PatientServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.Patient;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

public class PatientService implements PatientServiceInterface {
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
    private SecurityServiceInterface securityService;

    /**
     * MedicalRecordService
     */
    private MedicalRecordServiceInterface medicalRecordService;
    /**
     * MedicalReportService
     */
    private MedicalReportServiceInterface medicalReportService;

    /**
     * Constructor
     * @param securityService
     * @param medicalRecordService
     * @param medicalReportService
     */
    public PatientService(SecurityServiceInterface securityService, MedicalRecordServiceInterface medicalRecordService, MedicalReportServiceInterface medicalReportService) {
        this.securityService = securityService;
        this.medicalRecordService = medicalRecordService;
        this.medicalReportService = medicalReportService;
    }

    /**
     * Constructor
     */
    public PatientService() {
    }

    /**
     * Constructor
     * @param msZuulProxy
     * @param securityService
     * @param medicalRecordService
     * @param medicalReportService
     */
    public PatientService(MSZuulProxy msZuulProxy, SecurityServiceInterface securityService, MedicalRecordServiceInterface medicalRecordService, MedicalReportServiceInterface medicalReportService) {
        this.msZuulProxy = msZuulProxy;
        this.securityService = securityService;
        this.medicalRecordService = medicalRecordService;
        this.medicalReportService = medicalReportService;
    }

    /**
     * @see PatientServiceInterface {@link #getAllPatients(HttpSession)}
     */
    @Override
    public List<Patient> getAllPatients(HttpSession session) {
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        return msZuulProxy.msPatientAdmin_getAllPatients((String) session.getAttribute("token"));
    }

    /**
     * @see PatientServiceInterface {@link #searchPatient(HttpSession, String)}
     */
    @Override
    public List<Patient> searchPatient(HttpSession session, String search) {
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        return msZuulProxy.searchPatients((String) session.getAttribute("token"), search);
    }

    /**
     * @see PatientServiceInterface {@link #getPatient(HttpSession, Integer)}
     */
    @Override
    public Patient getPatient(HttpSession session, Integer id) {
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        if(id == null || id < 1) throw new EmptyDataException("Id is mandatory");
        Patient patient = msZuulProxy.msPatientAdmin_getPatient((String) session.getAttribute("token"), id);
        if(patient != null) {
            patient.setMedicalRecordList(medicalRecordService.getAllPatientMedicalRecords(session, patient.getId()));
            patient.setMedicalReport(medicalReportService.getMedicalReport(session, patient.getId()));
        }
        return patient;
    }

    /**
     * @see PatientServiceInterface {@link #createPatient(HttpSession, Patient)}
     */
    @Override
    public Patient createPatient(HttpSession session, Patient patient) {
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        Patient newPatient = msZuulProxy.msPatientAdmin_createPatient((String) session.getAttribute("token"), patient).getBody();
        if (newPatient == null) throw new NotFoundException("Patient can't be create");
        return newPatient;
    }

    /**
     * @see PatientServiceInterface {@link #updatePatient(HttpSession, Patient)}
     */
    @Override
    public void updatePatient(HttpSession session, Patient patient) {
        if(!securityService.isLog(session)) {
            logger.error("Permission denied");
            throw new NotAllowedException("Permission denied");
        }
        if(this.getPatient(session, patient.getId()) == null) {
            logger.error("Patient id unknown : " + patient.getId());
            throw new NotFoundException("Patient id unknown");
        }
        Patient updatePatient = msZuulProxy.msPatientAdmin_updatePatient((String) session.getAttribute("token"), patient).getBody();
        if(updatePatient == null) {
            logger.error("Patient can't be updated");
            throw new NotFoundException("Patient can't be updated");
        }
    }

    /**
     * @see PatientServiceInterface {@link #deletePatient(HttpSession, Integer)}
     */
    @Override
    public HttpStatus deletePatient(HttpSession session, Integer id) {
        if(!securityService.isLog(session)) {
            logger.error("Permission denied");
            throw new NotAllowedException("Permission denied");
        }

        if(id == null || id < 1) throw new EmptyDataException("Id is mandatory");

        if(this.getPatient(session, id) == null) {
            logger.error("Patient id unknown : " + id);
            throw new NotFoundException("Patient id unknown");
        }
        return msZuulProxy.msPatientAdmin_deletePatient((String) session.getAttribute("token"), id).getStatusCode();
    }
}
