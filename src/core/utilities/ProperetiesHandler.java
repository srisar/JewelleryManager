/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ProperetiesHandler {

    private final String filename;
    private InputStream input;
    private final Properties readablePropery;

    public ProperetiesHandler(String file) {
        filename = file;
        readablePropery = new Properties();

        try {

            input = new FileInputStream(filename);

            readablePropery.load(input);

        } catch (IOException ex) {
            ex.getMessage();

        }
    }

    /*
     * get the property value for the key
     */
    public String getProperty(String key) {
        return readablePropery.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue){
        return readablePropery.getProperty(key, defaultValue);
    }


    /*
     * close the streams and release the resources
     */
    public void close() {
        try {
            if (input != null) {
                input.close();
            }

        } catch (IOException ex) {

        }
    }

}
