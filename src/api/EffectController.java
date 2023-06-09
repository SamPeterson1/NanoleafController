package api;

import java.io.IOException; 
import java.util.HashMap;

import org.json.JSONObject;

import effects.CustomEffect;
import effects.StreamedEffect;
import net.APIEndpoint;
import net.NanoleafConnection;

public class EffectController {
	
	private HashMap<String, CustomEffect> customEffects;
	private HashMap<String, StreamedEffect> streamedEffects;
	
	private NanoleafConnection nanoleaf;

	private StreamRunner streamRunner;
	private ColorTracker colorTracker;
	
	public EffectController(NanoleafConnection nanoleaf) {
		this.nanoleaf = nanoleaf;
		this.customEffects = new HashMap<String, CustomEffect>();
		this.streamedEffects = new HashMap<String, StreamedEffect>();
		this.colorTracker = new ColorTracker();
	}
	
	public ColorTracker getColorTracker() {
		return this.colorTracker;
	}
	
	public void addEffect(CustomEffect effect) {
		customEffects.put(effect.getName(), effect);
	}
	
	public void addEffect(StreamedEffect effect) {
		streamedEffects.put(effect.getName(), effect);
	}
	
	private void saveEffect(CustomEffect effect) throws IOException {
		JSONObject data = new JSONObject();
		
		data.put("command", "add");
		data.put("version", "1.0");
		data.put("animName", effect.getName());
		data.put("animType", (effect.getNumFrames() == 1) ? "static" : "custom");
		data.put("loop", effect.isLoop());
		data.put("palette", "[]");
		data.put("colorType", "rgb");
		data.put("animData", effect.getAnimData());
		
		nanoleaf.makeRequest(APIEndpoint.SET_EFFECT, data);
	}
	
	private void sendEffect(StreamedEffect effect) throws IOException {
		this.streamRunner = new StreamRunner(effect, colorTracker, nanoleaf);
		this.streamRunner.start();
	}
	
	private void sendEffect(CustomEffect effect) throws IOException {
		if (!effect.isSaved()) {
			this.saveEffect(effect);
			effect.setSaved(true);
		}
		
		sendEffect(effect.getName());
	}
	
	private void sendEffect(String effect) throws IOException {
		JSONObject data = new JSONObject().put("select", effect);
		nanoleaf.makeRequest(APIEndpoint.SET_EFFECT, data);
	}
	
	public void setEffect(String name) throws IOException {
		if (streamRunner != null) {
			System.out.println("Interrupted previous effect");
			streamRunner.closeStream();
			streamRunner = null;
		}
		
		if (customEffects.containsKey(name)) {
			this.sendEffect(customEffects.get(name));
		} else if (streamedEffects.containsKey(name)) {
			System.out.println("sent " + name);
			this.sendEffect(streamedEffects.get(name));
		} else {
			this.sendEffect(name);
		}
	}
	
}
