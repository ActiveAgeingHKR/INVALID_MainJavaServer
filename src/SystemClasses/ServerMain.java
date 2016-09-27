/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SystemClasses;

import java.net.*;
import java.io.*;

/**
 *
 * @author panos, chris
 */
public class ServerMain extends Thread {
    
    private int portNumber = 0;

    public ServerMain(int portNumber) throws IOException {
        super("ServerMainThread");
        this.portNumber = portNumber;
    }
    
    public void run() {

        boolean listening = true;
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
            while (listening) {
	            new ServerThread(serverSocket.accept()).start();
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
    
}
