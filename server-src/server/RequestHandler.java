package server;

import java.io.IOException;

import org.json.JSONObject;

import api.EffectController;
import api.NanoleafInfo;
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
	
	public void handleRequest(JSONObject request) throws IOException {
		System.out.println("recieved json: " + request);
		if (request.getString("foo").equals("bar")) {
			effectController.setEffect("flash");
			System.out.println("set effect");
		}
	}
	
}
