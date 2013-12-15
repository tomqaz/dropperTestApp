package pl.edu.agh.dropper.appTest;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.List;

public class AbstractUDPSender extends Thread{
	protected final String name;
	protected final int port;
	protected int receiveCounter = 0;
	protected List<String> messages;
	
	public AbstractUDPSender(String name, int port){
		this.name = name;
		this.port = port;
		messages = new ArrayList<>();
	}
	protected void handleIncomingPacket(DatagramPacket receivePacket) {
		messages.add(new String(receivePacket.getData()));
		++receiveCounter;
		String msg = String.format("%s(%s) FROM: %d RECEIVED: %s",
				name.substring(0, 3), receiveCounter, receivePacket.getPort(),
				new String(receivePacket.getData()));
		System.out.println(msg);
	}
	
	
	public void printsStats() {
		System.out.println(name+" received: " + receiveCounter);
		for (String msg : messages) {
			System.out.println("\t" + msg);
		}
	}
}
