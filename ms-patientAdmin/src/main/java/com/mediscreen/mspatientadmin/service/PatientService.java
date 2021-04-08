package com.mediscreen.mspatientadmin.service;

import com.mediscreen.mspatientadmin.exception.NotFoundException;
import com.mediscreen.mspatientadmin.interfaces.PatientDaoInterface;
import com.mediscreen.mspatientadmin.interfaces.PatientServiceInterface;
import com.mediscreen.mspatientadmin.models.Patient;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PatientService implements PatientServiceInterface {
    /**
     * Logger log4j2
     */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Patient DAO
     */
    private final PatientDaoInterface patientDao;

    /**
     * Constructor
     * @param patientDao
     */
    public PatientService(PatientDaoInterface patientDao) {
        this.patientDao = patientDao;
    }

    /**
     * @see PatientServiceInterface {@link #getPatientById(Integer)}
     */
    @Override
    public Patient getPatientById(Integer id) {
        Patient patient = null;
        if (id != null && id > 0) {
            patient = patientDao.getPatientById(id);
        }
        return patient;
    }

    @Override
    public List<Patient> getAllPatient() {
        return patientDao.getAllPatient();
    }

    /**
     * @see PatientServiceInterface {@link #updatePatient(Patient)}
     */
    @Override
    public Patient updatePatient(Patient patient) {
        if (patient.getId() != null && patient.getId() > 0) {
            return patientDao.updatePatient(patient);
        }
        return null;
    }

    /**
     * @see PatientServiceInterface {@link #createPatient(Patient)}
     */
    @Override
    public Patient createPatient(Patient patient) {
        return patientDao.createPatient(patient);
    }

    /**
     * @see PatientServiceInterface {@link #deletePatientById(Integer)}
     */
    @Override
    public boolean deletePatientById(Integer id) {
        if (id != null && id > 0) {
            Patient patient = patientDao.getPatientById(id);
            if (patient != null) {
                patientDao.deletePatientById(id);
                return true;
            } else {
                logger.info("Unknown patient with id : " + id);
                throw new NotFoundException("Unknown patient with id : " + id);
            }
        }
        return false;
    }

    /**
     * @see PatientServiceInterface {@link #searchPatient(String)}
     */
    @Override
    public List<Patient> searchPatient(String search){
        if (!StringUtils.isBlank(search)) {
            return patientDao.searchPatients(search);
        } else {
            return null;
        }
    }
}