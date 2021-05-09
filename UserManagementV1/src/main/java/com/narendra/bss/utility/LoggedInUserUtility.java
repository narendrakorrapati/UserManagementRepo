package com.narendra.bss.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.narendra.bss.config.security.BSSUserDetails;
import com.narendra.bss.entity.User;

@Component
public class LoggedInUserUtility {

	public BSSUserDetails getPrincipal(){
		BSSUserDetails userDetails = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof BSSUserDetails) {
			userDetails = ((BSSUserDetails)principal);
		} else {
			userDetails = new BSSUserDetails(new User());
		}
		return userDetails;
	}
	
	public User getUser(){
		return getPrincipal().getUser();
	}
	
	public String getUserName(){
		return getPrincipal().getUsername();
	}
}
