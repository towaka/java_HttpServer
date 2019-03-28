package server.demo._02;

public class LoginServlet extends Servlet{

	@Override
	public void doGet(OwnRequest req,OwnResponse rep) throws Exception {
		String name = req.getParameter("uname");
		String pwd = req.getParameter("pwd");
		
		if(login(name,pwd)) {
			rep.println("登陆成功");
			
		}else {
			rep.println("登陆失败");
		}
	}
	
	public boolean login(String name,String pwd) { //这里既定的用户名和密码只是测试用，以后需要读数据库来获得
		return name.equals("gzkk") && pwd.equals("123456");	
	}

	@Override
	public void doPost(OwnRequest req,OwnResponse rep) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
 