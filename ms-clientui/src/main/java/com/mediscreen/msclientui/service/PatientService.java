package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msclientui.interfaces.MedicalReportServiceInterface;
import com.mediscreen.msclientui.interfaces.PatientServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.Patient;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

public class PatientService implements PatientServiceInterface {
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
     * @see PatientServiceInterface {@link #getAllPatients(HttpSession)}
     */
    @Override
    public List<Patient> getAllPatients(HttpSession session) {
        return msZuulProxy.msPatientAdmin_getAllPatients((String) session.getAttribute("token"));
    }

    /**
     * @see PatientServiceInterface {@link #searchPatient(HttpSession, String)}
     */
    @Override
    public List<Patient> searchPatient(HttpSession session, String search) {
        return msZuulProxy.searchPatients((String) session.getAttribute("token"), search);
    }

    /**
     * @see PatientServiceInterface {@link #getPatient(HttpSession, int)}
     */
    @Override
    public Patient getPatient(HttpSession session, int id) {
        Patient patient = msZuulProxy.msPatientAdmin_getPatient((String) session.getAttribute("token"), id);
        patient.setMedicalRecordList(medicalRecordService.getAllPatientMedicalRecords(session, patient.getId()));
        patient.setMedicalReport(medicalReportService.getMedicalReport(session, patient.getId()));
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
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        if(this.getPatient(session, patient.getId()) == null) throw new NotFoundException("Patient id unknown");
        Patient updatePatient = msZuulProxy.msPatientAdmin_updatePatient((String) session.getAttribute("token"), patient).getBody();
        if(updatePatient == null) throw new NotFoundException("Patient can't be updated");
    }

    /**
     * @see PatientServiceInterface {@link #deletePatient(HttpSession, int)}
     */
    @Override
    public HttpStatus deletePatient(HttpSession session, int id) {
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        if(this.getPatient(session, id) == null) throw new NotFoundException("Patient id unknown");
        return msZuulProxy.msPatientAdmin_deletePatient((String) session.getAttribute("token"), id).getStatusCode();
    }
}
