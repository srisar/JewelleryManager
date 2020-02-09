/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Saravana
 */
public class TestPropertiesFiles {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Properties prop = new Properties();
        
        InputStream input = new FileInputStream("config.properties");
        
        prop.load(input);
        
        System.out.println(prop.getProperty("name"));
        System.out.println(prop.getProperty("age"));
        
        
       
    }
    
}
