/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import java.sql.Date;
import org.joda.time.LocalDate;

/**
 *
 * @author Sri Saravana
 */
public class TestJodaDates {
    public static void main(String[] args) {
        
        LocalDate date = new LocalDate();
        
        int month = date.getMonthOfYear();
        int year = date.getYear();
        int day = date.getDayOfMonth();
        
        LocalDate newDate = new LocalDate(year, month, 1);
        
        java.sql.Date sqlDate = new Date(newDate.toDate().getTime());
        
        System.out.println("Month " + month);
        System.out.println(newDate.toString());
        System.out.println(sqlDate.toString());
        
    }
    
}
