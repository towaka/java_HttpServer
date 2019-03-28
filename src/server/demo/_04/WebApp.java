package server.demo._04;

import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 负责实现两种映射 1.字串和字串的键值映射 2.字串和servlet的键值映射
 * 
 * @author fukur
 *
 */
public class WebApp {
	private static ServletContext contxt;

	static {
		try {
			// 1.获取解析工厂（找到厂址）
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2.通过解析工厂获取解析器（把机器拿到手）
			SAXParser sax = factory.newSAXParser();
			// 3.指定XML+处理器（准备原料+确认好位置）
			WebHandler web = new WebHandler();
			sax.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("server/demo/_04/web.xml"), web);
			
			//将List转成Map 
			contxt = new ServletContext();

			/**
			 * url-pattern----->servlet-name
			 * 旧版本是直接往mapping里写死的，特定url字串和特定字符串之间的键值映射关系
			 */
			Map<String, String> mapping = contxt.getMapping();
			for(Mapping mapp:web.getMappingList()) {
				List<String> urls = mapp.getUrlPattern();
				for(String url:urls) { 
					//随着for each往下走，url子串可有多个，但是name只有一个值，name作为value存在，多个键指向他
					mapping.put(url, mapp.getName());//例子：mapping.put(/login,login);
				}
			}
			
			/**
			 * servlet-name----->servlet-class
			 * 旧版本是直接往servlet里写死的，特定字符串和特定servlet类的键值映射关系	
			 */
			Map<String, String> servlet = contxt.getServlet();
			for(Entity entity:web.getEntityList()) {
				servlet.put(entity.getName(),entity.getClz());//servlet.put(login,server/demo/_04/LoginServlet.java);
			}
		} catch (Exception e) {
			System.out.println("这里这里");
		}

		
	}

	public static Servlet getServlet(String url)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException { // 根据不同的url访问不同的Servlet
		if ((url == null) || (url = url.trim()).equals("")) {
			return null;
		}

		// return contxt.getServlet().get(contxt.getMapping().get(url));
		// 根据字符串（完整路径）创造对象
		String name = contxt.getServlet().get(contxt.getMapping().get(url));
		// 有了指定路径，就可以在动态使用时创建对象，这里使用强转型，使用顶层父类
		return (Servlet) Class.forName(name).newInstance();// 确保地址对应的类的空构造存在
	}
}
