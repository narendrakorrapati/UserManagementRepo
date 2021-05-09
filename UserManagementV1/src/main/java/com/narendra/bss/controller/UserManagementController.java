package com.narendra.bss.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.narendra.bss.config.security.BSSUserDetails;
import com.narendra.bss.dto.ChangePasswordDTO;
import com.narendra.bss.dto.UserDTO;
import com.narendra.bss.entity.User;
import com.narendra.bss.entity.UserRole;
import com.narendra.bss.service.UserRoleService;
import com.narendra.bss.service.UserService;
import com.narendra.bss.validator.ChangePasswordDTOValidator;

@Controller
public class UserManagementController {

	private static final Logger logger = Logger.getLogger(UserManagementController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	
	@ModelAttribute("roles")
	public List<UserRole> loadUserRoles() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(4);
		return userRoleService.findByIdNotIn(ids);
	}
	
	@RequestMapping(value = "/userManagement", method = RequestMethod.GET)
	public String getAllUsers(@ModelAttribute("userModel") UserDTO userModel, Model model) {
		
		return "userList";
	}
	
	@RequestMapping(value = "/listUsers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getUsersJson(@RequestParam Map<String, Object> reqParams) {
		
		System.out.println(reqParams);
		
		int offSet = Integer.parseInt(reqParams.get("offset").toString());
		int limit = Integer.parseInt(reqParams.get("limit").toString());
		
		offSet = offSet/limit;
		
		Page<User> userPage = userService.findAllUsers(offSet, Integer.parseInt(reqParams.get("limit").toString()),
				String.valueOf(reqParams.get("sort")), String.valueOf(reqParams.get("order")), String.valueOf(reqParams.get("search")));
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		response.put("total", userPage.getTotalElements());
		response.put("rows", userPage.getContent());
		response.put("pageNumber", Integer.parseInt(reqParams.get("offset").toString()) + 1);
		
		return response;
		
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> deleteUser(@RequestParam("primeId") List<Integer> primeIds) {
		String message = "";
		Map<String, String> response = new HashMap<String, String>();
		
		try {
			userService.deleteByIdIn(primeIds);
			message = "Users are deleted successfully";
		}catch (Exception e) {
			message = "Error in deleting the Users";
			logger.error(e);
		}
		
		response.put("message", message);
		
		return response;
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> addUser(@Valid @ModelAttribute("userModel") UserDTO userModel,BindingResult result, Locale locale, HttpServletRequest request) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<Object> errorList = new ArrayList<Object>();
		String popup = "hide";
		String successMessage = "";
		String errorMessage = "";
		
		
		if(result.hasGlobalErrors()) {
			List<ObjectError> globalErrorList = result.getGlobalErrors();
			
			for(ObjectError error: globalErrorList) {
				if(Arrays.asList(error.getCodes()).contains("UniqueEmail")) {
					errorList.add("email" +"~" + messageSource.getMessage(error, locale));
				}
			}
		} else if(result.hasFieldErrors()) {
			List<FieldError> fieldErrorList = result.getFieldErrors();
			
			for(FieldError error: fieldErrorList) {
				errorList.add(error.getField() +"~" + messageSource.getMessage(error, locale));
			}
		} else {
			
			try {
				String siteLink = request.getRequestURL().toString().replace("addUser", "");
				
				userService.saveUser(userModel, siteLink, locale);
				if (userModel.getId() != null) {
					successMessage = messageSource.getMessage("user.add.success.message", null, locale);
				} else {
					errorMessage = "User is not added";
				}

			} catch (Exception e) {
				errorMessage = "Error in processing:" + e.getMessage();
				logger.error(e);
			}

		}
		
		response.put("popup", popup);
		response.put("fieldErrors", errorList);
		response.put("successMessage", successMessage);
		response.put("errorMessage", errorMessage);
		
		return response;
	}
	
	@RequestMapping(value = "/editUser", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> editUser(@Valid @ModelAttribute("userModel") UserDTO userModel,BindingResult result, Locale locale) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<Object> errorList = new ArrayList<Object>();
		String popup = "hide";
		String successMessage = "";
		String errorMessage = "";
		
		if(result.hasFieldErrors()) {
			List<FieldError> fieldErrorList = result.getFieldErrors();
			
			for(FieldError error: fieldErrorList) {
				errorList.add(error.getField() +"~" + messageSource.getMessage(error, locale));
			}
		} else {
			
			try {
				userService.updateUser(userModel);
				if(userModel.getId() != null) {
					successMessage = messageSource.getMessage("user.edit.success.message", null, locale);
				} else {
					errorMessage = "User is not edited";
				}
				
			} catch (Exception e) {
				errorMessage = "Error in processing";
				logger.error(e);
			}

		}
		
		response.put("popup", popup);
		response.put("fieldErrors", errorList);
		response.put("successMessage", successMessage);
		response.put("errorMessage", errorMessage);
		
		return response;
	}
	
	
	@RequestMapping(value = "/checkEmailAvail", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public List<Object> checkEmailAvail(@RequestParam("email") String email, Locale locale) {
		
		String response = "";
		
		if(userService.countByEmail(email) > 0) {
			response = messageSource.getMessage("com.narendra.bss.constraints.UniqueEmail", null, locale);
		} else {
			response = "Available";
		}
		
		List<Object> list = new ArrayList<Object>();
		list.add(response);
		
		return list;
	}
	
	@ExceptionHandler
	public String handleException(Exception e) {
		
		e.printStackTrace();
		System.out.println(e.getCause());
		System.out.println(e.getMessage());
		
		return "dashboard";
	}
}
