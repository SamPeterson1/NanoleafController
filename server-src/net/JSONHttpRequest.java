package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class JSONHttpRequest {

	HttpURLConnection connection;
	
	public JSONHttpRequest(URL url, RequestMethod method) throws IOException {
		this.connection = (HttpURLConnection) url.openConnection();
		this.connection.setRequestMethod(method.getName());
		
		connection.setRequestProperty("content-type", "application/json");
		connection.setRequestProperty("accept", "application/json");
		
		connection.setDoInput(true);
	}
	
	public void setData(JSONObject data) throws IOException {
		connection.setDoOutput(true);

		OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
		
		osw.write(data.toString());
		osw.flush();
		osw.close();
	}
	
	public JSONObject send() throws IOException {
		int responseCode = connection.getResponseCode();
		
		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			boolean hasResponse = false;
						
			while ((inputLine = in.readLine()) != null) {
				hasResponse = true;
				response.append(inputLine);
			}
			
			in.close();
			
			if (hasResponse) {
				if (response.charAt(0) != '{') {
					response.insert(0, "{\"colorMode\":");
					response.append("}");
				}
							
				return new JSONObject(response.toString());
			}
		} else if (responseCode != HttpURLConnection.HTTP_NO_CONTENT){
			System.err.println("Response code: " + responseCode);
		}
		
		return null;
	}
}
