<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<h1 align="center">User List</h1>
	<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
FDFSDFSDF
</sec:authorize>

	<sec:authorize access="isAuthenticated()">
	Welcome, 
	<sec:authentication property="principal.firstName" />
		<sec:authentication property="principal.lastName" />
		<a href="${pageContext.request.contextPath}/auth/signOut">Logout</a>| 
		<a href="${pageContext.request.contextPath}/user/profile">Profile</a>
	</sec:authorize>

	<c:if test="${success !=null}">
		<h6 align="center">
			<c:out value="${success}" />
		</h6>
	</c:if>
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
							<td><a
								href="${pageContext.request.contextPath}/user/edit/${user.id}">Edit|</a>
								<a href="javascript:void('0')"
								onclick="deleteUser('${user.id}')">Delete</a>
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