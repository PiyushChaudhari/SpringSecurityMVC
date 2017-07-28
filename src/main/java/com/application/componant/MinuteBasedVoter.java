package com.application.componant;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.util.UrlPathHelper;

import com.application.config.UrlMapping;

public class MinuteBasedVoter implements AccessDecisionVoter<Object> {
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> collection) {

		FilterInvocation fi = (FilterInvocation) object;
		String currentUrl = fi.getRequestUrl();

		
		// System.out.println("Bypass:> " +
		// UrlMapping.byPassUrlList().contains(currentUrl));
		// System.out.println("LogOutUrl:> " +
		// UrlMapping.getLogOutUrl().equals(currentUrl));

		if (currentUrl.equals("/") || UrlMapping.byPassUrlList().contains(currentUrl)
				|| UrlMapping.getLogOutUrl().equals(currentUrl)) {
			return ACCESS_GRANTED;
		}
//		String urlDivider[] = currentUrl.split("/");
//
//		StringBuilder sb = new StringBuilder();
//		if (urlDivider.length > 0)
//			sb.append(urlDivider[1]);
//		if (urlDivider.length > 1)
//			sb.append("_" + urlDivider[2]);
//
//		String url = sb.toString().toUpperCase();
//		 System.out.println("Current URL:> " + url);
		
		HttpServletRequest req = (HttpServletRequest) fi.getRequest();

		String currenturl = new UrlPathHelper().getPathWithinApplication(req);
		String url = new StringBuilder(currenturl.replaceFirst("/", "").replace("/", "_")).toString().trim().toUpperCase();
		
		String urlDivider[] = currenturl.split("/");

		StringBuilder sb = new StringBuilder();
		if (urlDivider.length > 0)
			sb.append(urlDivider[1]);
		if (urlDivider.length > 1)
			sb.append("_" + urlDivider[2]);

		url = sb.toString().toUpperCase();
		 System.out.println("Current URL:> " + url);
		 
		// System.out.println("Current URL:> " +
		// authentication.getAuthorities().contains(url));
		// System.out.println("Current URL:> " + authentication.getAuthorities());
		return authentication.getAuthorities().contains(new SimpleGrantedAuthority(url)) ? ACCESS_GRANTED : ACCESS_DENIED;

		// return
		// authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
		// .filter(r -> "ROLE_ADMIN".equals(r)).findAny().map(s ->
		// ACCESS_DENIED).orElseGet(() -> ACCESS_ABSTAIN);
	}
}
