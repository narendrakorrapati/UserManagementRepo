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
					<c:url var="addMenuURL" value="/addMenu" />
					<form:form action="${addMenuURL}" id ="adduser-form" method="post" modelAttribute="menuDTO">
						<div class="custom-model-header">
							<h4 class="modal-title" id="modalTitle">Add New Menu</h4>
							<p class="text-right"> <span class ="mandatory-field">*</span> <span class="small">Mandatory Fields</span> </p>
						</div>
						<div class="modal-body">

							<div id = "errorMessage">
									
							</div>

							<div class="row">
								<div class="col col-md-6">
									<div class="form-group">
										<label for="menuName" ><s:message code="menuName" text="Menu Name"/></label> <span class="mandatory-field">*</span>
										<form:input path="menuName" id="menuName" cssClass="form-control form-control-sm" required = "true"/>
										<small  class="text-danger" id = "menuName"></small>
									</div>	
								</div>
								<div class="col col-md-6">
									<div class="form-group">
										<label for="menuType" ><s:message code="menuType" text="Menu Type"/></label> <span class="mandatory-field">*</span>
										<form:select path="menuType" id="menuType" cssClass="form-control form-control-sm" required = "true">
											<form:option value="0" label="--Not Selected--" selected = "selected"></form:option>
											<form:options items="${menuTypeList }" itemLabel="name" itemValue="id"/>
										</form:select>
										<small  class="text-danger" id = "menuType"></small>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col col-md-6">
									<div class="form-group">
										<label for="menuUrl" ><s:message code="menuUrl" text="Menu URL"/></label> <span class="mandatory-field">*</span>
										<form:input path="menuUrl" id="menuUrl" cssClass="form-control form-control-sm" required = "true"/>
										<small  class="text-danger" id = "menuUrl"></small>
									</div>	
								</div>
								<div class="col col-md-6">
									<div class="form-group">
										<label for="showStatus" ><s:message code="showStatus" text="Show Status"/></label> <span class="mandatory-field">*</span>
										<div>
											<label>Show </label><form:radiobutton path="showStatus" id="show" value="true" checked="checked"/>
											<label>Hide </label><form:radiobutton path="showStatus" id="hide" value="false"/>
											<small  class="text-danger" id = "showStatus"></small>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col col-md-6">
									<div class="form-group">
										<label for="privilege" ><s:message code="privilege" text="Privilege"/></label> <span class="mandatory-field">*</span>
										<form:input path="privilege" id="privilege" cssClass="form-control form-control-sm" required = "true"/>
										<small  class="text-danger" id = "privilege"></small>
									</div>	
								</div>
								<div class="col col-md-6" id = "displayOrder">
									<div class="form-group">
										<label for="displayOrder" ><s:message code="displayOrder" text="Display Order"/></label>
										<form:input path="displayOrder" id="displayOrder" cssClass="form-control form-control-sm"/>
										<small  class="text-danger" id = "displayOrder"></small>
									</div>	
								</div>
							</div>
							<div class="row">
								<div class="col col-md-6" id = "parentDiv" style="display: none;">
									<div class="form-group">
										<label for="parent" ><s:message code="parent" text="Parent"/></label> <span class="mandatory-field">*</span>
										<form:select path="parent" id = "parent" cssClass="form-control form-control-sm" >
											<form:option value="0" label="--Not Selected--" selected = "selected"></form:option>
											<%-- <form:options items="${parentMenus }" itemLabel="menuName" itemValue="id"/> --%>
										</form:select>
										<small  class="text-danger" id = "parent"></small>
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
							
							<div id = "role-options" style="display: none;">
								<c:forEach var = "role" items="${roles }">
									<option value="${role.id }"> ${role.roleName }</option>
								</c:forEach>
							</div>
							
							<div id = "submenu-options" style="display: none;">
								<c:forEach var = "menu" items="${parentMenus }">
									<c:if test="${menu.menuType == 'S' }">
										<option value="${menu.id }"> ${menu.menuName }</option>
									</c:if>
								</c:forEach>
							</div>
							<div id = "mainmenu-options" style="display: none;">
								<c:forEach var = "menu" items="${parentMenus }">
									<c:if test="${menu.menuType == 'M' }">
										<option value="${menu.id }"> ${menu.menuName }</option>
									</c:if>
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
				    <i class="far fa-caret-square-down"></i> Add
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
					data-side-pagination="client" 
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
					data-show-export="true"
					data-click-to-select="true"
					data-show-pagination-switch="true"
					data-id-field="id"
					data-response-handler="responseHandler"
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
		return ['<button id="edit" class="edit btn-sm btn-primary" data-toggle="modal" data-target="#myModal" title="Edit">',
				'<i class="fas fa-edit"></i>',
				'</button> ',
				
				'<button id="remove" class="remove btn-sm btn-danger" title="Remove">',
				'<i class="fa fa-trash"></i>',
				'</button>'

				 ].join('')
	}

	
	window.operateEvents = {
		'click .edit' : function(e, value, row, index) {
		
			
			$('#myModal').appendTo("body").modal('show');
			
			setFormData(row);
			
		},
		'click .remove' : function(e, value, row, index) {

			var status = confirm("Do you want to delete");

			if(status) {
				deleteRecord(row.id);
				refreshMyTable();
			} else {
				return false;
			}
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
			url : 'deleteMenu',
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

		var url = 'listMenus'
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

	function parentFormatter(value) {
		if(value !=null) {
			return value.menuName;
		}
	}

	function childFormatter(value) {
		var childNames = "";

		if(value!=null) {
			for (var index = 0; index < value.length; index++) {
				childNames += value[index].menuName;

		        // Only show a comma if it's not the last one in the loop
		        if (index < (value.length - 1)) {
		        	childNames += ', ';
		        }
		    }
		}
	    return childNames;
	}
	
	function initTable() {
	    $table.bootstrapTable('destroy').bootstrapTable({
	      formatSearch: function () {
	            return 'Search'
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
						field : 'menuName',
						title : 'Menu Name',
						sortable : true,
						align : 'center'
					},{
						field : 'parentName',
						title : 'Parent Name',
						sortable : true,
						align : 'center'
					}, {
						field : 'menuUrl',
						title : 'URL',
						sortable : true,
						align : 'center'
					}, {
						field : 'showStatus',
						title : 'Display Status',
						sortable : true,
						align : 'center'
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
			//console.log(name, args)
		})
		$remove.click(function() {
			var ids = getIdSelections()
			
			deleteRecord(ids);
			refreshMyTable();
			
			$remove.prop('disabled', true)
		})

		$table.on('expand-row.bs.table', function(e, index, row, $detail) {

			  var htmlText = "<b>Id: </b>" + row.id + "<br>"	
			  htmlText = htmlText + "<b>Childs: </b>" + childFormatter(row.childs)+ "<br>";
			  $detail.html(htmlText);
		});
		
	}

	$(function() {
		initTable()

	});
	
	
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
				console.log(data);
				if(data.successMessage!=null && data.successMessage.length > 0) {
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
				if(data.status == 401) {
					alert('You session has expired!! Please login again');
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
		
		$.get('listMenus', {
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
		$('#modalTitle').html("Add New Menu");

		$("#adduser-form").attr("action", "${addMenuURL}");
		$("input[name='id']").val("");
		
		$("#processFor").val("add");

		$('#userRoles').find('option').remove().end()
        .append($("#role-options").html());

		$("div[id='parentDiv']").hide();


		$('#show').prop('checked', true);
		$('#hide').prop('checked', false);
		$("div[id='displayOrder']").show();
		
	});

	function setFormData(row) {


		$('#modalTitle').html("Edit Menu");

		$("input[name='id']").val(row.id);
		$("input[id='menuName']").val(row.menuName);
		$("select[id='menuType']").val(row.menuType);
		$("input[id='menuUrl']").val(row.menuUrl);
		$("input[id='privilege']").val(row.privilege);
		$("input[id='displayOrder']").val(row.displayOrder);

		
		$("#adduser-form").attr("action", "${pageContext.request.contextPath}/editMenu");

		$("#processFor").val("edit");

		var userRoles = row.userRoles;

		for(var i = 0; i< userRoles.length; i++) {

			$('select[name=userRoles] option[value='+userRoles[i].id+']').attr('selected','selected');
		}

		if(row.showStatus == true) {
			$('#show').prop('checked', true);
			$('#hide').prop('checked', false);
		} else if(row.showStatus == false) {
			$('#hide').prop('checked', true);
			$('#show').prop('checked', false);
		}

		setParentOptions();
		
		if(row.menuType != "M") {
			$("div[id='parentDiv']").show();
		}

		$('select[name=parent] option[value='+row.parentId+']').attr('selected','selected');
		
	}

	function setParentOptions() {
		var menuType = $( "select[id='menuType']" ).val();

		if(menuType == "M" || menuType == "") {
			$("select[id='parent']").find('option').remove().end().append('<option value="0" selected = "selected">--Not Selected--</option>');
			$("div[id='parentDiv']").hide();
		} else if(menuType == "S") {
			$("select[id='parent']").find('option').remove().end().append('<option value="0" selected = "selected">--Not Selected--</option>')
			.append($("#mainmenu-options").html());
			$("div[id='parentDiv']").show();
		} else if(menuType == "P") {
			$("select[id='parent']").find('option').remove().end().append('<option value="0" selected = "selected">--Not Selected--</option>')
			.append($("#submenu-options").html());
			$("div[id='parentDiv']").show();
		} 
	}

	$( "select[id='menuType']" ).change(function (){
		setParentOptions();
	});

	$( "#hide" ).change(function (){
		$("div[id='displayOrder']").hide();
	});
	
	$( "#show" ).change(function (){
		$("div[id='displayOrder']").show();
	});

	$('#show').prop('checked', true);
	$('#hide').prop('checked', false);
			
</script>

</html>