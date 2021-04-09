package com.mediscreen.msclientui.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.model.MedicalRecord;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import com.mediscreen.msclientui.service.MedicalRecordService;
import com.mediscreen.msclientui.service.SecurityService;

public class MedicalRecordServiceImpl implements MedicalRecordService {
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
	 * Constructor
	 * 
	 * @param securityService
	 */
	public MedicalRecordServiceImpl(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Constructor
	 */
	public MedicalRecordServiceImpl() {
	}

	/**
	 * Constructor
	 * 
	 * @param msZuulProxy
	 * @param securityService
	 */
	public MedicalRecordServiceImpl(MSZuulProxy msZuulProxy, SecurityService securityService) {
		this.msZuulProxy = msZuulProxy;
		this.securityService = securityService;
	}

	/**
	 * @see MedicalRecordService
	 *      {@link #getAllPatientMedicalRecords(HttpSession, int)}
	 */
	@Override
	public List<MedicalRecord> getAllPatientMedicalRecords(HttpSession session, int patientId) {
		if (!securityService.isLog(session)) {
			logger.error("Permission denied : " + session.getAttribute("token"));
			throw new NotAllowedException("Permission denied");
		}
		List<MedicalRecord> medicalRecordList = msZuulProxy
				.msMedicalRecordGetAllPatientMedicalRecords((String) session.getAttribute("token"), patientId);
		if (medicalRecordList != null && medicalRecordList.size() > 0) {
			for (MedicalRecord medicalRecord : medicalRecordList) {
				if (!StringUtils.isBlank(medicalRecord.getContent())) {
					medicalRecord.setContent(StringUtils.replace(medicalRecord.getContent(), "\n", "</br>"));
				}
			}
		} else {
			logger.info("No medicalRecord for patient : " + patientId);
		}
		return medicalRecordList;
	}

	/**
	 * @see MedicalRecordService
	 *      {@link #createMedicalRecord(HttpSession, MedicalRecord)}
	 */
	@Override
	public MedicalRecord createMedicalRecord(HttpSession session, MedicalRecord medicalRecord) {
		if (!securityService.isLog(session)) {
			logger.error("Permission denied : " + session.getAttribute("token"));
			throw new NotAllowedException("Permission denied");
		}
		MedicalRecord newMedicalRecord = msZuulProxy
				.msMedicalRecordsCreateMedicalRecord((String) session.getAttribute("token"), medicalRecord).getBody();
		if (newMedicalRecord == null) {
			logger.error("Medical record can't be create");
			throw new NotFoundException("Medical record can't be create");
		}
		return newMedicalRecord;
	}

	/**
	 * @see MedicalRecordService
	 *      {@link #updateMedicalRecord(HttpSession, String, Integer, Boolean)}
	 */
	@Override
	public MedicalRecord updateMedicalRecord(HttpSession session, String id, Integer patientId, Boolean active) {
		if (!securityService.isLog(session)) {
			logger.error("Permission denied : " + session.getAttribute("token"));
			throw new NotAllowedException("Permission denied");
		}
		MedicalRecord medicalRecord = new MedicalRecord();
		medicalRecord.setId(id);
		medicalRecord.setPatientId(patientId);
		medicalRecord.setActive(active);
		MedicalRecord updateMedicalRecord = msZuulProxy
				.msMedicalRecordsUpdateMedicalRecord((String) session.getAttribute("token"), medicalRecord).getBody();
		if (updateMedicalRecord == null) {
			logger.error("Medical record can't be update");
			throw new NotFoundException("Medical record can't be update");
		}
		return updateMedicalRecord;
	}
}