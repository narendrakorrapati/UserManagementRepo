package com.narendra.bss.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.narendra.bss.dto.MenuDTO;
import com.narendra.bss.service.MenuService;

public class UniqueMenuNameValidator implements ConstraintValidator<UniqueMenuName, MenuDTO>{

	@Autowired
	private MenuService menuService;
	
	private String menuNameField;
	private String processForField;
	private String idField;
	
	@Override
	public void initialize(UniqueMenuName constraintAnnotation) {
		this.menuNameField = constraintAnnotation.menuNameField();
		this.processForField = constraintAnnotation.processForField();
		this.idField = constraintAnnotation.idField();
	}
	
	@Override
	public boolean isValid(MenuDTO value, ConstraintValidatorContext context) {
		
		String menuName = (String) new BeanWrapperImpl(value).getPropertyValue(menuNameField);
		String processFor = (String)new BeanWrapperImpl(value).getPropertyValue(processForField);
		Integer id = (Integer)new BeanWrapperImpl(value).getPropertyValue(idField);
		
		if("add".equals(processFor)) {
			if(menuService.countByMenuName(menuName) > 0) {
				return false;
			}
		}
		if("edit".equals(processFor)) {
			if(menuService.countByMenuNameIdNotEqual(menuName, id) > 0) {
				return false;
			}
		}
		
		return true;
	}
	
}
