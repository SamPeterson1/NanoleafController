package api;

import java.util.HashMap;

import effects.AnimationFrame;
import effects.CustomEffect;

public class ColorTracker {

	private HashMap<Integer, RGBColor> prevColors;
	private HashMap<Integer, RGBColor> nextColors;
	private HashMap<Integer, Integer> transitionTimes;
	
	private CustomEffect effect;
	
	private long lastRequestMillis;
	
	public ColorTracker() {
		this.prevColors = new HashMap<Integer, RGBColor>();
		this.nextColors = new HashMap<Integer, RGBColor>();
		this.transitionTimes = new HashMap<Integer, Integer>();
	}
	
	public HashMap<Integer, RGBColor> getCurrentColors() {
		long currentMillis = System.currentTimeMillis();
		long dt = currentMillis - lastRequestMillis;
		lastRequestMillis = currentMillis;
		
		HashMap<Integer, RGBColor> currentColors = new HashMap<Integer, RGBColor>();
		
		for (int id : NanoleafInfo.getPanelIds()) {
			RGBColor color = new RGBColor();
			color.r = (int) (255 * ((Math.sin(currentMillis + id) + 1) / 2));
			currentColors.put(id, color);
		}
		
		return currentColors;
	}
	
	public void update(CustomEffect effect) {
		this.effect = effect;
	}
	
	public void update(AnimationFrame frame) {
		
	}
	
}
