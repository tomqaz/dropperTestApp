package pl.edu.agh.dropper.appTest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

class UDPClient extends AbstractUDPSender {
	int msgToSendCount;

	public UDPClient(int port, int msgToSendCount) {
		super("Client", port);
		this.msgToSendCount = msgToSendCount;
	}

	@Override
	public void run() {
		try {
			send();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void send() throws Exception {
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];

		String sentence = "aaa";
		DatagramPacket sendPacket;
		clientSocket.setSoTimeout(500);
		for (int i = 0; i < msgToSendCount; ++i) {
			sendData = (sentence + i).getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length,
					IPAddress, port);
			clientSocket.send(sendPacket);

			try {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				clientSocket.receive(receivePacket);
				handleIncomingPacket(receivePacket);
			} catch (SocketTimeoutException e) {
				// System.out.println("timeout");
			}
		}

		clientSocket.close();
	}
}
