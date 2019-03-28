package server.demo._04;

public class RegisterServlet extends Servlet{

	@Override
	public void doGet(OwnRequest req, OwnResponse rep) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPost(OwnRequest req, OwnResponse rep) throws Exception {
		rep.println("<html><head><title>返回注册</title>");//标签页标题
		rep.println("</head><body>");
		rep.println("你的用户名为："+req.getParameter("uname"));
		rep.println("</body></html>");
	}
	
}
