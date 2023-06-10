package page;

import java.io.IOException;

import org.json.JSONObject;

import net.TCPClient;

public class PageClient {
	
	private TCPClient tcp;
	
	public PageClient() {
		try {
			tcp = new TCPClient("127.0.0.1", 3333);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject sendJSON(JSONObject json) {
		try {
			return tcp.sendJSON(json);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject test() {
		JSONObject json = new JSONObject().put("request", "test");
		return sendJSON(json);
	}
	
	public JSONObject requestPanelLayout() {
		JSONObject json = new JSONObject().put("request", "panelLayout");
		return sendJSON(json);
	}
	
	public JSONObject requestPanelColors() {
		JSONObject json = new JSONObject().put("request", "panelColors");
		return sendJSON(json);
	}
	
}
