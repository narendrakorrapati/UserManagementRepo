<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>  
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

 <link rel="stylesheet" href = "/static/bootstrap-4.0.0-dist/css/bootstrap.min.css"> 

 <link rel="stylesheet" href = "static/bootstrap-4.0.0-dist/css/bootstrap.min.css">
 <link rel="stylesheet" type="text/css" href = "static/css/app.css">

<script src="static/bootstrap-4.0.0-dist/js/jquery-3.5.1.min.js" ></script>
<script src="static/bootstrap-4.0.0-dist/js/popper.min.js" ></script>
<script src="static/bootstrap-4.0.0-dist/js/bootstrap.min.js" ></script>

<!-- 
<script type="text/javascript">
/// some script


// jquery ready start
$(document).ready(function() {
	// jQuery code

	//////////////////////// Prevent closing from click inside dropdown
    $(document).on('click', '.dropdown-menu', function (e) {
     e.stopPropagation();
    });

	
	
    // make it as accordion for smaller screens
    
    
    if ($(window).width() < 992) {
	  	$('.dropdown-menu a').click(function(e){
	  		//e.preventDefault();
	        if($(this).next('.submenu').length){
	        	$(this).next('.submenu').toggle();
	        	$(this).next('.submenu').focus();
	        }
	        $('.dropdown').on('hide.bs.dropdown', function () {
			   $(this).find('.submenu').hide();
			})
	  	});
	}
	
}); // jquery end 

</script>

<style type="text/css">

.dropdown-item,.dropdown-menu, .submenu {
	background-color: #273c75;
	color: gray;
}

.dropdown-item:hover{
	background-color: #273c75;
	color: white;
}


@media all and (min-width: 992px) {
	.navbar .nav-item #dropDownMenu {
		display: none;
	}
	.navbar .nav-item:hover .nav-link {
		color: #fff;
	}
	.navbar .nav-item:hover #dropDownMenu {
		display: block;
	}
	.navbar .nav-item #dropDown {
		margin-top: 0;
	}
}


@media ( min-width : 992px) {
	.dropdown-menu .dropdown-toggle:after {
		border-top: .3em solid transparent;
		border-right: 0;
		border-bottom: .3em solid transparent;
		border-left: .3em solid;
	}
	.dropdown-menu .dropdown-menu {
		margin-left: 0;
		margin-right: 0;
	}
	.dropdown-menu li {
		position: relative;
	}
	.dropdown-menu .submenu {
		display: none;
		position: absolute;
		left: 100%;
		top: -7px;
	}
	.nav-item .submenu-left {
		right: 100%;
		left: auto;
	}
	
	/*
	.dropdown-menu>li:hover {
		background-color: #f1f1f1
		
	}*/
	
	.dropdown-menu>li:hover>.submenu {
		display: block;
	}
}

</style>
 -->

</head>
<body>

<sec:authorize access="isAuthenticated()" var="loggedin" />
<c:url var="loginurl" value="/login" />
<c:url var="logoutUrl" value="/logout" />

<form action="${logoutUrl}" id="logout" method="post" style="display: none;">
     <input type="hidden" name="${_csrf.parameterName}"
         value="${_csrf.token}" />
 </form>

<nav class = "navbar navbar-dark navbar-expand-sm" style="border-radius: 10px; background-color: #273c75"> 
	<a class ="navbar-brand" href = "#">
	 	BSS
	 </a>
	<button type = "button" data-toggle = "collapse" data-target = "#toggle"  class = "navbar-toggler">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div id="toggle" class="collapse navbar-collapse">
		<ul class="navbar-nav">
		<c:forEach var="menu" items="${MENU_SET}">
			
			<c:if test="${menu.showStatus.equals('1') }"></c:if>
			
			<li class="${menu.childs.size() > 0 ? 'nav-item dropdown' : 'nav-item'}">
				
				<c:if test="${menu.childs.size() == 0 }">
					<a class="nav-link" href="${pageContext.servletContext.contextPath}${menu.menuUrl}">${menu.menuName}</a>
				</c:if>
				
				<c:if test="${menu.childs.size() > 0 }">
					<a class="nav-link dropdown-toggle" data-toggle = "dropdown" role="button" href="#">${menu.menuName}</a>
					<ul class="dropdown-menu" id="dropDownMenu">
						<c:forEach var = "subMenu" items="${menu.childs }">
							<c:if test="${subMenu.menuName == 'Logout'}">
								<a class="dropdown-item" href = "#" onclick="document.getElementById('logout').submit();">${subMenu.menuName}</a>	
								<%--  
								<li id = "subMenuLi">
									<a class="dropdown-item" href = "#" tabindex="-1">${subMenu.menuName} &raquo</a>
									<ul class="submenu dropdown-menu">
										 <li><a class="dropdown-item" href="#" tabindex="-1"> Third level 1 &raquo</a>
										 
										 	<ul class="submenu dropdown-menu">
												 <li><a class="dropdown-item" href=""> Fourth level 1</a></li>
											</ul>
										 </li>
										 
									</ul>
								</li> --%>
							</c:if>
							<c:if test="${subMenu.menuName != 'Logout'}">
								<li><a class="dropdown-item" href="${pageContext.servletContext.contextPath}${subMenu.menuUrl}">${subMenu.menuName}</a></li>	
							</c:if>
						</c:forEach>
					</ul>
				</c:if>
			</li>
		</c:forEach>
	</ul>
	
	<ul class="navbar-nav  ml-auto">
		<li class="nav-item">
			<c:if test="${!loggedin }"><a class="nav-link" href="${loginurl}"> Login </a></c:if> 
			<%-- <c:if test="${loggedin }"> <a class="nav-link" href="${logoutUrl}">Logout</a></c:if> --%>
		</li>
	</ul>
	
	</div>

</nav>
</body>

</html>