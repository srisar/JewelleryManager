/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author Sri Saravana
 */
public class TestPDFGeneration {

    private static final String FILE = "d://mypdf.pdf";
    private static final Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font bodyFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.RED);

    public static void main(String[] args) {

        try {
            
            Document doc = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(FILE));
            doc.open();
            addMetadata(doc);
            addTitlePage(doc);
            addTable(doc);
            
            doc.close();
            
        }catch(FileNotFoundException | DocumentException e){
            System.out.println(e.getMessage());
        }

    }

    private static void addMetadata(Document document) {

        document.addTitle("This is the title!");
        document.addSubject("This is the subject");
        document.addAuthor("Saravana");

    }

    private static void addTitlePage(Document doc) throws DocumentException {

        Paragraph preface = new Paragraph();

        preface.add(new Paragraph(" "));
        preface.add(new Paragraph("This is a generated PDF File", catFont));

        doc.add(preface);

    }
    
    private static void addTable(Document doc) throws DocumentException{
        

        
        Paragraph p = new Paragraph("This is a table.", bodyFont);

        
        PdfPTable pTable = new PdfPTable(2);
        
        
        
        PdfPCell c1 = new PdfPCell(new Phrase("Information", bodyFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTable.addCell(c1);
        
        c1 = new PdfPCell(new Phrase("Details", bodyFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        pTable.addCell(c1);
        
        pTable.setHeaderRows(1);
        
        pTable.addCell("Name");
        pTable.addCell("Saravana");
        pTable.addCell("Address");
        pTable.addCell("Some place, \nsomewhere");
        
        p.add(pTable);
        

        
        doc.add(p);
        
        
        
    }

}
