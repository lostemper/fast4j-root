package org.fast4j.framework.mvc;
import java.util.regex.*;
public class RegexTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String  inputSeq = "https://";
		Pattern  p =  Pattern.compile("httpa+");
		Matcher   m  =  p.matcher(inputSeq);
		System.out.println(m.matches());
		System.out.println(m.find());
		
		
		
		
		inputSeq = "bbbb{username}=needed";
		System.out.println(inputSeq.matches(".+\\{\\w+\\}.*"));
		//System.out.println(m.group());
		
		
		String  currentPath   =   "/user/89/richard";
		String  actionPath = "/user/{id}/{name}";
		String  actionPathRegex = actionPath.replaceAll("\\{\\w+\\}", "(\\\\w+)");
		
		System.out.println("actionPathRegex="+actionPathRegex);
		
		System.out.println("\\\\");
		
		Matcher  ma = Pattern.compile(actionPathRegex).matcher(currentPath);
		if(ma.matches())  {
			System.out.println(currentPath +  " matched  "+actionPath);
		} else  {
			System.out.println(currentPath +  " not  matched  "+actionPath);
		}
	}

}
