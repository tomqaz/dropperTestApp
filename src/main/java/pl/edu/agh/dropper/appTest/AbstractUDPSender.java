package pl.edu.agh.dropper.appTest;

import java.net.DatagramPacket;
import java.util.List;

public class AbstractUDPSender extends Thread{
	int port;
	int receiveCounter = 0;
	final String name;
	List<String> messages;
	
	public AbstractUDPSender(String name){
		this.name = name;
	}
	protected void handleIncomingPacket(DatagramPacket receivePacket) {
		messages.add(new String(receivePacket.getData()));
		++receiveCounter;
	}
	
	
	public void printsStats() {
		System.out.println(name+" received: " + receiveCounter);
		for (String msg : messages) {
			System.out.println("\t" + msg);
		}
	}
}
