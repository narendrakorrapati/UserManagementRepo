package com.narendra.bss.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.narendra.bss.dto.MenuDTO;

public class MenuParentValidator implements ConstraintValidator<MenuParent, MenuDTO>{

	private String parentField;
	private String menuTypeField;
	
	@Override
	public void initialize(MenuParent constraintAnnotation) {
		this.parentField = constraintAnnotation.parentField();
		this.menuTypeField = constraintAnnotation.menuTypeField();
	}
	
	@Override
	public boolean isValid(MenuDTO value, ConstraintValidatorContext context) {
		
		Integer parent = (Integer) new BeanWrapperImpl(value).getPropertyValue(parentField);
		String menuType = (String)new BeanWrapperImpl(value).getPropertyValue(menuTypeField);
		
		if(!"M".equals(menuType)) {
			if(parent ==null ||  parent == 0) {
				return false;
			}
		}
		
		return true;
	}
	
}
