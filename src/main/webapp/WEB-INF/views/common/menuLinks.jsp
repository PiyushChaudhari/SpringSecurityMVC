<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%@ taglib prefix="secure"
	uri="http://www.springframework.org/secure/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<div align="center">
		<sec:authorize access="isAuthenticated()">
	Welcome, 
	<sec:authentication property="principal.firstName" />
			<sec:authentication property="principal.lastName" />
			<a href="${pageContext.request.contextPath}/auth/signOut">Logout</a> 
		

			<c:if test="${actionName !='profile'}">
					| <a href="${pageContext.request.contextPath}/user/profile">Profile</a>
			</c:if>
			<secure:authenticate hasPermission="<%=com.application.config.UrlMapping.PERMISSION_USER_LIST%>">
					<c:if test="${actionName !='list'}">
					| 	<a href="${pageContext.request.contextPath}/user/list">
							User List
						</a>
					</c:if>
			</secure:authenticate>
			
			<secure:authenticate hasPermission="<%=com.application.config.UrlMapping.PERMISSION_USER_ACTIVE%>">
					<c:if test="${actionName !='active'}">
					| 	<a href="${pageContext.request.contextPath}/user/active">
							LoggedIn Users
						</a>
					</c:if>
			</secure:authenticate>

		</sec:authorize>
	</div>
</body>
</html>