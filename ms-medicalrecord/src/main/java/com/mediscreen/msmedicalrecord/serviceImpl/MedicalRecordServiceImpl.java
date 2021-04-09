package com.mediscreen.msmedicalrecord.serviceImpl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mediscreen.msmedicalrecord.dao.MedicalRecordDao;
import com.mediscreen.msmedicalrecord.exception.NotFoundException;
import com.mediscreen.msmedicalrecord.model.MedicalRecord;
import com.mediscreen.msmedicalrecord.proxy.MSZuulProxy;
import com.mediscreen.msmedicalrecord.service.MedicalRecordService;

public class MedicalRecordServiceImpl implements MedicalRecordService {
	/**
	 * Logger
	 */
	private final Logger logger = LogManager.getLogger(this.getClass());

	/**
	 * MedicalRecordDao
	 */
	private MedicalRecordDao medicalRecordDao;

	/**
	 * ems-zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	/**
	 * Constructor
	 * 
	 * @param medicalRecordDao
	 */
	public MedicalRecordServiceImpl(MedicalRecordDao medicalRecordDao) {
		this.medicalRecordDao = medicalRecordDao;
	}

	/**
	 * Constructor
	 * 
	 * @param medicalRecordDao
	 * @param msZuulProxy
	 */
	public MedicalRecordServiceImpl(MedicalRecordDao medicalRecordDao, MSZuulProxy msZuulProxy) {
		this.medicalRecordDao = medicalRecordDao;
		this.msZuulProxy = msZuulProxy;
	}

	/**
	 * @see MedicalRecordService {@link #getPatientMedicalRecords(String, Integer)}
	 */
	@Override
	public List<MedicalRecord> getPatientMedicalRecords(String token, Integer id) {
		if (id != null && id > 0) {
			if (msZuulProxy.msPatientAdminGetPatient(token, id) == null) {
				logger.error("Unknown patient with id : " + id);
				throw new NotFoundException("Unknown patient with id : " + id);
			}
			return medicalRecordDao.getAllPatientMedicalRecords(id);
		}
		return null;
	}

	/**
	 * @see MedicalRecordService {@link #createMedicalRecord(String, MedicalRecord)}
	 */
	@Override
	public MedicalRecord createMedicalRecord(String token, MedicalRecord medicalRecord) {
		if (medicalRecord.getPatientId() != null && medicalRecord.getPatientId() > 0) {
			if (msZuulProxy.msPatientAdminGetPatient(token, medicalRecord.getPatientId()) == null) {
				logger.error("Unknown patient with id : " + medicalRecord.getId());
				throw new NotFoundException("Unknown patient with id : " + medicalRecord.getId());
			}
			return medicalRecordDao.createMedicalRecord(medicalRecord);
		}
		return null;
	}

	/**
	 * @see MedicalRecordService {@link #updateMedicalRecord(String, MedicalRecord)}
	 */
	@Override
	public MedicalRecord updateMedicalRecord(String token, MedicalRecord medicalRecord) {
		if (!StringUtils.isBlank(medicalRecord.getId()) && medicalRecord.getPatientId() != null
				&& medicalRecord.getPatientId() > 0) {
			if (msZuulProxy.msPatientAdminGetPatient(token, medicalRecord.getPatientId()) == null) {
				logger.error("Unknown patient with id : " + medicalRecord.getId());
				throw new NotFoundException("Unknown patient with id : " + medicalRecord.getId());
			}
			return medicalRecordDao.updateMedicalRecord(medicalRecord);
		}
		return null;
	}
}