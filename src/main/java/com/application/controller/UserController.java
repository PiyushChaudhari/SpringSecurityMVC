package com.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.application.config.UrlMapping;
import com.application.model.User;
import com.application.service.UserService;

@RequestMapping(UrlMapping.CONTROLLER_USER)
@Controller
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = { UrlMapping.CONTROLLER_USER, UrlMapping.CONTROLLER_USER_LIST }, method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView(
				new StringBuilder(UrlMapping.CONTROLLER_USER).append(UrlMapping.CONTROLLER_USER_LIST).toString());
		mv.addObject("userList", userService.getAllUser());
		return mv;
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_USER_SHOW }, method = RequestMethod.GET)
	public ModelAndView show(@PathVariable String id) {
		ModelAndView mv = new ModelAndView("/user/show");
		mv.addObject("user", userService.get(id));
		return mv;
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_USER_EDIT }, method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable String id) {
		ModelAndView mv = new ModelAndView("/user/edit");
		mv.addObject("user", userService.get(id));
		return mv;
	}

	@RequestMapping(value = UrlMapping.CONTROLLER_USER_UPDATE, method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("user") User user, final RedirectAttributes redirectAttributes) {
		try {
			userService.update(user);
		} catch (RuntimeException e) {
			if (e.getMessage().equals("Row Updated")) {
				redirectAttributes.addFlashAttribute("error", "User details already update.");
				return new ModelAndView("redirect:" + new StringBuilder(UrlMapping.CONTROLLER_USER)
						.append(UrlMapping.CONTROLLER_USER_EDIT).toString().replace("{id}", user.getId()));
			}
		}
		redirectAttributes.addFlashAttribute("success", "User has been update successfully.");
		return new ModelAndView("redirect:"
				+ new StringBuilder(UrlMapping.CONTROLLER_USER).append(UrlMapping.CONTROLLER_USER_LIST).toString());
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_USER_DELETE }, method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable String id, final RedirectAttributes redirectAttributes) {
		userService.delete(id);
		redirectAttributes.addFlashAttribute("success", "User has been delete successfully.");
		return new ModelAndView("redirect:"
				+ new StringBuilder(UrlMapping.CONTROLLER_USER).append(UrlMapping.CONTROLLER_USER_LIST).toString());
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_USER_PROFILE }, method = RequestMethod.GET)
	public ModelAndView profile() {
		ModelAndView mv = new ModelAndView(
				new StringBuilder(UrlMapping.CONTROLLER_USER).append(UrlMapping.CONTROLLER_USER_PROFILE).toString());
		mv.addObject("user", userService.getLoggedInUser());
		return mv;
	}

	@RequestMapping(value = UrlMapping.CONTROLLER_USER_UPDATE_PROFILE, method = RequestMethod.POST)
	public ModelAndView updateProfile(@ModelAttribute("user") User user, final RedirectAttributes redirectAttributes) {
		userService.update(user);
		userService.updateLoggedInUser(user);
		redirectAttributes.addFlashAttribute("success", "Profile has been update successfully.");
		return new ModelAndView("redirect:"
				+ new StringBuilder(UrlMapping.CONTROLLER_USER).append(UrlMapping.CONTROLLER_USER_PROFILE).toString());
	}

	@RequestMapping(value = { UrlMapping.CONTROLLER_USER_ACTIVE }, method = RequestMethod.GET)
	public ModelAndView active() {
		ModelAndView mv = new ModelAndView(
				new StringBuilder(UrlMapping.CONTROLLER_USER).append(UrlMapping.CONTROLLER_USER_ACTIVE).toString());
		mv.addObject("userList", userService.getAllLoggedInUser());
		return mv;
	}
}
