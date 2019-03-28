package server.demo._04;

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
	 * 例如：login ---包名：server.demo._03.LoginServlet
	 * value位置改为String
	 */
	private Map<String,String> servlet;
	
	
	ServletContext() {
		servlet = new HashMap<String,String>();
		mapping = new HashMap<String,String>();
	}
	
	
	public Map<String, String> getServlet() {
		return servlet;
	}
	public void setServlet(Map<String, String> servlet) {
		this.servlet = servlet;
	}
	public Map<String, String> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}
	
	
}
