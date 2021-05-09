package com.narendra.bss.service;

import java.util.List;

import javax.validation.Valid;

import com.google.common.collect.Multimap;
import com.narendra.bss.dto.MenuDTO;
import com.narendra.bss.entity.Menu;

public interface MenuService {

	List<Menu> findNavBarMenus(Integer userId);

	Multimap<String, String> getRoleURLMapping();

	List<Menu> findAll();

	List<Menu> findAllCustom();

	List<Menu> findDisplayMenus();

	void save(MenuDTO menuDTO);

	void deleteByIdIn(List<Integer> primeIds);

	void updateMenu(MenuDTO menuDTO);

	Long countByMenuName(String menuName);

	Long countByMenuNameIdNotEqual(String menuName, Integer id);
}
