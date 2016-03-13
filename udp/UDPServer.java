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
	private boolean close = true;
	int [] lostMessages;
	int totalLostMessages = 0;
	
	private void run() {
		
		int				pacSize = 500;
		byte[]			pacData = new byte[pacSize];
		DatagramPacket 	pac = new DatagramPacket(pacData, pacSize);

		
		long startTime = System.currentTimeMillis();
		while(close){
			if(System.currentTimeMillis() - startTime > 3000){
				close = false; 
			}
		// TO-DO: Receive the messages and process them by calling processMessage(...).
		//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever
			
			
			try {
				recvSoc.receive(pac);
				String payload = new String(pac.getData(), 0, pacSize).trim();
				processMessage(payload);
				
				
			} catch (IOException e) {
				System.out.println("Problem in recvSoc.receive().");
				e.printStackTrace();
			}

		}
	}

	public void processMessage(String data) {

		// TO-DO: Use the data to construct a new MessageInfo object
		try {
			MessageInfo msg = new MessageInfo(data);
			
			if (totalMessages == -1 ){
				receivedMessages = new int[msg.totalMessages];
				totalMessages = 0;
				lostMessages = new int[msg.totalMessages];
			}
			
			// TO-DO: Log receipt of the message
			receivedMessages[totalMessages] = msg.messageNum;
			totalMessages += 1;
			
			if(msg.messageNum + 1  == msg.totalMessages){

				int i = 0, j=1;
				
				while( i < totalMessages){
					if (receivedMessages[i] != j ){
						while(j < receivedMessages[i]){
							System.out.println("Message: " + j+1 + " is missing");
							lostMessages[totalLostMessages] = j+1;
							totalLostMessages++;
							j++;
						}
					}
					j++;
					i++;
				}
				
				System.out.println("Messages sent: " + totalMessages);
				System.out.println("Messages received: " + totalMessages);
				if(totalLostMessages > 0){
				System.out.println("Missing Messages are: " + lostMessages.toString());
				}
			}
			
		} catch (Exception e) {
			System.out.println("Can't convert incoming data to MessageInfo");
			e.printStackTrace();
		}
	

		// TO-DO: If this is the last expected message, then identify
		//        any missing messages

	}


	public UDPServer(int rp) {
		// TO-DO: Initialise UDP socket for receiving data
		try {
			recvSoc = new DatagramSocket(rp);
		} catch (SocketException e) {
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
		

		// TO-DO: Construct Server object and start it by calling run().
		UDPServer server = new UDPServer(recvPort);
		server.run();
		
	}

}
