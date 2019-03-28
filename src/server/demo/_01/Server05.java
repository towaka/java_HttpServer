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
public class Server05 {
	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	
	
	public static void main(String[] args) throws IOException {
		Server05 server = new Server05();
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
			//请求
			OwnRequest req = new OwnRequest(client.getInputStream());
			//响应
			OwnResponse rep = new OwnResponse(client.getOutputStream());
			rep.println("<html><head><title>HTTP响应示例</title>");
			rep.println("</head><body>");
			rep.println("欢迎：").println(req.getParameter("uname")).println("回来");
			rep.println("</body></html>");
			rep.pushToClient(200);//输入需要的状态码
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void stop() {
		
	}
}
