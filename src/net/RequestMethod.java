package net;

public enum RequestMethod {

	GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");
	
	private String name;
	
	private RequestMethod(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
}
