package com.narendra.bss.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.narendra.bss.config.security.ForceChangePasswordFilter;
//Changes for beta branch
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		//return new Class[] {JpaConfig.class};
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebAppConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		servletContext.getSessionCookieConfig().setHttpOnly(true);
		//servletContext.getSessionCookieConfig().setSecure(true); Should be enabled when https is used.
		
		System.out.println("onStartUp is called");
	}

	@Override
	protected Filter[] getServletFilters() {
		
		return new Filter[] {new ForceChangePasswordFilter()};
	}
	
}
