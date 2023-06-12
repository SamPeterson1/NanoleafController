package effects;

import java.util.ArrayList;
import java.util.Set;

import api.RGBColor;

public class CustomEffect {

	private ArrayList<AnimationFrame> frames;
	private AnimationFrame startFrame;

	private String name;
	
	private boolean isSaved;
	
	private int defaultTransitionTime;
	private boolean isLoop;
	
	public CustomEffect(String name) {
		this.name = name;
		this.frames = new ArrayList<AnimationFrame>();
		this.startFrame = null;
		this.defaultTransitionTime = 0;
	}
	
	public boolean isSaved() {
		return this.isSaved;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isLoop() {
		return this.isLoop;
	}
	
	public void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}
	
	public void setDefaultTransitionTime(int defaultTransitionTime) {
		this.defaultTransitionTime = defaultTransitionTime;
	}
	
	public void setIsLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	public int getNumFrames() {
		return this.frames.size();
	}
	
	public AnimationFrame getFrame(int t) {
		return this.frames.get(t);
	}
	
	public AnimationFrame getStartFrame() {
		return this.startFrame;
	}
	
	public void setStartFrame(AnimationFrame startFrame) {
		this.startFrame = startFrame;
	}
	
	public AnimationFrame nextFrame() {
		AnimationFrame frame = new AnimationFrame();
		frame.setTransitionTime(defaultTransitionTime);
		
		this.frames.add(frame);
		
		return frame;
	}
	
	private RGBColor writeFrame(StringBuilder data, int panelId, RGBColor lastColor, int t) {
		AnimationFrame frame = (t == -1) ? this.startFrame : this.frames.get(t);
		
		RGBColor c = frame.hasPanel(panelId) ? frame.getPanelColor(panelId) : lastColor;
		data.append(" " + c.r + " " + c.g + " " + c.b + " 0");
		
		if (t == -1)
			data.append(" -1");
		else
			data.append(" " + frame.getPanelTransitionTime(panelId));
		
		return c;
	}
	
	public String getAnimData() {
		boolean hasStartFrame = (this.startFrame != null);
		StringBuilder data = new StringBuilder();
		
		AnimationFrame firstFrame = hasStartFrame ? this.startFrame : this.frames.get(0);
		Set<Integer> panelIds = firstFrame.getPanelIds();
		
		data.append(panelIds.size());
		
		int numFrames = this.frames.size();
		if (hasStartFrame) numFrames ++;

		RGBColor persistentColor = null;
		
		for (int panelId : firstFrame.getPanelIds()) {
			data.append(" " + panelId);
			data.append(" " + numFrames);

			for (int i = 0; i < numFrames; i ++) {
				if (hasStartFrame)
					persistentColor = writeFrame(data, panelId, persistentColor, i - 1);
				else 
					persistentColor = writeFrame(data, panelId, persistentColor, i - 1);
			}
		}
		
		return data.toString();
	}
	
}
