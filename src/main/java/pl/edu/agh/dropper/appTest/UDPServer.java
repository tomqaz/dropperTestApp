package pl.edu.agh.dropper.appTest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

class UDPServer extends AbstractUDPSender {
	boolean keepReceiving = true;

	public UDPServer(int port) {
		super("Server", port);
	}

	@Override
	public void run() {
		try {
			receive();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receive() throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(port);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		serverSocket.setSoTimeout(10);

		while (keepReceiving) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			String sentence;
			try {
				serverSocket.receive(receivePacket);
				handleIncomingPacket(receivePacket);

				sentence = new String(receivePacket.getData());
				InetAddress IPAddress = receivePacket.getAddress();
				int senderPort = receivePacket.getPort();

				String capitalizedSentence = sentence.toUpperCase();
				sendData = capitalizedSentence.getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, senderPort);
				serverSocket.send(sendPacket);
			} catch (SocketTimeoutException e) {
				// System.out.println("timeout");
			}

		}
		serverSocket.close();
	}

	public void stopReceiving() {
		keepReceiving = false;
	}

}