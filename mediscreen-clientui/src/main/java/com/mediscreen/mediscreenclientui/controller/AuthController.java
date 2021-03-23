package com.mediscreen.mediscreenclientui.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mediscreen.mediscreenclientui.model.Login;
import com.mediscreen.mediscreenclientui.service.SecurityService;
import com.mediscreen.mediscreenclientui.utils.ControllerUtils;

@RestController
public class AuthController {
	@Autowired
	private ControllerUtils controllerUtils;

	@Autowired
	private SecurityService securityService;

	@GetMapping("/login")
	public ModelAndView getLogin(HttpSession session) {
		if (securityService.isLog(session))
			return controllerUtils.rootRedirect();

		Map<String, Object> model = new HashMap<>();
		model.put("page", "login");
		model.put("isLogin", true);

		return new ModelAndView("template.html", model);
	}

	@PostMapping("/login")
	public ModelAndView postLogin(HttpSession session, @RequestParam Login login) {
		Map<String, String> userLogin = securityService.logUser(login);
		if (userLogin != null)
			userLogin.forEach((k, v) -> session.setAttribute(k, v));

		return controllerUtils.rootRedirect();
	}

	@GetMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return controllerUtils.rootRedirect();
	}
}