package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class HTTPServer {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	
	private PrintWriter out;
	private BufferedReader in;

	private RequestHandler requestHandler;
	
	public HTTPServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		System.out.println("beep"); 
		clientSocket = serverSocket.accept();
		System.out.println("connected");
		
		out = new PrintWriter(clientSocket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		requestHandler = new RequestHandler();
	}
	
	public void run() throws IOException {
		while (true) {
			String[] startLineTokens = in.readLine().split(" ");
			HTTPMethod method = HTTPMethod.fromString(startLineTokens[0]);
			String requestURI = startLineTokens[1];
			String version = startLineTokens[2];
			
			HTTPRequest request = new HTTPRequest(version, requestURI, method);
			
			String header;
			
			while ((header = in.readLine()).length() != 0) {
				request.addHeader(header);
			}
			
			String content = null;
			
			if (request.hasHeader("Content-Length")) {
				int contentLength = Integer.valueOf(request.getHeader("Content-Length"));
				
				char[] cbuf = new char[contentLength];
				in.read(cbuf);
				
				content = String.valueOf(cbuf);
			}
			
			if (content != null)
				request.setContent(content);
			
			HTTPResponse response = requestHandler.handleRequest(request);
			
			out.print(response);
			out.flush();
		}
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
