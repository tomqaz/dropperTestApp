package pl.edu.agh.dropper.appTest;


public class TestApp {
	public static void main(String[] args) throws Exception {
		int clientSendPort = 9871;
		int serverReceivePort = 9872;
		int sendCount = 20;
		
		UDPServer server = new UDPServer(serverReceivePort);
		UDPClient client = new UDPClient(clientSendPort, sendCount);
		
		server.start();
		client.start();
		
		client.join();
		server.stopReceiving();
		server.join();
		
		server.printsStats();
		client.printsStats();
	}
}




