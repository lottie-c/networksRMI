/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Arrays;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPServer {

	private DatagramSocket recvSoc;
	private int totalMessages = -1;
	private int[] receivedMessages;
	private boolean close;

	private void run() {
		
		int				pacSize;
		byte[]			pacData;
		DatagramPacket 	pac;

		
		long startTime = System.currentTimeMillis();
		while((System.currentTimeMillis() - startTime < 30000)){
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
		 
		pacData = new byte[500];
		pac = new DatagramPacket(pacData,pacData.length);
		try {
			recvSoc.receive(pac);
		} catch (IOException e) {
			System.out.println("Failed to receive packet");
			e.printStackTrace();
		}
		
		//processMessage()
		}
	}

	public void processMessage(String data) {

		MessageInfo msg = null;

		// TO-DO: Use the data to construct a new MessageInfo object

		// TO-DO: On receipt of first message, initialise the receive buffer

		// TO-DO: Log receipt of the message

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			DatagramSocket recvSoc = new DatagramSocket(rp);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem initialising server side socket.");
			e.printStackTrace();
		}
		// Done Initialisation
		System.out.println("UDPServer ready");
	}

	
	public static void main(String args[]) {
		int	recvPort;

		// Get the parameters from command line
		if (args.length < 1) {
			System.err.println("Arguments required: recv port");
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[0]);

		UDPServer server = new UDPServer(recvPort);
		server.run();
		// TO-DO: Construct Server object and start it by calling run().
	}

}
