package server.demo._01;

import java.io.IOException;
import java.net.Socket;

import Util.CloseUtil;

/**
 * 将对请求和响应的处理共同封装在一个类中，一对请求和相应代表一个线程
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
		Servlet serv = new Servlet();
		//封装回应行为
		serv.service(req, rep);
		//输入需要的状态码
		try {
			rep.pushToClient(code);//推送到客户端
		} catch (IOException e) {
			
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
