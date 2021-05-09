package com.narendra.bss.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.narendra.bss.entity.User;
import com.narendra.bss.exception.UserNotFoundException;
import com.narendra.bss.service.UserService;

@Component
public class CustomLoginFailedHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		super.setDefaultFailureUrl("/login-error");
		String email = request.getParameter("email");
		
		System.out.println("attempted user id: " + email);
		
		User user = null;
		
		try {
			user = userService.findByEmail(email);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		
		if(user != null) {
			if(!user.isEnabled()) {
				exception = new DisabledException("Your account is disabled. Contact Administrator");
			} else if(user.isAccountNonLocked()) {
				if(user.getFailedLoginAttempts() < UserService.MAX_LOGIN_ATTEMPTS_ALLOWED -1) {
					userService.updatefailedLoginAttempts(user);
				} else {
					userService.lockUser(user);
					exception = new LockedException("Your account has been locked due to 3 failed attempts. It will be unlocked after 24 hours");
				}
			} else if(!user.isAccountNonLocked()){
				boolean status = userService.unlockUser(user);
				
				if(status ) {
					exception = new LockedException("Your account has been unlocked. Please try to login again");
				} else {
					exception = new LockedException("Your account has been locked. Wait until unlock time");
				}
			}
		} else {
			System.out.println("user id doesn't exist");
		}
		
		super.onAuthenticationFailure(request, response, exception);
	}
}
