package com.narendra.bss.service;

import java.util.List;

import com.narendra.bss.dto.UserRoleDTO;
import com.narendra.bss.entity.UserRole;


public interface UserRoleService {

	UserRole findById(int id);

	UserRole findByRoleName(String roleName);
	
	List<UserRole> findAll();
	
	List<UserRole> findByIdNotIn(List<Integer> ids);
	
	List<UserRole> findByIdIn(List<Integer> ids);

	void saveUserRole(UserRoleDTO userRoleDTO);

	void updateUserRole(UserRoleDTO userRoleDTO);

	void deleteUserRole(UserRoleDTO userRoleDTO);

	void saveUserRole(UserRole userRole);

	void deleteByIdIn(List<Integer> ids);
}
