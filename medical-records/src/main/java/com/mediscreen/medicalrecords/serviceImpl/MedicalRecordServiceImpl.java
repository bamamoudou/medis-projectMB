package com.mediscreen.medicalrecords.serviceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mediscreen.medicalrecords.dao.MedicalRecordDao;
import com.mediscreen.medicalrecords.model.MedicalRecord;
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
	 * @see MedicalRecordServiceInterface {@link #getPatientMedicalRecords(Integer)}
	 */
	@Override
	public List<MedicalRecord> getPatientMedicalRecords(Integer id) {
		List<MedicalRecord> medicalRecordList = null;
		if (id != null && id > 0) {
			medicalRecordList = medicalRecordDao.getAllPatientMedicalRecords(id);
		}
		return medicalRecordList;
	}

	/**
	 * @see MedicalRecordServiceInterface
	 *      {@link #createMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord.getId() != null && medicalRecord.getId() > 0 && medicalRecord.getPatientId() != null
				&& medicalRecord.getPatientId() > 0) {
			return medicalRecordDao.createMedicalRecord(medicalRecord);
		}
		return null;
	}

	/**
	 * @see MedicalRecordServiceInterface
	 *      {@link #updateMedicalRecord(MedicalRecord)}
	 */
	@Override
	public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
		if (medicalRecord.getId() != null && medicalRecord.getId() > 0 && medicalRecord.getPatientId() != null
				&& medicalRecord.getPatientId() > 0) {
			return medicalRecordDao.updateMedicalRecord(medicalRecord);
		}
		return null;
	}
}