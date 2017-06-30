package com.application.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.application.commandObject.LoginCommandObject;
import com.application.config.UrlMapping;

@RequestMapping(value = { UrlMapping.CONTROLLER_DEFAULT, UrlMapping.CONTROLLER_AUTH })
@Controller
public class AuthController {

	@RequestMapping(value = { UrlMapping.CONTROLLER_DEFAULT,
			UrlMapping.CONTROLLER_AUTH_LOGIN }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView(
				new StringBuilder(UrlMapping.CONTROLLER_AUTH).append(UrlMapping.CONTROLLER_AUTH_LOGIN).toString());
		modelAndView.addObject("loginCommandObject", new LoginCommandObject());
		return modelAndView;
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_AUTH_SIGN_IN }, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView signIn(@ModelAttribute("loginCommandObject") LoginCommandObject loginCommandObject,
			final RedirectAttributes redirectAttributes, HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView(
				new StringBuilder(UrlMapping.CONTROLLER_AUTH).append(UrlMapping.CONTROLLER_AUTH_LOGIN).toString());
		return modelAndView;
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_AUTH_SIGN_OUT }, method = RequestMethod.GET)
	public ModelAndView signOut(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return new ModelAndView("redirect:" + UrlMapping.getLoginUrl());
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_AUTH_UNAUTHORIZED }, method = RequestMethod.GET)
	public ModelAndView unauthorized() {
		return new ModelAndView(new StringBuilder(UrlMapping.CONTROLLER_AUTH)
				.append(UrlMapping.CONTROLLER_AUTH_UNAUTHORIZED).toString());
	}

}
