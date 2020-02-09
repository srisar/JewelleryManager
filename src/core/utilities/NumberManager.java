
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package core.utilities;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.JOptionPane;

/**
 *
 * @author Sri Saravana
 */
public final class NumberManager {

    // this method checks whether the string can be converted into integer
    public static boolean canConveterToNumber(String numberText) {
        try {
            double doubleValue = Double.parseDouble(numberText);

            return true;
        } catch (NumberFormatException ex) {
            System.err.println("Cant convert into number: " + ex.getMessage());
        }

        return false;
    }

    // parse int from string
    // when fails, it returns just 0
    public static int toInt(String numberText) {
        try {
            int value = Integer.parseInt(numberText);

            return value;
        } catch (NumberFormatException ex) {
            System.err.println("Cant convert into int: " + ex.getMessage());
        }

        return 0;
    }

    // Parse float from string
    public static double toFloat(String numberText) {
        try {
            float value = Float.parseFloat(numberText);

            return value;
        } catch (NumberFormatException ex) {
            System.err.println("Cant convert into float: " + ex.getMessage());
        }

        return 0.0F;
    }

    // parse double from string
    public static double toDouble(String numberText) {
        try {
            double value = Double.parseDouble(numberText);

            return value;
        } catch (NumberFormatException ex) {
            System.err.println("Cant convert into double: " + ex.getMessage());
        }

        return 0.0;
    }

    public static String toString(double number) {
        return Double.toString(number);
    }

    public static String toString(int number) {
        return Integer.toString(number);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
