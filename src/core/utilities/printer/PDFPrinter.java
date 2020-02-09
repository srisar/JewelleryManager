
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.utilities.printer;

//~--- non-JDK imports --------------------------------------------------------
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfWriter;

//~--- JDK imports ------------------------------------------------------------
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Sri Saravana
 */
public class PDFPrinter {

    private String author, title, subject;
    private PdfWriter pdfWriter;
    private Document document;
    private File tempFolder;
    private String tempFolderPath, pdfFile, fullPdfFilePath;

    // main constructor
    public PDFPrinter(String pdfFile) {
        this.pdfFile = pdfFile;

        // this will find the temp folder for windows and create temp pdf file
        // in that folder
        try {
            tempFolder = File.createTempFile("gravitide-pdf", "tmp");
            tempFolderPath = tempFolder.getParent();

            // this will delete the temp file created to find the temp folder path,
            // when JVm exits
            tempFolder.deleteOnExit();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        fullPdfFilePath = tempFolderPath + "\\" + pdfFile;

        try {
            document = new Document();
            document.setPageSize(PageSize.A5);
            document.setMargins(20, 20, 20, 20);

            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(fullPdfFilePath));
            
            PdfAction action = new PdfAction(PdfAction.PRINTDIALOG);
            pdfWriter.setOpenAction(action);
            
        } catch (FileNotFoundException | DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void openDocument() {
        document.open();
    }

    public void closeDocument() {
        document.close();
    }

    public void setMetadata(String title, String author, String subject) {
        this.title = title;
        this.author = author;
        this.subject = subject;
        document.addTitle(title);
        document.addAuthor(author);
        document.addSubject(subject);
    }

    // get the document
    public Document getDocument() {
        return document;
    }

    public String getFullPdfFilePath() {
        return fullPdfFilePath;
    }
}
