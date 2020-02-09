/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import core.controller.MainController;
import core.object.Pawn;
import core.utilities.printer.JewelleryReceiptPrinter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sri Saravana
 */
public class TestAbhiraReceiptPrinter {
    
    public static void main(String[] args) throws IOException {
        
        MainController controller = new MainController();
        
        Pawn p = controller.pawnController.getPawnByID(5);
        
        
//        File tempFile = File.createTempFile("temp-file", "tmp");
//        
//        String temp = tempFile.getParent();
//        
//        System.out.println(temp);
        
        
        JewelleryReceiptPrinter printer = new JewelleryReceiptPrinter("receipt.pdf");
        
        printer.printPawningReceipt(p);
        
        try {
            
            Process process = Runtime.getRuntime().exec("C:\\Program Files\\Tracker Software\\PDF Viewer\\pdfxcview.exe " + printer.getPdfPrinter().getFullPdfFilePath());
            
        } catch (IOException ex) {
            Logger.getLogger(TestAbhiraReceiptPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        tempFile.deleteOnExit();
        
        
    }
    
}
