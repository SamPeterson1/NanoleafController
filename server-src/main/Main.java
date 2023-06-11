package main;

import java.io.IOException;

import server.HTTPServer;

public class Main {

	public static void main(String[] args) {
		try {
			HTTPServer server = new HTTPServer(3333);
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
