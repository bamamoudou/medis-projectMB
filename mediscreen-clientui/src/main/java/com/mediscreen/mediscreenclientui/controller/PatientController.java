package com.mediscreen.mediscreenclientui.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mediscreen.mediscreenclientui.model.Patient;
import com.mediscreen.mediscreenclientui.service.PatientService;
import com.mediscreen.mediscreenclientui.service.SecurityService;
import com.mediscreen.mediscreenclientui.utils.ControllerUtils;

@RestController
public class PatientController {
	@Autowired
	private ControllerUtils controllerUtils;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private PatientService patientService;

	@GetMapping("/search")
	public ModelAndView getSearch(HttpSession session) {
		if (!securityService.isLog(session))
			return controllerUtils.rootRedirect();

		ModelMap model = new ModelMap();
		model.addAttribute("page", "search");
		model.addAttribute("patients", patientService.getAllPatients(session));
		model.addAttribute("isLogin", true);

		return new ModelAndView("template", model);
	}

	@GetMapping("/patient/search")
	public List<Patient> searchPatient(HttpSession session, @RequestParam(required = true) String search) {
		return patientService.searchPatient(session, search);
	}

	@GetMapping("/patient")
	public ModelAndView getPatientPath(HttpSession session) {
		if (!securityService.isLog(session))
			return controllerUtils.rootRedirect();
		else
			return controllerUtils.doRedirect("/search");
	}

	@GetMapping("/patient/{id}")
	public ModelAndView getPatient(HttpSession session, @PathVariable(value = "id") Integer id,
			@RequestParam(required = false) String error) {
		if (!securityService.isLog(session))
			return controllerUtils.rootRedirect();

		ModelMap model = new ModelMap();
		model.addAttribute("page", "patient-sheet");
		model.addAttribute("patient", patientService.getPatient(session, id));
		if (!StringUtils.isBlank(error))
			model.addAttribute("error", error);
		model.addAttribute("isLogin", true);

		return new ModelAndView("template", model);
	}

	@GetMapping("/patient/create")
	public ModelAndView getPatientCreate(HttpSession session, @RequestParam(required = false) String error) {
		if (!securityService.isLog(session))
			return controllerUtils.rootRedirect();

		ModelMap model = new ModelMap();
		model.addAttribute("page", "patient-create");
		model.addAttribute("patient", new Patient());
		if (!StringUtils.isBlank(error))
			model.addAttribute("error", error);
		model.addAttribute("isLogin", true);

		return new ModelAndView("template", model);
	}

	@PostMapping("/patient/create")
	public ModelAndView createPatient(HttpSession session, @ModelAttribute Patient patient) {
		try {
			Patient newPatient = patientService.createPatient(session, patient);
			return controllerUtils.doRedirect("/patient/" + newPatient.getId());
		} catch (RuntimeException e) {
			return controllerUtils.doRedirect("/patient/create?error=" + e);
		}
	}

	@PostMapping("/patient/{id}/update")
	public ModelAndView updatePatient(HttpSession session, @PathVariable(value = "id") Integer id,
			@ModelAttribute Patient patient) {
		try {
			patient.setId(id);
			patientService.updatePatient(session, patient);
			return controllerUtils.doRedirect("/patient/" + id);
		} catch (RuntimeException e) {
			return controllerUtils.doRedirect("/patient/" + id + "?error=" + e);
		}
	}

	@GetMapping("/patient/{id}/delete")
	public ModelAndView deletePatient(HttpSession session, @PathVariable(value = "id") Integer id) {
		try {
			patientService.deletePatient(session, id);
			return controllerUtils.doRedirect("/search");
		} catch (RuntimeException e) {
			return controllerUtils.doRedirect("/patient/" + id + "?error=" + e);
		}
	}
}