package com.narendra.bss.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.narendra.bss.dto.ChangePasswordDTO;

public class ChangePasswordDTOValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(target instanceof ChangePasswordDTO) {
			ChangePasswordDTO changePasswordDTO = (ChangePasswordDTO)target;
			
			if(changePasswordDTO.getOldPassword().equals(changePasswordDTO.getNewPassword())) {
				errors.reject("oldpass.newpass.should.not.equal");
			}
			
			if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getReenterNewPassword())) {
				errors.reject("newpass.reenterpass.should.be.equal");
			}
		}
		
	}

}
