package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class TCPServer {
	
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	private RequestHandler requestHandler;
	
	public TCPServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();
		System.out.println("connected");
		
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		requestHandler = new RequestHandler();
	}
	
	public void run() throws IOException {
		System.out.println("running");
		JSONObject request = new JSONObject(in.readLine());
		System.out.println("Recieved request: " + request);
		out.println(requestHandler.handleRequest(request));
	}
	
	public void sendJSON(JSONObject json) {
		out.println(json);
	}
	
	public void stop() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}
	
}
