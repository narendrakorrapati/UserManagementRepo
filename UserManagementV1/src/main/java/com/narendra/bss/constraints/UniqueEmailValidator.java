package com.narendra.bss.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.narendra.bss.dto.UserDTO;
import com.narendra.bss.service.UserService;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDTO>{

	@Autowired
	private UserService userService;
	
	private String emailFiled;
	private String processForField;
	private String idField;
	
	@Override
	public void initialize(UniqueEmail constraintAnnotation) {
		this.emailFiled = constraintAnnotation.emailFiled();
		this.processForField = constraintAnnotation.processForField();
		this.idField = constraintAnnotation.idField();
	}
	
	@Override
	public boolean isValid(UserDTO value, ConstraintValidatorContext context) {
		
		String email = (String) new BeanWrapperImpl(value).getPropertyValue(emailFiled);
		String processFor = (String)new BeanWrapperImpl(value).getPropertyValue(processForField);
		Integer id = (Integer)new BeanWrapperImpl(value).getPropertyValue(idField);
		
		if("add".equals(processFor)) {
			if(userService.countByEmail(email) > 0) {
				return false;
			}
		}
		if("edit".equals(processFor)) {
			if(userService.countByEmailIdNotEqual(email, id) > 0) {
				return false;
			}
		}
		
		return true;
	}
	
}
