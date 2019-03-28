package reflect;

import server.demo._03.Servlet;

/**
 * 使用反射功能，根据已存在的class创建实例 调用空构造
 * @author fukur
 *
 */
public class Demo_02 {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> clz = Class.forName("server.demo._03.LoginServlet");
		//调用空构造 确保指定类存在空构造
		Servlet serv = (Servlet)clz.newInstance();
	}
}
