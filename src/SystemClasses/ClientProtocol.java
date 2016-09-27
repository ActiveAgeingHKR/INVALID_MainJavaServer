/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SystemClasses;

import SystemClasses.DBHandler;

/**
 *
 * @author Chris
 */
public class ClientProtocol {
    private static final int WAITING = 0;
    private static final int REQUESTED_ID = 1;
    private static final int CLIENT_AUTHENTICATED = 2;
    
    
    
    private int state = WAITING;
    
    
    public String processInput(String fromClient) {
        String toClient = null;
        if(state == WAITING) {
            //then this is initial contact and it's server's job to say hello
            toClient = "Hello. Please state your clientID";
            state = REQUESTED_ID;
        }
        else if(state == REQUESTED_ID) {
            DBHandler handler = new DBHandler();
            /*something like this:
            if(handler.authenticateClient(fromClient)) {
                toClient = "State your business"
                state = CLIENT_AUTHENTICATED
            } else {
                toClient = "Bye"
                state = WAITING    
            }*/        
        }
        return toClient;
    }
    
}
