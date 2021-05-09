package com.narendra.bss.controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.narendra.bss.dto.ForgotPasswordDTO;
import com.narendra.bss.entity.User;
import com.narendra.bss.exception.UserNotFoundException;
import com.narendra.bss.service.UserService;
import com.narendra.bss.validator.ForgotPasswordDTOValidator;

@Controller
public class ForgotPasswordController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String forgotPassword() {
		return "forgotPassword";
	}
	
	@RequestMapping(value = "/sendResetPassLink", method = RequestMethod.POST)
	public String sendResetPassLink(@RequestParam("email") String email, Model model, HttpServletRequest request, Locale locale) {
		
		User user = null;
		
		try {
			user = userService.findByEmail(email);
			String token = RandomStringUtils.random(50, true, false);
			
			user.setForgotPasswordToken(token);
			
			String resetLink = request.getRequestURL().toString().replace("sendResetPassLink", "");
			resetLink = resetLink + "resetPassword?token=" + token;
			
			userService.sendResetPassLink(user, resetLink, locale);
			model.addAttribute("errorMessage", "Email Sent to your email id");
			
		} catch (UserNotFoundException e) {
			model.addAttribute("errorMessage", e.getMessage());
		} catch (MessagingException e) {
			model.addAttribute("errorMessage", "We are not able to send email now! Please try after some time");
		}
		
		return "forgotPassword";
	}
	
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPasswordForm(@ModelAttribute("forgotPasswordDTO") ForgotPasswordDTO forgotPasswordDTO,
			Model model) {
	
		String responseString = "forgotPassword";
		User user = null;
		
		try {
			user = userService.findByForgotPasswordToken(forgotPasswordDTO.getToken());
			responseString = "resetPassword";
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
		
		return responseString;
	}
	
	
	@RequestMapping(value = "/processRestPassword", method = RequestMethod.POST)
	public String processRestPassword(@Valid @ModelAttribute("forgotPasswordDTO") ForgotPasswordDTO forgotPasswordDTO,
			BindingResult result, Model model) {

		String responseString = "resetPassword";
		User user = null;
		
		if(!result.hasErrors()) {
			try {
				user = userService.findByForgotPasswordToken(forgotPasswordDTO.getToken());
				user.setForgotPasswordToken(null);
				userService.saveUser(user, forgotPasswordDTO.getNewPassword());
				responseString = "redirect:/login?reset";
			} catch (Exception e) {
				model.addAttribute("errorMessage", e.getMessage());
			}
		}
		
		return responseString;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new ForgotPasswordDTOValidator());
	}
}
