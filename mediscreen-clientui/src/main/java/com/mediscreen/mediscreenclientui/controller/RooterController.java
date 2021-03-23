package com.mediscreen.mediscreenclientui.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mediscreen.mediscreenclientui.config.AppProperties;
import com.mediscreen.mediscreenclientui.service.SecurityService;
import com.mediscreen.mediscreenclientui.utils.ControllerUtils;

@RestController
public class RooterController {
	@Autowired
	private ControllerUtils controllerUtils;

	@Autowired
	private SecurityService securityService;

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