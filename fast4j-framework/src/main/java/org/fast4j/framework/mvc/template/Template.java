package org.fast4j.framework.mvc.template;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface Template {
	
	  /**
     * Render by template engine.
     * 
     * @param request HttpServletRequest object.
     * @param response HttpServletResponse object.
     * @param model Model as java.util.Map.
     * @throws Exception If render failed.
     */
    void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws Exception;

}
