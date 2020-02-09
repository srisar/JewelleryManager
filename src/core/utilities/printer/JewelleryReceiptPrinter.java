/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.utilities.printer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import core.object.Pawn;
import core.utilities.NumberManager;

/**
 *
 * @author Sri Saravana
 */
public class JewelleryReceiptPrinter extends ReceiptPrinter {

    public JewelleryReceiptPrinter(String file) {
        super(file);

        setDocumentTitle("ABIRA JEWELLERY PAWNING RECEIPT");
    }

    public void printPawningReceipt(Pawn pawn) {

        Paragraph tableParagraph = new Paragraph(" ");

        PdfPTable dataTable = new PdfPTable(2);

        PdfPCell cell;

        float cellpadding = 8f;

        cell = new PdfPCell(new Phrase("Receipt Number", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(NumberManager.toString(pawn.getReceiptNumber()), bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Date", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(pawn.getDateOfEntry().toString(), bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Full Name", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(pawn.getFullName(), bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Address", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(pawn.getAddress(), bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase("NIC Number", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(pawn.getNicNumber(), bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Description", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(pawn.getDescription(), bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Net weight", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(NumberManager.toString(pawn.getNetWeight()) + " Grams", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Amount Paid", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(NumberManager.toString(pawn.getAmountPaid()) + " Rupees", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Date of Recollection", bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        cell = new PdfPCell(new Phrase(pawn.getDateOfRecollection().toString(), bodyFont));
        cell.setPadding(cellpadding);
        dataTable.addCell(cell);

        // add the table to the paragraph.
        tableParagraph.add(dataTable);

        // add signature part
        Paragraph signature = new Paragraph("");

        signature.setSpacingBefore(90f);
        
        PdfPTable signatureTable = new PdfPTable(2);

        cell = new PdfPCell(new Phrase("  "));
        cell.setPadding(20f);
        signatureTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "));
        cell.setPadding(20f);
        signatureTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Signature of Receipent", bodyFont));
        cell.setPadding(cellpadding);
        signatureTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Signature of Cashier", bodyFont));
        cell.setPadding(cellpadding);
        signatureTable.addCell(cell);
        
        signature.add(signatureTable);
        
        try {
            printer.getDocument().add(tableParagraph);
            printer.getDocument().add(signature);
        } catch (DocumentException ex) {
            System.out.println(ex.getMessage());
        }

        //close the printer so other programs can access the file
        printer.closeDocument();

    }

}
