package server.demo._01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import org.omg.CORBA.Request;

import Util.CloseUtil;

/**
 * 快速创建服务器并启动
 * 1.请求
 * 2.相应
 * 
 * @author fukur
 *
 */
public class Server06 {
	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	
	
	public static void main(String[] args) throws IOException {
		Server06 server = new Server06();
		server.start();
		
	}
	
	/**
	 * 启动方法
	 */
	public void start(){
		try {
			server = new ServerSocket(8699);
			this.receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 接收客户端信息 改良
	 */
	private void receive() {
		try {
			Socket client = server.accept();
			Servlet serv = new Servlet();
			//请求
			OwnRequest req = new OwnRequest(client.getInputStream());
			//响应
			OwnResponse rep = new OwnResponse(client.getOutputStream());
			//封装回应行为
			serv.service(req, rep);
			//输入需要的状态码
			rep.pushToClient(200);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void stop() {
		
	}
}
