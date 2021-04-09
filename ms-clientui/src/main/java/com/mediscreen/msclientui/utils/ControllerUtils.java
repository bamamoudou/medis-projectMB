package com.mediscreen.msclientui.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

public class ControllerUtils {
	public ControllerUtils() {
	}

	public RedirectView redirect(String path) {
		return new RedirectView(path);
	}

	public ModelAndView loginRedirect() {
		return new ModelAndView(redirect("/login"));
	}

	public ModelAndView rootRedirect() {
		return new ModelAndView(redirect("/"));
	}

	public ModelAndView doRedirect(String path) {
		if (StringUtils.isNotBlank(path)) {
			return new ModelAndView(redirect(path));
		} else {
			throw new NullPointerException("ControllerUtils.doRedirect() -> Path empty");
		}
	}
}