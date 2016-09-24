/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainjavaserver;

import Controllers.ConnectionSettingsController;
import SystemClasses.ConnectionSettings;
import SystemClasses.DBHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author panos
 */


/**
 * 
 * @author panos
 * Upon the startup of the server, our application will try to connect to the MySQL server.
 * All the information that are required to establish this connection will be saved in a configuration file mysql_conn.conf (wow great name eh?)
 * If this file is found, it is loaded on the memory, de-serialized and its data are read by the server and pass as parameters in the DBHandler.
 * If the file it is not found , which is likely on the first run of the application, a window will pop up and will ask you to enter the required information yourself.
 * After hitting the 'save' button the file is created and will be used the next time the server application runs.
 */
public class MainJavaServer extends Application {
     private Path connectionSettingsFile =  Paths.get("/Resources/mysql_conn.conf");
     
    @Override
    public void start(Stage stage) throws Exception {
        
         if (Files.exists(connectionSettingsFile)) {
            try {
                //read the file and load
                ObjectInputStream input = new ObjectInputStream(Files.newInputStream(connectionSettingsFile));
                
                ConnectionSettings conn = (ConnectionSettings) input.readObject();

                if (DBHandler.connect(conn.getServerAddress(), conn.getPortNumber(), conn.getUsername(), conn.getPassword())) {
                    Parent mainViewRoot = FXMLLoader.load(getClass().getResource("/Views/MainWindow.fxml"));

                    Scene scene = new Scene(mainViewRoot);

                    stage.setScene(scene);
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    stage.setTitle("CareTaker_JavaServer");
                    stage.show();
                } else {
                    
                    //load a pop up window that connection has failed
                Alert alert = new Alert(Alert.AlertType.ERROR, "This server could not connect to the remote"
                        + " MySQL server. ", ButtonType.CLOSE);
                    alert.setTitle("CareTaker_JavaServer");
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("/Views/ConnectionSettings.fxml"));
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    stage.setTitle("CareTaker_JavaServer");
                    
                    stage.show();
                }

            } catch (IOException ex) {
                Logger.getLogger(ConnectionSettingsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (Files.notExists(connectionSettingsFile)) {
            
            Alert alert = new Alert(Alert.AlertType.ERROR, "The file mysql_conn.conf is not found.", ButtonType.CLOSE);
                    alert.setTitle("CareTaker_JavaServer");
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
                    
            Parent root = FXMLLoader.load(getClass().getResource("/Views/ConnectionSettings.fxml"));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setTitle("CareTaker_JavaServer");
            stage.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
