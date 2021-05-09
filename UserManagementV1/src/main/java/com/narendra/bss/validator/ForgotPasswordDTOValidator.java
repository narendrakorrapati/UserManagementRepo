package com.narendra.bss.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narendra.bss.dto.ForgotPasswordDTO;

public class ForgotPasswordDTOValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ForgotPasswordDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(target instanceof ForgotPasswordDTO) {
			ForgotPasswordDTO forgotPasswordDTO = (ForgotPasswordDTO)target;
			
			if(!forgotPasswordDTO.getNewPassword().equals(forgotPasswordDTO.getReenterNewPassword())) {
				errors.reject("newpass.reenterpass.should.be.equal");
			}
			
		}
	}

}
