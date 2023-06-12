package api;

public class RGBColor {
	
	public int r, g, b;
	
	public RGBColor() {
		this.r = 0;
		this.g = 0;
		this.b = 0;
	}
	
	public RGBColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public static RGBColor lerp(RGBColor from, RGBColor to, float t) {
		RGBColor ret = new RGBColor();
		
		ret.r = (int) ((float) (to.r - from.r) * t + from.r);
		ret.g = (int) ((float) (to.g - from.g) * t + from.g);
		ret.b = (int) ((float) (to.b - from.b) * t + from.b);
		
		return ret;
	}
	
}
