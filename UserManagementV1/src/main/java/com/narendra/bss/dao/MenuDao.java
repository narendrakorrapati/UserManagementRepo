package com.narendra.bss.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.narendra.bss.entity.Menu;

public interface MenuDao extends JpaRepository<Menu, Integer>{

	List<Menu> findNavBarMenus(@Param("userId") Integer userId);
	
	@Query(value = " select m.menuUrl, r.roleName from Menu m inner join m.userRoles r order by m.menuUrl ")
	List<Object[]> findUrlRoleMappings();
	
	List<Menu> findAllCustom();
	
	List<Menu> findDisplayMenus();
	
	@Modifying
	void deleteByIdIn(List<Integer> ids);

	Object countByMenuName(String menuName);
	
	Object countByMenuNameIdNotEqual(@Param("menuName") String menuName, @Param("id") Integer id);

	//@Modifying
	//void deleteRoleMenuMapping(List<Integer> primeIds);
}
