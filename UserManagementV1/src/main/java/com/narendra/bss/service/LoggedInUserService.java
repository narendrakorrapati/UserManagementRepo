package com.narendra.bss.service;

import java.util.Map;

import javax.servlet.http.HttpSession;

public interface LoggedInUserService {

	Map<Integer, HttpSession> getLoggedInUserSessions();
	
	void addLoggedInUserSession(Integer id, HttpSession session);
	
	void removeLoggedInUserSession(Integer id);

	HttpSession getLoggedInUserSession(Integer id);
}
