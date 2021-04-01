package com.mediscreen.medicalrecords.serviceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mediscreen.medicalrecords.dao.MedicalRecordDao;
import com.mediscreen.medicalrecords.exception.NotFoundException;
import com.mediscreen.medicalrecords.model.MedicalRecord;
import com.mediscreen.medicalrecords.proxy.MSZuulProxy;
import com.mediscreen.medicalrecords.service.MedicalRecordService;

public class MedicalRecordServiceImpl implements MedicalRecordService {
	/**
	 * Logger log4j2
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * MedicalRecordDao
	 */
	private MedicalRecordDao medicalRecordDao;

	/**
	 * Constructor
	 * 
	 * @param medicalRecordDao
	 */
	public MedicalRecordServiceImpl(MedicalRecordDao medicalRecordDao) {
		this.medicalRecordDao = medicalRecordDao;
	}

	/**
	 * ems-zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	/**
	 * @see MedicalRecordServiceInterface
	 *      {@link #getPatientMedicalRecords(String, Integer)}
	 */
	@Override
	public List<MedicalRecord> getPatientMedicalRecords(String token, Integer id) {
		if (id != null && id > 0) {
			if (msZuulProxy.msPatient_getPatient(token, id) == null)
				throw new NotFoundException("Unknown patient with id : " + id);
			return medicalRecordDao.getAllPatientMedicalRecords(id);
		}
		return null;
	}

	/**
	 * @see MedicalRecordServiceInterface
	 *      {@link #createMedicalRecord(String, MedicalRecord)}
	 */
	@Override
	public MedicalRecord createMedicalRecord(String token, MedicalRecord medicalRecord) {
		if (medicalRecord.getId() != null && medicalRecord.getId() > 0 && medicalRecord.getPatientId() != null
				&& medicalRecord.getPatientId() > 0) {
			if (msZuulProxy.msPatient_getPatient(token, medicalRecord.getId()) == null)
				throw new NotFoundException("Unknown patient with id : " + medicalRecord.getId());
			return medicalRecordDao.createMedicalRecord(medicalRecord);
		}
		return null;
	}

	/**
	 * @see MedicalRecordServiceInterface
	 *      {@link #updateMedicalRecord(String, MedicalRecord)}
	 */
	@Override
	public MedicalRecord updateMedicalRecord(String token, MedicalRecord medicalRecord) {
		if (medicalRecord.getId() != null && medicalRecord.getId() > 0 && medicalRecord.getPatientId() != null
				&& medicalRecord.getPatientId() > 0) {
			if (msZuulProxy.msPatient_getPatient(token, medicalRecord.getId()) == null)
				throw new NotFoundException("Unknown patient with id : " + medicalRecord.getId());
			return medicalRecordDao.updateMedicalRecord(medicalRecord);
		}
		return null;
	}
}