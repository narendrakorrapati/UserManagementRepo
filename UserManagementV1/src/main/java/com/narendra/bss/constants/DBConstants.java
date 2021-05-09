package com.narendra.bss.constants;

public class DBConstants {

	public static final String MAIN_MENUS;
	
	static {
		MAIN_MENUS = new StringBuilder().append(" select distinct m.id, m.menuName, m.menuType, u.url_name, m.menu_link_id ")
				  .append(" from menus m, urls u, roles_menus rm, users us, roles_users ru ")
				  .append(" where u.id = m.url_id and rm.menu_id = m.id ")
				  .append(" and rm.role_id = ru.ROLE_ID and us.id = ru.USER_ID ")
				  .append(" and m.menuType = 'M' and us.id = :userId ").toString();
						
	}
}
