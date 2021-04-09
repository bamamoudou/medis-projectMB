package com.mediscreen.msclientui.controller;

import com.mediscreen.msclientui.configuration.AppProperties;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class RooterController implements ErrorController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private AppProperties appProperties;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/")
    public ModelAndView root(HttpSession session) {
        if (securityService.isLog(session)) {
            return controllerUtils.doRedirect(appProperties.getDefaultUrl());
        } else {
            return controllerUtils.loginRedirect();
        }
    }

    @GetMapping("/error")
    public ModelAndView handleError(HttpSession session, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        model.addAttribute("page", "error");
        model.addAttribute("error_code", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        model.addAttribute("error_msg", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        model.addAttribute("error_exp", request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
        model.addAttribute("isLogin", securityService.isLog(session));

        return new ModelAndView("template" , model);
    }
}
