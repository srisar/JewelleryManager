/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.object;

/**
 *
 * @author Saravana
 */
public class User {

    public static enum Role {

        Administrator, User
    }

    private int ID;
    private String userName, shaHash, password;

    private Role role;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShaHash() {
        return shaHash;
    }

    public void setShaHash(String shaHash) {
        this.shaHash = shaHash;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return getUserName() + "@" + getRole().name();
    }

}
