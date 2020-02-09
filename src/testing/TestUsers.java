/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import core.controller.MainController;
import core.object.User;
import java.util.ArrayList;

/**
 *
 * @author Saravana
 */
public class TestUsers {
    
    public static void main(String[] args) {
        
        MainController mc = new  MainController();
        
        ArrayList<User> lst = new ArrayList<>();
        
        lst = mc.userController.getAllUsers();
        
        for(User u : lst){
            System.out.println(u);
        }
        
    }
    
}
