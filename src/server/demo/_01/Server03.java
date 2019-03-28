package server.demo._01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import Util.CloseUtil;

/**
 * 快速创建服务器并启动
 * 1.请求
 * 2.相应
 * 
 * @author fukur
 *
 */
public class Server03 {
	private ServerSocket server;
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	
	
	public static void main(String[] args) throws IOException {
		Server03 server = new Server03();
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
			
			
			//响应
			StringBuilder responseContext = new StringBuilder();
			responseContext.append("<html>\r\n" + 
					"<head><title>HTTP响应示例</title>\r\n" + "</head>\r\n" + 
					"<body>\r\n" + 
					"hello tomcat\r\n" + 
					"</body>\r\n" + 
					"</html>");
			
			StringBuilder response = new StringBuilder();
			//1.HTTP协议版本 、 状态代码 、 描述
			//都要带换行
			response.append("HTTP/1.1").append(BLANK).append("200").append(BLANK).append("OK").append(CRLF);
			//2.响应头
			response.append("Server:bjsxt Server/0.0.1").append(CRLF);
			response.append("Date:").append(new Date()).append(CRLF);//当前时间
			response.append("Content-type:text/html;charset=GBK").append(CRLF);
			//正文长度 "字节数组长度"，转为字节更为通用
			//将接受正文的String类变量转为字符串，再将其转为字节数组，然后获取此字节数组长度
			response.append("Content-length:").append(responseContext.toString().getBytes().length).append(CRLF);
			//3.正文之前
			response.append(CRLF);
			//4.正文
			response.append(responseContext);
			
			
			//将上面响应部分全部输出出去
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			
			bw.write(response.toString());
			bw.flush();
			CloseUtil.close(bw);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void stop() {
		
	}
}
