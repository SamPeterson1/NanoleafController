package effects;

import api.HexPanel;
import api.NanoleafInfo;
import api.RGBColor;

public class TransitionEffect extends StreamedEffect {

	public TransitionEffect() {
		super("transition", 2);
	}

	@Override
	public boolean refresh(AnimationFrame frame) {		
		for (int id : NanoleafInfo.getPanelIds()) {
			RGBColor color = new RGBColor();
			HexPanel panel = NanoleafInfo.getPanel(id);
			color.g = 100 + (int) (155 * ((Math.sin(System.currentTimeMillis()/500.0 + (float) panel.getX() / 100) + 1) / 2));
			frame.setColor(id, color);
		}
		
		return true;
	}

}
