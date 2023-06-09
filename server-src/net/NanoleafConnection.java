package net;

import java.io.IOException;
import java.net.URL;

import org.json.JSONObject;

public class NanoleafConnection {

	private static final int PORT = 16021;
	
	private String baseURL;
	private String authToken;
	private String ip;
	
	public NanoleafConnection(String ip) throws IOException {
		this.ip = ip;
		this.authToken = authorize(ip);
		this.baseURL = String.format("http://%s:%d/api/v1/", ip, PORT); 
	}
	
	public NanoleafConnection(String ip, String authToken) {
		this.ip = ip;
		this.authToken = authToken;
		this.baseURL = String.format("http://%s:%d/api/v1/", ip, PORT); 
	}
	
	public NanoleafUDPConnection openUDPConnection() throws IOException {
		JSONObject data = new JSONObject()
				.put("write", 
						new JSONObject()
						.put("command", "display")
						.put("animType", "extControl")
						.put("extControlVersion", "v2")
				);
		
		this.makeRequest(APIEndpoint.SET_EFFECT, data);
		
		return new NanoleafUDPConnection(ip);
	}
	
	public JSONObject makeRequest(APIEndpoint endpoint) throws IOException {
		return makeRequest(endpoint, null);
	}
	
	public JSONObject makeRequest(APIEndpoint endpoint, JSONObject data) throws IOException {
		URL requestUrl;
		
		if (endpoint.useAuth()) {
			requestUrl = new URL(this.baseURL + this.authToken + "/" + endpoint.getEndpoint());
		} else {
			requestUrl = new URL(this.baseURL + endpoint.getEndpoint());
		}
		
		JSONHttpRequest request = new JSONHttpRequest(requestUrl, endpoint.getMethod());
		
		if (data != null)
			request.setData(data);
		
		return request.send();
	}
	
	private String authorize(String ip) throws IOException {
		JSONObject authJSON = makeRequest(APIEndpoint.ADD_USER);
		
		if (authJSON != null) {
			return authJSON.getString("auth_token");
		} else {
			System.err.println("Authorization failed. Try pressing power button for 5-7 seconds");
			return null;
		}
		
	}
	
}
