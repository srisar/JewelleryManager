/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import core.db.Database;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sri Saravana
 */
public class MainController {

    private Database db;
    public PawnController pawnController;
    public UserController userController;

    public MainController() {

        try {

            db = new Database();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting with database!\nPlease run the database server instance. (XAMPP)", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        //initiate controllers
        pawnController = new PawnController(db);
        userController = new UserController(db);

    }

    public Database getDb() {
        return db;
    }

}
