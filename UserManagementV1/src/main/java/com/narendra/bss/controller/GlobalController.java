package com.narendra.bss.controller;

import java.util.Date;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalController {

	@ExceptionHandler
	public String handleException(Exception e, Model model) {
		model.addAttribute("errorMessage", "Internal Server error occured:" + e.getMessage());
		model.addAttribute("serverTime", new Date());
		return "exception";
	}
}
