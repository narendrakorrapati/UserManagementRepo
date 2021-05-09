package com.narendra.bss.service;

import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.narendra.bss.dao.MenuDao;
import com.narendra.bss.dto.MenuDTO;
import com.narendra.bss.entity.Menu;
import com.narendra.bss.entity.UserRole;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private UserRoleService userRoleService;
	
	
	@Override
	public List<Menu> findNavBarMenus(Integer userId) {
		
		return menuDao.findNavBarMenus(userId);
	}

	@Override
	public Multimap<String, String> getRoleURLMapping() {
		
		Multimap<String, String> multimap = MultimapBuilder.hashKeys().hashSetValues().build();
    	
		List<Object[]> mappings = menuDao.findUrlRoleMappings();
		
    	for(Object[] arr : mappings) {
    		multimap.put(String.valueOf(arr[0]), "ROLE_" + String.valueOf(arr[1]));
    	}
		return multimap;
	}

	@Override
	public List<Menu> findAll() {
		return menuDao.findAll();
	}
	
	@Override
	public List<Menu> findAllCustom() {
		return menuDao.findAllCustom();
	}
	
	@Override
	public List<Menu> findDisplayMenus() {
		return menuDao.findDisplayMenus();
	}

	@Override
	@Transactional
	public void save(MenuDTO menuDTO) {
		Menu entity = new Menu();
		
		entity.setMenuName(menuDTO.getMenuName());
		entity.setMenuType(menuDTO.getMenuType());
		entity.setMenuUrl(menuDTO.getMenuUrl());
		
		entity.setShowStatus(menuDTO.getShowStatus());
		entity.setPrivilege(menuDTO.getPrivilege());
		
		entity.setUserRoles(new HashSet<>(userRoleService.findByIdIn(menuDTO.getUserRoles())));
		
		if(menuDTO.getParent() != null) {
			entity.setParent(menuDao.findById(menuDTO.getParent()).get());
		}
		
		entity.setDisplayOrder(menuDTO.getDisplayOrder());
		menuDao.save(entity);
		
		menuDTO.setId(entity.getId());
		
	}

	@Override
	@Transactional
	public void deleteByIdIn(List<Integer> primeIds) {
		menuDao.deleteByIdIn(primeIds);//Deletes all the associated childs also because of CaseCadeType Remove on the childs in Menu entity
		//If the id is of a child, deletes only the child.
	}

	@Override
	public void updateMenu(MenuDTO menuDTO) {
		
		Menu entity = menuDao.findById(menuDTO.getId()).get();
		
		entity.setMenuName(menuDTO.getMenuName());
		entity.setMenuType(menuDTO.getMenuType());
		entity.setMenuUrl(menuDTO.getMenuUrl());
		
		entity.setShowStatus(menuDTO.getShowStatus());
		entity.setPrivilege(menuDTO.getPrivilege());
		
		entity.setUserRoles(new HashSet<>(userRoleService.findByIdIn(menuDTO.getUserRoles())));
		
		if(menuDTO.getParent() != null && menuDTO.getParent()!=0 && menuDTO.getParent() != entity.getParentId()) {
			entity.setParent(menuDao.findById(menuDTO.getParent()).get());
		}
		
		entity.setDisplayOrder(menuDTO.getDisplayOrder());
		
		menuDao.save(entity);
		menuDTO.setId(entity.getId());
		
	}

	@Override
	public Long countByMenuName(String menuName) {
		return (long)menuDao.countByMenuName(menuName);
	}

	@Override
	public Long countByMenuNameIdNotEqual(String menuName, Integer id) {
		return (long)menuDao.countByMenuNameIdNotEqual(menuName, id);
	}
}
