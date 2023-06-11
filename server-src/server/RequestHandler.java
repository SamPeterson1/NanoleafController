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
import effects.TransitionEffect;
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
		
		effectController.addEffect(new FlashEffect());
		effectController.addEffect(new TransitionEffect());
	}
	
	public HTTPResponse handleRequest(HTTPRequest request) {
				
		HTTPResponse response = new HTTPResponse(request.getVersion());
		JSONObject requestContent = request.getContent();
		JSONObject content = null;
		String uri = request.getURI();
		HTTPMethod method = request.getMethod();
		
		boolean successful = true;
		
		if (method == HTTPMethod.OPTIONS) {
			response.addHeader("Access-Control-Allow-Methods", ALLOWED_METHODS);
			response.addHeader("Access-Control-Allow-Credentials", "true");
			response.addHeader("Access-Control-Allow-Headers", "origin");
		} else if (method == HTTPMethod.GET) {
			content = new JSONObject();
			
			if (uri.equals("/panelLayout")) {	
				Collection<HexPanel> panels = NanoleafInfo.getPanels();
				JSONArray layoutInfo = new JSONArray();
				
				content.put("panelLayout", layoutInfo);
				
				for (HexPanel panel : panels) {
					layoutInfo.put(new JSONObject().put("id", panel.getId())
												   .put("x", panel.getX())
												   .put("y", panel.getY()));
				}
				
				content.put("orientation", NanoleafInfo.getGlobalOrientation());
				content.put("sideLength", NanoleafInfo.getSideLength());
			} else if (uri.equals("/panelColors")) {
				JSONObject colorInfo = new JSONObject();
				
				content.put("panelColors", colorInfo);
				HashMap<Integer, RGBColor> panelColors = effectController.getColorTracker().getCurrentColors();
				RGBColor black = new RGBColor();
				
				for (int id : NanoleafInfo.getPanelIds()) {
					
					RGBColor color = panelColors.getOrDefault(id, black);
					colorInfo.put(String.valueOf(id), new JSONObject().put("r", color.r)
												  	  				  .put("g", color.g)
												  	  				  .put("b", color.b));
				}
			} else {
				successful = false;
				response.setResponseCode(HTTPResponseCode.NOT_FOUND);
			}
		} else if (method == HTTPMethod.PUT) {
			if (uri.equals("/effect")) {
				String effect = requestContent.getString("name");
				System.out.println("requested effect " + effect);
				try {
					effectController.setEffect(effect);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				successful = false;
				response.setResponseCode(HTTPResponseCode.NOT_FOUND);
			}
		}
		
		if (successful) {
			if (content != null) {
				response.setContent(content);
				response.setResponseCode(HTTPResponseCode.OK);
			} else {
				response.setResponseCode(HTTPResponseCode.NO_CONTENT);
			}			
		}
		
		response.addHeader("Date", String.valueOf(new Date()));
		response.addHeader("Access-Control-Allow-Origin", ALLOWED_URL);
		
		return response;
		
	}
	
}
