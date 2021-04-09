package com.mediscreen.msclientui.controller;

import com.mediscreen.msclientui.exception.EmptyDataException;
import com.mediscreen.msclientui.exception.NotAllowedException;
import com.mediscreen.msclientui.interfaces.SecurityServiceInterface;
import com.mediscreen.msclientui.model.Login;
import com.mediscreen.msclientui.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class AuthenticateController {

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private SecurityServiceInterface securityService;

    @GetMapping("/login")
    public ModelAndView getLogin(HttpSession session){
        if (securityService.isLog(session)) return controllerUtils.rootRedirect();

        ModelMap model = new ModelMap();
        model.addAttribute("page", "login");
        model.addAttribute("login", new Login());
        model.addAttribute("isLogin", false);

        return new ModelAndView("template" , model);
    }

    @PostMapping("/login")
    public ModelAndView postLogin(HttpSession session, @ModelAttribute Login login){
        try {
            session.setAttribute("token", securityService.logUser(login));
            return controllerUtils.rootRedirect();
        } catch (EmptyDataException | NotAllowedException e){
            ModelMap model = new ModelMap();
            model.addAttribute("page", "login");
            model.addAttribute("login", new Login());
            model.addAttribute("isLogin", false);
            model.addAttribute("error", e.getMessage());
            return new ModelAndView("template" , model);
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session){
        session.invalidate();
        return controllerUtils.rootRedirect();
    }
}
