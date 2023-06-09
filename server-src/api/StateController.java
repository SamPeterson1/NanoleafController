package api;

import java.io.IOException;

import org.json.JSONObject;

import net.APIEndpoint;
import net.NanoleafConnection;

public class StateController {
	
	private boolean on = false;
	private boolean changedOn = false;
	
	private int ct = 0;
	private boolean changedCt = false;
	
	private HSBColor color = null;
	private boolean changedColor = false;
	
	private NanoleafConnection connection;
	
	public StateController(NanoleafConnection connection) {
		this.connection = connection;
	}

	public void setOn(boolean on) {
		this.on = on;
		this.changedOn = true;
	}

	public void setCt(int ct) {
		this.ct = ct;
		this.changedCt = true;
	}

	public void setColor(HSBColor color) {
		this.color = color;
		this.changedColor = true;
	}
	
	public void sync() throws IOException {
		JSONObject data = new JSONObject();
		
		if (changedOn) data.put("on", new JSONObject().put("value", this.on));
		if (changedCt) data.put("ct", new JSONObject().put("value", this.ct));
		if (changedColor) {
			data.put("hue", new JSONObject().put("value", this.color.h));
			data.put("sat", new JSONObject().put("value", this.color.s));
			data.put("brightness", new JSONObject().put("value", this.color.b));
		}
		
		changedOn = false;
		changedCt = false;
		changedColor = false;
		
		connection.makeRequest(APIEndpoint.SET_STATE, data);
	}
	
}
