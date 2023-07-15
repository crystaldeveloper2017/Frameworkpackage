
package pdfGeneration;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
 
public class EstimatePDFHelper  extends PdfPageEventHelper
{
	public static void main(String[] args) throws DocumentException, MalformedURLException, IOException 
	{
		
		//generatePDF("Ref:- SSEGPL/19/008/R","Date:- November 17th 2018","AKT Oil");
		List<LinkedHashMap<String,Object>> prodDetails=new ArrayList<LinkedHashMap<String,Object>>();
		 
		 LinkedHashMap<String, Object> prodMap=new LinkedHashMap<>();
		  prodMap.put("itemId", "1");
		  prodMap.put("itemName", "test");
		  prodMap.put("mrp", "mrptest");
		  prodMap.put("offerPrice", "offerPrice");

		  prodDetails.add(prodMap);
	
	
	
//	generatePDFForPO("1.pdf","1234","shoaeb","123",prodDetails);
		
	}
	
	
	
	
	public static void generatePDFForPO(String DestinationPath,String estimateNo,List<LinkedHashMap<String,Object>> prodDetails) throws DocumentException, MalformedURLException, IOException
	{

		  Document document = new Document (PageSize.A4.rotate(), 20, 20, 20, 60);
		  PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DestinationPath));
		  /*PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("F:\\1.pdf"));*/
		  
		  EstimatePDFHelper event = new EstimatePDFHelper();
	        writer.setPageEvent(event);
		  document.open();     

		  
		  PdfPTable table = new PdfPTable(1);
	        PdfPCell cell;        
	        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.UNDERLINE);
	        Font regSmallFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
	        cell = new PdfPCell(new Phrase("Estimate No - "+estimateNo,new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD)));	        
	        cell.setBorder(Rectangle.NO_BORDER);
	        
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);	        
		  document.add(table);
		  
		  
		  document.add(new Paragraph("\n\n"));
		  

		  
		  
				table = new PdfPTable(4);				
				table.setWidthPercentage(100);    
				table.setWidths(new int[]{5,6,5,5});
		  
		  
		  
				cell = new PdfPCell(new Phrase("Estimate No : "+estimateNo ,new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL) ));
				 cell.setBorderWidthBottom(0);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setRowspan(2);
				table.addCell(cell);
			
		  
		  
		  
		  table = new PdfPTable(2);
		  table.setWidthPercentage(100);
		  
	      table.setWidths(new int[]{3,10});

		  
		  
		
	        
	        
	        int srno=1;
	        for(HashMap<String,Object> prod:prodDetails)
	        {
	        	
	        	
	        	cell = new PdfPCell(new Phrase("Product Image",new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL) ));
	    		  cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	        table.addCell(cell);
		        
	        
			        cell = new PdfPCell(new Phrase(prod.get("imgPath").toString(),new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL) ));
			        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        table.addCell(cell);
	        	
	        	
	        	
	        	  cell = new PdfPCell(new Phrase("Product Name",new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL) ));
	    		  cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	    		  cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	        table.addCell(cell);
		        
	        
			        cell = new PdfPCell(new Phrase(prod.get("itemName").toString(),new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL) ));
			        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        table.addCell(cell);
			        
			       
			        
		        
	        }	
	        
	       
	        
	       
	        
	        
		  document.add(table);
		 
		document.close();
		
		
	}
	

	

	 public void onStartPage(PdfWriter writer, Document document)
	 {
		 try
		 {
			 //addHeader(document);
			 /*addWaterMark(document,writer);*/
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
    }
	 
	 public void onEndPage(PdfWriter writer, Document document)
	 {
		 try
		 {}
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
    }
	 
	
	
	
	
		
	
}