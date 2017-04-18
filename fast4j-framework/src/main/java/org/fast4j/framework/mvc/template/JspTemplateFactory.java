package org.fast4j.framework.mvc.template;

import org.fast4j.framework.mvc.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JspTemplateFactory extends TemplateFactory {

	private   Logger  logger  = LoggerFactory.getLogger(JspTemplateFactory.class);

    public Template loadTemplate(String path) throws Exception {
        
    	logger.debug("Load JSP template '" + path + "'.");
        return new JspTemplate(path);
    }

    public void init(Config config) {
    	logger.info("JspTemplateFactory init ok.");
    }




}
