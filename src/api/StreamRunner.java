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
	private volatile boolean shouldClose = false;
	private volatile boolean hasClosed = false;
	
	public StreamRunner(StreamedEffect effect, ColorTracker colorTracker, NanoleafConnection nanoleaf) throws IOException {
		this.stream = new StreamController(nanoleaf.openUDPConnection());
		this.frame = stream.getFrame();
		this.frame.setTransitionTime(effect.getTransitionTime());
		this.colorTracker = colorTracker;
		
		this.effect = effect;
	}
	
	public void closeStream() {
		shouldClose = true;
		while (!hasClosed);
	}
	
	@Override
	public void run() {
		effect.init();
		
		while (!shouldClose) {
			if (effect.refresh(frame)) {
				colorTracker.update(frame);
				try {
					this.stream.refresh();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			long startWaitMillis = System.currentTimeMillis();
			
			while (!shouldClose && System.currentTimeMillis() - startWaitMillis < frame.getTransitionTimeMillis()) {
				colorTracker.updateCurrentColors();
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		stream.close();
		hasClosed = true;
		System.out.println("Stream successfully closed");
	}
	
}
