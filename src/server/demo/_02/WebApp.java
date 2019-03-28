package server.demo._02;

import java.util.Map;
/**
 * 实现两种映射
 * 1.字串和字串的键值映射
 * 2.字串和servlet的键值映射
 * @author fukur
 *
 */
public class WebApp {
	private static ServletContext contxt;
	
	static {
		contxt = new ServletContext();
		
		Map<String,String> mapping = contxt.getMapping();
		//一个容器的value和一个容器的key的映射关系，特别是value采用String，String类的字符串是不可更改且在常量池共享
		mapping.put("/login", "login");
		mapping.put("/log", "login");
		mapping.put("/reg", "register");
		
		Map<String,Servlet> servlet = contxt.getServlet();
		servlet.put("login", new LoginServlet());
		servlet.put("register", new RegisterServlet());
	}
	
	public static Servlet getServlet(String url) { //根据不同的url访问不同的Servlet
		if((url==null) || (url=url.trim()).equals("")) {
			return null;
		}
		/**
		 * contxt.getServlet()--->返回 Map<String, Servlet>类容器
		 * 
		 * contxt.getMapping().get(url)--->返回String类value(Map<String,String>),这个value在另一个容器里是key
		 * 
		 * contxt.getServlet().get(contxt.getMapping().get(url))
		 *                          ↓
		 * 根据mapping返回的String类value找到对应的Servlet(Map<String,Servlet>)
		 */
		return contxt.getServlet().get(contxt.getMapping().get(url));
	}
}
