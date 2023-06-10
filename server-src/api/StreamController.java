package api;

import java.io.IOException;
import java.util.ArrayList;

import effects.AnimationFrame;
import net.NanoleafUDPConnection;

public class StreamController {
	
	private NanoleafUDPConnection udp;
	private AnimationFrame frame;
		
	public StreamController(NanoleafUDPConnection udp) {
		this.udp = udp;
		this.frame = new AnimationFrame();
	}
	
	public void close() {
		this.udp.close();
	}
	
	public AnimationFrame getFrame() {
		return this.frame;
	}
	
	private byte[] asTwoBytes(int a) {
		int b1 = (a / 256);
		int b2 = a - 256 * b1;
		
		return new byte[] {(byte) b1, (byte) b2};
	}
	
	public void refresh() throws IOException {
		int numPanels = frame.getNumPanels();
		byte[] data = new byte[2 + numPanels * 8];
		
		byte[] numPanelsBytes = asTwoBytes(numPanels);
		data[0] = numPanelsBytes[0];
		data[1] = numPanelsBytes[1];
		
		int ptr = 2;
		
		for (Integer panelId : frame.getPanelIds()) {
			byte[] panelIdBytes = asTwoBytes(panelId);
			data[ptr++] = panelIdBytes[0];
			data[ptr++] = panelIdBytes[1];
			
			RGBColor color = frame.getPanelColor(panelId);
			data[ptr++] = (byte) color.r;
			data[ptr++] = (byte) color.g;
			data[ptr++] = (byte) color.b;
			data[ptr++] = 0;
			
			int panelTransitionTime = frame.getPanelTransitionTime(panelId);
			byte[] transitionTimeBytes = asTwoBytes(panelTransitionTime);
			data[ptr++] = transitionTimeBytes[0];
			data[ptr++] = transitionTimeBytes[1];
		}
		
		udp.send(data);
	}
	
	private void updatePanelColors() {		
		for (int panelId : frame.getPanelIds()) {
			
		}
	}
	
}
