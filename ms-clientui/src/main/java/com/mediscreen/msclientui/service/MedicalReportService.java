package com.mediscreen.msclientui.service;

import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.interfaces.MedicalReportServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.MedicalReport;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class MedicalReportService implements MedicalReportServiceInterface {
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
    public MedicalReportService(SecurityServiceInterface securityService) {
        this.securityService = securityService;
    }

    /**
     * Constructor
     */
    public MedicalReportService() {
    }

    /**
     * Constructor
     * @param msZuulProxy
     * @param securityService
     */
    public MedicalReportService(MSZuulProxy msZuulProxy, SecurityServiceInterface securityService) {
        this.msZuulProxy = msZuulProxy;
        this.securityService = securityService;
    }

    /**
     * @see MedicalReportServiceInterface {@link #getMedicalReport(HttpSession, Integer)}
     */
    @Override
    public MedicalReport getMedicalReport(HttpSession session, Integer patientId) {
        if(!securityService.isLog(session)) {
            logger.error("Permission denied : " + session.getAttribute("token"));
            throw new NotAllowedException("Permission denied");
        }
        MedicalReport medicalReport = msZuulProxy.msMedicalReport_generateMedicalReport((String) session.getAttribute("token"), patientId);
        if (medicalReport == null) {
            logger.error("Medical report can't be getted");
            throw new NotFoundException("Medical report can't be getted");
        }
        return medicalReport;
    }
}
