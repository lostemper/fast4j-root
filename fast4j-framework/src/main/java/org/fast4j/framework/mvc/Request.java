package org.fast4j.framework.mvc;

public class Request {
	
	public static  enum   MethodType  {
		GET,POST,GET_OR_POST,UNKNOWN
	}
	
	
	private   Request.MethodType   method;
	private  String  requestPath;
	
	public Request.MethodType getMethod() {
		return method;
	}

	public void setMethod(Request.MethodType method) {
		this.method = method;
	}



	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
	
	public   Request(MethodType  type,  String  path)  {
		 this.method  =  type;
		 this.requestPath =   path;
	}

}
