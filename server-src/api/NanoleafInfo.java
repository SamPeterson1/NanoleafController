package api;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import net.APIEndpoint;
import net.NanoleafConnection;

public class NanoleafInfo {
	
	private static boolean firmwareUpToDate;
	private static String firmwareVersion;
	private static String serialNumber;
	private static String name;
	private static String hardwareVersion;
	private static String model;
	
	private static ArrayList<HexPanel> panels;	
	private static int controllerX, controllerY, controllerOrientation;
	
	private static ArrayList<String> effectsList;
	private static String selectedEffect;
	
	private static int ct;
	private static HSBColor color;
	private static String colorMode;
	private static boolean on;
	
	private static NanoleafConnection connection;
	
	public static void setConnection(NanoleafConnection connection) throws IOException {
		NanoleafInfo.connection = connection;
		
		panels = new ArrayList<HexPanel>();
		effectsList = new ArrayList<String>();
		
		updateAll();
	}
		
	private static void readAll(JSONObject data) {
		firmwareUpToDate = (data.getJSONObject("firmwareUpgrade").keySet().size() == 0);
		firmwareVersion = data.getString("firmwareVersion");
		serialNumber = data.getString("serialNo");
		name = data.getString("name");
		hardwareVersion = data.getString("hardwareVersion");
		model = data.getString("model");
	
		readLayout(data.getJSONObject("panelLayout"));
		readState(data.getJSONObject("state"));
		readEffects(data.getJSONObject("effects"));
	}

	private static void readEffects(JSONObject effects) {
		selectedEffect = effects.getString("select");
		effectsList.clear();
		
		for (Object effect : effects.getJSONArray("effectsList"))
			effectsList.add((String) effect);
	}
	
	private static void readLayout(JSONObject layout) {
		panels.clear();
		
		JSONArray positionData = layout.getJSONObject("layout").getJSONArray("positionData");
		
		for (Object obj : positionData) {
			JSONObject panelJSON = (JSONObject) obj;
			
			int id = panelJSON.getInt("panelId");
			int x = panelJSON.getInt("x");
			int y = panelJSON.getInt("y");
			int orientation = panelJSON.getInt("o");
			
			if (id != 0) {
				panels.add(new HexPanel(id, x, y, orientation));
			} else {
				controllerX = x;
				controllerY = y;
				controllerOrientation = orientation;
			}
		}
	}
	
	private static void readState(JSONObject state) {
		ct = state.getJSONObject("ct").getInt("value");
		
		int h = state.getJSONObject("hue").getInt("value");
		int s = state.getJSONObject("sat").getInt("value");
		int b = state.getJSONObject("brightness").getInt("value");
		
		color = new HSBColor(h, s, b);
		colorMode = state.getString("colorMode");
		on = state.getJSONObject("on").getBoolean("value");
	}
	
	public static void updateAll() throws IOException {
		readAll(connection.makeRequest(APIEndpoint.ALL_INFO));
	}
	
	public static void updateLayout() throws IOException {
		readLayout(connection.makeRequest(APIEndpoint.LAYOUT));
	}
	
	public static void updateEffects() throws IOException {
		readEffects(connection.makeRequest(APIEndpoint.EFFECT));
	}
	
	public static void updateState() throws IOException {
		readState(connection.makeRequest(APIEndpoint.STATE));
	}
	
	public static boolean isFirmwareUpToDate() {
		return firmwareUpToDate;
	}

	public static String getFirmwareVersion() {
		return firmwareVersion;
	}

	public static String getSerialNumber() {
		return serialNumber;
	}

	public static String getName() {
		return name;
	}

	public static String getHardwareVersion() {
		return hardwareVersion;
	}

	public static String getModel() {
		return model;
	}

	public static ArrayList<HexPanel> getPanels() {
		return panels;
	}
	
	public static int getControllerX() {
		return controllerX;
	}
	
	public static int getControllerY() {
		return controllerY;
	}
	
	public static int getControllerOrientation() {
		return controllerOrientation;
	}

	public static ArrayList<String> getEffectsList() {
		return effectsList;
	}

	public static String getSelectedEffect() {
		return selectedEffect;
	}

	public static int getCt() {
		return ct;
	}

	public static HSBColor getColor() {
		return color;
	}

	public static String getColorMode() {
		return colorMode;
	}

	public static boolean isOn() {
		return on;
	}
}
