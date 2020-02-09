/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.controller;

import core.db.Database;
import core.object.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Saravana
 */
public class UserController {

    private final Database db;

    public UserController(Database db) {
        this.db = db;
    }

    /*
     ============================================================================
     */
    /*
     CURD methods
     */
    public ArrayList<User> getAllUsers() {

        ArrayList<User> userList = new ArrayList<>();

        try {
            String query = "SELECT * FROM tbl_users";

            ResultSet rs = db.executeQuery(query);

            while (rs.next()) {

                userList.add(generateUserObject(rs));

            }
            return userList;

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return null;
    }

    public int insertUser(User u) {

        try {

            String query = "INSERT INTO tbl_users ( UserName, SHAHash, UserRole ) VALUES ( ?, SHA1(?), ? )";

            PreparedStatement ps = db.getPreparedStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole().name());

            ps.executeUpdate();

            // this will get the id for the inserted row, and return it
            ResultSet insertedIdResult = ps.getGeneratedKeys();
            if (insertedIdResult.next()) {
                return insertedIdResult.getInt(1);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return -1;
    }

    /*
     * get user by id
     */
    public User getUserbyID(int ID) {

        try {

            String query = "SELECT * FROM tbl_users WHERE ID = ? LIMIT 1";

            PreparedStatement ps = db.getPreparedStatement(query);

            ps.setInt(1, ID);

            ResultSet rs = ps.executeQuery();

            User u = new User();
            while (rs.next()) {
                u = generateUserObject(rs);
            }

            return u;

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return null;
    }

    /*
     * get user by username
     */
    public User getUserByUserName(String username) {

        try {
            String query = "SELECT * FROM tbl_users WHERE UserName = ? LIMIT 1";

            PreparedStatement ps = db.getPreparedStatement(query);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            User u = new User();
            while (rs.next()) {
                u = generateUserObject(rs);
            }

            return u;

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return null;
    }

    /*
     * delete a user 
     */
    public boolean deleteUser(User u) {

        try {
            PreparedStatement preparedStatement = db.getPreparedStatement("DELETE FROM tbl_users WHERE ID=?");
            preparedStatement.setInt(1, u.getID());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.getMessage();
        }
        return false;
    }

    /*
     * update current user
     */
    public boolean updateUser(User u) {
        try {
            PreparedStatement ps = db.getPreparedStatement("UPDATE tbl_users SET UserName = ?, UserRole = ? WHERE ID = ?");
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getRole().name());
            ps.setInt(3, u.getID());

            ps.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return false;
    }
    
    public boolean updateUserAndPassword(User u){
        try {
            PreparedStatement ps = db.getPreparedStatement("UPDATE tbl_users SET UserName = ?, SHAHash = SHA1(?), UserRole = ? WHERE ID = ?");
            ps.setString(1, u.getUserName());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole().name());
            ps.setInt(4, u.getID());

            ps.executeUpdate();

            return true;

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return false;
    }

    /*
     ============================================================================
     */
    /*
     Utility methods
     */
    /*
     * generate user object from the resultset
     * this can be used inside other CURD methods to generate single user object
     */
    private User generateUserObject(ResultSet rs) throws SQLException {
        User user = new User();

        user.setID(rs.getInt("ID"));
        user.setUserName(rs.getString("UserName"));
        user.setShaHash(rs.getString("SHAHash"));

        String role = rs.getString("UserRole");

        user.setRole(User.Role.valueOf(role));

        return user;
    }

    /*
     * get the SHA1 hash for the given string (password)
     * used to check if the passwords entered are matching with the 
     * hash in the db tbl_users
     */
    public String getPasswordHash(String password) {

        String hash = "";

        try {

            String query = "SELECT SHA1(?)";
            PreparedStatement ps = db.getPreparedStatement(query);
            ps.setString(1, password);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                hash = rs.getString(1);
            }

            return hash;

        } catch (SQLException ex) {

        }

        return null;
    }

}
