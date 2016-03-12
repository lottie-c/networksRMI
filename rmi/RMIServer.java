/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

/**
 * @author bandara
 *
 */
public class RMIServer extends UnicastRemoteObject implements RMIServerI {

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
		super();
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {

		// TO-DO: On receipt of first message, initialise the receive buffer
		if (totalMessages == -1 ){
			receivedMessages = new int[msg.totalMessages];
			totalMessages = 0;
		}
		
		// TO-DO: Log receipt of the message
		receivedMessages[totalMessages] = msg.messageNum;
		totalMessages += 1;
		System.out.println("Message:" + msg.messageNum + " received");
		
	
		// TO-DO: If this is the last expected message, then identify
		//        any missing messages
		if(msg.messageNum == totalMessages){
			int i = 0, j=1;
			
			while( i < totalMessages){
				if (receivedMessages[i] != j ){
					while(j < receivedMessages[i]){
						System.out.println("Message: " + j + " is missing");
						j++;
					}
				}
				j++;
				i++;
			}
		}

	}


	public static void main(String[] args) {
		RMIServer rmis = null;
		 
		if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
		try{
			String name = "RMIServerI";
			RMIServerI server = new RMIServer();
	        /*RMIServerI stub =
	            (RMIServerI) UnicastRemoteObject.exportObject(server, 0);*/
	        Registry registry = LocateRegistry.createRegistry(2000);
	        registry.rebind(name, server);
	        System.out.println("RMIServerI bound");
	    }catch (Exception e) {
	        System.err.println("RMIServerI exception:");
	        e.printStackTrace();
	    }
	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
				// Start / find the registry (hint use LocateRegistry.createRegistry(...)
				// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

				// TO-DO:
				// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
				// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
				// expects different things from the URL field.
		
		
	}
		
		
		
}

