package page;

import java.io.IOException;

import org.json.JSONObject;

import net.TCPClient;

public class Test {
	
	private TCPClient tcp;
	
	public Test() {
		try {
			tcp = new TCPClient("127.0.0.1", 3333);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void test() {
		JSONObject json = new JSONObject().put("foo", "bar");
		try {
			tcp.sendJSON(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
