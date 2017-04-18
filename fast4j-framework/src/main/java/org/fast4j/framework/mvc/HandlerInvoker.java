package org.fast4j.framework.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HandlerInvoker {
	
	
	  void invokeHandler(HttpServletRequest request, HttpServletResponse response, RequestHandler handler) throws Exception;

}
