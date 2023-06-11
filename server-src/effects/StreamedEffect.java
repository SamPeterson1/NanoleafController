package effects;

//streamed effects
//static effects

public abstract class StreamedEffect {
	
	private String name;
	private int transitionTime;
	
	public StreamedEffect(String name, int transitionTime) {
		this.name = name;
		this.transitionTime = transitionTime;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getTransitionTime() {
		return this.transitionTime;
	}
	
	public void init() {}
	
	public abstract boolean refresh(AnimationFrame frame);
	
}
