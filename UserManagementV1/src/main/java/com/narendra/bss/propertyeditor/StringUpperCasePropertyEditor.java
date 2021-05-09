package com.narendra.bss.propertyeditor;

import java.beans.PropertyEditorSupport;

public class StringUpperCasePropertyEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		
		if(text !=null) {
			setValue(text.toUpperCase());
		}else {
			setValue(text);
		}
		
		
	}

}
