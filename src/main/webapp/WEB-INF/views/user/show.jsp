<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>User Details</title>
</head>
<body>

	<div class="center">
		<c:set var="userDeatils" value="${user}"></c:set>
		<table align="center">
			<tr>
				<td>First Name:</td>
				<td><c:out value="${userDeatils.firstName}" /></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><c:out value="${userDeatils.lastName}" /></td>
			</tr>

			<tr>
				<td>Email:</td>
				<td><c:out value="${userDeatils.email}" /></td>
			</tr>

			<tr>
				<td colspan="2"></td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
				<input type="button" value="Back"
			onclick="window.location.href='${pageContext.request.contextPath}/user/list'" />
				</td>
			</tr>
		</table>

		
	</div>

</body>
</html>