package com.mediscreen.msclientui.serviceImpl;

import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.model.MedicalReport;
import com.mediscreen.msclientui.proxy.MSZuulProxy;
import com.mediscreen.msclientui.service.MedicalReportService;
import com.mediscreen.msclientui.service.SecurityService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

public class MedicalReportServiceImpl implements MedicalReportService {
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
	public MedicalReportServiceImpl(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * Constructor
	 */
	public MedicalReportServiceImpl() {
	}

	/**
	 * Constructor
	 * 
	 * @param msZuulProxy
	 * @param securityService
	 */
	public MedicalReportServiceImpl(MSZuulProxy msZuulProxy, SecurityService securityService) {
		this.msZuulProxy = msZuulProxy;
		this.securityService = securityService;
	}

	/**
	 * @see MedicalReportService {@link #getMedicalReport(HttpSession, Integer)}
	 */
	@Override
	public MedicalReport getMedicalReport(HttpSession session, Integer patientId) {
		if (!securityService.isLog(session)) {
			logger.error("Permission denied : " + session.getAttribute("token"));
			throw new NotAllowedException("Permission denied");
		}
		MedicalReport medicalReport = msZuulProxy
				.msMedicalReportGenerateMedicalReport((String) session.getAttribute("token"), patientId);
		if (medicalReport == null) {
			logger.error("Medical report can't be getted");
			throw new NotFoundException("Medical report can't be getted");
		}
		return medicalReport;
	}
}