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

		// TO-DO: Initialise Security Manager
		if (System.getSecurityManager() == null){
			//does this need to be RMIsecurityManager
			System.setSecurityManager(new RMISecurityManager());
		}
		System.out.println("Security Manager initialised.");
		// TO-DO: Instantiate the server class
		
		// TO-DO: Bind to RMI registry
		try {
			rmis = new RMIServer();
			System.out.println("New server instantiated.");
							//what does this  URL need to be?
			RMIServer.rebindServer("RMIServer", rmis);
			System.out.println("Server Bound to RMI registry.");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.print("Trouble " + e);
			e.printStackTrace();
		}
		
	}

	protected static void rebindServer(String serverURL, RMIServer server) {

		// TO-DO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)
		Registry r;
		try {
			//should be createRegistry(//portnumber?)
			//r = LocateRegistry.getRegistry(3000);
			Naming.rebind(serverURL, server);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("No registry to get.");
			try {
				r = LocateRegistry.createRegistry(3000);
				System.out.println("registry created.");
				r.rebind(serverURL, server);
				System.out.println("registry bound.");
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				System.out.println("can't create registry " + e1);
				e.printStackTrace();
				}
			} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		// TO-DO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
		
}

