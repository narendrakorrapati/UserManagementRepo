package com.narendra.bss.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Menus")
@NamedQueries({
		@NamedQuery(name = Menu.FIND_NAV_BAR_MENUS, query = "select distinct m from Menu m left join fetch m.childs c inner join fetch m.userRoles r inner join fetch r.users u where u.id = ?1 and m.showStatus = '1' and m.menuType = 'M' order by m.displayOrder "), 
		@NamedQuery(name = Menu.FIND_ALL, query = "select distinct m from Menu m left join fetch m.childs c join fetch m.userRoles r order by m.id"),
		@NamedQuery(name = Menu.FIND_DISPLAY_MENUS, query = "select m from Menu m where m.menuType in ('M','S') and m.showStatus = '1' order by m.menuName"),
		@NamedQuery(name = Menu.COUNT_BY_MENUNAME_WHERE_ID_NOT_EQUAL_TO, query = "select count(*) from Menu m where m.menuName = :menuName and m.id <> :id")
})
public class Menu implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String FIND_NAV_BAR_MENUS = "Menu.findNavBarMenus";
	public static final String FIND_ALL = "Menu.findAllCustom";
	public static final String FIND_DISPLAY_MENUS = "Menu.findDisplayMenus";
	public static final String DELETE_ROLE_MENU_MAPPING = "Menu.deleteRoleMenuMapping";
	public static final String COUNT_BY_MENUNAME_WHERE_ID_NOT_EQUAL_TO = "Menu.countByMenuNameIdNotEqual";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String menuName;
	
	private String menuType;
	
	private String menuUrl;
	
	private boolean showStatus;
	
	private String privilege;
	
	@ManyToMany
	@JoinTable(name = "ROLES_MENUS", joinColumns = { @JoinColumn(name = "menu_id") }, inverseJoinColumns = {
			@JoinColumn(name = "role_id") })
	@OrderBy("roleName ASC")
	private Set<UserRole> userRoles;
	
	@ManyToOne
	@JoinColumn(name = "menu_link_id")
	@JsonIgnore//for a parent by id the JSON implementation was triggering a recursion between Parent and Child
	//https://stackoverflow.com/questions/52671963/java-lang-instrument-assertion-failed-after-adding-dependency-for-spring-boot-ac
	private Menu parent;
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE) //When parent is removed, all childs are removed automatically.
	@OrderBy("displayOrder ASC")
	private Set<Menu> childs;
	
	private Double displayOrder;
	
	@Transient
	private String parentName;
	
	@Transient
	private Integer parentId;
	
	public Menu() {
		
	}

	public Menu(String menuName, String menuUrl) {
		super();
		this.menuName = menuName;
		this.menuUrl = menuUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Set<Menu> getChilds() {
		return childs;
	}

	public void setChilds(Set<Menu> childs) {
		this.childs = childs;
	}
	
	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public boolean getShowStatus() {
		return showStatus;
	}

	public void setShowStatus(boolean showStatus) {
		this.showStatus = showStatus;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
	public Double getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Double displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@PostLoad
	public void setParentDetailsForJSON() {
		
		if(this.parent !=null) {
			this.parentId = parent.getId();
			this.parentName = parent.getMenuName();
		}
	}
	
	@Override
	public String toString() {
		return "Menu [id=" + id + ", menuName=" + menuName + ", menuType=" + menuType + "]";
	}
	
	
}
