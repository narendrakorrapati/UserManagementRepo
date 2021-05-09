package com.narendra.bss.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.narendra.bss.config.security.BSSUserDetails;
import com.narendra.bss.entity.User;

@Service
public class ForceLogoutServiceImpl implements ForceLogoutService{

	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Autowired
	private LoggedInUserService loggedInUserService;
	
	/*@Override
	public Page<User> getLoggedInUsers(int page, int size, String sort, String order, String search) {
		
		Page<User> userPage = null;
		List<User> content = new ArrayList<>();
		
		List<Object> principals = sessionRegistry.getAllPrincipals();
		
		
		for(Object principal: principals) {
			
			if(principal instanceof BSSUserDetails) {
				
				BSSUserDetails userDetails = (BSSUserDetails)principal;
				
				List<SessionInformation> sessionInfoList = sessionRegistry.getAllSessions(principal, false);
				
				if(sessionInfoList.size() > 0) {
					content.add(userDetails.getUser());
				}
				
			}
		}
		
		Pageable pageable = null;
		
		if(StringUtils.hasLength(sort)) {
			pageable = PageRequest.of(page, size, Sort.by(("asc".equalsIgnoreCase(order) ? Sort.Direction.ASC :Sort.Direction.DESC) , sort));
		} else {
			pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "firstName"));
		}
		
		userPage = new PageImpl<>(content, pageable, content.size());
		
		return userPage;
	}*/

	@Override
	public void forceLogoutUsers(List<Integer> primeIds) {
		
		if(primeIds.size() > 0) {
			
			List<Object> principals = sessionRegistry.getAllPrincipals();
			
			for(Object principal: principals) {
				
				if(principal instanceof BSSUserDetails) {
					
					BSSUserDetails userDetails = (BSSUserDetails)principal;
					
					if(primeIds.contains(userDetails.getUser().getId())) {
						List<SessionInformation> sessionInfoList = sessionRegistry.getAllSessions(principal, false);
						
						if(sessionInfoList.size() > 0) {
							
							for(SessionInformation info: sessionInfoList) {
								sessionRegistry.removeSessionInformation(info.getSessionId());
								info.expireNow();
								HttpSession session = loggedInUserService.getLoggedInUserSession(userDetails.getUser().getId());
								session.invalidate();
								loggedInUserService.removeLoggedInUserSession(userDetails.getUser().getId());
							}
						}
					}
				}
			}
		}
	}

	@Override
	public List<User> getLoggedInUsers() {
		
		List<Object> principals = sessionRegistry.getAllPrincipals();
		List<User> content = new ArrayList<>();
		
		for(Object principal: principals) {
			
			if(principal instanceof BSSUserDetails) {
				
				BSSUserDetails userDetails = (BSSUserDetails)principal;
				
				List<SessionInformation> sessionInfoList = sessionRegistry.getAllSessions(principal, false);
				
				if(sessionInfoList.size() > 0) {
					content.add(userDetails.getUser());
				}
				
			}
		}
		
		return content;
	}
}
