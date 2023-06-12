package server;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

public class HTTPRequest {
	
	private static final String EOL = "\r\n";
	
	private String version;
	private HTTPMethod method;
	private JSONObject content;
	private HashMap<String, String> headers;
	private String uri;
	
	public HTTPRequest(String version, String uri, HTTPMethod method) {
		this.version = version;
		this.uri = uri;
		this.method = method;
		this.headers = new HashMap<String, String>();
	}
	
	public HTTPRequest(String version, String uri, HTTPMethod method, JSONObject content) {
		this.version = version;
		this.uri = uri;
		this.method = method;
		this.content = content;
		this.headers = new HashMap<String, String>();
	}
	
	public void setContent(String content) {
		this.content = new JSONObject(content);
	}
	
	public void addHeader(String header) {
		String[] keyValue = header.split(": ");
		headers.put(keyValue[0], keyValue[1]);
	}
	
	public String getURI() {
		return uri;
	}
	
	public String getVersion() {
		return version;
	}
	
	public boolean hasHeader(String header) {
		return headers.containsKey(header);
	}
	
	public String getHeader(String header) {
		return headers.get(header);
	}
	
	public JSONObject getContent() {
		return content;
	}
	
	public HTTPMethod getMethod() {
		return method;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(method.name()).append(" ").append(version).append(EOL);
		for (Entry<String, String> header : headers.entrySet())
			str.append(header.getKey()).append(": ").append(header.getValue()).append(EOL);
		
		str.append(EOL);
		
		if (content != null) 
			str.append(content);
		
		return str.toString();
	}
	
}
