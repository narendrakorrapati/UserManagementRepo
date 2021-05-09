package com.narendra.bss.service;

import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.narendra.bss.dao.UserRoleDao;
import com.narendra.bss.dto.UserRoleDTO;
import com.narendra.bss.entity.UserRole;

@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService{
	
	@Autowired
	private UserRoleDao dao;
	
	@Autowired
	private ModelMapper modelMapper;

	
	public UserRole findById(int id) {
		return dao.findById(id);
	}

	public List<UserRole> findByIdIn(List<Integer> ids) {
		
		return dao.findByIdIn(ids);
	}
	
	public UserRole findByRoleName(String roleName){
		return dao.findByRoleName(roleName);
	}

	public List<UserRole> findAll() {
		return dao.findAll();
	}

	@Override
	public List<UserRole> findByIdNotIn(List<Integer> ids) {
		return dao.findByIdNotIn(ids);
	}
	
	@Override
	public void saveUserRole(UserRoleDTO userRoleDTO) {
		UserRole userRole = modelMapper.map(userRoleDTO, UserRole.class);
		saveUserRole(userRole);
		userRoleDTO.setId(userRole.getId());
	}
	
	@Override
	@Transactional
	public void updateUserRole(UserRoleDTO userRoleDTO) {
		UserRole userRole = dao.findById(userRoleDTO.getId()).get();
		userRole.setRoleName(userRoleDTO.getRoleName());
	}
	
	@Override
	public void deleteUserRole(UserRoleDTO userRoleDTO) {
		UserRole userRole = modelMapper.map(userRoleDTO, UserRole.class);
		dao.delete(userRole);
	}
	
	@Override
	public void saveUserRole(UserRole userRole) {
		dao.save(userRole);
	}
	
	@Override
	@Transactional
	public void deleteByIdIn(List<Integer> ids) {
		dao.deleteByIdIn(ids);
	}
}
