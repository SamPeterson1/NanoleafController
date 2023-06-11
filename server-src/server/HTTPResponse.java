package server;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONObject;

public class HTTPResponse {
	
	private static final String EOL = "\r\n";
	
	public HTTPResponseCode responseCode;
	public String content;
	public String version;
	public HashMap<String, String> headers;
	
	public HTTPResponse(String version) {
		this.version = version;
		this.responseCode = HTTPResponseCode.INTERNAL_SERVER_ERROR;
		this.headers = new HashMap<String, String>();
	}
	
	public void setResponseCode(HTTPResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	
	public void setContent(JSONObject content) {
		this.content = content.toString();
		
		addHeader("Content-Type", "application/json");
		addHeader("Content-Length", String.valueOf(this.content.length()));
	}
	
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		str.append(version).append(" ").append(responseCode).append(EOL);
		for (Entry<String, String> header : headers.entrySet())
			str.append(header.getKey()).append(": ").append(header.getValue()).append(EOL);
		
		str.append(EOL);
		
		if (content != null) 
			str.append(content);
		
		return str.toString();
	}
	
}
