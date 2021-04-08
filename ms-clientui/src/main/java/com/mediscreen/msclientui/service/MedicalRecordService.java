package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.MedicalRecord;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;

public class MedicalRecordService implements MedicalRecordServiceInterface {
    /**
     * Zuul proxy
     */
    @Autowired
    private MSZuulProxy msZuulProxy;

    /**
     * Security service
     */
    @Autowired
    private SecurityServiceInterface securityService;

    /**
     * @see MedicalRecordServiceInterface {@link #getAllPatientMedicalRecords(HttpSession, int)}
     */
    @Override
    public List<MedicalRecord> getAllPatientMedicalRecords(HttpSession session, int patientId) {
        return msZuulProxy.msMedicalRecord_getAllPatientMedicalRecords((String) session.getAttribute("token"), patientId);
    }

    /**
     * @see MedicalRecordServiceInterface {@link #createMedicalRecord(HttpSession, MedicalRecord)}
     */
    @Override
    public MedicalRecord createMedicalRecord(HttpSession session, MedicalRecord medicalRecord) {
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        MedicalRecord newMedicalRecord = msZuulProxy.msMedicalRecords_createMedicalRecord((String) session.getAttribute("token"), medicalRecord).getBody();
        if (newMedicalRecord == null) throw new NotFoundException("Medical record can't be create");
        return newMedicalRecord;
    }

    /**
     * @see MedicalRecordServiceInterface {@link #updateMedicalRecord(HttpSession, String, Integer, Boolean)}
     */
    @Override
    public MedicalRecord updateMedicalRecord(HttpSession session, String id, Integer patientId, Boolean active) {
        if(!securityService.isLog(session)) throw new NotAllowedException("Permission denied");
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setId(id);
        medicalRecord.setPatientId(patientId);
        medicalRecord.setActive(active);
        MedicalRecord updateMedicalRecord = msZuulProxy.msMedicalRecords_updateMedicalRecord((String) session.getAttribute("token"), medicalRecord).getBody();
        if (updateMedicalRecord == null) throw new NotFoundException("Medical record can't be update");
        return updateMedicalRecord;
    }
}
