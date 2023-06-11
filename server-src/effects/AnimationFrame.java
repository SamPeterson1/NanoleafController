package effects;

import java.util.HashMap;
import java.util.Set;

import api.RGBColor;

public class AnimationFrame {

	private HashMap<Integer, RGBColor> panelColors;
	private HashMap<Integer, Integer> panelTransitionTimes;
	
	private int transitionTime;
	
	public AnimationFrame() {
		this.transitionTime = 1;
		this.panelColors = new HashMap<Integer, RGBColor>();
		this.panelTransitionTimes = new HashMap<Integer, Integer>();
	}
	
	public Set<Integer> getPanelIds() {
		return this.panelColors.keySet();
	}
	
	public boolean hasPanel(int panelId) {
		return this.panelColors.containsKey(panelId);
	}
	
	public int getNumPanels() {
		return this.panelColors.size();
	}
	
	public int getTransitionTimeMillis() {
		return 100 * this.transitionTime;
	}
	
	public int getTransitionTime() {
		return this.transitionTime;
	}
	
	public int getPanelTransitionTime(int panelId) {
		if (this.panelTransitionTimes.containsKey(panelId))
			return this.panelTransitionTimes.get(panelId);
		
		return this.transitionTime;
	}
		
	public RGBColor getPanelColor(int panelId) {
		return this.panelColors.get(panelId);
	}
	
	public void clearColors() {
		this.panelColors.clear();
	}

	public void setTransitionTime(int transitionTime) {
		this.panelTransitionTimes.clear();
		this.transitionTime = transitionTime;
	}
	
	public void setPanel(int panelId, RGBColor color, int transitionTime) {
		this.setColor(panelId, color);
		this.setTransitionTime(panelId, transitionTime);
	}
	
	public void setTransitionTime(int panelId, int transitionTime) {
		this.panelTransitionTimes.put(panelId, transitionTime);
	}
	
	public void setColor(int panelId, RGBColor color) {
		this.panelColors.put(panelId, color);
	}
	
}
