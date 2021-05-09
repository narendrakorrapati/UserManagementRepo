package com.narendra.bss.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.narendra.bss.dto.UserDTO;
import com.narendra.bss.entity.User;
import com.narendra.bss.service.ForceLogoutService;

@Controller
public class ForceLogoutUserController {

	@Autowired
	private ForceLogoutService forceLogoutService;
	
	@RequestMapping(value = "/forceLogoutUser", method = RequestMethod.GET)
	public String getLoggedInUI(@ModelAttribute("userModel") UserDTO userModel, Model model) {
		return "loggedInuserList";
	}
	
	@RequestMapping(value = "/loggedInUsers", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String, Object> getLoggedInUsers(@RequestParam Map<String, Object> reqParams) {
		
		System.out.println(reqParams);
		
		//int offSet = Integer.parseInt(reqParams.get("offset").toString());
		//int limit = Integer.parseInt(reqParams.get("limit").toString());
		
		//offSet = offSet/limit;
		
		/*
		Page<User> userPage = forceLogoutService.getLoggedInUsers(offSet, Integer.parseInt(reqParams.get("limit").toString()),
				String.valueOf(reqParams.get("sort")), String.valueOf(reqParams.get("order")), String.valueOf(reqParams.get("search")));
		
		*/
		List<User> content = forceLogoutService.getLoggedInUsers();
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		//response.put("total", userPage.getTotalElements());
		response.put("rows", content);
		//response.put("pageNumber", Integer.parseInt(reqParams.get("offset").toString()) + 1);
		
		return response;
	}
	
	@RequestMapping(value = "/forceLogoutUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String>  forceLogoutUser(@RequestParam("primeId") List<Integer> primeIds) {
		String message = "";
		Map<String, String> response = new HashMap<String, String>();
		
		try {
			forceLogoutService.forceLogoutUsers(primeIds);
			message = "Users are Logged out successfully";
		}catch (Exception e) {
			message = "Error in Logging out the Users";
		}
		
		response.put("message", message);
		
		return response;
	}
}
