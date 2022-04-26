package com.shopme.admin.export;

import java.io.IOException;
import java.util.List;
import java.awt.Color;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.User;

public class UserPDFExporter extends AbstractExporter {
	
	public void export(List<User> user, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/pdf", ".pdf");
		
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		
		Paragraph para = new Paragraph("List of Users", font);
		para.setAlignment(Paragraph.ALIGN_CENTER);
		document.open();
		document.add(para);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] {1.2f, 3.5f, 3.0f, 3.0f ,3.0f, 1.7f});
		
		writerTableHeader(table);
		writeTableData(table, user);
		document.add(table);
		
		document.close();
		
	}
	
	private void writeTableData(PdfPTable table, List<User> user) {
		for(User users : user) {
			table.addCell(String.valueOf((users.getId())));
			table.addCell(users.getFirstName());
			table.addCell(users.getLastName());
			table.addCell(users.getEmail());
			table.addCell(users.getRoles().toString());
			table.addCell(String.valueOf((users.isEnabled())));
		}
	}

	public void writerTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("User ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("First Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Last Name", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Roles", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Enabled", font));
		table.addCell(cell);
		
		
	}
}
