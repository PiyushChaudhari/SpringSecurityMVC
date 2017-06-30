package com.application.config.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.application.config.UrlMapping;

@Component
public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		super.setUseForward(true);
		super.setDefaultFailureUrl(
				new StringBuilder(UrlMapping.CONTROLLER_AUTH).append(UrlMapping.CONTROLLER_AUTH_SIGN_IN).toString());
		super.onAuthenticationFailure(request, response, exception);
	}

}
