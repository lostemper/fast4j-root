package org.fast4j.framework.mvc;

import java.lang.reflect.Method;
import java.util.regex.Matcher;

public class RequestHandler {
	private Class<?> actionClass;
    private Method actionMethod;
    private Matcher requestPathMatcher;  
  

	public RequestHandler(Class<?> actionClass, Method actionMethod) {
        this.actionClass = actionClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getActionClass() {
        return actionClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
    

    public Matcher getRequestPathMatcher() {
		return requestPathMatcher;
	}

	public void setRequestPathMatcher(Matcher requestPathMatcher) {
		this.requestPathMatcher = requestPathMatcher;
	}


}
