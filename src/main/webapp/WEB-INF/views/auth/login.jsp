<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<h1 align="center">Login</h1>

	<dir class="center">
		<form:form method="POST"
			action="${pageContext.request.contextPath}/auth/signIn"
			commandName="loginCommandObject">

			<table>

				<tr>
					<td><form:label path="email">Email:</form:label></td>
					<td><form:input path="email"
							value="${loginCommandObject.email}" /></td>
				</tr>
				<tr>
					<td><form:label path="password">Password:</form:label></td>
					<td><form:password path="password"
							value="${loginCommandObject.password}" /></td>
				</tr>

				<c:if test="${error !=null}">
					<tr>
						<td colspan="2"></td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2"><c:out value="${error}" /></td>
				</tr>
			</table>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<input type="submit" value="Login" />
		</form:form>
	</dir>
</body>
</html>