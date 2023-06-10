package api;

import java.io.IOException;

import effects.AnimationFrame;
import effects.StreamedEffect;
import net.NanoleafConnection;

public class StreamRunner extends Thread {
	
	private StreamController stream;
	private AnimationFrame frame;
	private ColorTracker colorTracker;
	
	private StreamedEffect effect;
	
	public StreamRunner(StreamedEffect effect, ColorTracker colorTracker, NanoleafConnection nanoleaf) throws IOException {
		this.stream = new StreamController(nanoleaf.openUDPConnection());
		this.frame = stream.getFrame();
		this.colorTracker = colorTracker;
		
		this.effect = effect;
	}
	
	@Override
	public void run() {
		effect.init();
		
		while (!Thread.interrupted()) {
			if (effect.refresh(frame)) {
				colorTracker.update(frame);
				try {
					this.stream.refresh();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				stream.close();
			}
		}
		
		stream.close();
	}
	
}
