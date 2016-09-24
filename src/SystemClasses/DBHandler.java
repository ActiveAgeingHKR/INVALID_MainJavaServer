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
