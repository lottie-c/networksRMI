/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import common.MessageInfo;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
		
		if (System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
			try {
				Registry r = LocateRegistry.getRegistry(args[0]);
				// TO-DO: Bind to RMIServer
				RMIServer server = (RMIServer)r.lookup(urlServer);
				
				// TO-DO: Attempt to send messages the specified number of times
				for (int i =1; i <= numMessages; i++){
					MessageInfo msg = new MessageInfo(numMessages, i);
					server.receiveMessage(msg);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.print("An exception occurred during the " +
						"execution of the remote procedure call to receiveMessage()");
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				System.out.print("Tried to look up a registry that has no" +
						" associated binding");
				e.printStackTrace();
			}
		}

	}
}
