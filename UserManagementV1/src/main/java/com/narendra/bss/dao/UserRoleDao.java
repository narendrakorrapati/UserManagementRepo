package com.narendra.bss.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.narendra.bss.entity.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole, Integer> {

	UserRole findByRoleName(String roleName);

	UserRole findById(int id);
	
	List<UserRole> findByIdNotIn(List<Integer> ids);
	
	List<UserRole> findByIdIn(List<Integer> ids);
	
	@Modifying
	void deleteByIdIn(List<Integer> ids);
}
