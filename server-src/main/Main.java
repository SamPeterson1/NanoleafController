package main;

import java.io.IOException;

import server.TCPServer;

public class Main {

	public static void main(String[] args) {
		try {
			TCPServer server = new TCPServer(3333);
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
