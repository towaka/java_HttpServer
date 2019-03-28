package server.demo._03;

import java.util.Map;
/**
 * 负责实现两种映射
 * 1.字串和字串的键值映射
 * 2.字串和servlet的键值映射
 * @author fukur
 *
 */
public class WebApp {
	private static ServletContext contxt;
	
	static {
		contxt = new ServletContext();
		
		/**
		 * 一个容器的value和一个容器的key之间的映射关系，特别是value采用String，String类的字符串是不可更改且在常量池共享
		 * /login-->login-->server.demo._03.LoginServlet
		 */
		Map<String,String> mapping = contxt.getMapping();
		mapping.put("/login", "login");
		mapping.put("/log", "login");
		mapping.put("/reg", "register");
		
		Map<String,String> servlet = contxt.getServlet();
		servlet.put("login","server.demo._03.LoginServlet");
		servlet.put("register","server.demo._03.RegisterServlet");
	}
	
	public static Servlet getServlet(String url) throws ClassNotFoundException, InstantiationException, IllegalAccessException { //根据不同的url访问不同的Servlet
		if((url==null) || (url=url.trim()).equals("")) {
			return null;
		}
		
		//return contxt.getServlet().get(contxt.getMapping().get(url));
		//根据字符串（完整路径）创造对象
		String name = contxt.getServlet().get(contxt.getMapping().get(url));
		//有了指定路径，就可以在动态使用时创建对象，这里使用强转型，使用顶层父类
		return (Servlet)Class.forName(name).newInstance();//确保地址对应的类的空构造存在
	}
}
