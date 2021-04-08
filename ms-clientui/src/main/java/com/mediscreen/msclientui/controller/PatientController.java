package com.mediscreen.msclientui.controller;

import com.mediscreen.msclientui.interfaces.PatientServiceInterface;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.models.Patient;
import com.mediscreen.msclientui.utils.ControllerUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private PatientServiceInterface patientService;

    @GetMapping("/search")
    public ModelAndView getSearch(HttpSession session){
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        ModelMap model = new ModelMap();
        model.addAttribute("page", "search");
        model.addAttribute("patients", patientService.getAllPatients(session));
        model.addAttribute("isLogin", true);

        return new ModelAndView("template" , model);
    }

    @GetMapping("/patient/search")
    public List<Patient> searchPatient(HttpSession session, @RequestParam(required = true) String search) {
        return patientService.searchPatient(session, search);
    }

    @GetMapping("/patient")
    public ModelAndView getPatientPath(HttpSession session){
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();
        else return controllerUtils.doRedirect("/search");
    }

    @GetMapping("/patient/{id}")
    public ModelAndView getPatient(HttpSession session, @PathVariable(value="id") Integer id, @RequestParam(required = false) String error){
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        ModelMap model = new ModelMap();
        model.addAttribute("page", "patient-sheet");
        model.addAttribute("patient", patientService.getPatient(session, id));
        if (!StringUtils.isBlank(error)) model.addAttribute("error", error);
        model.addAttribute("isLogin", true);

        return new ModelAndView("template" , model);
    }

    @GetMapping("/patient/create")
    public ModelAndView getPatientCreate(HttpSession session, @RequestParam(required = false) String error){
        if (!securityService.isLog(session)) return controllerUtils.rootRedirect();

        ModelMap model = new ModelMap();
        model.addAttribute("page", "patient-create");
        model.addAttribute("patient", new Patient());
        if (!StringUtils.isBlank(error)) model.addAttribute("error", error);
        model.addAttribute("isLogin", true);

        return new ModelAndView("template" , model);
    }

    @PostMapping("/patient/create")
    public ModelAndView createPatient(HttpSession session, @ModelAttribute Patient patient){
        try {
            Patient newPatient = patientService.createPatient(session, patient);
            return controllerUtils.doRedirect("/patient/" + newPatient.getId());
        } catch (RuntimeException e){
            return controllerUtils.doRedirect("/patient/create?error=" + e);
        }
    }

    @PostMapping("/patient/{id}/update")
    public ModelAndView updatePatient(HttpSession session, @PathVariable(value="id") Integer id, @ModelAttribute Patient patient){
        try {
            patient.setId(id);
            patientService.updatePatient(session, patient);
            return controllerUtils.doRedirect("/patient/" + id);
        } catch (RuntimeException e) {
            return controllerUtils.doRedirect("/patient/" + id + "?error=" + e);
        }
    }

    @GetMapping("/patient/{id}/delete")
    public ModelAndView deletePatient(HttpSession session, @PathVariable(value="id") Integer id){
        try {
            patientService.deletePatient(session, id);
            return controllerUtils.doRedirect("/search");
        } catch (RuntimeException e) {
            return controllerUtils.doRedirect("/patient/" + id + "?error=" + e);
        }
    }
}