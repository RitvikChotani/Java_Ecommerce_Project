package com.shopme.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

public class AbstractExporter {
	
	public void setResponseHeader(HttpServletResponse response, String contentType, String extension) {
		DateFormat dateFormatter = new SimpleDateFormat("dd_mm_yyyy_HH-mm-ss");
		String time = dateFormatter.format(new Date());
		
		String fileName = "users_" + time + extension;
		
		response.setContentType(contentType);
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + fileName;
		response.setHeader(headerKey, headerValue);
	}
	
}
