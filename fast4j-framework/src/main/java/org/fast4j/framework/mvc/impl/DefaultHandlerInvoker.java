package org.fast4j.framework.mvc.impl;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fast4j.framework.mvc.HandlerInvoker;
import org.fast4j.framework.mvc.RequestHandler;
import org.fast4j.framework.mvc.bean.Params;
import org.fast4j.framework.util.CastUtil;
import org.fast4j.framework.util.ClassUtil;
import org.fast4j.framework.util.MapUtil;
import org.fast4j.framework.util.WebUtil;

public class DefaultHandlerInvoker implements HandlerInvoker {

	@Override
	public void invokeHandler(HttpServletRequest request,
			HttpServletResponse response, RequestHandler handler)
			throws Exception {
		Class<?>   actionClass  =  handler.getActionClass();
		Method   m  =  handler.getActionMethod();
		
		//通过class需要创建instance
		Object  obj  =  (Class<?>) actionClass.newInstance();
		
		//通过m.invoke来调用方法   需要实例  参数  
		List<Object>  args   =  getParamList(request, handler);
	
		Object   result =  invokeActionMethod(m,obj,args);

	}
	
	
	private  List<Object>  getParamList(HttpServletRequest request, RequestHandler handler )  {
		 List<Object>  args =  new  ArrayList<Object>();
		 Class<?>[]   paramsType  = handler.getActionMethod().getParameterTypes();
		
		 List<Object>  pathParamList =  getPathParamList(handler.getRequestPathMatcher(), paramsType);
				 
         args.addAll(pathParamList);
         
         //如果请求有queryString参数或者post form data那么在服务端的action中就必须定义params这个参数来存储这个map.
         
         Map<String, Object> requestParamMap = WebUtil.getRequestParamMap(request);
         if (MapUtil.isNotEmpty(requestParamMap)) {
        	 args.add(new Params(requestParamMap));
         }
         return  args;
	}
	
	
	private   List<Object>  getPathParamList(Matcher matcher,Class<?>[]  paramsType )  {
		
		List<Object>  pathParamList =  new  ArrayList<Object>();
		
		//group  是从1开始计算索引的
		for(int  i= 1 ;  i< matcher.groupCount();  i++)  {
			
			Class<?>  paramType  =  paramsType[i-1];
			String  paramValue =  matcher.group(i);

			if(ClassUtil.isInt(paramType))  {
				pathParamList.add(CastUtil.castInt(paramValue));
			}  else  if(ClassUtil.isLong(paramType))  {
				pathParamList.add(CastUtil.castLong(paramValue));
			}  else  if(ClassUtil.isDouble(paramType))  {
				pathParamList.add(CastUtil.castDouble(paramValue));
			}  else  {
				pathParamList.add(paramValue);
			}
		}
		
		return  pathParamList;
	}
	
	  private Object invokeActionMethod(Method actionMethod, Object actionInstance, List<Object> actionMethodParamList) throws IllegalAccessException, InvocationTargetException {
	        // 通过反射调用 Action 方法
	        actionMethod.setAccessible(true); // 取消类型安全检测（可提高反射性能）
	        return actionMethod.invoke(actionInstance, actionMethodParamList.toArray());
	    }

}
