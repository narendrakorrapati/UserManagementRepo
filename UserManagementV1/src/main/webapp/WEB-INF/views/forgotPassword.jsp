<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body>
	<c:url var="forgotPassTokeURL" value="/sendResetPassLink" />
	<div class="container">
		<div class="row" style="align-content: center;">
			<div class="col-md-5 mx-auto" >
				<div class="modal-content">	
					<form action="${forgotPassTokeURL}" method="post">
						
						<div class="custom-model-header">
							
							<h4 class="modal-title">Forgot Password</h4>
							<p class="text-right"> <span class ="mandatory-field">*</span> <span class="small">Mandatory Fields</span> </p>
						
						</div>
						<div class="modal-body">
							<div class="alert alert-info small">
								<i class="fas fa-info-circle"></i> We will be sending the reset password link to your email id
							</div>
							<c:if test="${errorMessage != null}">
								<div class="text-danger text-center">
									<p>${errorMessage}</p>
								</div>
							</c:if>
							<div class="form-group">
								<label for="email"><s:message code="email" /></label> <span class ="mandatory-field">*</span>
								<input type="text"
									class="form-control form-control-sm" id="email" name="email"
									required>
							</div>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							</div>
							<div class="modal-footer">
								<input type="submit" class="btn btn-block btn-primary " value="Send Email">
							</div>
					</form>
					</div>
				</div>
		</div>
	</div>
</body>
</html>