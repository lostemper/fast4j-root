/**
 * 
 */
package org.fast4j.framework.mvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fast4j.framework.core.ClassHelper;
import org.fast4j.framework.mvc.annotation.Action;
import org.fast4j.framework.util.ArrayUtil;
import org.fast4j.framework.util.CollectionUtil;
import org.fast4j.framework.util.FactoryUtil;
import org.fast4j.framework.util.StringUtil;
import org.fast4j.framework.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.fast4j.framework.mvc.annotation.*;
import org.fast4j.framework.mvc.impl.DefaultRequestHandlerMapping;
import org.fast4j.framework.mvc.template.JspTemplateFactory;
import org.fast4j.framework.mvc.template.TemplateFactory;

/**
 * @author xuminrui
 *
 */
public class DispatchServlet extends HttpServlet {
	

	private static final long serialVersionUID = 1L;
	 private static final Logger logger = LoggerFactory.getLogger(DispatchServlet.class);



	@Override
	public void init(final  ServletConfig config) throws ServletException
	{
		super.init(config);
		initTemplateFactory(  new Config() {
            public String getInitParameter(String name) {
                return config.getInitParameter(name);
            }

            public ServletContext getServletContext() {
                return config.getServletContext();
            }
        });
		loadActionMap();
	}
	
	
                                           
	private   void   loadActionMap()  { 
		//需要构造一个URL到Handle的映射
		//需要读取所有的注解类以及带注解的方法   提取出path和method做为key
		DefaultRequestHandlerMapping.initBeansAndMapping();
	}
	
	void initTemplateFactory(Config config) {
	        String name = config.getInitParameter("template");
	        if (name==null) {
	            name = JspTemplateFactory.class.getName();
	            //log.info("No template factory specified. Default to '" + name + "'.");
	        }
	        TemplateFactory tf = FactoryUtil.createTemplateFactory(name);
	        tf.init(config);
	       // log.info("Template factory '" + tf.getClass().getName() + "' init ok.");
	        TemplateFactory.setTemplateFactory(tf);
    }

	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		  // 获取当前请求相关数据
        String currentRequestMethod = request.getMethod();
		String  currentRequestPath   = WebUtil.getRequestPath(request);
		RequestHandler  handler  =  DefaultRequestHandlerMapping.getHandler(DefaultRequestHandlerMapping.convertMethodToEnum(currentRequestMethod),
				    currentRequestPath);
		
		
		
		
	  
    }

}
