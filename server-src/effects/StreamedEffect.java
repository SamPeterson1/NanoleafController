package effects;

//streamed effects
//static effects

public abstract class StreamedEffect {
	
	private String name;
	
	public StreamedEffect(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void init() {}
	
	public abstract boolean refresh(AnimationFrame frame);
	
}
