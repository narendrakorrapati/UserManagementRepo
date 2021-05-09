package com.narendra.bss.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class LoggedInUserServiceImpl implements LoggedInUserService{

	private Map<Integer, HttpSession> loggedInUserSessions;

	@Override
	public Map<Integer, HttpSession> getLoggedInUserSessions() {
		return loggedInUserSessions;
	}

	@Override
	public HttpSession getLoggedInUserSession(Integer id) {
		return loggedInUserSessions.get(id);
	}
	
	@Override
	public void addLoggedInUserSession(Integer id, HttpSession session) {
		
		if(loggedInUserSessions == null) {
			loggedInUserSessions = new HashMap<>();
		}
		
		this.loggedInUserSessions.put(id, session);
	}
	
	@Override
	public void removeLoggedInUserSession(Integer id) {
		
		if(loggedInUserSessions == null) {
			loggedInUserSessions = new HashMap<>();
		}
		
		this.loggedInUserSessions.remove(id);
	}
	
}