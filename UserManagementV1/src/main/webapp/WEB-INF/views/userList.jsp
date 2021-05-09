<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>  
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>

<head>
	 
	 
	

</head>

<body>

	<div class="container">
		<!-- <h2>Modal Example</h2>
		  Trigger the modal with a button
		  <button type="button" class="btn btn-info btn-md" data-toggle="modal" data-target="#myModal">Open Modal</button>
		 -->
		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
			<div class="modal-dialog modal-dialog-centered modal-lg">

				<!-- Modal content-->

				<div class="modal-content">
					<c:url var="addUserURL" value="/addUser" />
					<form:form action="${addUserURL}" id ="adduser-form" method="post" modelAttribute="userModel">
						<div class="custom-model-header">
							<h4 class="modal-title" id="modalTitle">Add New User</h4>
							<p class="text-right"> <span class ="mandatory-field">*</span> <span class="small">Mandatory Fields</span> </p>
						</div>
						<div class="modal-body">

							<div id = "errorMessage">
									
							</div>

							<div class="row">
								<div class="col col-md-6">
									<div class="form-group">
										<label for="firstName" ><s:message code="firstName" /><span class="mandatory-field"> *</span></label> 
										<form:input path="firstName" id="firstName" cssClass="form-control form-control-sm" required = "true"/>
										<small  class="text-danger" id = "firstName"></small>
									</div>	
								</div>
								<div class="col col-md-6">
									
									<div class="form-group">
										<label for="lastName" ><s:message code="lastName" /></label> <span class="mandatory-field">*</span>
										<form:input path="lastName" id="lastName" cssClass="form-control form-control-sm" required = "true"/>
										<small  class="text-danger" id = "lastName"></small>
									</div>	
									
								</div>
							</div>
							
							<div class="row">
								<div class="col col-md-6">
									<div class="form-group">
										<label for="email"><s:message code="email" /></label> <span class="mandatory-field">*</span>
										<div class = "input-group">
											<form:input path="email" class="form-control form-control-sm " id="email" required = "true"/>
											<span class="input-group-btn">
										        <button id ="check" class="btn btn-secondary btn-sm" type="button"><i class="fas fa-user-check"></i></button>
										     </span>
										</div>
										<small  class="text-danger" id = "email"></small>
									</div>
								</div>
								<div class="col col-md-6">
									<div class="form-group">
										<label for="userRoles"><s:message code="userRoles" /></label> <span class="mandatory-field">*</span>
										<form:select path="userRoles" id = "userRoles" cssClass="form-control form-control-sm" multiple="true" >
											<form:options items="${roles }" itemLabel="roleName" itemValue="id"/>
										</form:select>
										<small  class="text-danger" id = "userRoles"></small>
									</div>
								</div>
							</div>
							
							<input type="hidden" name="id" value="" />
							<input type="hidden" name="processFor" id = "processFor" value="add" />
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							
							<div id = "edit-email" style="display: none;">
								
							</div>
							
							<div id = "role-options" style="display: none;">
								<c:forEach var = "role" items="${roles }">
									<option value="${role.id }"> ${role.roleName }</option>
								</c:forEach>
							</div>
							
						</div>
						<div class="modal-footer">
							
							<input type="submit" class="btn btn-primary btn-md" value = '<s:message code="submit" />' />
							<button type="button" class="btn btn-primary btn-md"
								data-dismiss="modal"><s:message code="close" /></button>
						</div>
					</form:form>
				</div>

			</div>
		</div>

	</div>

	<div class="container">
		<div class="row">
			<div id = "col-sm-12">
				<div id = "successMessage">
								
				</div>
			</div>
			
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div id="toolbar">
				  <button id="remove" class="btn btn-danger" disabled>
				    <i class="fa fa-trash"></i> Delete
				  </button>
				   <button id="add-user" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
				    <i class="fas fa-user-plus"></i> Add
				  </button>
				</div>
				<table 
					class="table-sm"
					id="table" 
					data-toolbar="#toolbar"
					data-toggle="table" 
					data-height="460"
					data-ajax="ajaxRequest"
					data-search="true"
					data-side-pagination="server" 
					data-pagination="true"
					data-page-size = "10"
					data-page-list="[10, 25, 50, 100, all]"
					data-show-refresh="true"
					data-show-toggle="true"
					data-show-fullscreen="true"
					data-show-columns="true"
					data-show-columns-search="true"
					data-show-columns-toggle-all="true"
					data-detail-view="true"
					data-detail-formatter="detailFormatter"
					data-show-export="true"
					data-click-to-select="true"
					data-minimum-count-columns="2"
					data-show-pagination-switch="true"
					data-id-field="id"
					data-response-handler="responseHandler"
					data-auto-refresh = "true"
					data-auto-refresh-status = "false"
				>
				</table>	
			</div>
		</div>		
	</div>
</body>

<script type="text/javascript">

	var $table = $('#table')
	var $remove = $('#remove')
	var selections = []

	function getIdSelections() {
		return $.map($table.bootstrapTable('getSelections'), function(row) {
			return row.id
		})
	}

	function responseHandler(res) {
		$.each(res.rows, function(i, row) {
			row.state = $.inArray(row.id, selections) !== -1
		})
		return res
	}

	function detailFormatter(index, row) {
		var html = []
		$.each(row, function(key, value) {
			html.push('<p><b>' + key + ':</b> ' + value + '</p>')
		})
		return html.join('')
	}

	function userRolesFormatter(value) {
		var roles = "";

		if(value!=null) {
			for (var index = 0; index < value.length; index++) {
				roles += value[index].roleName;

		        // Only show a comma if it's not the last one in the loop
		        if (index < (value.length - 1)) {
		        	roles += ', ';
		        }
		    }
		}
	    return roles;
	}

	
	function operateFormatter(value, row, index) {
		return [ 

				/*'<a class="edit" href="javascript:void(0)" data-toggle="modal" data-target="#myModal" title="Edit">',
				'<i class="fas fa-pen-square"></i>', '</a>  ',*/

				
				/*'<a class="remove " href="javascript:void(0)" title="Remove">',
				'<i class="fa fa-trash"></i>', '</a>',*/

				'<button id="edit" class="edit btn-sm btn-primary" data-toggle="modal" data-target="#myModal" title="Edit">',
				'<i class="fas fa-edit"></i>',
				'</button> ',
				
				'<button id="remove" class="remove btn-sm btn-danger" title="Remove">',
				'<i class="fa fa-trash"></i>',
				'</button>'

				 ].join('')
	}

	
	window.operateEvents = {
		'click .edit' : function(e, value, row, index) {
			//alert('You click edit action, row: ' + JSON.stringify(row));
			
			$('#myModal').appendTo("body").modal('show');
			
			setFormData(row);
			
		},
		'click .remove' : function(e, value, row, index) {

			deleteRecord(row.id);
			refreshMyTable();
			
		}
	}

	function deleteRecord(primeId) {

		var csrfParameter = '${_csrf.parameterName}';
		var csrfToken = '${_csrf.token}';

		var postData = "";

		if(primeId.length > 0) {
			for(var i =0; i<primeId.length; i++) {
				postData = postData + "primeId=" + primeId[i] + "&"
			}
		} else {
			postData = postData + "primeId=" + primeId + "&"
			//postData = postData + "primeId=" + "" + "&"
		}

		postData = postData + csrfParameter + "=" + csrfToken;

		console.log(postData);
		
		$.ajax({
			type : 'post',
			url : 'deleteUser',
			data : postData

		}).done(function(data) {

			alert(data.message);
			refreshMyTable();

		}).fail(function(data) {
			console.log(data);
			alert("failed");
		});
		
	}
	
	function ajaxRequest(params) {

		var url = 'listUsers'
		$.get(url + '?' + $.param(params.data)).then(function(res) {
			console.log(res);
			params.success(res)
		})
	}

	function responseHandler(res) {
		$.each(res.rows, function(i, row) {
			row.state = $.inArray(row.id, selections) !== -1
		})
		return res
	}

	function initTable() {
	    $table.bootstrapTable('destroy').bootstrapTable({
	      formatSearch: function () {
	            return 'Search First Name'
	      },  
	      height: 500,
	      
			columns : [ [ {
						field : 'state',
						checkbox : true,
						align : 'center',
						valign : 'middle'
					}, {
						field : 'id',
						title : 'Id',
						sortable : true,
						align : 'center'
					}, {
						field : 'firstName',
						title : 'First Name',
						sortable : true,
						align : 'center'
					}, {
						field : 'lastName',
						title : 'Last Name',
						sortable : true,
						align : 'center'
					}, {
						field : 'email',
						title : 'Email',
						sortable : true,
						align : 'center'
					}, {
						//field : 'roleNames',
						field : 'userRoles',
						title : 'Roles',
						sortable : false,
						align : 'center',
						formatter: userRolesFormatter
					}, {
						field : 'operate',
						title : 'Action',
						align : 'center',
						sortable : false,
						clickToSelect : false,
						events : window.operateEvents,
						formatter : operateFormatter
					} ] ]
		})
		$table.on('check.bs.table uncheck.bs.table '
				+ 'check-all.bs.table uncheck-all.bs.table', function() {
			$remove.prop('disabled',
					!$table.bootstrapTable('getSelections').length)

			// save your data, here just save the current page
			selections = getIdSelections()
			// push or splice the selections if you want to save all data selections
		})
		$table.on('all.bs.table', function(e, name, args) {
			console.log(name, args)
		})
		$remove.click(function() {
			var ids = getIdSelections()
			
			deleteRecord(ids);
			refreshMyTable();
			
			$remove.prop('disabled', true)
		})

		$table.on('expand-row.bs.table', function(e, index, row, $detail) {

			  var htmlText = "<b>Id: </b>" + row.id + "<br>"	

			  htmlText = htmlText + "<b>First Name: </b>" + row.firstName + "<br>"
			  htmlText = htmlText + "<b>Last Name: </b>" + row.lastName + "<br>"
			  htmlText = htmlText + "<b>Email: </b>" + row.email+ "<br>"
			  htmlText = htmlText + "<b>Roles: </b>" + userRolesFormatter(row.userRoles)+ ""
			  
			  $detail.html(htmlText);
		});
		
	}

	$(function() {
		initTable()

	})
	
	
	$(function() {
		$('#adduser-form').submit(function(event) {
			event.preventDefault();

			$('label').removeClass('text-danger');
			$('input, select').removeClass('is-invalid');
			$('small').html('');

			var form = $(this);

			console.log(form.serialize());
			
			$.ajax({
				type : form.attr('method'),
				url : form.attr('action'),
				data : form.serialize()
			}).done(function(data) {
				
				if(data.successMessage.length > 0) {
					alert(data.successMessage);
					refreshMyTable();
					$('#myModal').appendTo("body").modal('hide');
					
				} else if(data.errorMessage.length > 0) {
					$('#errorMessage').addClass("alert alert-danger");
					$('#errorMessage').html("<p> " + data.errorMessage + "</p>");
				} else if(data.fieldErrors.length > 0) {

					var fieldErrors = data.fieldErrors;
					
					for (var i = 0; i < fieldErrors.length; i++) {

						var field = fieldErrors[i].split("~");
						var labelId = '#label-' + field[0];
						var smallId = '#small-' + field[0];
						var error = field[1];

						$('label[for="' + field[0] + '"]').addClass("text-danger");
						$('#' + field[0]).addClass("is-invalid");
						$('small[id="' + field[0] + '"]').html(error);

					}
				}
				
			}).fail(function(data) {
				console.log(data);
				if(data.status == 403) {
					alert('You are not authorized to access this page');
				}
			});
		});
	});

	function refreshMyTable(number, size, search) {

		var options = $table.bootstrapTable('getOptions');

		var number = options.pageNumber;
		var size = options.pageSize;
		var search = options.searchText;

		var order = '';
		var sort = '';
		
		$.get('listUsers', {
			offset : (number - 1) * size,
			limit : size,
			search : search,
			order : order,
			sort : sort
		}, function(res) {
			$table.bootstrapTable('load', res);
		});
	}

	$(window).on('hidden.bs.modal', function() {

		$('#adduser-form').trigger("reset");

		$('label').removeClass("text-danger");
		$('input, select').removeClass("is-invalid");
		$('small').html('');

		$('#errorMessage').removeClass("alert alert-danger");
		$('#errorMessage').html("");
		$('#modalTitle').html("Add New User");

		$("#adduser-form").attr("action", "${pageContext.request.contextPath}/addUser");
		$("input[name='id']").val("");
		$("#edit-email").html('');

		$("input[id='email']").removeAttr("disabled");
		$("button[id='check']").removeAttr("disabled");
		
		$('#userRoles').find('option').remove().end()
            .append($("#role-options").html());

		$("#processFor").val("add");
		
	});


	$("#check").click(
			function(e) {
				var email = $('#email').val();

				if (email.length > 2) {
					checkEmail(email);
				} else {

					$('small[id="email"]').html(
							"<s:message code = 'status.min.characters' />");

				}
			});

	function checkEmail(email) {

		var csrfParameter = '${_csrf.parameterName}';
		var csrfToken = '${_csrf.token}';

		var jsonParams = {};
		jsonParams['email'] = email;
		jsonParams[csrfParameter] = csrfToken;

		$.ajax({
			type : 'post',
			url : 'checkEmailAvail',
			data : jsonParams,
			dataType : 'json'

		}).done(function(data) {

			$('small[id="email"]').html(data[0]);

		}).fail(function(data) {
			console.log(data);
			alert("failed");
		});
	}

	function setFormData(row) {


		$('#modalTitle').html("Edit User");

		$("input[name='id']").val(row.id);
		$("input[id='firstName']").val(row.firstName);
		$("input[id='lastName']").val(row.lastName);
		$("input[id='email']").val(row.email);


		$("input[id='email']").attr("disabled", "disabled");
		$("button[id='check']").attr("disabled", "disabled");
		

		$("#edit-email").html('<input type = "hidden" name = "email" value = "'+row.email+'"/>');
		
		var userRoles = row.userRoles;

		for(var i = 0; i< userRoles.length; i++) {

			$('select[name=userRoles] option[value='+userRoles[i].id+']').attr('selected','selected');
		}

		$("#adduser-form").attr("action", "${pageContext.request.contextPath}/editUser");

		$("#processFor").val("edit");
	}

	 $(function() {
		    $('#table').bootstrapTable({
		      formatSearch: function () {
		        return 'Search Item Price'
		      }
		    })
		  });
	
</script>

</html>