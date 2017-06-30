package com.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.application.config.UrlMapping;

@RequestMapping(value = { UrlMapping.CONTROLLER_HOME, UrlMapping.CONTROLLER_AUTH })
@Controller
public class HomeController {

	@RequestMapping(value = { UrlMapping.CONTROLLER_HOME_INDEX }, method = RequestMethod.GET)
	public ModelAndView index() {
		System.out.println("Called Home Index...");
		return new ModelAndView("/home/index");
	}

}
