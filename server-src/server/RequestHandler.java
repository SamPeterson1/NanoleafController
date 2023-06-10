package server;

import java.io.IOException;
import java.util.Collection;
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

	private NanoleafConnection nanoleaf;
	private EffectController effectController;
	
	public RequestHandler() throws IOException {
		nanoleaf = new NanoleafConnection("10.2.6.248", "g41E1WIVqUcrWteNraznhxfJrHegyAQg");
		NanoleafInfo.setConnection(nanoleaf);
		
		effectController = new EffectController(nanoleaf);
		
		FlashEffect flash = new FlashEffect();
		effectController.addEffect(flash);
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
			JSONArray colorInfo = new JSONArray();
			
			response.put("panelColors", colorInfo);
			HashMap<Integer, RGBColor> panelColors = effectController.getColorTracker().getCurrentColors();
			
			for (int id : NanoleafInfo.getPanelIds()) {
				RGBColor color = panelColors.get(id);
				colorInfo.put(new JSONObject().put("id", id)
											  .put("r", color.r)
											  .put("g", color.g)
											  .put("b", color.b));
			}
		}
		
		System.out.println("Sent response: " + response);
		
		return response;
	}
	
}
