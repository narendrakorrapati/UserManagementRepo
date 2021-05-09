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
				    <i class="fas fa-sign-out-alt"></i> Force Logout
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
					data-side-pagination="" 
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
		return ['<button id="remove" class="remove btn-sm btn-danger" title="Force Logout">',
				'<i class="fas fa-sign-out-alt"></i>',
				'</button>'

				 ].join('')
	}

	
	window.operateEvents = {
		'click .remove' : function(e, value, row, index) {

			forceLogoutUser(row.id);
			refreshMyTable();
			
		}
	}

	function forceLogoutUser(primeId) {

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
			url : 'forceLogoutUser',
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

		var url = 'loggedInUsers'
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
			
			forceLogoutUser(ids);
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
	
	function refreshMyTable(number, size, search) {

		var options = $table.bootstrapTable('getOptions');

		var number = options.pageNumber;
		var size = options.pageSize;
		var search = options.searchText;

		var order = '';
		var sort = '';
		
		$.get('loggedInUsers', {
			offset : (number - 1) * size,
			limit : size,
			search : search,
			order : order,
			sort : sort
		}, function(res) {
			$table.bootstrapTable('load', res);
		});
	}

	
</script>

</html>