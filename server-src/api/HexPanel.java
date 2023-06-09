package api;

public class HexPanel {
	
	private int id;
	
	private int x, y;
	private int orientation;
	
	public HexPanel(int id, int x, int y, int orientation) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getOrientation() {
		return orientation;
	}
	
}
