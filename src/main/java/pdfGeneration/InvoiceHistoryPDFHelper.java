
package pdfGeneration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.crystal.Frameworkpackage.CommonFunctions;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class InvoiceHistoryPDFHelper extends PdfPageEventHelper {
	CommonFunctions cf = new CommonFunctions();

	public static void main(String[] args) throws DocumentException, MalformedURLException, IOException {

		// generatePDF("Ref:- SSEGPL/19/008/R","Date:- November 17th 2018","AKT Oil");

	}

	public void generatePDFForTransfer(String DestinationPath, String BufferedImagesFolderPath,
			HashMap<String, Object> invoiceHistoryDetails, Connection con)
			throws DocumentException, MalformedURLException, IOException {

		List<LinkedHashMap<String, Object>> ListOfItemDetails = (List<LinkedHashMap<String, Object>>) invoiceHistoryDetails
				.get("listOfItems");

		BaseFont base = BaseFont.createFont(BufferedImagesFolderPath + "/CALIBRI.TTF", BaseFont.WINANSI, false);
		Font font = new Font(base, 12, Font.NORMAL);

		Document document = new Document(PageSize.A4, 20, 20, 0, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		/*
		 * PdfWriter writer = PdfWriter.getInstance(document, new
		 * FileOutputStream("F:\\1.pdf"));
		 */

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		document.add(new Paragraph("\n"));

		PdfPTable table = new PdfPTable(8);
		PdfPCell cell;

		table.setWidthPercentage(100f);

		String LabelString = "Stock Transfer";

		cell = new PdfPCell(new Phrase(LabelString, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(8);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceFirm").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Stock Transfer No", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceWareHouseName").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("stock_modification_id").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("FormattedTransactionDate").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceAddress1").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Mode / Terms of Payment", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(
				invoiceHistoryDetails.get("sourceCity").toString() + " - "
						+ invoiceHistoryDetails.get("sourcePincode").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("GST No:- " + invoiceHistoryDetails.get("sourceGSTNO").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Supplier's Ref.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Other Reference(s)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceCity").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Email :- " + invoiceHistoryDetails.get("sourceEmail").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Consignee", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Buyers Order No ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("destinationFirm").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("destinationWareHouseName").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Doc No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("destinationAddress1").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(
				invoiceHistoryDetails.get("destinationCity").toString() + "-"
						+ invoiceHistoryDetails.get("destinationPincode").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Through .", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Destination", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Buyer (If other than consignee)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Terms Of Delivery", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Sr No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Description of goods", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("HSN / SAC", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Qty", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		int srno = 1;
		Double totalQty = 0d;
		for (HashMap<String, Object> prod : ListOfItemDetails) {

			cell = new PdfPCell(new Phrase(String.valueOf(srno++), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("item_name").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setColspan(4);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			String hsnCode = prod.get("hsn_code") == null ? "" : prod.get("hsn_code").toString();

			cell = new PdfPCell(new Phrase(hsnCode, font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			cell.setColspan(2);

			table.addCell(cell);

			totalQty += Double.parseDouble(prod.get("qty").toString());
			cell = new PdfPCell(new Phrase(prod.get("qty").toString(), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

		}

		if (ListOfItemDetails.size() <= 10) {
			for (int i = 0; i < 10; i++) {
				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				cell.setColspan(4);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("".toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);
			}
		}

		cell = new PdfPCell(new Phrase("Total Quantities", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setMinimumHeight(20f);
		cell.setColspan(7);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase(String.valueOf(totalQty), new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setMinimumHeight(20f);
		cell.setColspan(1);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Recd. in Good Condition.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("for " + invoiceHistoryDetails.get("firm_name"),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Authorized Signature", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		document.add(table);

		document.close();

	}

	public void generatePDFForAddStock(String DestinationPath, String BufferedImagesFolderPath,
			HashMap<String, Object> invoiceHistoryDetails, Connection con, String type)
			throws DocumentException, MalformedURLException, IOException {

		List<LinkedHashMap<String, Object>> ListOfItemDetails = (List<LinkedHashMap<String, Object>>) invoiceHistoryDetails
				.get("listOfItems");

		BaseFont base = BaseFont.createFont(BufferedImagesFolderPath + "/CALIBRI.TTF", BaseFont.WINANSI, false);
		Font font = new Font(base, 12, Font.NORMAL);

		Document document = new Document(PageSize.A4, 20, 20, 0, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		/*
		 * PdfWriter writer = PdfWriter.getInstance(document, new
		 * FileOutputStream("F:\\1.pdf"));
		 */

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		document.add(new Paragraph("\n"));

		PdfPTable table = new PdfPTable(8);
		PdfPCell cell;

		table.setWidthPercentage(100f);

		String LabelString = type.equals("Remove") ? "Damage Stock" : "Add Stock";

		cell = new PdfPCell(new Phrase(LabelString, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(8);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceFirm").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Modification No", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceWareHouseName").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("stock_modification_id").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("FormattedTransactionDate").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceAddress1").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Mode / Terms of Payment", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(
				invoiceHistoryDetails.get("sourceCity").toString() + " - "
						+ invoiceHistoryDetails.get("sourcePincode").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("GST No:- " + invoiceHistoryDetails.get("sourceGSTNO").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Supplier's Ref.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Other Reference(s)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("sourceCity").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Email :- " + invoiceHistoryDetails.get("sourceEmail").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Consignee", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Buyers Order No ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Doc No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Through .", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Destination", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Buyer (If other than consignee)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Terms Of Delivery", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Sr No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Description of goods", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("HSN / SAC", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Qty", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		int srno = 1;
		Double totalQty = 0d;
		for (HashMap<String, Object> prod : ListOfItemDetails) {

			cell = new PdfPCell(new Phrase(String.valueOf(srno++), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("item_name").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setColspan(4);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			String hsnCode = prod.get("hsn_code") == null ? "" : prod.get("hsn_code").toString();

			cell = new PdfPCell(new Phrase(hsnCode, font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			cell.setColspan(2);

			table.addCell(cell);

			totalQty += Double.parseDouble(prod.get("qty").toString());
			cell = new PdfPCell(new Phrase(prod.get("qty").toString(), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

		}

		if (ListOfItemDetails.size() <= 10) {
			for (int i = 0; i < 10; i++) {
				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				cell.setColspan(4);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("".toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);
			}
		}

		cell = new PdfPCell(new Phrase("Total Quantities", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setMinimumHeight(20f);
		cell.setColspan(7);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase(String.valueOf(totalQty), new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setMinimumHeight(20f);
		cell.setColspan(1);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Recd. in Good Condition.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("for " + invoiceHistoryDetails.get("firm_name"),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Authorized Signature", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		document.add(table);

		document.close();

	}

	public void generatePDFForChallan(String DestinationPath, String BufferedImagesFolderPath,
			HashMap<String, Object> invoiceHistoryDetails, Connection con, String type)
			throws DocumentException, MalformedURLException, IOException {

		List<LinkedHashMap<String, Object>> ListOfItemDetails = (List<LinkedHashMap<String, Object>>) invoiceHistoryDetails
				.get("listOfItems");

		BaseFont base = BaseFont.createFont(BufferedImagesFolderPath + "/CALIBRI.TTF", BaseFont.WINANSI, false);
		Font font = new Font(base, 12, Font.NORMAL);

		Document document = new Document(PageSize.A4, 20, 20, 0, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		/*
		 * PdfWriter writer = PdfWriter.getInstance(document, new
		 * FileOutputStream("F:\\1.pdf"));
		 */

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		document.add(new Paragraph("\n"));

		PdfPTable table = new PdfPTable(8);
		PdfPCell cell;

		table.setWidthPercentage(100f);

		String LabelString = "";
		if (type.equals("I")) {
			LabelString = "Challan In";
		} else {
			LabelString = "Challan Out";
		}

		cell = new PdfPCell(new Phrase(LabelString, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(8);
		table.addCell(cell);

		String name = type.equals("I") ? "Client_name" : "firm_name";

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(name).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Challan No", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		String addresKey = type.equals("I") ? "Client_address" : "address_line_1";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addresKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("challan_id").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("FormattedChallanDate").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		String addresKey2 = type.equals("I") ? "Client_address" : "address_line_2";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addresKey2).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Mode / Terms of Payment", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		String cityKey = type.equals("I") ? "Client_city" : "firm_city";
		String pincodeKey = type.equals("I") ? "Client_pincode" : "firm_pincode";

		cell = new PdfPCell(new Phrase(
				invoiceHistoryDetails.get(cityKey).toString() + " - "
						+ invoiceHistoryDetails.get(pincodeKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		String gstKey = type.equals("I") ? "Client_gst" : "firm_gst";
		cell = new PdfPCell(new Phrase("GST No:- " + invoiceHistoryDetails.get(gstKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Supplier's Ref.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Other Reference(s)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		String stateKey = type.equals("I") ? "Client_state" : "firm_state";
		String stateValue = invoiceHistoryDetails.get(stateKey) == null ? ""
				: "State :-" + invoiceHistoryDetails.get(stateKey);
		cell = new PdfPCell(new Phrase(stateValue, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		String emailKey = type.equals("I") ? "Client_email" : "firm_email";
		cell = new PdfPCell(new Phrase("Email :- " + invoiceHistoryDetails.get(emailKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Consignee", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Buyers Order No ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String nameKey = type.equals("I") ? "firm_name" : "Client_name";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(nameKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String addressKey = type.equals("I") ? "address_line_1" : "Client_address";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addressKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Doc No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String addressKey1 = type.equals("I") ? "address_line_2" : "Client_address";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addressKey1).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		String cityKey1 = type.equals("I") ? "firm_city" : "Client_city";
		String pincodeKey1 = type.equals("I") ? "firm_pincode" : "Client_pincode";

		String cityValue = invoiceHistoryDetails.get(cityKey1) == null ? ""
				: invoiceHistoryDetails.get(cityKey1).toString();
		String pincodeValue = invoiceHistoryDetails.get(pincodeKey1) == null ? ""
				: invoiceHistoryDetails.get(pincodeKey1).toString();

		cell = new PdfPCell(
				new Phrase(cityValue + "-" + pincodeValue, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Through .", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Destination", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String stateKey1 = type.equals("I") ? "firm_state" : "Client_state";
		String valueState = invoiceHistoryDetails.get(stateKey1) == null ? ""
				: invoiceHistoryDetails.get(stateKey1).toString();
		cell = new PdfPCell(new Phrase(valueState, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Buyer (If other than consignee)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Terms Of Delivery", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Sr No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Description of goods", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("HSN / SAC", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Qty", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		int srno = 1;
		Double totalQty = 0d;
		for (HashMap<String, Object> prod : ListOfItemDetails) {

			cell = new PdfPCell(new Phrase(String.valueOf(srno++), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("item_name").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setColspan(4);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			String hsnCode = prod.get("hsn_code") == null ? "" : prod.get("hsn_code").toString();

			cell = new PdfPCell(new Phrase(hsnCode, font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			cell.setColspan(2);

			table.addCell(cell);

			totalQty += Double.parseDouble(prod.get("formattedQty").toString());
			cell = new PdfPCell(new Phrase(prod.get("formattedQty").toString(), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

		}

		if (ListOfItemDetails.size() <= 10) {
			for (int i = 0; i < 10; i++) {
				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				cell.setColspan(4);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("".toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);
			}
		}

		cell = new PdfPCell(new Phrase("Total Quantities", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setMinimumHeight(20f);
		cell.setColspan(7);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase(String.valueOf(totalQty), new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		cell.setMinimumHeight(20f);
		cell.setColspan(1);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Recd. in Good Condition.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("for " + invoiceHistoryDetails.get("firm_name"),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Authorized Signature", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		document.add(table);

		document.close();

	}

	public void generatePDFForInvoice(String DestinationPath, String BufferedImagesFolderPath,
			HashMap<String, Object> invoiceHistoryDetails, Connection con, String type)
			throws DocumentException, MalformedURLException, IOException {

		LinkedHashMap<String, String> ClientDetails = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("ClientDetails");
		LinkedHashMap<String, String> totalDetails = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("totalDetails");
		List<LinkedHashMap<String, Object>> ListOfItemDetails = (List<LinkedHashMap<String, Object>>) invoiceHistoryDetails
				.get("listOfItems");

		BaseFont base = BaseFont.createFont(BufferedImagesFolderPath + "/CALIBRI.TTF", BaseFont.WINANSI, false);
		Font font = new Font(base, 12, Font.NORMAL);

		Document document = new Document(PageSize.A4, 20, 20, 0, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		/*
		 * PdfWriter writer = PdfWriter.getInstance(document, new
		 * FileOutputStream("F:\\1.pdf"));
		 */

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		document.add(new Paragraph("\n"));

		PdfPTable table = new PdfPTable(8);
		PdfPCell cell;

		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 3, 8, 5, 3, 3, 3, 5, 5 });

		String LabelString = "";
		if (type.equals("S")) {
			LabelString = "Sales Invoice";
		} else {
			LabelString = "Purchase Invoice";
		}

		cell = new PdfPCell(new Phrase(LabelString, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(8);
		table.addCell(cell);

		String name = type.equals("P") ? "Client_name" : "firm_name";

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(name).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Invoice No", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		String addresKey = type.equals("P") ? "Client_address" : "address_line_1";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addresKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("invoice_id").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("FormattedChallanDate").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		table.addCell(cell);

		String addresKey2 = type.equals("P") ? "Client_address2" : "address_line_2";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addresKey2).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Mode / Terms of Payment", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		cell.setRowspan(2);
		table.addCell(cell);

		String cityKey = type.equals("P") ? "Client_city" : "firm_city";
		String pincodeKey = type.equals("P") ? "Client_pincode" : "firm_pincode";

		cell = new PdfPCell(new Phrase(
				invoiceHistoryDetails.get(cityKey).toString() + " - "
						+ invoiceHistoryDetails.get(pincodeKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		String gstKey = type.equals("P") ? "Client_gst" : "firm_gst";
		cell = new PdfPCell(new Phrase("GST No:- " + invoiceHistoryDetails.get(gstKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Supplier's Ref.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Other Reference(s)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorderWidthBottom(0);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setRowspan(3);
		table.addCell(cell);

		String stateKey = type.equals("P") ? "Client_state" : "firm_state";
		String stateValue = invoiceHistoryDetails.get(stateKey) == null ? ""
				: "State :-" + invoiceHistoryDetails.get(stateKey);
		cell = new PdfPCell(new Phrase(stateValue, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		String emailKey = type.equals("P") ? "Client_email" : "firm_email";
		cell = new PdfPCell(new Phrase("Email :- " + invoiceHistoryDetails.get(emailKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Consignee", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Buyers Order No ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Dated ", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String nameKey = type.equals("P") ? "firm_name" : "Client_name";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(nameKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String addressKey = type.equals("P") ? "address_line_1" : "Client_address";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addressKey).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Doc No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String addressKey1 = type.equals("P") ? "address_line_2" : "Client_address2";
		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get(addressKey1).toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		String cityKey1 = type.equals("P") ? "firm_city" : "Client_city";
		String pincodeKey1 = type.equals("P") ? "firm_pincode" : "Client_pincode";

		String cityValue = invoiceHistoryDetails.get(cityKey1) == null ? ""
				: invoiceHistoryDetails.get(cityKey1).toString();
		String pincodeValue = invoiceHistoryDetails.get(pincodeKey1) == null ? ""
				: invoiceHistoryDetails.get(pincodeKey1).toString();

		cell = new PdfPCell(
				new Phrase(cityValue + "-" + pincodeValue, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Despatch Through .", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Destination", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);

		table.addCell(cell);

		String stateKey1 = type.equals("P") ? "firm_state" : "Client_state";
		String valueState = invoiceHistoryDetails.get(stateKey1) == null ? ""
				: invoiceHistoryDetails.get(stateKey1).toString();
		cell = new PdfPCell(new Phrase(valueState, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(2);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Buyer (If other than consignee)", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Terms Of Delivery", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setColspan(4);
		cell.setMinimumHeight(20f);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setColspan(4);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Sr No.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Description of goods", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		cell.setColspan(2);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("HSN / SAC", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Quantity", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Rate", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("GST", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Basic", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
		cell.setMinimumHeight(20f);
		table.addCell(cell);

		int srno = 1;
		Double totalQty = 0d;
		for (HashMap<String, Object> prod : ListOfItemDetails) {

			cell = new PdfPCell(new Phrase(String.valueOf(srno++), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("item_name").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			cell.setColspan(2);
			table.addCell(cell);

			String hsnCode = prod.get("hsn_code") == null ? "" : prod.get("hsn_code").toString();

			cell = new PdfPCell(new Phrase(hsnCode, font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);

			table.addCell(cell);

			totalQty += Double.parseDouble(prod.get("formattedQty").toString());
			cell = new PdfPCell(new Phrase(prod.get("formattedQty").toString(), font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			Double price = Double.parseDouble(prod.get("rate").toString());
			String priceString = String.format("%.0f", price);
			cell = new PdfPCell(new Phrase(priceString, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("gst_percentage").toString() + "%",
					new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

			Double basicAmount = price * Double.parseDouble(prod.get("formattedQty").toString());
			String basicAmountString = String.format("%.0f", basicAmount);

			cell = new PdfPCell(new Phrase(basicAmountString, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
			cell.setMinimumHeight(20f);
			table.addCell(cell);

		}

		if (ListOfItemDetails.size() <= 10) {
			for (int i = 0; i < 10; i++) {
				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				cell.setColspan(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("".toString(), font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("", font));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
				cell.setMinimumHeight(20f);
				table.addCell(cell);

			}
		}

		cell = new PdfPCell(new Phrase("Gross Amount", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setMinimumHeight(20f);
		cell.setColspan(7);
		table.addCell(cell);

		Double gross_amount = Double.parseDouble(invoiceHistoryDetails.get("gross_amount").toString());

		String s = String.format("%.0f", gross_amount);

		cell = new PdfPCell(new Phrase(s, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(20f);
		cell.setColspan(1);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Total GST", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setMinimumHeight(20f);
		cell.setColspan(7);
		table.addCell(cell);

		Double gst_amount = Double.parseDouble(invoiceHistoryDetails.get("total_gst").toString());

		String gst_amountString = String.format("%.0f", gst_amount);

		cell = new PdfPCell(new Phrase(gst_amountString, new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(20f);
		cell.setColspan(1);
		table.addCell(cell);

		if (!invoiceHistoryDetails.get("freight_amount").toString().equals("0")) {

			cell = new PdfPCell(new Phrase("Frieght Amount", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setMinimumHeight(20f);
			cell.setColspan(7);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("freight_amount").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(20f);
			cell.setColspan(1);
			table.addCell(cell);
		}

		if (!invoiceHistoryDetails.get("other_amount").toString().equals("0")) {
			cell = new PdfPCell(new Phrase("Other Amount", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setMinimumHeight(20f);
			cell.setColspan(7);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("other_amount").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setMinimumHeight(20f);
			cell.setColspan(1);
			table.addCell(cell);
		}

		cell = new PdfPCell(new Phrase("Total Amount", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setMinimumHeight(20f);
		cell.setColspan(7);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(invoiceHistoryDetails.get("total_amount").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setMinimumHeight(20f);
		cell.setColspan(1);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase("Recd. in Good Condition.", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("for " + invoiceHistoryDetails.get("firm_name"),
				new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);

		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Authorized Signature", new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);

		cell.setMinimumHeight(20f);
		cell.setColspan(4);
		table.addCell(cell);

		document.add(table);

		document.close();

	}

	public void onStartPage(PdfWriter writer, Document document) {
		/*
		 * try { addHeader(document); addWaterMark(document,writer); } catch(Exception
		 * e) { e.printStackTrace(); }
		 */
	}

	int pageNo = 0;

	public void onEndPage(PdfWriter writer, Document document) {
		try {
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setTotalWidth(590);

			PdfPCell cell;

			cell = new PdfPCell(
					new Phrase("Page No:-" + (++pageNo), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(cell);
			table.writeSelectedRows(0, -1, 0, 20, writer.getDirectContent());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addWaterMark(Document documen, PdfWriter writer)
			throws MalformedURLException, IOException, DocumentException {
		PdfContentByte canvas = writer.getDirectContentUnder();

		URL resource = InvoiceHistoryPDFHelper.class.getResource("watermark.png");
		Image image = Image.getInstance(resource);

		image.setAbsolutePosition(115, 230);
		image.scaleAbsolute(400, 400);
		canvas.saveState();
		PdfGState state = new PdfGState();
		// state.setFillOpacity(0.1f);
		canvas.setGState(state);
		canvas.addImage(image);
		canvas.restoreState();

	}

	public static void addHeader(Document document) throws MalformedURLException, IOException, DocumentException {

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);

		URL resource = InvoiceHistoryPDFHelper.class.getResource("topLeft.png");
		Image img1 = Image.getInstance(resource);

		resource = InvoiceHistoryPDFHelper.class.getResource("topRight.jpg");
		Image img2 = Image.getInstance(resource);
		PdfPCell cell;
		img1.scalePercent(40);
		cell = new PdfPCell(img1);
		img2.setAlignment(Image.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		img2.scalePercent(22);
		cell = new PdfPCell(img2);
		cell.setHorizontalAlignment(Image.ALIGN_RIGHT);
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
		document.add(table);

		document.add(new Paragraph("\n"));
	}

	public void generatePDFForClientLedger(String DestinationPath, HashMap<String, Object> invoiceHistoryDetails,
			Connection con) throws DocumentException, MalformedURLException, IOException {

		LinkedHashMap<String, String> ClientDetails = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("ClientDetails");
		LinkedHashMap<String, String> totalDetails = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("totalDetails");
		List<LinkedHashMap<String, Object>> ListOfItemDetails = (List<LinkedHashMap<String, Object>>) invoiceHistoryDetails
				.get("ListOfItemDetails");

		Document document = new Document(PageSize.A4, 20, 20, 20, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		/*
		 * PdfWriter writer = PdfWriter.getInstance(document, new
		 * FileOutputStream("F:\\1.pdf"));
		 */

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		PdfPTable table = new PdfPTable(1);
		PdfPCell cell;
		cell = new PdfPCell(new Phrase("Client Ledger", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		document.add(table);

		document.add(new Paragraph("\n"));

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });

		cell = new PdfPCell(new Phrase("Party Name :- " + ClientDetails.get("Client_name").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Mobile No :- " + ClientDetails.get("mobile_number").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Date :- " + invoiceHistoryDetails.get("fromDate").toString() + " to "
				+ invoiceHistoryDetails.get("toDate").toString()));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		document.add(new Paragraph("\n"));

		table = new PdfPTable(7);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 4, 11, 3, 4, 5, 5, 5 });

		cell = new PdfPCell(new Phrase("Firm Name", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Transaction Date", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Particular", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Transaction Type", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Ref Id", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Debit", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Credit", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		for (HashMap<String, Object> prod : ListOfItemDetails) {

			cell = new PdfPCell(new Phrase(prod.get("firm_name").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("transaction_date").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("Particular").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("type").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("RefId").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("debitAmount").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("creditAmount").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

		}

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Opening Balance", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(String.valueOf(totalDetails.get("openingAmount")),
				new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Debit Total", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(String.valueOf(totalDetails.get("debitSum")),
				new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Credit Total", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(String.valueOf(totalDetails.get("creditSum")),
				new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Closing Balance As On :-" + invoiceHistoryDetails.get("toDate"),
				new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(4);
		table.addCell(cell);

		String totalAmount = String.valueOf(totalDetails.get("totalAmount"));
		double d = Double.parseDouble(totalAmount);
		String creditTotal = "";
		String debitTotal = "";

		if (d > 0) {
			creditTotal = String.valueOf(d);
		} else {
			debitTotal = String.valueOf(d);
		}

		cell = new PdfPCell(
				new Phrase(String.valueOf(debitTotal), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase(String.valueOf(creditTotal), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);

		document.close();

	}

	public void generatePDFForStockStatusClubbed(String DestinationPath, String DestinationPathBuffered,
			HashMap<String, Object> invoiceHistoryDetails, Connection con)
			throws DocumentException, MalformedURLException, IOException {

		List<LinkedHashMap<String, Object>> ListOfItemDetails = (List<LinkedHashMap<String, Object>>) invoiceHistoryDetails
				.get("ListOfItemDetails");
		LinkedHashMap<String, String> ListOfCategories = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("ListOfCategories");
		LinkedHashMap<String, String> listOffirm = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("listOffirm");
		LinkedHashMap<String, String> listofwarehouse = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("listofwarehouse");
		LinkedHashMap<String, String> totalDetails = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("totalDetails");

		Document document = new Document(PageSize.A4, 20, 20, 20, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		/*
		 * PdfWriter writer = PdfWriter.getInstance(document, new
		 * FileOutputStream("F:\\1.pdf"));
		 */

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		PdfPTable table = new PdfPTable(1);
		PdfPCell cell;
		cell = new PdfPCell(new Phrase("Stock Status Clubbed", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		document.add(table);

		document.add(new Paragraph("\n"));

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });

		document.add(table);

		document.add(new Paragraph("\n"));

		table = new PdfPTable(6);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 4, 6, 5, 4, 5, 5,  });

		

		cell = new PdfPCell(new Phrase("Item Name", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Product Code", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Size", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Color", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Available Quantity", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Product Image", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		for (HashMap<String, Object> prod : ListOfItemDetails) {

			

			cell = new PdfPCell(new Phrase(prod.get("item_name").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("product_code").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("size").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("color").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("sumQty").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			String s = prod.get("fileName") == null ? "" : prod.get("fileName").toString();

			if (!s.equals("")) {
				String IMG1 = DestinationPathBuffered + s;

				Image img1 = Image.getInstance(IMG1);
				// img1.scaleAbsolute(200, 200);
				// cell.setFixedHeight(150);

				cell.addElement(img1);
				table.addCell(cell);
			} else {
				cell = new PdfPCell(new Phrase("No Img", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}

		}

		document.add(table);

		document.close();

	}

	public void generateClientItemHistory(String DestinationPath, HashMap<String, Object> invoiceHistoryDetails,
			Connection con) throws DocumentException, MalformedURLException, IOException {

		LinkedHashMap<String, String> totalDetails = (LinkedHashMap<String, String>) invoiceHistoryDetails
				.get("totalDetails");
		List<LinkedHashMap<String, Object>> ListOfItemDetails = (List<LinkedHashMap<String, Object>>) invoiceHistoryDetails
				.get("ListOfItemDetails");

		Document document = new Document(PageSize.A4, 20, 20, 20, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		/*
		 * PdfWriter writer = PdfWriter.getInstance(document, new
		 * FileOutputStream("F:\\1.pdf"));
		 */

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		PdfPTable table = new PdfPTable(1);
		PdfPCell cell;
		cell = new PdfPCell(
				new Phrase("Party Wise Item Wise Report", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		document.add(table);

		document.add(new Paragraph("\n"));

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });

		cell = new PdfPCell(new Phrase("Date :- " + invoiceHistoryDetails.get("fromDate").toString() + " to "
				+ invoiceHistoryDetails.get("toDate").toString()));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		document.add(new Paragraph("\n"));

		table = new PdfPTable(10);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 10, 5, 20, 5, 5, 7, 7, 7, 10, 10 });

		cell = new PdfPCell(new Phrase("Invoice Date", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Ref", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Item Name", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Return", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Billed Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("MRP", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Rate", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Discount", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Bill Amount", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		String ClientName = "";

		for (HashMap<String, Object> prod : ListOfItemDetails) {

			if (!ClientName.equals(prod.get("Client_name"))) {

				cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setMinimumHeight(15);
				cell.setColspan(10);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(prod.get("Client_name").toString(),
						new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setMinimumHeight(15);
				cell.setColspan(10);
				table.addCell(cell);

			}
			ClientName = prod.get("Client_name").toString();
			cell = new PdfPCell(new Phrase(prod.get("formattedInvoiceDate").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("invoice_no").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("item_name").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("qty").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			String qtytoreturn = prod.get("qty_to_return") == null ? "" : prod.get("qty_to_return").toString();

			cell = new PdfPCell(new Phrase(qtytoreturn, new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("BilledQty").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("rate").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("custom_rate").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("DiscountAmount").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("ItemAmount").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

		}

		document.add(table);
		document.close();

	}

	public void generatePDFForCostSheetReport(String DestinationPath, HashMap<String, Object> costSheetReportDetails,
			Connection con) throws DocumentException, MalformedURLException, IOException {

		LinkedHashMap<String, Object> costSheetReport = (LinkedHashMap<String, Object>) costSheetReportDetails
				.get("requiredData");

		String jobsheetno = (String) costSheetReportDetails.get("jobsheetno");

		List<LinkedHashMap<String, Object>> lstOfPurchase = (List<LinkedHashMap<String, Object>>) costSheetReport
				.get("lstPurchase");

		List<LinkedHashMap<String, Object>> lstFromOtherJobSheet = (List<LinkedHashMap<String, Object>>) costSheetReport
				.get("lstFromOtherJobSheet");
		List<LinkedHashMap<String, Object>> lstOfSales = (List<LinkedHashMap<String, Object>>) costSheetReport
				.get("lstSales");

		List<LinkedHashMap<String, Object>> lstOfExpense = (List<LinkedHashMap<String, Object>>) costSheetReport
				.get("lstExpense");
		String profit = (String) costSheetReport.get("profit");
		String profitPercentage = (String) costSheetReport.get("profitPercentage");

		BigDecimal totalExpense = (BigDecimal) costSheetReport.get("totalExpense");
		BigDecimal totalPurchase = (BigDecimal) costSheetReport.get("totalPurchase");
		BigDecimal totalPurchaseCount = (BigDecimal) costSheetReport.get("totalPurchaseCount");

		BigDecimal totalPurchaseFromOtherJobSheet = (BigDecimal) costSheetReport.get("totalPurchaseFromOtherJobSheet");
		BigDecimal totalPurchaseCountFromOtherJobSheet = (BigDecimal) costSheetReport
				.get("totalPurchaseCountFromOtherJobSheet");
		BigDecimal totalSales = (BigDecimal) costSheetReport.get("totalSales");

		BigDecimal totalSalesCount = (BigDecimal) costSheetReport.get("totalSalesCount");
		BigDecimal SOQty = (BigDecimal) costSheetReport.get("SOQty");

		Document document = new Document(PageSize.A4, 20, 20, 20, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		PdfPTable table = new PdfPTable(1);
		PdfPCell cell;
		cell = new PdfPCell(new Phrase("Cost Sheet Report", new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
		document.add(table);

		document.add(new Paragraph("\n"));

		table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[] { 1 });

		cell = new PdfPCell(
				new Phrase("J S No. :- " + jobsheetno, new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Firm Name :- "+costSheetReport.get("firmName"), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Sales Person :- ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Client Name :- " + costSheetReport.get("Client_name").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);
		String vendorName=costSheetReport.get("vendorName")==null?"":costSheetReport.get("vendorName").toString();
		cell = new PdfPCell(new Phrase("Vendor Name :- " + vendorName,
				new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Product Name :- " + costSheetReport.get("item_name").toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase("Sales", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(5);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 6, 10, 4, 4, 5 });

		cell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Rate", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Value", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("salesId", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		for (HashMap<String, Object> prod : lstOfSales) {

			cell = new PdfPCell(new Phrase(prod.get("FormattedFromDate").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("Qty").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("Rate").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("values1").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("invoice_id").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

		}

		cell = new PdfPCell(new Phrase("Total Sales Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase(totalSalesCount.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Total Sales", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(totalSales.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);
		
		
		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase("Purchase", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(5);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 6, 10, 4, 4, 5 });

		cell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Rate", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Value", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("purchaseId", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		for (HashMap<String, Object> prod : lstOfPurchase) {

			cell = new PdfPCell(new Phrase(prod.get("FormattedFromDate").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("Qty").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("Rate").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("values1").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("invoice_id").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

		}
		
		
		cell = new PdfPCell(new Phrase("Total Purchase Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase(totalPurchaseCount.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Total Purchase", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(totalPurchase.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);
		
		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);


		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(
				new Phrase("Purchase Used From Other Job Sheet", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(6);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 8, 10, 6, 4, 5, 8 });

		cell = new PdfPCell(new Phrase("Date", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Rate", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Value", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("purchaseId", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Job Sheet No", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		for (HashMap<String, Object> prod : lstFromOtherJobSheet) {

			cell = new PdfPCell(new Phrase(prod.get("FormattedFromDate").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("Qty").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("Rate").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("values1").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("invoice_id").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("job_sheet_no").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

		}

		cell = new PdfPCell(new Phrase("Total Purchase Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(totalPurchaseCountFromOtherJobSheet.toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Total Purchase", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(totalPurchaseFromOtherJobSheet.toString(),
				new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);
		
		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);


		table = new PdfPTable(3);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 15, 15, 15 });

		cell = new PdfPCell(new Phrase("Balance Dispatch", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(SOQty.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("SO Qty", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("SO Balance", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);
		
		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase("Expenses", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(3);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 15, 15, 15 });

		for (HashMap<String, Object> prod : lstOfExpense) {

			cell = new PdfPCell(new Phrase(prod.get("Client_name").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase(prod.get("creditClient").toString(),
					new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);

			cell = new PdfPCell(
					new Phrase(prod.get("amount").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(cell);
		}

		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Total", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(totalExpense.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		
		document.add(table);
		
		table = new PdfPTable(1);
		table.setWidthPercentage(100);

		table.setWidths(new int[] { 20 });

		cell = new PdfPCell(new Phrase(" ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(cell);

		document.add(table);

		table = new PdfPTable(2);
		table.setWidthPercentage(100);

		table.setWidths(new int[] {  66 , 33 });
		
		cell = new PdfPCell(new Phrase("Profit", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(profit.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("% Profit", new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		cell = new PdfPCell(
				new Phrase(profitPercentage.toString(), new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		document.add(table);

		document.close();

	}
	public void generatePDFPaymentVoucher(String DestinationPath, HashMap<String, Object> paymentDetails,
			Connection con) throws DocumentException, MalformedURLException, IOException {
		
		


		List<LinkedHashMap<String, Object>> lstOfPayments = (List<LinkedHashMap<String, Object>>) paymentDetails
				.get("listOfItems");
		
		Document document = new Document(PageSize.A4, 20, 20, 20, 60);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));

		InvoiceHistoryPDFHelper event = new InvoiceHistoryPDFHelper();
		writer.setPageEvent(event);
		document.open();

		PdfPTable table = new PdfPTable(6);
		PdfPCell cell;
		table.setWidthPercentage(100);
        table.getFooterRows();
		table.setWidths(new int[] { 10, 10, 6, 6, 6, 6});
		
		cell = new PdfPCell(new Phrase(paymentDetails.get("firm_name").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(6);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setMinimumHeight(15);
		cell.setColspan(6);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("VOT NO : ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(1);
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase(paymentDetails.get("payment_id").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Date : ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);		
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(paymentDetails.get("formattedPaymentDate").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);		
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setMinimumHeight(15);
		cell.setColspan(6);
		table.addCell(cell);
		
		
		
		cell = new PdfPCell(new Phrase("Paid For/To", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(1);
		table.addCell(cell);
		
		
		
		cell = new PdfPCell(new Phrase(paymentDetails.get("Client_name").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(6);
		table.addCell(cell);
		
		
		
		for(LinkedHashMap<String, Object> paym:lstOfPayments)
		{
		
		cell = new PdfPCell(new Phrase("Amount : ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(1);
		table.addCell(cell);
		

		cell = new PdfPCell(new Phrase(paym.get("amount").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(2);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Job Sheet : ", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(1);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase(paym.get("job_sheet_no").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(2);
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setMinimumHeight(15);
		cell.setColspan(6);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Description", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(6);
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase(paym.get("remarks").toString(), new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setColspan(6);
		table.addCell(cell);
		
		}
		
		
		
		
		
		
		cell = new PdfPCell(new Phrase("Prepare By", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(40);
		cell.setMinimumHeight(50);
		cell.setColspan(1);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("Authorised Sign", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(40);
		cell.setMinimumHeight(50);
		cell.setColspan(1);
		cell.setBorderWidthTop(0);
		table.addCell(cell);
		
		
		
		
		cell = new PdfPCell(new Phrase("Accounts", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(40);
		cell.setMinimumHeight(50);
		cell.setColspan(3);
		table.addCell(cell);
		
		
		cell = new PdfPCell(new Phrase("Receiver", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL)));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPaddingTop(40);
		cell.setMinimumHeight(50);
		cell.setColspan(3);
		table.addCell(cell);
		document.add(table);

		document.add(new Paragraph("\n"));


		document.close();

	
		
	}

	}

	
