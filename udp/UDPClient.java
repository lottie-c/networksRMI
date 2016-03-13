/*
 * Created on 07-Sep-2004
 * @author bandara
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import common.MessageInfo;

/**
 * @author bandara
 *
 */
public class UDPClient {

	private DatagramSocket sendSoc;

	public static void main(String[] args) {
		InetAddress	serverAddr = null;
		int			recvPort;
		int 		countTo;
		//String 		message;

		// Get the parameters
		if (args.length < 3) {
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		// TO-DO: Construct UDP client class and try to send messages
		try {
			serverAddr = InetAddress.getByName(args[0]);
		} catch (UnknownHostException e) {
			System.out.println("Bad server address in UDPClient, " + args[0] + " caused an unknown host exception " + e);
			System.exit(-1);
		}
		recvPort = Integer.parseInt(args[1]);
		countTo = Integer.parseInt(args[2]);

		UDPClient client = new UDPClient();
		client.testLoop(serverAddr, recvPort, countTo);
	}

	public UDPClient() {
		// TO-DO: Initialise the UDP socket for sending data
		try {
			sendSoc = new DatagramSocket();
		} catch (SocketException e) {
			System.out.println("Problem initialising client side socket.");
			e.printStackTrace();
		}
	}

	private void testLoop(InetAddress serverAddr, int recvPort, int countTo) {
		//int				tries = 0;
		// TO-DO: Send the messages to the server
		for (int i = 0; i < countTo; i++){
			String 			payload;
			MessageInfo msg = new MessageInfo(countTo, i);
			payload = msg.toString();
			this.send(payload ,serverAddr, recvPort);
			System.out.println("message " + i + " sent");
			if(i == countTo - 1){
				System.out.println("All messages sent");
			}
		}
		
	}

	private void send(String payload, InetAddress destAddr, int destPort) {
		int				payloadSize;
		byte[]				pktData;
		DatagramPacket		pkt;
		
		pktData = payload.getBytes();
		payloadSize = pktData.length;
		// TO-DO: build the datagram packet and send it to the server
		pkt = new DatagramPacket(pktData, payloadSize, destAddr, destPort);
		try {
			sendSoc.send(pkt);
		} catch (IOException e) {
			System.out.println("Problem sending packet, client end.");
			e.printStackTrace();
		}
	}
}
