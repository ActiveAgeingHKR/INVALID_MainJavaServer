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
 * @author panos
 */
public class ServerThread extends Thread{
    
    private Socket socket = null;

    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    
    public void run() {

        try (
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            ClientProtocol clientProtocol = new ClientProtocol();
            outputLine = clientProtocol.processInput(null);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                //sends each line from client to clientProtocol for processing
                outputLine = clientProtocol.processInput(inputLine);
                //gets appropriate response and sends it to client
                out.println(outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
