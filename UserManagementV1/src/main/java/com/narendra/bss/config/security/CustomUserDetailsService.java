package com.narendra.bss.config.security;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.narendra.bss.entity.User;
import com.narendra.bss.exception.UserNotFoundException;
import com.narendra.bss.service.UserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private UserService userService;
	
	@Transactional 
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		User user = null;
		try {
			user = userService.findByEmail(email);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Username not found");
		}
		return new BSSUserDetails(user);
	}
	
}
