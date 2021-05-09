package com.narendra.bss.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.narendra.bss.entity.User;

public interface ForceLogoutService {

	/*
	 * Page<User> getLoggedInUsers(int page, int size, String sort, String order,
	 * String search);
	 */
	
	List<User> getLoggedInUsers();

	void forceLogoutUsers(List<Integer> primeIds);

}
