/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import core.controller.MainController;
import core.controller.PawnController;
import core.db.Database;
import core.object.Pawn;
import java.sql.SQLException;

/**
 *
 * @author Sri Saravana
 */
public class TestDatabaseConnection {

    public static void main(String[] args) {

       
            MainController controller = new MainController();
            
            
            Pawn p = controller.pawnController.getPawnByID(1);
            
            System.out.println("Pawn Person Name: " + p.getFullName());
            System.out.println("Pawn Date: " + p.getDateOfEntry());
            
      

    }

}
