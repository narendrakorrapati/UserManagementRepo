<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  
  <link rel="stylesheet" href = "/static/bootstrap-4.0.0-dist/css/bootstrap.min.css">
  
 <!-- <link rel="stylesheet" href = "static/bootstrap-4.0.0-dist/css/bootstrap.min.css"> -->
 <link rel="stylesheet" type="text/css" href = "static/css/app.css">
 
 <link rel="stylesheet" href = "static/bootstrap-4.0.0-dist/css/bootstrap-table.min.css">
 <link rel="stylesheet" href = "static/fontawesome-free-5.15.1-web/css/all.css">
 
 
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
    <sec:authorize access="isAuthenticated()" var="loggedin" />
    <header>
    	<div class="p-md-1" id="topHeader">
	    	<div class="container-fluid">
	    		<div class="row">
	    			<div class="col-md-6">
	    				<div class="text-left">
	    					<img alt="logo" width="25%" src="static/images/company-logo.png" >
	    				</div>
	    			</div>
	    			<div class="col-md-3 offset-3">
	    				<div class="text-primary">
    						<c:if test="${loggedin }">
		    					<b>
		    						Welcome <sec:authentication property="principal.user.firstName"/> <sec:authentication property="principal.user.lastName"/> <br>
		    						Roles: ${USER_ROLES} 
		    					</b>
		    				</c:if>
		    			</div>
	    			</div>
	    		</div>
	    	</div>
    	</div>
    	<div class="p-md-1" id="bottomHeader">
	    	<div class="container-fluid">
	    		<div class="row">
	    			<div class="col-md-12">
	    				<tiles:insertAttribute name="menu" />
	    			</div>
	    		</div>
	    	</div>
    	</div>
    </header>
    
	<div class="container-fluid">
	   	<div class="row">
	   		<div class="col-md-12 p-2">
	   			<tiles:insertAttribute name="body" />
	   		</div>
	   	</div>
   	</div>
	
    <footer>
    	<div class="navbar fixed-bottom" style="background-color: #efefef">
    		<div class="mx-auto order-0">
		        Footer
		    </div>
    	</div>
    </footer>
 </body>

	 
<script src="static/bootstrap-4.0.0-dist/js/tableExport.min.js" ></script>
<script src="static/bootstrap-4.0.0-dist/js/bootstrap-table.min.js" ></script>
<script src="static/bootstrap-4.0.0-dist/js/bootstrap-table-auto-refresh.min.js" ></script>
<script src="static/bootstrap-4.0.0-dist/js/bootstrap-table-export.min.js" ></script>

</html>
