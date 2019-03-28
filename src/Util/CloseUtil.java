package Util;


import java.io.Closeable;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author fukur
 *
 */
public class CloseUtil {
	/**
	 * 工具类关闭流
	 * 可变参数：...（三个点,参数数量可变）
	 * 但只能放在形参最后一个位置,处理方式和数组一致
	 * @param args
	 */
	public static void close(Closeable ... io) {
		for(Closeable temp:io) {
			if(temp!=null) {
				try {
					temp.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * * 工具类关闭流
	 * 可变参数：...（三个点,参数数量可变）
	 * 但只能放在形参最后一个位置,处理方式和数组一致
	 * ---使用泛型---
	 */
	@SafeVarargs
	public static <T extends Closeable> void close_Gen(T ... io) {
		for(Closeable temp:io) {
			if(temp!=null) {
				try {
					temp.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void closeSocket(ServerSocket socket) {
		try {
			if(null!=socket) {
				socket.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeSocket(DatagramSocket socket) {
		try {
			if(null!=socket) {
				socket.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeSocket(Socket socket) {
		try {
			if(null!=socket) {
				socket.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
