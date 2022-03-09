package dev.blue.blocks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server implements Runnable {

	private Thread thread;
	private DatagramSocket socket;
	private boolean running;
	private byte[] buf = new byte[256];

	public Server() {
		thread = new Thread(this);
		try {
			socket = new DatagramSocket(45225);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (running) {
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			InetAddress address = packet.getAddress();
			int port = packet.getPort();
			packet = new DatagramPacket(buf, buf.length, address, port);
			String received = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Received: "+received);

			if (received.equals("end")) {
				running = false;
				socket.close();
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
		}
		return;
	}
	
	public synchronized void start() {
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		socket.close();
		running = false;
	}
	
}