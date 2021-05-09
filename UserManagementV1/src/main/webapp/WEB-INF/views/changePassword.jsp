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
	<c:url var="processChangePassword" value="/processChangePassword" />
	<div class="container">
	<div class="row" style="align-content: center;">
		<div class="col-md-5 mx-auto" >
			
				<form:form id="changepass-form" action="${processChangePassword}" method="post" modelAttribute="changePasswordDTO">
					
					<div class="custom-model-header">
						<c:if test="${title != null}">
							<h5 class="modal-title">
								${title}
							</h5>
							<p class="text-right"> 
								<span class ="mandatory-field">*</span> 
								<span class="small">Mandatory Fields</span> 
							</p>
						</c:if>
					</div>
						<div class="alert alert-info small">
							<i class="fas fa-info-circle"></i> <s:message code="password.hint"></s:message>
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
							<label for="oldPassword"><s:message code = "oldPassword" /></label><span class="mandatory-field"> *</span> 
							<form:password path="oldPassword" cssClass="form-control form-control-sm" id="oldPassword" name="oldPassword" onfocus="clearErrors();"/>
							<form:errors path ="oldPassword" id = "error-oldPassword" cssClass="small text-danger"></form:errors>
						</div>
						<div class="form-group">
							<label for="newPassword"><s:message code = "newPassword" /></label><span class="mandatory-field"> *</span> 
							<form:password path="newPassword" cssClass="form-control form-control-sm" id="newPassword" name="newPassword" onfocus="clearErrors();" oninput="validateOldNewPass(this);"/>
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
						
						<div align="center">
							<input type="submit" id = "submit-button" class="btn btn-block btn-primary" value="Change Password">
						</div>
						
				</form:form>
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">


	function clearErrors() {
		$("#error-oldPassword").html("");
		$("#error-newPassword").html("");
		$("#error-reenterNewPassword").html("");
	}

	function validateOldNewPass() {

		var oldPass = $("#oldPassword").val();
		var newPass = $("#newPassword").val();

		if (oldPass == newPass) {
			$('small[id="newPassword"]').html(
					"<s:message code = 'oldpass.newpass.should.not.equal' />");
			return false;
		} else {
			$('small[id="newPassword"]').html("");
			return true;
		}
	}

	function validateNewReenterPass() {
		var newPass = $("#newPassword").val();
		var reenterNewPass = $("#reenterNewPassword").val();

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
		if (validateOldNewPass() && validateNewReenterPass()) {
			$("#changepass-form").attr('action','${processChangePassword}');
			$("#changepass-form").submit();
		} else {
			return false;
		}
	});
</script>

</html>