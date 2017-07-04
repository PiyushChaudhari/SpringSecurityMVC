package com.application.taglib.secure;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.application.model.Role;
import com.application.model.User;

public class SecurityTag extends BodyTagSupport {

	private static final long serialVersionUID = -4761558710232737901L;

	private String hasRole;
	private String hasAnyRole;

	private String hasPermission;

	public void setHasRole(String hasRole) {
		this.hasRole = hasRole;
	}

	public void setHasAnyRole(String hasAnyRole) {
		this.hasAnyRole = hasAnyRole;
	}

	public void setHasPermission(String hasPermission) {
		this.hasPermission = hasPermission;
	}

	@Override
	public int doAfterBody() {

		Authentication auth = currentAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {

			if (this.hasRole == null && this.hasAnyRole == null && this.hasPermission == null) {
				executeDoAfterBody();
				return SKIP_BODY;
			}

			if ((this.hasRole != null && validRole(this.hasRole))
					|| (this.hasAnyRole != null && validAnyRole(this.hasAnyRole))
					|| (this.hasPermission != null && validPermission(this.hasPermission))) {
				executeDoAfterBody();
				return SKIP_BODY;
			}

		}
		return SKIP_BODY;
	}

	private void executeDoAfterBody() {
		BodyContent body = getBodyContent();
		String filteredBody = body.getString();
		JspWriter out = body.getEnclosingWriter();
		try {
			out.print(filteredBody);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private Authentication currentAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	private boolean validRole(String role) {

		Authentication auth = currentAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			if (user.getRole().isEmpty())
				return false;
			else
				return user.getRole().stream().filter(r -> r.getName().equals(role)).count() > 0 ? true : false;

		} else {
			return false;
		}
	}

	private boolean validAnyRole(String role) {
		Authentication auth = currentAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			if (user.getRole().isEmpty())
				return false;
			else {
				List<String> checkRoles = Arrays.asList(role.split(","));
				if (checkRoles.isEmpty())
					return false;
				else {
					List<String> roles = user.getRole().stream().map(Role::getName).collect(Collectors.toList());
					return roles.stream().filter(checkRoles::contains).collect(Collectors.toList()).isEmpty() ? false
							: true;
				}
			}

		} else {
			return false;
		}
	}

	private boolean validPermission(String permission) {

		Authentication auth = currentAuthentication();
		User user = (User) auth.getPrincipal();
		if (user != null) {
			if (user.getRole().isEmpty())
				return false;
			else {
				long count = 0;

				for (Role r : user.getRole()) {
					count = count + r.getPermissions().stream().filter(p -> p.getName().equals(permission)).count();
					if (count > 0)
						break;
				}
				if (count > 0)
					return true;
				else
					return false;
			}

		} else {
			return false;
		}
	}

}
