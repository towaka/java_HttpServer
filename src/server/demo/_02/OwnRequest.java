package server.demo._02;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 封装请求过程
 * 想办法从get/post方法下的头信息里拿东西
 * 

 * 
 * @author fukur
 *
 */
public class OwnRequest {
	//请求方式
	private String method;
	//请求资源
	private String url;
	//请求参数 看需求，有时候一个key会对应多个value
	private Map<String,List<String>> parameterMapValue;
	
	//内部
	public static final String CRLF = "\r\n"; //换行符，必须的
	private InputStream is;
	private String requestInfo; //存放接收到的信息
	
	public OwnRequest() { //初始化基础变量
		method = "";
		url = "";
		parameterMapValue = new HashMap<String,List<String>>();
		requestInfo = null;
	}
	
	public OwnRequest(InputStream is) { //针对输入流的转化
		this();	
		this.is = is;
		try {
			byte[] data = new byte[2048];
			int len = is.read(data);
			requestInfo = new String(data,0,len);
		} catch (IOException e) {
			return;
		}
		//分析头信息
		parseRequestInfo();
	}
	
	public String getUrl() {
		return url;
	}


	/**
	 * 分析请求信息
	 */
	private void parseRequestInfo() {
		if(requestInfo==null || (requestInfo=requestInfo.trim()).equals("0")) {
			return;
		}
		/**
		 * ======================
		 * 从信息的首行分解出：请求方式，请求路径，请求参数（get可能存在）
		 * 如：GET /index.html?uname=faker&pwd=666&fav=0&fav=1&fav=2 HTTP/1.1
		 * 
		 * 如果是Post方式，请求参数可能在最后的正文中
		 * 
		 * ======================
		 */
		String paramString = "";//接收请求参数
		
		//1.获取请求方式
		String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF));//第一行。请求方法-资源（url）-参数（例如用户名和其下的参数）
		int idx = requestInfo.indexOf("/"); // get or post方法后 的"/"的位置
		this.method = firstLine.substring(0,idx).trim(); //从第一行的第一位开始到“/”号之前。这里先去掉空格，好确认请求方法
		String urlStr = firstLine.substring(idx, firstLine.indexOf("HTTP/")).trim(); //idx和 "HTTP/"的中间是地址
		if(this.method.equalsIgnoreCase("post")) {
			this.url = urlStr;//保存其中的超链接
			paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim(); 
			
		}else if(this.method.equalsIgnoreCase("get")) {
			if(urlStr.contains("?")) { //是否存在参数 
				/**
				 * 按照这里，urlStr被分为了两份，?前是超链接地址，?后是各种请求参数，用户名或者多选框参数等
				 * index.html?uname=falke&pwd=666&fav=0&fav=1&fav=2
				 */
				String[] urlArray = urlStr.split("\\?");
				this.url = urlArray[0];
				paramString = urlArray[1]; //接收请求参数
				
			}else {
				this.url = urlStr;
			}
		}
		//如果不存在参数
		if(paramString.equals("")) {
			return ;
		}
		//2.将请求参数封装到Map中	
		parseParams(paramString);
	}
	
	/**
	 * 分析参数
	 * @param paramString
	 */
	private void parseParams(String paramString) {
		//分割 将字符串转成数组
		StringTokenizer token = new StringTokenizer(paramString,"&");
		while(token.hasMoreTokens()) {
			String keyvalue = token.nextToken(); 
			String[] keyvalues = keyvalue.split("=");
			/**
			 * 例子
			 * index.html?uname=falke&pwd=666&fav=0&fav=1&fav=2
			 * 
			 * 当捕抓到了"&"之后，可以定位"&"前的参数，然后再以"="为分界
			 * 将其左值和右值分别丢入keyvalues数组
			 * 当确认左值存在时，立刻开辟多一位空间存放右值
			 */
			if(keyvalues.length==1) {
				keyvalues = Arrays.copyOf(keyvalues, 2);
				keyvalues[1] = null;
			}
			String key = keyvalues[0].trim();
			String value = (null==keyvalues[1])//如果为null
					?
						null //打印null
					: //否则
						decode(keyvalues[1].trim(),"utf-8"); //顺带解决字符集解码问题
			//转换成Map 分拣
			if(!parameterMapValue.containsKey(key)) {
				parameterMapValue.put(key,new ArrayList<String>());
			}
			
			List<String> values = parameterMapValue.get(key);
			values.add(value);
		}
	}
	
	/**
	 * 根据页面的name，获取对应的多个值
	 * @param name
	 * @return
	 */
	public String[] getParameterValues(String name) {
		List<String> values= null;
		if((values=parameterMapValue.get(name)) == null) {
			return null;
		}else {
			return values.toArray(new String[0]);
		}
	}
	
	/**
	 * 根据页面的name，获取对应的单个值
	 * @param name
	 * @return
	 */
	public String getParameter(String name) { 
		String[] values = getParameterValues(name);
		if(null==values) {
			return null;
		}
		return values[0];
	}

	/**	
	 * 解决中文字符集解码的问题
	 * @param value
	 * @param code
	 * @return
	 */
	private String decode(String value,String code) {
		try {
			return java.net.URLDecoder.decode(value, code);
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
	
	
}
