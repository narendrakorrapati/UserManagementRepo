package com.narendra.bss.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.narendra.bss.dto.MenuDTO;
import com.narendra.bss.dto.MenuTypeDTO;
import com.narendra.bss.entity.Menu;
import com.narendra.bss.entity.UserRole;
import com.narendra.bss.service.MenuService;
import com.narendra.bss.service.UserRoleService;
import com.narendra.bss.utility.LoggedInUserUtility;

@Controller
public class AccessManagementController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LoggedInUserUtility loggedInUserUtility;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	
	@ModelAttribute("roles")
	public List<UserRole> loadUserRoles() {
		return userRoleService.findAll();
	}
	
	@ModelAttribute("parentMenus")
	public List<Menu> loadParentMenus() {
		return menuService.findDisplayMenus();
	}
	
	@ModelAttribute("menuTypeList")
	public List<MenuTypeDTO> loadMenuTypeList() {
		
		List<MenuTypeDTO> menuTypeList = new ArrayList<>();
		
		menuTypeList.add(new MenuTypeDTO("M", "Main Menu"));
		menuTypeList.add(new MenuTypeDTO("S", "Sub Menu"));
		menuTypeList.add(new MenuTypeDTO("P", "Program Menu"));
		
		return menuTypeList;
	}
	
	@GetMapping(value = "/accessManagement")
	public String getAcessManagementUI(@ModelAttribute("menuDTO") MenuDTO menuDTO) {
		return "menuList";
	}
	
	@RequestMapping(value = "/listMenus", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getMenus(@RequestParam Map<String, Object> reqParams) {
		
		logger.info("entered getMenus() method", loggedInUserUtility.getUserName());
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		List<Menu> content = menuService.findAllCustom();
		
		logger.debug("getMenus() content size:", content.size());
		response.put("rows", content);
		
		logger.info("exiting getMenus() method", loggedInUserUtility.getUserName());
		
		return response;
		
	}
	
	@RequestMapping(value = "/addMenu", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> addMenu(@Valid @ModelAttribute("menuDTO") MenuDTO menuDTO,BindingResult result, Locale locale, HttpServletRequest request) {
		
		logger.info("entered addMenu() method", loggedInUserUtility.getUserName());
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<Object> errorList = new ArrayList<Object>();
		String successMessage = "";
		String errorMessage = "";
		
		if(result.hasGlobalErrors()) {
			List<ObjectError> globalErrorList = result.getGlobalErrors();
			
			for(ObjectError error: globalErrorList) {
				if(Arrays.asList(error.getCodes()).contains("MenuParent")) {
					errorList.add("parent" +"~" + messageSource.getMessage(error, locale));
				} else if(Arrays.asList(error.getCodes()).contains("UniqueMenuName")) {
					errorList.add("menuName" +"~" + messageSource.getMessage(error, locale));
				}
			}
		} if(result.hasFieldErrors()) {
			List<FieldError> fieldErrorList = result.getFieldErrors();
			
			for(FieldError error: fieldErrorList) {
				errorList.add(error.getField() +"~" + messageSource.getMessage(error, locale));
			}
		} else {
			try {
				
				menuService.save(menuDTO);
				
				if (menuDTO.getId() != null) {
					successMessage = messageSource.getMessage("menu.add.success.message", null, locale);
					logger.debug("Menu is added successfully", new Object[] {menuDTO.getMenuName(), loggedInUserUtility.getUserName()});
				} else {
					logger.warn("Menu is not added", new Object[] {menuDTO.getMenuName(), loggedInUserUtility.getUserName()});
					errorMessage = "Menu is not added";
				}

			} catch (Exception e) {
				logger.error("error in saving menu:", e);
				errorMessage = "Error in processing:" + e.getMessage();
			}
		}
		response.put("fieldErrors", errorList);
		response.put("successMessage", successMessage);
		response.put("errorMessage", errorMessage);
		
		logger.info("exiting addMenu() method", loggedInUserUtility.getUserName());
		
		return response;
	}
	
	@RequestMapping(value = "/editMenu", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, Object> editMenu(@Valid @ModelAttribute("menuDTO") MenuDTO menuDTO,BindingResult result, Locale locale) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		List<Object> errorList = new ArrayList<Object>();
		String successMessage = "";
		String errorMessage = "";
		
		if(result.hasGlobalErrors()) {
			List<ObjectError> globalErrorList = result.getGlobalErrors();
			
			for(ObjectError error: globalErrorList) {
				if(Arrays.asList(error.getCodes()).contains("MenuParent")) {
					errorList.add("parent" +"~" + messageSource.getMessage(error, locale));
				} else if(Arrays.asList(error.getCodes()).contains("UniqueMenuName")) {
					errorList.add("menuName" +"~" + messageSource.getMessage(error, locale));
				}
			}
		} else {
			
			try {
				menuService.updateMenu(menuDTO);
				
				if(menuDTO.getId() != null) {
					successMessage = messageSource.getMessage("menu.edit.success.message", null, locale);
				} else {
					errorMessage = "Menu is not edited";
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
	
	@RequestMapping(value = "/deleteMenu", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public Map<String, String> deleteMenu(@RequestParam("primeId") List<Integer> primeIds) {
		String message = "";
		Map<String, String> response = new HashMap<String, String>();
		
		try {
			menuService.deleteByIdIn(primeIds);
			message = "Menus are deleted successfully";
		}catch (Exception e) {
			message = "Error in deleting the Menus";
		}
		response.put("message", message);
		
		return response;
	}
}
