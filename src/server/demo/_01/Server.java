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
public class Server {
	private ServerSocket server;
	
	public static void main(String[] args) throws IOException {
		Server server = new Server();
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
	 * 接收客户端信息
	 */
	private void receive() {
		try {
			Socket client = server.accept();
			
			StringBuilder sb = new StringBuilder();
			String msg = null;
			
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			while((msg=br.readLine()).length()>0) {
				sb.append(msg);
				sb.append("\r\n");
				if(null==msg)
					break;
				
			}
			
			String requestInfo = sb.toString().trim();
			System.out.println(requestInfo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void stop() {
		
	}
}
