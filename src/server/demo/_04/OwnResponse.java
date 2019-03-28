package server.demo._04;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

import Util.CloseUtil;
/**
 * 封装Response
 * @author fukur
 *
 */
public class OwnResponse {
	//两个常量，空格和换行符
	public static final String CRLF = "\r\n";
	public static final String BLANK = " ";
	//流
	private BufferedWriter bw;
	//正文
	private StringBuilder content;
	//存储头信息
	private StringBuilder headInfo;
	//正文长度
	private int len;
	//三个构造方法
	
	/**
	 * 创建头信息和正文的空间，初始化正文长度计算器
	 */
	public OwnResponse() {
		headInfo = new StringBuilder();
		content = new StringBuilder();
		len = 0;
	}
	/**
	 * 针对输出流的构造方法
	 * @param os
	 */
	public OwnResponse (OutputStream os) { 
		this();
		bw = new BufferedWriter(new OutputStreamWriter(os));
	}
	/**
	 * 针对客户端输出到服务器的信息的捕获
	 * @param client
	 */
	public OwnResponse (Socket client) { 
		this();
		try {
			bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			headInfo = null;
		}
	}
	
	/**
	 * 构建正文
	 * @param info
	 * @return
	 */
	public OwnResponse print(String info) {
		content.append(info);//不断填充正文
		len+=info.getBytes().length;//将正文转化为字节数组然后获取其长度，不断累加
		return this;
	}
	
	/**
	 * 构建正文(带换行)
	 * @param info
	 * @return
	 */
	public OwnResponse println(String info) { //加上换行符的版本
		content.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}
	
	
	/**
	 * 构建响应头
	 * @param code
	 */
	private void creareHeadInfo(int code) {
		//1.HTTP协议版本 、 状态代码 、 描述
		//都要带换行
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch (code) {
		case 200:
			headInfo.append("OK");
			break;
		case 404:
			headInfo.append("NOT FOUND");
			break;
		case 500:
			headInfo.append("SERVER ERROR");
			break;
		}
		
		headInfo.append(CRLF);
		//2.响应头
		headInfo.append("Server:bjsxt Server/0.0.1").append(CRLF);
		headInfo.append("Date:").append(new Date()).append(CRLF);//当前时间
		headInfo.append("Content-type:text/html;charset=GBK").append(CRLF);
		//正文长度 "字节数组长度"，转为字节更为通用
		//len:将接受正文的String将其转为字节数组，然后获取此字节数组长度
		headInfo.append("Content-length:").append(len).append(CRLF);
		headInfo.append(CRLF);
	}
	
	
	/**
	 * 推送到客户端
	 * @param code
	 * @throws IOException
	 */
	void pushToClient(int code) throws IOException {
		if(null==headInfo) {
			code = 500;
		}
		creareHeadInfo(code);
		//头信息+分隔符
		bw.append(headInfo.toString());
		//正文
		bw.append(content.toString());
		bw.flush();
	}
	
	public void close(){
		CloseUtil.close(bw);
	}
}
