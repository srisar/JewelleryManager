/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package core.db;

import core.utilities.ProperetiesHandler;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Sri Saravana
 */
public class Database {

    private final Connection connection;
    private final Statement statement;
    private PreparedStatement prepStatement;
    private ResultSet rs;
    private final ProperetiesHandler configHandler;

    /*
     * Creates the database object from the name and parameters specified.
     * This Database object is mysql specific.
     */
    public Database() throws SQLException {
        
        configHandler = new ProperetiesHandler("config.properties");
        
        String url = configHandler.getProperty("db_config_string", "jdbc:mysql://localhost:3306/jewelleryDB");

        connection = DriverManager.getConnection(url, DbInformation.DB_USER, DbInformation.DB_PASS);
        statement = connection.createStatement();
        
        rs = null;
    }
    /*
     * Return the connection
     */

    public Connection getConnection() {
        return connection;
    }

    /*
     * Returns the statement 
     */
    public Statement getStatement() {
        return statement;
    }
    
    /*
     * Returns the prepared statement
     */
    public PreparedStatement getPreparedStatement(String sql) throws SQLException{
        return connection.prepareStatement(sql);
    }
    
    public PreparedStatement getPreparedStatement(String sql, int option) throws SQLException{
        return connection.prepareStatement(sql, option);
    }

    /*
     * Returns resultset
     */
    public ResultSet getResultSet() {
        return rs;
    }

    public ResultSet executeQuery(String sql) {
        try {
            rs = statement.executeQuery(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return rs;
    }

    public int executeUpdate(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return -1;
    }

    /*
     * Closes database
     */
    public void closeDatabase() {
        try {

            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}