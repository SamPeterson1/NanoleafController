package net;

public enum APIEndpoint {
	
	ADD_USER("new", RequestMethod.POST, false),
	DELETE_USER("", RequestMethod.DELETE, false),
	
	ALL_INFO("/", RequestMethod.GET),
	
	STATE("state", RequestMethod.GET),
	SET_STATE("state", RequestMethod.PUT),
	
	EFFECT("effects", RequestMethod.GET),
	SET_EFFECT("effects", RequestMethod.PUT),

	LAYOUT("panelLayout", RequestMethod.GET),
	IDENTIFY("identify", RequestMethod.PUT);
	
	private String endpoint;
	private RequestMethod method;
	private boolean useAuth;
	
	private APIEndpoint(String endpoint, RequestMethod method) {
		this(endpoint, method, true);
	}
	
	private APIEndpoint(String endpoint, RequestMethod method, boolean useAuth) {
		this.endpoint = endpoint;
		this.method = method;
		this.useAuth = useAuth;
	}
	
	public String getEndpoint() {
		return this.endpoint;
	}
	
	public RequestMethod getMethod() {
		return this.method;
	}
	
	public boolean useAuth() {
		return this.useAuth;
	}
	
}
