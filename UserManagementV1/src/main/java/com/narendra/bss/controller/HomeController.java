package com.narendra.bss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.narendra.bss.utility.LoggedInUserUtility;

@Controller
public class HomeController {

	@Autowired
	private AuthenticationTrustResolver authenticationTrustResolver;
	
	@Autowired
	private LoggedInUserUtility loggedInUserUtility;
	
	@RequestMapping(value = {"/welcome", "/"}, method = RequestMethod.GET)
	public String welcomePage() {
		
		return "welcome";
	}
	
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String loginPage(HttpServletRequest request, Model model) {
	
		if(isCurrentAuthenticationAnonymous()) {
			return "login";
		} else {
			return "redirect:/dashboard";
		}
	}
	
	@RequestMapping("/dashboard")
	public String dashBoardPage(Model model) {
		return "dashboard";
	}
	
	/**
	 * This method handles logout requests.
	 * Toggle the handlers if you are RememberMe functionality is useless in your app.
	 */

	/*
	 * @RequestMapping(value = "/logout", method = RequestMethod.GET) public String
	 * logoutPage(HttpServletRequest request, HttpServletResponse response) {
	 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	 * if (auth != null) { new SecurityContextLogoutHandler().logout(request,
	 * response, auth); SecurityContextHolder.getContext().setAuthentication(null);
	 * }
	 * 
	 * if (request.getParameter("reset") != null) { return "redirect:/login?reset";
	 * }
	 * 
	 * return "redirect:/login?logout"; }
	 */
	 
	
	@RequestMapping(value = "/accessDenied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model,
			HttpServletResponse response) {
		model.addAttribute("loggedinuser", loggedInUserUtility.getPrincipal().getUsername());
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return "accessDenied";
	}
	
	@RequestMapping(value = "/invalidSession", method = RequestMethod.GET)
	public String invalidSession(ModelMap model, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		model.addAttribute("errorMessage", "Session Expired!!");
		return "login";
	}
	
	@RequestMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = ex.getMessage();
            }
        }
        System.out.println(errorMessage);
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }
	
	private boolean isCurrentAuthenticationAnonymous() {
	    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    return authenticationTrustResolver.isAnonymous(authentication);
	}
}
