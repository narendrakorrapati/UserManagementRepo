package com.narendra.bss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.narendra.bss.config.security.BSSUserDetails;
import com.narendra.bss.dto.ChangePasswordDTO;
import com.narendra.bss.service.LoggedInUserService;
import com.narendra.bss.service.UserService;
import com.narendra.bss.utility.LoggedInUserUtility;
import com.narendra.bss.validator.ChangePasswordDTOValidator;

@Controller
public class ChangePasswordController {

	@Autowired
	private LoggedInUserUtility loggedInUserUtility;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoggedInUserService loggedInUserService;
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(@ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO,
			ModelMap model, HttpServletResponse response) {

		if (loggedInUserUtility.getPrincipal().getUser().isPasswordExpired()) {
			model.addAttribute("title", "Change your expired Password");
		} else {
			model.addAttribute("title", "Change Password");
		}

		return "changePassword";
	}
	
	@RequestMapping(value = "/processChangePassword", method = RequestMethod.POST)
	public String processChangePassword(@Valid @ModelAttribute("changePasswordDTO") ChangePasswordDTO changePasswordDTO,
			BindingResult result,ModelMap model) {
		
		String responseString = "changePassword";
		
		if (loggedInUserUtility.getPrincipal().getUser().isPasswordExpired()) {
			model.addAttribute("title", "Change your expired Password");
		} else {
			model.addAttribute("title", "Change Password");
		}
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> System.out.println(error));
		} else {
			try {
				userService.verifyOldPassword(changePasswordDTO.getOldPassword(), loggedInUserUtility.getPrincipal().getUser().getPassword());
				userService.saveUser(loggedInUserUtility.getPrincipal().getUser(), changePasswordDTO.getNewPassword());
				responseString = "forward:/changePassSuccess";
			} catch (Exception e) {
				model.addAttribute("errorMessage", e.getMessage());
			}
		}

		return responseString;
	}
	@RequestMapping(value = "/changePassSuccess", method = RequestMethod.POST)
	public String changePassSuccess(HttpServletRequest request, HttpServletResponse response) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			BSSUserDetails userDetails = (BSSUserDetails)auth.getPrincipal();
			loggedInUserService.removeLoggedInUserSession(userDetails.getUser().getId());
			new SecurityContextLogoutHandler().logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return "redirect:/login?reset";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ChangePasswordDTOValidator());
	}
}
