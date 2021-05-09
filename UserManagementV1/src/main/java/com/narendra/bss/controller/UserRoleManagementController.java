package com.narendra.bss.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.narendra.bss.dto.UserRoleDTO;
import com.narendra.bss.entity.UserRole;
import com.narendra.bss.propertyeditor.StringUpperCasePropertyEditor;
import com.narendra.bss.service.UserRoleService;

@Controller
public class UserRoleManagementController {

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	
	@GetMapping(value = "/userRoleManagement")
	public String userRoleManagementUI(@ModelAttribute("userRoleDTO") UserRoleDTO userRoleDTO) {
		return "userRoleList";
	}
	
	@RequestMapping(value = "/listUserRoles", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getUserRoles(@RequestParam Map<String, Object> reqParams) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		List<UserRole> content = userRoleService.findAll();
		
		response.put("rows", content);
		
		return response;
		
	}
	
	@RequestMapping(value = "/addUserRole", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> addUserRole(@Valid @ModelAttribute("userRoleDTO") UserRoleDTO userRoleDTO,BindingResult result, Locale locale, HttpServletRequest request) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<Object> errorList = new ArrayList<Object>();
		String successMessage = "";
		String errorMessage = "";
		
		if(result.hasGlobalErrors()) {
			List<ObjectError> globalErrorList = result.getGlobalErrors();
			
			for(ObjectError error: globalErrorList) {
				if(Arrays.asList(error.getCodes()).contains("UniqueRoleName")) {
					errorList.add("roleName" +"~" + messageSource.getMessage(error, locale));
				}
			}
		} else if(result.hasFieldErrors()) {
			List<FieldError> fieldErrorList = result.getFieldErrors();
			
			for(FieldError error: fieldErrorList) {
				errorList.add(error.getField() +"~" + messageSource.getMessage(error, locale));
			}
		} else {
			try {
				userRoleService.saveUserRole(userRoleDTO);
				
				if (userRoleDTO.getId() != null) {
					successMessage = messageSource.getMessage("userrole.add.success.message", null, locale);
				} else {
					errorMessage = "User Role is not added";
				}

			} catch (Exception e) {
				errorMessage = "Error in processing:" + e.getMessage();
			}
		}
		response.put("fieldErrors", errorList);
		response.put("successMessage", successMessage);
		response.put("errorMessage", errorMessage);
		
		return response;
	}
	
	@RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> deleteUserRole(@RequestParam("primeId") List<Integer> primeIds) {
		String message = "";
		Map<String, String> response = new HashMap<String, String>();
		
		try {
			userRoleService.deleteByIdIn(primeIds);
			message = "User Roles are deleted successfully";
		}catch (Exception e) {
			message = "Error in deleting the User Roles";
		}
		response.put("message", message);
		
		return response;
	}
	
	@RequestMapping(value = "/editUserRole", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> editUserRole(@Valid @ModelAttribute("userRoleDTO") UserRoleDTO userRoleDTO,BindingResult result, Locale locale) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<Object> errorList = new ArrayList<Object>();
		String successMessage = "";
		String errorMessage = "";
		
		if(result.hasFieldErrors()) {
			List<FieldError> fieldErrorList = result.getFieldErrors();
			
			for(FieldError error: fieldErrorList) {
				errorList.add(error.getField() +"~" + messageSource.getMessage(error, locale));
			}
		} else {
			
			try {
				userRoleService.updateUserRole(userRoleDTO);
				
				if(userRoleDTO.getId() != null) {
					successMessage = messageSource.getMessage("userrole.edit.success.message", null, locale);
				} else {
					errorMessage = "User Role is not edited";
				}
				
			} catch (Exception e) {
				errorMessage = "Error in processing";
			}
		}
		
		response.put("fieldErrors", errorList);
		response.put("successMessage", successMessage);
		response.put("errorMessage", errorMessage);
		
		return response;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		binder.registerCustomEditor(String.class, "roleName", new StringUpperCasePropertyEditor());
	}
}
