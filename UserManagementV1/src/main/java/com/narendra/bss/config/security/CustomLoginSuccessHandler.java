package com.narendra.bss.config.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.narendra.bss.entity.Menu;
import com.narendra.bss.entity.User;
import com.narendra.bss.service.LoggedInUserService;
import com.narendra.bss.service.MenuService;
import com.narendra.bss.service.UserService;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	@Autowired
	private UserService userService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private LoggedInUserService loggedInUserService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		BSSUserDetails userDetails = (BSSUserDetails)authentication.getPrincipal();
		User user = userDetails.getUser();
		
		String userRoles = user.getUserRoles().toString().replaceAll("\\[", "").replaceAll("\\]", "");
		
		List<Menu> menus = menuService.findNavBarMenus(user.getId());
		
		for(Menu menu: menus) {
			
			System.out.println(menu);
			
			if(menu.getChilds().size() > 0) {
				System.out.println(menu.getChilds());
			}
		}
		
		loggedInUserService.addLoggedInUserSession(user.getId(), request.getSession());
		
		request.getSession().setMaxInactiveInterval(600);
		
		request.getSession().setAttribute("MENU_SET", menus);
		request.getSession().setAttribute("USER_ROLES", userRoles);
		
		if(user.getFailedLoginAttempts() > 0) {
			userService.resetFailedLoginAttempts(user.getId());
		}
		super.setDefaultTargetUrl("/dashboard");
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
