package server;

public enum HTTPResponseCode {
	
	OK("200 OK"), NO_CONTENT("204 No Content"), BAD_REQUEST("400 Bad Request"), NOT_FOUND("404 Not Found"), INTERNAL_SERVER_ERROR("500 Internal Server Error");
	
	private String name;
	
	private HTTPResponseCode(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
