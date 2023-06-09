package effects;

import java.util.ArrayList;

import api.HexPanel;
import api.NanoleafInfo;
import api.RGBColor;

public class FlashEffect extends StreamedEffect {

	private ArrayList<HexPanel> panels;
	
	public FlashEffect() {
		super("flash");
	}

	@Override
	public void init() {
		panels = NanoleafInfo.getPanels();
	}

	@Override
	public boolean refresh(AnimationFrame frame) {
		
		for (HexPanel panel : panels) {
			frame.setColor(panel.getId(), new RGBColor((int) (10 * Math.random()), (int) (0 * Math.random()), (int) (100 * Math.random())));
		}
		
		return true;
	}

	
	
}
