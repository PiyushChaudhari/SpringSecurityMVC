<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit User</title>
</head>
<body>

	<dir class="center">

		<form:form method="POST"
			action="${pageContext.request.contextPath}/user/update"
			commandName="user">
			<form:hidden path="id" value="${user.id}" />
			<form:hidden path="version" value="${user.version}" />
			<table>
				<tr>
					<td><form:label path="firstName">First Name:</form:label></td>
					<td><form:input path="firstName" value="${user.firstName}" /></td>
				</tr>
				<tr>
					<td><form:label path="lastName">Last Name:</form:label></td>
					<td><form:input path="lastName" value="${user.lastName}" /></td>
				</tr>
				<tr>
					<td><form:label path="email">Email:</form:label></td>
					<td><form:input path="email" value="${user.email}"
							readonly="true" /></td>
				</tr>

				<tr>
					<td colspan="2"><input type="submit" value="Save" /> <input
						type="button" value="Cancle"
						onclick="window.location.href='${pageContext.request.contextPath}/user/list'" />
					</td>
				</tr>

				<c:if test="${error !=null}">
					<tr>
						<td colspan="2"><c:out value="${error}"></c:out></td>
					</tr>
				</c:if>
			</table>

		</form:form>

	</dir>

</body>
</html>