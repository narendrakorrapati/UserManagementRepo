<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>

<body>
	<c:url var="processRestPassword" value="/processRestPassword" />
	<div class="container">
	<div class="row" style="align-content: center;">
		<div class="col-md-5 mx-auto" >
			
				<form:form id="changepass-form" action="${processRestPassword}" method="post" modelAttribute="forgotPasswordDTO">
					
					<div class="custom-model-header">
						<h5 class="modal-title">Rest Password</h5>
						<p class="text-right">
							<span class="mandatory-field">*</span> <span class="small">Mandatory
								Fields</span>
						</p>
					</div>
						<div class="alert alert-info small">
							<i class="fas fa-info-circle"></i><s:message code="password.hint"></s:message>
						</div>
						
						<div class="text-danger small">
							<form:errors path=""/>
						</div>
						 
						
						<c:if test="${errorMessage != null}">
							<div class="text-danger small">
								<p>${errorMessage}</p>
							</div>
						</c:if>
						
						<div class="form-group">
							<label for="newPassword"><s:message code = "newPassword" /></label><span class="mandatory-field"> *</span> 
							<form:password path="newPassword" cssClass="form-control form-control-sm" id="newPassword" name="newPassword" required = "true" onfocus="clearErrors();" />
							<small  class="text-danger" id = "newPassword"></small>
							<form:errors path ="newPassword" id="error-newPassword" cssClass="small text-danger"></form:errors>
						</div>
						<div class="form-group">
							<label for="reenterNewPassword"><s:message code = "confirmPassword" /></label><span class="mandatory-field"> *</span>
							 <form:password path="reenterNewPassword" cssClass="form-control form-control-sm" id="reenterNewPassword" name="reenterNewPassword" onfocus="clearErrors();" oninput="validateNewReenterPass();"/>
							 <small  class="text-danger" id = "reenterNewPassword"></small>
							 <form:errors path ="reenterNewPassword" id="error-reenterNewPassword" cssClass="small text-danger"></form:errors>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						
						<form:hidden path="token"/>
						
						<div align="center">
							<input type="submit" id = "submit-button" class="btn btn-block btn-primary" value="Reset Password">
						</div>
						
				</form:form>
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">


	function clearErrors() {
		
		$("#error-newPassword").html("");
		$("#error-reenterNewPassword").html("");
	}

	function validateNewReenterPass() {
		var newPass = $("#newPassword").val();
		var reenterNewPass = $("#reenterNewPassword").val();

		if(newPass == "") {
			$('small[id="newPassword"]').html("Please enter New Password");
			return false;
		} else {
			$('small[id="newPassword"]').html("");
		}

		if(reenterNewPass == "") {
			$('small[id="reenterNewPassword"]').html("Please enter Confirm Password");
			return false;
		} else {
			$('small[id="reenterNewPassword"]').html("");
		}
		
		if (newPass != reenterNewPass) {
			$('small[id="reenterNewPassword"]')
					.html(
							"<s:message code = 'newpass.reenterpass.should.be.equal' />");
			return false;

		} else {
			$('small[id="reenterNewPassword"]').html("");
			return true;
		}
	}

	$("#submit-button").click(function() {
		if (validateNewReenterPass()) {
			$("#changepass-form").attr('action','${processRestPassword}');
			$("#changepass-form").submit();
		} else {
			return false;
		}
	});
</script>

</html>