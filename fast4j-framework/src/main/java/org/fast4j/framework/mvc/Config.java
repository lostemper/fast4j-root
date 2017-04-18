package org.fast4j.framework.mvc;

import javax.servlet.ServletContext;

public interface Config {
	
	  /**
     * Get ServletContext object.
     */
    public ServletContext getServletContext();

    /**
     * Get init parameter value by name.
     */
    public String getInitParameter(String name);

}
