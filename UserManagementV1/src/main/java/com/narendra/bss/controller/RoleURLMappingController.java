package com.narendra.bss.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

import com.narendra.bss.service.MenuService;

@Controller
public class RoleURLMappingController implements ServletContextAware {

	@Autowired
	private MenuService menuService;
	
	private ServletContext servletContext;
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		
		System.out.println("***********Called*************");
		this.servletContext = servletContext;
		
		servletContext.setAttribute("ROLE_URL_MAPPINGS", menuService.getRoleURLMapping());
		System.out.println(servletContext.getAttribute("ROLE_URL_MAPPINGS"));
	}
	
	@RequestMapping(value = "/addRoleURLMapping", method = RequestMethod.POST)
	public String addRoleURLMapping() {
		//To do: to map the new user roles to urls
		return "success";
	}
}
