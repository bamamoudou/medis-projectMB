package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.MedicalRecord;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;

public class MedicalRecordService implements MedicalRecordServiceInterface {
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
     * Constructor
     * @param securityService
     */
    public MedicalRecordService(SecurityServiceInterface securityService) {
        this.securityService = securityService;
    }

    /**
     * Constructor
     */
    public MedicalRecordService() {
    }

    /**
     * Constructor
     * @param msZuulProxy
     * @param securityService
     */
    public MedicalRecordService(MSZuulProxy msZuulProxy, SecurityServiceInterface securityService) {
        this.msZuulProxy = msZuulProxy;
        this.securityService = securityService;
    }

    /**
     * @see MedicalRecordServiceInterface {@link #getAllPatientMedicalRecords(HttpSession, int)}
     */
    @Override
    public List<MedicalRecord> getAllPatientMedicalRecords(HttpSession session, int patientId) {
        if(!securityService.isLog(session)) {
            logger.error("Permission denied : " + session.getAttribute("token"));
            throw new NotAllowedException("Permission denied");
        }
        List<MedicalRecord> medicalRecordList = msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords((String) session.getAttribute("token"), patientId);
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
     * @see MedicalRecordServiceInterface {@link #createMedicalRecord(HttpSession, MedicalRecord)}
     */
    @Override
    public MedicalRecord createMedicalRecord(HttpSession session, MedicalRecord medicalRecord) {
        if(!securityService.isLog(session)) {
            logger.error("Permission denied : " + session.getAttribute("token"));
            throw new NotAllowedException("Permission denied");
        }
        MedicalRecord newMedicalRecord = msZuulProxy.msMedicalRecords_createMedicalRecord((String) session.getAttribute("token"), medicalRecord).getBody();
        if (newMedicalRecord == null) {
            logger.error("Medical record can't be create");
            throw new NotFoundException("Medical record can't be create");
        }
        return newMedicalRecord;
    }

    /**
     * @see MedicalRecordServiceInterface {@link #updateMedicalRecord(HttpSession, String, Integer, Boolean)}
     */
    @Override
    public MedicalRecord updateMedicalRecord(HttpSession session, String id, Integer patientId, Boolean active) {
        if(!securityService.isLog(session)) {
            logger.error("Permission denied : " + session.getAttribute("token"));
            throw new NotAllowedException("Permission denied");
        }
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(id);
        medicalRecord.setPatientId(patientId);
        medicalRecord.setActive(active);
        MedicalRecord updateMedicalRecord = msZuulProxy.msMedicalRecords_updateMedicalRecord((String) session.getAttribute("token"), medicalRecord).getBody();
        if (updateMedicalRecord == null) {
            logger.error("Medical record can't be update");
            throw new NotFoundException("Medical record can't be update");
        }
        return updateMedicalRecord;
    }
}
