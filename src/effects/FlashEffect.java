package effects;

import api.NanoleafInfo;
import api.RGBColor;

public class FlashEffect extends StreamedEffect {


	public FlashEffect() {
		super("flash", 1);
	}

	@Override
	public void init() {

	}

	@Override
	public boolean refresh(AnimationFrame frame) {	
		for (int id : NanoleafInfo.getPanelIds()) {
			RGBColor color = new RGBColor();
			color.r = (int) (255 * ((Math.sin(System.currentTimeMillis()/1000.0 + id) + 1) / 2));
			frame.setColor(id, color);
		}
		
		return true;
	}

	
	
}
