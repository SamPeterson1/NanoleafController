package server;

public enum HTTPMethod {
	
	OPTIONS, GET, POST, PUT, UNSUPPORTED;

	public static HTTPMethod fromString(String s) {
		if (s.equals("OPTIONS")) {
			return HTTPMethod.OPTIONS;
		} else if (s.equals("GET")) {
			return HTTPMethod.GET;
		} else if (s.equals("POST")) {
			return HTTPMethod.POST;
		} else if (s.equals("PUT")) {
			return HTTPMethod.PUT;
		} else {
			return HTTPMethod.UNSUPPORTED;
		}
	}
	
}
