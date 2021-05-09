package com.narendra.bss.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.narendra.bss.constraints.UniqueEmail;

@UniqueEmail(emailFiled = "email", idField = "id", processForField = "processFor")
public class UserDTO {

	private Integer id;
	
	@NotBlank
	@Size(min = 3, max = 35)
	private String firstName;
	
	@NotBlank
	@Size(min = 3, max = 35)
	private String lastName;
	
	@NotBlank
	@Email
	private String email;
	
	@NotEmpty
	@Size(min = 1, max = 3)
	private List<Integer> userRoles;
	
	private String processFor;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Integer> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<Integer> userRoles) {
		this.userRoles = userRoles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcessFor() {
		return processFor;
	}

	public void setProcessFor(String processFor) {
		this.processFor = processFor;
	}

	@Override
	public String toString() {
		return "UserModel [firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", userRoles=" + userRoles + "]";
	}
	
}
