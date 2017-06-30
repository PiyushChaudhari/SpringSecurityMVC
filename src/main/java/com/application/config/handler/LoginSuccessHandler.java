package com.application.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.application.config.UrlMapping;
import com.application.enums.UserType;
import com.application.model.User;
import com.application.service.UserService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		super.onAuthenticationSuccess(request, response, authentication);
	}

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response);

		if (response.isCommitted()) {
			System.out.println("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}
		super.getRedirectStrategy().sendRedirect(request, response, targetUrl);
		super.clearAuthenticationAttributes(request);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		User user = userService.getLoggedInUser();
		if (user.getUserType().equals(UserType.ADMIN) || user.getUserType().equals(UserType.ADVISER)) {
			return UrlMapping.generateUrl(UrlMapping.CONTROLLER_USER, UrlMapping.CONTROLLER_USER_LIST);
		} else {
			return UrlMapping.generateUrl(UrlMapping.CONTROLLER_USER, UrlMapping.CONTROLLER_USER_PROFILE);
		}
	}
}
