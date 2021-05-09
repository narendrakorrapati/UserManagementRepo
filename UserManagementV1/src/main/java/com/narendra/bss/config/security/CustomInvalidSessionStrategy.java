package com.narendra.bss.config.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.InvalidSessionStrategy;

public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {

	private List<String> urls = new ArrayList<String>();
	
	public CustomInvalidSessionStrategy() {
		urls.add("/");
		urls.add("/login");
		urls.add("/invalidSession");
	}
	
	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		System.out.println("Invalid Session Strategy :"+request.getRequestURI());
		request.getSession();//To avoid looping issue, we should create a new session.
		
		if(!urls.contains(request.getRequestURI().replace(request.getContextPath(), ""))) {
			response.sendRedirect(request.getContextPath() + "/invalidSession");
		} else {
			RequestDispatcher dispatcher = request.getServletContext()
				      .getRequestDispatcher(request.getRequestURI().replace(request.getContextPath(), ""));
				    dispatcher.forward(request, response);
				    
					/* response.sendRedirect(request.getRequestURI()); */
		}
	}
}
