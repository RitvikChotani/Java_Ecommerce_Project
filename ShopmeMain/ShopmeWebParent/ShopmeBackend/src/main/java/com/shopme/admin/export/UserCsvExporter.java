package com.shopme.admin.export;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.User;

public class UserCsvExporter extends AbstractExporter {
	
	public void export(List<User> user, HttpServletResponse response) throws IOException {
		
		super.setResponseHeader(response, "text/csv", ".csv");
		
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		String[] header = {"User ID", "First Name", "Last Name", "Roles", "Enabled"};
		String[] fieldMap = {"id","firstName","lastName","email","roles"};
		csvWriter.writeHeader(header);
		for(User users : user) {
			csvWriter.write(users, fieldMap);
		}
		csvWriter.close();
	}
	
}
