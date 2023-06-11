package api;

import java.util.HashMap;

import effects.AnimationFrame;

public class ColorTracker {

	private HashMap<Integer, RGBColor> prevColors;
	private volatile HashMap<Integer, RGBColor> currentColors;
	
	private long frameStartTimeMillis;
	private AnimationFrame frame;
	
    private final Object lock = new Object();
		
	public ColorTracker() {
		this.prevColors = new HashMap<Integer, RGBColor>();
		this.currentColors = new HashMap<Integer, RGBColor>();
		this.frame = new AnimationFrame();
	}
	
	public HashMap<Integer, RGBColor> getCurrentColors() {
		synchronized (lock) {
			return currentColors;
		}
	}
	
	public void updateCurrentColors() {
		synchronized (lock) {
			long dt = System.currentTimeMillis() - frameStartTimeMillis;
						
			for (int id : frame.getPanelIds()) {
				RGBColor prevColor = prevColors.getOrDefault(id, new RGBColor());
				float t = (float) dt / frame.getTransitionTimeMillis();
				
				if (frame.hasPanel(id)) {
					RGBColor nextColor = RGBColor.lerp(prevColor, frame.getPanelColor(id), t);
					currentColors.put(id, nextColor);
				} else {
					currentColors.put(id, prevColor);
				}
			}
		}
	}

	public void update(AnimationFrame frame) {
		synchronized (lock) {
			long frameStartTimeMillis = System.currentTimeMillis();
			
			this.frame = frame;
			prevColors = currentColors;
			currentColors = new HashMap<Integer, RGBColor>();
			
			this.frameStartTimeMillis = frameStartTimeMillis;			
		}
	}
	
}
