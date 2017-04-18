package org.fast4j.framework.util;

import org.fast4j.framework.mvc.template.TemplateFactory;

public class FactoryUtil {
	
	  public static TemplateFactory createTemplateFactory(String name) {
		  
	        TemplateFactory tf = tryInitTemplateFactory(name);
	        if (tf==null)
	            tf = tryInitTemplateFactory(TemplateFactory.class.getPackage().getName() + "." + name + TemplateFactory.class.getSimpleName());
	        if (tf==null) {
	            //log.warn("Cannot init template factory '" + name + "'.");
	            //throw new ConfigException("Cannot init template factory '" + name + "'.");
	        }
	        return tf;
	    }

	    static TemplateFactory tryInitTemplateFactory(String clazz) {
	        try {
	            Object obj = Class.forName(clazz).newInstance();
	            if (obj instanceof TemplateFactory)
	                return (TemplateFactory) obj;
	        }
	        catch(Exception e) { }
	        return null;
	    }

}
