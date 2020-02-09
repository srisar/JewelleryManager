
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.utilities.printer;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Sri Saravana
 */
public class ReceiptPrinter {

    // set some default fonts and sizes
    protected final Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    protected final Font bodyFont = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    protected final PDFPrinter printer;

    // main constructor
    public ReceiptPrinter(String file) {

        printer = new PDFPrinter(file);
        printer.openDocument();
    }

    public void execPdfViewer() {

        if (Desktop.isDesktopSupported()) {

            try {
                Desktop.getDesktop().open(new File(printer.getFullPdfFilePath()));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    // this will allow you to set headding 1 title
    public void setDocumentTitle(String title) {
        Paragraph titleParagraph = new Paragraph();
        Paragraph titleText = new Paragraph(title, titleFont);

        titleText.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.add(titleText);

        try {
            printer.getDocument().add(titleParagraph);
        } catch (DocumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // get the PDFPrinter instance
    public PDFPrinter getPdfPrinter() {
        return printer;
    }

    // close printer
    public void closePrinter() {
        printer.closeDocument();
    }
}
