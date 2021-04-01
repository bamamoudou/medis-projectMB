package com.mediscreen.mediscreenclientui.serviceImpl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.mediscreen.mediscreenclientui.exception.NotAllowedException;
import com.mediscreen.mediscreenclientui.exception.NotFoundException;
import com.mediscreen.mediscreenclientui.model.Patient;
import com.mediscreen.mediscreenclientui.proxy.MSZuulProxy;
import com.mediscreen.mediscreenclientui.service.PatientService;
import com.mediscreen.mediscreenclientui.service.SecurityService;

public class PatientServiceImpl implements PatientService {
	/**
	 * Zuul proxy
	 */
	@Autowired
	private MSZuulProxy msZuulProxy;

	/**
	 * Security service
	 */
	@Autowired
	private SecurityService securityService;

	/**
	 * @see PatientServiceInterface {@link #getAllPatients(HttpSession)}
	 */
	@Override
	public List<Patient> getAllPatients(HttpSession session) {
		return msZuulProxy.msPatientAdmin_getAllPatients((String) session.getAttribute("token"));
	}

	/**
	 * @see PatientServiceInterface {@link #searchPatient(HttpSession, String)}
	 */
	@Override
	public List<Patient> searchPatient(HttpSession session, String search) {
		if (!StringUtils.isBlank(search)) {
			return msZuulProxy.searchPatients((String) session.getAttribute("token"), search);
		} else {
			return null;
		}
	}

	/**
	 * @see PatientServiceInterface {@link #getPatient(HttpSession, int)}
	 */
	@Override
	public Patient getPatient(HttpSession session, int id) {
		return msZuulProxy.msPatient_getPatient((String) session.getAttribute("token"), id);
	}

	/**
	 * @see PatientServiceInterface {@link #createPatient(HttpSession, Patient)}
	 */
	@Override
	public Patient createPatient(HttpSession session, Patient patient) {
		if (!securityService.isLog(session))
			throw new NotAllowedException("Permission denied");
		Patient newPatient = msZuulProxy.msPatient_createPatient((String) session.getAttribute("token"), patient)
				.getBody();
		if (newPatient == null)
			throw new NotFoundException("Patient can't be create");
		return newPatient;
	}

	/**
	 * @see PatientServiceInterface {@link #updatePatient(HttpSession, Patient)}
	 */
	@Override
	public void updatePatient(HttpSession session, Patient patient) {
		if (!securityService.isLog(session))
			throw new NotAllowedException("Permission denied");
		if (this.getPatient(session, patient.getId()) == null)
			throw new NotFoundException("Patient id unknown");
		Patient updatePatient = msZuulProxy
				.msPatient_updatePatient((String) session.getAttribute("token"), patient).getBody();
		if (updatePatient == null)
			throw new NotFoundException("Patient can't be updated");
	}

	/**
	 * @see PatientServiceInterface {@link #deletePatient(HttpSession, int)}
	 */
	@Override
	public HttpStatus deletePatient(HttpSession session, int id) {
		if (!securityService.isLog(session))
			throw new NotAllowedException("Permission denied");
		if (this.getPatient(session, id) == null)
			throw new NotFoundException("Patient id unknown");
		return msZuulProxy.msPatient_deletePatient((String) session.getAttribute("token"), id).getStatusCode();
	}
}