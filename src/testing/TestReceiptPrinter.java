/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import core.utilities.printer.ReceiptPrinter;

/**
 *
 * @author Sri Saravana
 */
public class TestReceiptPrinter {
    
    public static void main(String[] args) {
        
        ReceiptPrinter printer = new ReceiptPrinter("D:/myfile.pdf");
        
        printer.setDocumentTitle("Abhira Jwellery Pawning Receipt");
        
        printer.closePrinter();
        
        
    }
    
}
