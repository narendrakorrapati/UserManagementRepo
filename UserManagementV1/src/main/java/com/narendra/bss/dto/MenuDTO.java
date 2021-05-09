package com.narendra.bss.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.narendra.bss.constraints.MenuParent;
import com.narendra.bss.constraints.UniqueMenuName;


@MenuParent(parentField = "parent", menuTypeField = "menuType")
@UniqueMenuName(idField = "id", menuNameField = "menuName", processForField = "processFor")
public class MenuDTO {

	private Integer id;
	
	@NotBlank
	@Size(min = 3, max = 30)
	private String menuName;
	
	@NotBlank
	@Size(min = 1, max = 1)
	private String menuType;
	
	@NotBlank
	@Size(min = 3, max = 30)
	private String menuUrl;
	
	@NotNull
	private boolean showStatus;
	
	@NotBlank
	@Size(min = 5, max = 30)
	private String privilege;
	
	private Double displayOrder;
	
	@NotNull
	private Integer parent;
	
	@NotEmpty
	List<Integer> userRoles;
	
	private String processFor;
	
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
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
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
	
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public List<Integer> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(List<Integer> userRoles) {
		this.userRoles = userRoles;
	}
	
	public String getProcessFor() {
		return processFor;
	}
	public void setProcessFor(String processFor) {
		this.processFor = processFor;
	}
	@Override
	public String toString() {
		return "MenuDTO [id=" + id + ", menuName=" + menuName + ", menuType=" + menuType + ", menuUrl=" + menuUrl
				+ ", showStatus=" + showStatus + ", privilege=" + privilege + ", displayOrder=" + displayOrder
				+ ", parent=" + parent + ", userRoles=" + userRoles + "]";
	}
}
