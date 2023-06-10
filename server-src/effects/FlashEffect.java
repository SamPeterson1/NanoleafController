package effects;

import java.util.Collection;
import java.util.HashMap;

import api.HexPanel;
import api.NanoleafInfo;
import api.RGBColor;

public class FlashEffect extends StreamedEffect {

	private Collection<HexPanel> panels;
	private HashMap<Integer, RGBColor> colors;
	
	public FlashEffect() {
		super("flash");
	}

	@Override
	public void init() {
		panels = NanoleafInfo.getPanels();
		colors = new HashMap<Integer, RGBColor>();
		
		for (HexPanel panel : panels) {
			colors.put(panel.getId(), new RGBColor((int) (10 * Math.random()), (int) (0 * Math.random()), (int) (100 * Math.random())));
		}
	}

	@Override
	public boolean refresh(AnimationFrame frame) {
		
		for (HexPanel panel : panels) {
			frame.setColor(panel.getId(), colors.get(panel.getId()));
		}
		
		return true;
	}

	
	
}
