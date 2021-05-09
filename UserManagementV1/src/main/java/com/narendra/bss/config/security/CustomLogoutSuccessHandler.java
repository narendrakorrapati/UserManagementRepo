package com.narendra.bss.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.narendra.bss.entity.User;
import com.narendra.bss.service.LoggedInUserService;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	
	@Autowired
	private LoggedInUserService loggedInUserService;
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		BSSUserDetails userDetails = (BSSUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		
		loggedInUserService.removeLoggedInUserSession(user.getId());
		
		super.setDefaultTargetUrl("/login?logout");
		super.onLogoutSuccess(request, response, authentication);
	}

}
