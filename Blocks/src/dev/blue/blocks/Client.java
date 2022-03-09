package dev.blue.blocks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	private DatagramSocket socket;
	private InetAddress address;

	private byte[] buf;

	public Client() {
		try {
			address = InetAddress.getByName("localhost");
			socket = new DatagramSocket();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		socket.close();
	}
	
	public void sendMessage(String msg) {
		buf = msg.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 45225);
		try {
			socket.send(packet);
			String sent = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Sent: "+sent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}