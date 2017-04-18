package org.fast4j.framework.mvc.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fast4j.framework.core.ClassHelper;
import org.fast4j.framework.mvc.DispatchServlet;
import org.fast4j.framework.mvc.Request;
import org.fast4j.framework.mvc.RequestHandler;
import org.fast4j.framework.mvc.annotation.Action;
import org.fast4j.framework.mvc.annotation.ActionMethod;
import org.fast4j.framework.mvc.annotation.Path;
import org.fast4j.framework.util.ArrayUtil;
import org.fast4j.framework.util.CollectionUtil;
import org.fast4j.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 默认处理器映射
 *
 * @author Richard
 * @since 0.1
 */
public class DefaultRequestHandlerMapping {

	 private static final Logger logger = LoggerFactory.getLogger(DefaultRequestHandlerMapping.class);
	
	 //notice  the actionMap  is linkedHashMap 
	 private  static   Map<Request, RequestHandler>   actionMap = new LinkedHashMap<Request, RequestHandler>();

	public  static  void initBeansAndMapping() {
		
		 List<Class<?>> actionClassList = ClassHelper.getClassListByAnnotation(Action.class);
	        if (CollectionUtil.isNotEmpty(actionClassList)) {
	            // 定义两个 Action Map
	            Map<Request, RequestHandler> commonActionMap = new HashMap<Request, RequestHandler>(); // 存放普通 Action Map
	            Map<Request, RequestHandler> regexpActionMap = new HashMap<Request, RequestHandler>(); // 存放带有正则表达式的 Action Map
	            // 遍历 Action 类
	            for (Class<?> actionClass : actionClassList) {
	                // 获取并遍历该 Action 类中所有的方法
	                Method[] actionMethods = actionClass.getDeclaredMethods();
	                
	                if (ArrayUtil.isNotEmpty(actionMethods)) {
	                    for (Method actionMethod : actionMethods) {
	                        // 处理 Action 方法
	                    	  if (actionMethod.isAnnotationPresent(Path.class)) {
	                              String requestPath = actionMethod.getAnnotation(Path.class).value();
	                              logger.debug("request  path ={}", requestPath );
	                              Request.MethodType  method   =  getMethodType(actionMethod);
	                              // 判断 Request Path 中是否带有占位符
                                 if (requestPath.matches(".+\\{\\w+\\}.*")) {
                                    // 将请求路径中的占位符 {\w+} 转换为正则表达式 (\\w+)
                                    requestPath = StringUtil.replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
                                    // 将 Requester 与 Handler 放入 Regexp Action Map 中
                                   regexpActionMap.put(new Request(method, requestPath), new RequestHandler(actionClass, actionMethod));
                                 } else {
                                   // 将 Requester 与 Handler 放入 Common Action Map 中
                                   commonActionMap.put(new Request(method, requestPath), new RequestHandler(actionClass, actionMethod));
                                 }
	                          } 
	                    }
	                }
	            }
	            // 初始化最终的 Action Map（将 Common 放在 Regexp 前面的原因是：优先匹配 ）
	            actionMap.putAll(commonActionMap);
	            actionMap.putAll(regexpActionMap);
	        }
		
	}


	public static   Map<Request, RequestHandler> getRequestHandleMap() {
	
		return  DefaultRequestHandlerMapping.actionMap;
	}
	
	public  static  RequestHandler getHandler(Request.MethodType  currentRequestMethod, String currentRequestPath) {
	        // 定义一个 RequestHandler
	        RequestHandler handler = null;
	        // 获取并遍历 Action 映射
	        Map<Request, RequestHandler> actionMap = getRequestHandleMap();
	        for (Map.Entry<Request, RequestHandler> actionEntry : actionMap.entrySet()) {
	            // 从 Requester 中获取 Request 相关属性
	            Request request = actionEntry.getKey();
	            Request.MethodType requestMethod = request.getMethod();
	            String requestPath = request.getRequestPath(); // 正则表达式
	            // 获取请求路径匹配器（使用正则表达式匹配请求路径并从中获取相应的请求参数）
	            Matcher requestPathMatcher = Pattern.compile(requestPath).matcher(currentRequestPath);
	            // 判断请求方法与请求路径是否同时匹配
	            if (requestMethod == currentRequestMethod  && requestPathMatcher.matches()) {
	                // 获取 Handler 及其相关属性
	                handler = actionEntry.getValue();
	                // 设置请求路径匹配器
	                if (handler != null) {
	                	handler.setRequestPathMatcher(requestPathMatcher);
	                	logger.debug("get Handle ok,request={} , {}",new  Object[]  {currentRequestMethod, currentRequestPath});
	                }
	                // 若成功匹配，则终止循环
	                break;
	            }
	        }
	        // 返回该 Handler
	        return handler;
	 }
	 
	 private  static  Request.MethodType  getMethodType(Method actionMethod)  {
			
			 Request.MethodType   methodType =  Request.MethodType.UNKNOWN;
	         if(actionMethod.isAnnotationPresent(ActionMethod.Get.class)) {
	       	    methodType  =  Request.MethodType.GET;
	         }
	         if(actionMethod.isAnnotationPresent(ActionMethod.Post.class)) {
	       	    methodType  =  Request.MethodType.POST;
	         }
	         return  methodType;
	 }
	 
	 public  static Request.MethodType   convertMethodToEnum(String  currentMethod)  {
		    Request.MethodType   methodType =  Request.MethodType.UNKNOWN;
		    if(currentMethod.equalsIgnoreCase("get")) {
	       	    methodType  =  Request.MethodType.GET;
	         }
		    if(currentMethod.equalsIgnoreCase("post")) {
	       	    methodType  =  Request.MethodType.POST;
	         }
		    return  methodType;
	 }
}
