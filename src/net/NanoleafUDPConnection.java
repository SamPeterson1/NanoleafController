package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NanoleafUDPConnection {

	private static final int PORT = 60222;
	
	private InetAddress address;
	private DatagramSocket socket;
	
	public NanoleafUDPConnection(String ip) throws IOException {
		this.socket = new DatagramSocket();
		this.address = InetAddress.getByName(ip);
	}
	
	public void send(byte[] data) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, this.address, PORT);
		this.socket.send(packet);
	}
	
	public void close() {
		socket.close();
	}
	
}
