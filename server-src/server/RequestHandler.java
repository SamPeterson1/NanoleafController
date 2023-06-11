package server;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import api.EffectController;
import api.HexPanel;
import api.NanoleafInfo;
import api.RGBColor;
import effects.FlashEffect;
import net.NanoleafConnection;

public class RequestHandler {

	private static final String ALLOWED_URL = "http://10.2.7.84:8080";
	private static final String ALLOWED_METHODS = "PUT, POST, GET, OPTIONS";
	
	private NanoleafConnection nanoleaf;
	private EffectController effectController;
	
	public RequestHandler() throws IOException {
		nanoleaf = new NanoleafConnection("10.2.6.248", "g41E1WIVqUcrWteNraznhxfJrHegyAQg");
		NanoleafInfo.setConnection(nanoleaf);
		
		effectController = new EffectController(nanoleaf);
		
		FlashEffect flash = new FlashEffect();
		effectController.addEffect(flash);
		
	}
	
	public HTTPResponse handleRequest(HTTPRequest request) {
		
		HTTPResponse response = new HTTPResponse(request.getVersion());
		HTTPMethod method = request.getMethod();
		
		if (method == HTTPMethod.OPTIONS) {
			response.setResponseCode(HTTPResponseCode.NO_CONTENT);
			response.addHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
		} else if (method != HTTPMethod.UNSUPPORTED) {
			response.setResponseCode(HTTPResponseCode.OK);
			response.setContent(new JSONObject().put("foo", "bar").put("hoo", new JSONObject().put("beep", "boop")));
		}
		
		response.addHeader("Date", String.valueOf(new Date()));
		response.addHeader("Access-Control-Allow-Origin", ALLOWED_URL);
		
		return response;
		
	}
	
	public JSONObject handleRequest(JSONObject request) throws IOException {
		JSONObject response = new JSONObject();
		System.out.println("recieved request: " + request);
		
		if (request.getString("request").equals("panelLayout")) {
			Collection<HexPanel> panels = NanoleafInfo.getPanels();
			JSONArray layoutInfo = new JSONArray();
			
			response.put("panelLayout", layoutInfo);
			
			for (HexPanel panel : panels) {
				layoutInfo.put(new JSONObject().put("id", panel.getId())
											   .put("x", panel.getX())
											   .put("y", panel.getY()));
			}
			
			response.put("orientation", NanoleafInfo.getGlobalOrientation());
			response.put("sideLength", NanoleafInfo.getSideLength());
		} else if (request.getString("request").equals("test")) {
			effectController.setEffect("flash");
			response.put("success", true);
		} else if (request.getString("request").equals("panelColors")) {
			JSONObject colorInfo = new JSONObject();
			
			response.put("panelColors", colorInfo);
			HashMap<Integer, RGBColor> panelColors = effectController.getColorTracker().getCurrentColors();
			
			for (int id : NanoleafInfo.getPanelIds()) {
				RGBColor color = panelColors.get(id);
				colorInfo.put(String.valueOf(id), new JSONObject().put("r", color.r)
											  	  				  .put("g", color.g)
											  	  				  .put("b", color.b));
			}
		}
		
		System.out.println("Sent response: " + response);
		
		return response;
	}
	
}
