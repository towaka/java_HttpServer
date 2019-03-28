package server.demo._01;
/**
 * 封装回应方法
 * @author fukur
 *
 */
public class Servlet {
	public void service(OwnRequest req,OwnResponse rep) {
		rep.println("<html><head><title>HTTP响应示例</title>");
		rep.println("</head><body>");
		rep.println("欢迎：").println(req.getParameter("uname")).println("回来");
		rep.println("</body></html>");
	}
}
