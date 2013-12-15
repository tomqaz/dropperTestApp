package pl.edu.agh.dropper.appTest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

class UDPClient extends AbstractUDPSender {
	int count;

	public UDPClient(int port, int count) {
		super("Client");
		this.port = port;
		this.count = count;
		messages = new ArrayList<>();
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
		for (int i = 0; i < count; ++i) {
			sendData = (sentence + i).getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length,
					IPAddress, port);
			clientSocket.send(sendPacket);

			try {
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				clientSocket.receive(receivePacket);
				String msg = String.format("\t%s(%s) FROM: %d RECEIVED: %s",
						name.substring(0, 3), receiveCounter,
						receivePacket.getPort(),
						new String(receivePacket.getData()));
				System.out.println(msg);
				handleIncomingPacket(receivePacket);
			} catch (SocketTimeoutException e) {
				// System.out.println("timeout");
			}

		}

		clientSocket.close();
	}
}
