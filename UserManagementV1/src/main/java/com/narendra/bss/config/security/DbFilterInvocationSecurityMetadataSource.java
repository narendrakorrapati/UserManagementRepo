package com.narendra.bss.config.security;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.google.common.collect.Multimap;

public class DbFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		FilterInvocation fi=(FilterInvocation)object;
	    String url=fi.getRequestUrl();

	    System.out.println("URL requested: " + url);

	    @SuppressWarnings("unchecked")
	    Multimap<String, String> mappings = (Multimap<String, String>)fi.getHttpRequest().getServletContext().getAttribute("ROLE_URL_MAPPINGS");
		
	    String[] stockArr = null;
	    
	    if(mappings == null) {
	    	stockArr = new String[] {};
	    } else {
	    	Collection<String> roles =  mappings.get(url);
	    	
	    	if(roles == null || roles.size() ==0) {
	    		stockArr = new String[] {};
	    		if(url.startsWith("/static/") || url.equals("/")) {
	    			stockArr = new String[] {"ROLE_ANY"};
	    		}
	    	} else {
	    		stockArr = roles.toArray(new String[mappings.get(url).size()]);
	    	}
	    }
	    
	    return SecurityConfig.createList(stockArr);
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		System.out.println("getAllConfigAttributes() called");
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
