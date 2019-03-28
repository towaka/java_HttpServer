package server.demo._01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 快速创建服务器并启动
 * @author fukur
 *
 */
public class Server02 {
	private ServerSocket server;
	
	public static void main(String[] args) throws IOException {
		Server02 server = new Server02();
		server.start();
		
	}
	
	/**
	 * 启动方法
	 */
	public void start(){
		try {
			server = new ServerSocket(8475);
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
			
			byte[] data = new byte[2048];
			int len = client.getInputStream().read(data);
			
			
			String requestInfo = new String(data,0,len).trim();
			System.out.println(requestInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void stop() {
		
	}
}
