package com.narendra.bss.service;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

	public String getEmailTemplate(String path) {

		String result = "";

		try {

			String mapping = String.format(path);
			ClassLoader classLoader = getClass().getClassLoader();
			result = IOUtils.toString(classLoader.getResourceAsStream(mapping), "UTF-8");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
