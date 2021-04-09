package com.mediscreen.msclientui.controller;

import com.mediscreen.msclientui.configuration.AppProperties;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class RooterController {
    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private SecurityServiceInterface securityService;

    @Autowired
    private AppProperties appProperties;

    @GetMapping("/")
    public ModelAndView root(HttpSession session) {
        if (securityService.isLog(session)) {
            return controllerUtils.doRedirect(appProperties.getDefaultUrl());
        } else {
            return controllerUtils.loginRedirect();
        }
    }
}
