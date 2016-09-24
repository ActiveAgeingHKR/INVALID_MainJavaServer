/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;



import SystemClasses.ConnectionSettings;
import SystemClasses.DBHandler;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author panos
 */
public class ConnectionSettingsController implements Initializable {

    @FXML
    private TextField serverAddressTxt, portNumberTxt, usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    private String addr, port, uname, pass;

    private Path connectionSettingsFile = Paths.get("src/Resources/mysql_conn.conf");
    private ObjectOutputStream output = null;

    @FXML
    public void saveAction(ActionEvent event) {
        addr = serverAddressTxt.getText();
        port = portNumberTxt.getText();
        uname = usernameTxt.getText();
        pass = passwordTxt.getText();

        try {
            ConnectionSettings settings = new ConnectionSettings(addr, port, uname, pass);
            output = new ObjectOutputStream(Files.newOutputStream(connectionSettingsFile));
            output.writeObject(settings);
            if (DBHandler.connect(addr, port, uname, pass)) {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource("/Views/MainWindow.fxml"));

                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    stage.setTitle("CareTaker_JavaServer");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ConnectionSettingsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //load a pop up window that connection has failed
                Alert alert = new Alert(Alert.AlertType.ERROR, "This server could not connect to the remote"
                        + " MySQL server. ", ButtonType.CLOSE);

                    alert.setTitle("CareTaker_JavaServer");
                    Stage s = (Stage) alert.getDialogPane().getScene().getWindow();
                    alert.showAndWait();
            }

        } catch (Exception ex) {
            Logger.getLogger(ConnectionSettingsController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        

//        
    }
    
    @FXML
    private void clickExitButton(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage window = (Stage) node.getScene().getWindow();
        window.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
