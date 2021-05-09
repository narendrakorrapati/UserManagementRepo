package com.narendra.bss.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.narendra.bss.entity.Menu;
import com.narendra.bss.entity.User;
import com.narendra.bss.entity.UserRole;

public class BSSUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static final Logger logger = LoggerFactory.getLogger(BSSUserDetails.class);
	
	private User user;
	
	private Collection<? extends GrantedAuthority>  authorities;
	
	public BSSUserDetails(User user) {
		this.user = user;
		setAuthorities(user);/// we have to access the lazy loading dependencies of user object within the
								/// constructor scope because @Transactional is closed after the constructor is
								/// called from the CustomUserDetailService
	}
	
	private void setAuthorities(User user) {
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(UserRole userRole : user.getUserRoles()){
			logger.info("UserProfile : {}", userRole);
			authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getRoleName()));
			
			for(Menu menu: userRole.getMenus()) {
				authorities.add(new SimpleGrantedAuthority(menu.getPrivilege()));
				
				for(Menu child : menu.getChilds()) {
					authorities.add(new SimpleGrantedAuthority(child.getPrivilege()));
				}
			}
			
		}
		logger.info("authorities : {}", authorities);
		
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof BSSUserDetails))
			return false;
		BSSUserDetails that = (BSSUserDetails) o;
		return getUsername().equals(that.getUsername()) && getPassword().equals(that.getPassword());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername(),getPassword());
	}
	
	///equals and hashcode methods should be overrided to distinguish the users to allow maximum login per username
}
