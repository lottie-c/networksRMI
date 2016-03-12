/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class RMIClient {

	public static void main(String[] args) {

		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if (args.length < 2){
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// TO-DO: Initialise Security Manager

		// TO-DO: Bind to RMIServer

		// TO-DO: Attempt to send messages the specified number of times
	
	
	        if (System.getSecurityManager() == null) {
	            System.setSecurityManager(new SecurityManager());
	        }
	        
	        try {
	            String name = "RMIServerI";
	            Registry registry = LocateRegistry.getRegistry(args[0],2000);
	            RMIServerI server = (RMIServerI) registry.lookup(name);
	            for (int i = 0; i < numMessages; i++){
	            	MessageInfo msg = new MessageInfo(numMessages, i);
	            	server.receiveMessage(msg);
	            }
	            System.out.println("All Messages Sent");
	        } catch (Exception e) {
	            System.err.println("server exception:");
	            e.printStackTrace();
	        }
	    }    
	
	
	
}

/*RMIServerI iRMIServer = null;

// Check arguments for Server host and number of messages
if (args.length < 2){
	System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
	System.exit(-1);
}

String urlServer = new String("rmi://" + args[0] + "/RMIServer");
int numMessages = Integer.parseInt(args[1]);

// TO-DO: Initialise Security Manager

if (System.getSecurityManager() == null){
	System.setSecurityManager(new RMISecurityManager());

		// TO-DO: Bind to RMIServer
		Registry r;
		try {
			//Returns a reference to the remote object Registry on the specified host and port.
			r = LocateRegistry.getRegistry(args[0],3000);
			System.out.println("getRegistry() complete");
			//Returns the remote reference bound to the specified name in this registry
			iRMIServer = (RMIServer)r.lookup("RMIServer");
			System.out.println("lookup() complete");
			// TO-DO: Attempt to send messages the specified number of times
			for (int i =1; i <= numMessages; i++){
				MessageInfo msg = new MessageInfo(numMessages, i);
				iRMIServer.receiveMessage(msg);
			}
		} catch (RemoteException e) {
			System.out.println("in here yp" +
					"");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/


	
		
