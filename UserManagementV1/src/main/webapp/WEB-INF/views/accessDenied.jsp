<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>AccessDenied page</title>
</head>
<body>
	<sec:authorize access="isAuthenticated()" var="loggedin" />
	<div class="container">
		<div class="row">
			<div class="col-md-12 text-center">
				<div class="text-danger">
					<span>Dear 
						<strong>
							<c:if test="${loggedin }">
								<sec:authentication property="principal.user.firstName"/> <sec:authentication property="principal.user.lastName"/>
							</c:if>
							<c:if test="${!loggedin }">
								Guest
							</c:if>
						</strong>, You are not authorized to access this page.
					</span>
				</div>
			</div>
		</div>
	</div>
</body>
</html>