package server.demo._03;

import java.io.IOException;
import java.net.Socket;

import Util.CloseUtil;

/**
 * 将对请求和响应的处理共同封装在一个类中，并且根据头信息中超链接的字串使用对应的servlet
 * 一对请求和相应代表一个线程
 * 一对请求和相应就是一个Dispatcher
 * @author fukur
 *
 */
public class Dispatcher implements Runnable{
	private Socket client;
	
	private OwnRequest req;
	
	private OwnResponse rep;
	
	private int code = 200;
	
	Dispatcher(Socket client){
		this.client = client;
		try {
			req = new OwnRequest(client.getInputStream());
			rep = new OwnResponse(client.getOutputStream());
		} catch (IOException e) {
			code = 500;
			return ;
		}
	}
	
	
	@Override
	public void run() {
		try {
			Servlet serv = WebApp.getServlet(req.getUrl());//根据头信息中的超链接的字串中（例如login、reg等）寻找需要使用的servlet
			if(null==serv) { //如果找不到对应的字串
				code=404; //无法实施对应处理
			}else {
				serv.service(req, rep);
			}
			
			rep.pushToClient(code);//推送到客户端
		} catch (Exception e) {
			e.printStackTrace();
			this.code = 500; //系统内部错误
		}
		
		
		
		try {
			rep.pushToClient(500);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//推送到客户端
		
		CloseUtil.closeSocket(client);
	}

}
