package com.narendra.bss.config.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.narendra.bss.entity.User;

@Component
public class ForceChangePasswordFilter implements Filter {

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String requestUrl = httpRequest.getRequestURL().toString();
		
		if (requestUrl.contains("/static/") || requestUrl.contains("/changePassword") || requestUrl.contains("/logout")
				|| requestUrl.contains("/processChangePassword") || requestUrl.contains("/logout")) {
			chain.doFilter(httpRequest, response);
			return;
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = null;
		
		if(authentication != null) {
			principal = authentication.getPrincipal();
		}
		
		if(principal != null && principal instanceof BSSUserDetails) {
			BSSUserDetails userDetails = (BSSUserDetails) principal;
			User user = userDetails.getUser();
			
			if(user.isPasswordExpired() || user.isFirstTimeLogin()) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/changePassword");
			} else {
				chain.doFilter(httpRequest, response);
			}
			
		} else {
			chain.doFilter(httpRequest, response);
		}
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
