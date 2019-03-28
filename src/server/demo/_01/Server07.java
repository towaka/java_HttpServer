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
 * 面对复数对的响应和请求（多线程）
 * 1.请求
 * 2.响应
 * 
 * 可在启动方法中指定特定端口
 * 
 * 
 * @author fukur
 *
 */
public class Server07 {
	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	private boolean isShutDown = false;
	
	
	public static void main(String[] args) throws IOException {
		Server07 server = new Server07();
		server.start();
		
	}
	
	/**
	 * 启动方法
	 */
	public void start(){
		start(8699);
	}
	
	
	/**
	 * 启动方法(指定端口)
	 */
	public void start(int port){
		try {
			server = new ServerSocket(port);
			this.receive();
		} catch (IOException e) {
			stop();
		}
		
	}
	
	/**
	 * 接收客户端信息 改良
	 */
	private void receive() {
		try {
			Socket client = server.accept();
			while(!isShutDown) {
				new Thread(new Dispatcher(client)).start();
			}
		} catch (Exception e) {
			stop();
		}
	}
	
	/**
	 * 停止服务器
	 */
	public void stop() {
		isShutDown = true;
		CloseUtil.closeSocket(server);
	}
}
