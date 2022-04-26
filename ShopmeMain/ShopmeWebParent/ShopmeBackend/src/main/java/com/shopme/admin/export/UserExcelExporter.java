package com.shopme.admin.export;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.User;

public class UserExcelExporter extends AbstractExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	public UserExcelExporter() {
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLines() {
		sheet = workbook.createSheet("Users");
		XSSFRow row = sheet.createRow(0);

		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "User ID", style);
		createCell(row, 1, "FIrst Name", style);
		createCell(row, 2, "Last Name", style);
		createCell(row, 3, "Email", style);
		createCell(row, 4, "Roles", style);
		createCell(row, 5, "Enabled", style);
	}

	private void createCell(XSSFRow row, int colIndex, Object value, CellStyle style) {

		XSSFCell cell = row.createCell(colIndex);
		sheet.autoSizeColumn(colIndex);
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}

		cell.setCellStyle(style);
	}

	public void export(List<User> user, HttpServletResponse response) throws IOException {

		super.setResponseHeader(response, "application/octet-stream", ".xlsx");

		writeHeaderLines();
		writeDataLiner(user);

		ServletOutputStream outStream = response.getOutputStream();
		workbook.write(outStream);
		workbook.close();
		outStream.close();
	}

	private void writeDataLiner(List<User> user) {
		int rowIndex = 1;

		XSSFCellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		for (User users : user) {
			XSSFRow row = sheet.createRow(rowIndex++);
			createCell(row, 0, users.getId(), style);
			createCell(row, 1, users.getFirstName(), style);
			createCell(row, 2, users.getLastName(), style);
			createCell(row, 3, users.getEmail(), style);
			createCell(row, 4, users.getRoles().toString(), style);
			createCell(row, 5, users.isEnabled(), style);
	
		}
	}

}
