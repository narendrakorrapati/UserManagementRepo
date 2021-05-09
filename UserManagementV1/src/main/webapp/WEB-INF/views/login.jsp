<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login page</title>
</head>

<body>
	<c:url var="loginUrl" value="/login" />
	<div class="container">
		<div class="row" style="align-content: center;">
			<div class="col-md-5 mx-auto" >
				<div class="modal-content">	
					<form action="${loginUrl}" method="post">
						
						<div class="custom-model-header">
							
							<h4 class="modal-title">Login</h4>
							<p class="text-right"> <span class ="mandatory-field">*</span> <span class="small">Mandatory Fields</span> </p>
						
						</div>
						<div class="modal-body">
							<c:if test="${errorMessage != null}">
								<div class="text-danger text-center">
									<p>${errorMessage}</p>
								</div>
							</c:if>
							<c:if test="${param.logout != null}">
								<div class="text-danger text-center">
									<p>You have been logged out successfully.</p>
								</div>
							</c:if>
							<c:if test="${param.reset != null}">
								<div class="text-success text-center">
									<p>Your password has been reset successfully.</p>
								</div>
							</c:if>
							<div class="form-group">
								<label for="email"><s:message code="email" /></label> <span class ="mandatory-field">*</span><input type="text"
									class="form-control form-control-sm" id="email" name="email"
									required>
							</div>
							<div class="form-group">
								<label for="password"><s:message code="password" /></label> <span class ="mandatory-field">*</span><input type="password"
									class="form-control form-control-sm" id="password" name="password"
									 required>
							</div>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							</div>
							<div>
								<input type="submit" class="btn btn-block btn-primary " value="Log in">
								<br>
								<div class="text-center small-font">
									<a href='<c:url value = "/forgotPassword"/>'>Forgot Password?</a>
								</div>
								
							</div>
					</form>
					</div>
				</div>
		</div>
	</div>
</body>
</html>