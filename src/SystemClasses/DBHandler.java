/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SystemClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Panadmin
 */
public class DBHandler {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String DB_URL;
    private static String USER;
    private static String PASS;   
    private static Connection connection = null;
    private static PreparedStatement stmt = null;
    private static String dbname;
    

    //Prepared String for prepared Statements.
    private static String showDatabasesStatement = "show databases  like ;";

    public static boolean connect(String addr, String port, String uname, String pass) {
        boolean connectionEstablished = false;
        try {

            DB_URL = "jdbc:mysql://" + addr + "/";
            USER = uname;
            PASS = pass;
            //STEP 2: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            if (connection.isClosed()) {
                connectionEstablished = false;
            } else {
                connectionEstablished = true;
            }          
        } catch (SQLException se) {

        } catch (ClassNotFoundException e) {

        }
        System.out.println("Connections is succesful :"+connectionEstablished);
        return connectionEstablished;
    }//end main
    
    //prepared statements for testing
    public static boolean findEmployee(String userName, String tableName) {
        boolean found = false;
        try {
            PreparedStatement findEmployee = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE emp_username=?;");
            findEmployee.setString(1, userName);
            ResultSet rs = findEmployee.executeQuery();
            
            while (rs.next()) {
                if (userName.contentEquals(rs.getString("emp_username"))) {
                    found = true;
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return found;
    }
    
    public static boolean findCUfirstName(String firstName, String tableName) {
        boolean found = false;
        try {
            PreparedStatement findEmployee = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE cu_firstname=?;");
            findEmployee.setString(1, firstName);
            ResultSet rs = findEmployee.executeQuery();
            
            while (rs.next()) {
                if (firstName.contentEquals(rs.getString("cu_firstname"))) {
                    found = true;
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return found;
    }
    
    public static boolean findCUlastName(String lastName, String tableName) {
        boolean found = false;
        try {
            PreparedStatement findEmployee = connection.prepareStatement(
                    "SELECT * FROM " + tableName + " WHERE cu_lastname=?;");
            findEmployee.setString(1, lastName);
            ResultSet rs = findEmployee.executeQuery();
            
            while (rs.next()) {
                if (lastName.contentEquals(rs.getString("cu_lastname"))) {
                    found = true;
                } else {
                    throw new Exception();
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return found;
    }
    
    public static void addVisitor(String firstName, String lastName, String email,
            int repeated) throws Exception {
        
        try {
            PreparedStatement addVisitor = connection.prepareStatement("INSERT INTO visitors "
            + "(vis_firstname, vis_lastname, vis_email, vis_repeated) VALUES (?, ?, ?, ?)");
            
            addVisitor.setString(1, firstName);
            addVisitor.setString(2, lastName);
            addVisitor.setString(3, email);
            addVisitor.setInt(4, repeated);
            addVisitor.execute();
        } catch (SQLException ex) {
            throw new Exception("Error");
        }
        
    }
    
    public static void addCustomer(String firstName, String lastName, String address) throws Exception {
        
        try {
            PreparedStatement addVisitor = connection.prepareStatement("INSERT INTO customers "
            + "(cu_firstname, cu_lastname, cu_address) VALUES (?, ?, ?)");
            
            addVisitor.setString(1, firstName);
            addVisitor.setString(2, lastName);
            addVisitor.setString(3, address);
            addVisitor.execute();
        } catch (SQLException ex) {
            throw new Exception("Error");
        }
        
    }



    //Incase we need them.
    private static boolean createDatabase(String dbName) {
        boolean success = true;
        try {
            //STEP 4: Execute a query
            System.out.println("Creating database...");
            String sql = "CREATE SCHEMA IF NOT EXISTS " + dbName + " DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;";
            stmt = connection.prepareStatement(sql);
            stmt.executeUpdate();
            System.out.println("Database created successfully...");
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        return success;
    }

    public static ObservableList<String> getDatabases(String dbName) {
        ObservableList<String> databases = FXCollections.observableArrayList();
        int i = 0;
        showDatabasesStatement = showDatabasesStatement+"\'"+dbName+"_%\'";
        try {
            stmt = connection.prepareStatement(showDatabasesStatement);
            stmt.executeUpdate();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                databases.add(rs.getString(1));
                System.out.println(databases.get(i++));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return databases;
    }

}
