/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import SystemClasses.ServerMain;
import java.io.*;

/**
 *
 * @author panos, chris
 */
public class MainWindowController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private TextField ipTxt, portTxt;
    //not sure what to do with ip field since server socket is constructed just with port (ip is assumed to be current machine)
    
    private int portNumber;
    
    @FXML
    private void handleStartBtnAction(ActionEvent event) {
        portNumber = Integer.parseInt(portTxt.getText());
        try{
            new ServerMain(portNumber).start();
        } catch (IOException e) {
            System.err.println("Could not start new main server thread");
            System.exit(-1);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
