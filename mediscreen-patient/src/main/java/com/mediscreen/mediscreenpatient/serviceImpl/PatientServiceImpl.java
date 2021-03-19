package com.mediscreen.mediscreenpatient.serviceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mediscreen.mediscreenpatient.dao.PatientDAO;
import com.mediscreen.mediscreenpatient.exception.NotFoundException;
import com.mediscreen.mediscreenpatient.model.Patient;
import com.mediscreen.mediscreenpatient.service.PatientService;

public class PatientServiceImpl implements PatientService {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * Patient DAO
	 */
	private final PatientDAO patientDao;

	/**
     * Constructor
     * @param patientDao
     */
    public PatientServiceImpl(PatientDAO patientDao) {
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
}