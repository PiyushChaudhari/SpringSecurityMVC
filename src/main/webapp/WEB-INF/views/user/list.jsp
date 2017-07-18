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
<title>User List</title>
<script type="text/javascript">
function deleteUser(id) {
  var txt = "";
  if (confirm("Are you sure you want to delete this user?") == true) {
    window.location.href = '${pageContext.request.contextPath}/user/delete/'
      + id;
  }
}

function editUser(id) {
  window.location.href = '${pageContext.request.contextPath}/user/edit/'
    + id;
}
</script>
</head>
<body>
	<h3 align="center">User List</h3>

	<jsp:include page="/WEB-INF/views/common/menuLinks.jsp"></jsp:include>

	<c:if test="${success !=null}">
		<h6 align="center">
			<c:out value="${success}" />
		</h6>
	</c:if>
	<br>
	<table align="center" border="1">
		<thead>
			<tr>
				<th>Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email</th>
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>

				<c:when test="${!empty userList}">
					<c:forEach items="${userList}" var="user">
						<tr>
							<td>
								<%-- <c:out value="${employee.id}" /> --%> <a
								href="${pageContext.request.contextPath}/user/show/${user.id}">
									<c:out value="${user.id}" />
							</a>
							</td>
							<td><c:out value="${user.firstName}" /></td>
							<td><c:out value="${user.lastName}" /></td>
							<td><c:out value="${user.email}" /></td>
							<td>
								<secure:authenticate hasPermission="<%=com.application.config.UrlMapping.PERMISSION_USER_EDIT%>">
									<a href="${pageContext.request.contextPath}/user/edit/${user.id}">Edit</a>
								</secure:authenticate>
								<secure:authenticate hasPermission="<%=com.application.config.UrlMapping.PERMISSION_USER_DELETE%>">
									| <a href="javascript:void('0')" onclick="deleteUser('${user.id}')">Delete</a>
								</secure:authenticate>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="5" align="center">No, recored found</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
		<tfoot>
		</tfoot>
	</table>

</body>
</html>