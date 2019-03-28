package server.demo._02;

import java.util.HashMap;
import java.util.Map;

/**
 * 从请求方式的信息中得到启动特定servlet的信息
 * @author fukur
 *
 */
public class ServletContext {
	/**
	 *  为url中多种形式的行为字串取别名
	 *  url-->login or url-->reg
	 *  例子：
	 *  
	 *  /login-->Login
	 *  /reg-->Register
	 */
	private Map<String,String> mapping;
	/**
	 * 为每个servlet取一个别名
	 * 例如：login --- LoginServlet
	 * 
	 */
	private Map<String,Servlet> servlet; //但是作为对象，value位置的Servlet容量需求过大
	
	
	ServletContext() {
		servlet = new HashMap<String,Servlet>();
		mapping = new HashMap<String,String>();
	}
	
	
	public Map<String, Servlet> getServlet() {
		return servlet;
	}
	public void setServlet(Map<String, Servlet> servlet) {
		this.servlet = servlet;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	
	
}
