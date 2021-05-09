package com.narendra.bss.config.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MyAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		if(configAttributes == null){
	        return  ;
	    }
		
	    Iterator<ConfigAttribute> ite = configAttributes.iterator();
	    while(ite.hasNext()){

	        ConfigAttribute ca = ite.next();

	        String needRole = ((SecurityConfig)ca).getAttribute();
	        
	        if("ROLE_ANY".equals(needRole)) {
	        	return;
	        }
	        
	        for(GrantedAuthority grantedAuthority : authentication.getAuthorities()){
	            if(needRole.trim().equals(grantedAuthority.getAuthority().trim())){
	                return;
	            }
	        }
	    }
	    
	    System.out.println("Access Denied");
	    throw new AccessDeniedException("Access is denied");

	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
