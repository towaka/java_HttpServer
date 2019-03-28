package reflect;
/**
 * 获取结构信息Class对象（源头）
 * @author fukur
 *
 */
public class Demo_01 {
	public static void main(String[] args) throws ClassNotFoundException {
		String str = "abc";
		
		//如何通过反射获取指定的类
		//1.对象.Class
		Class<?> clz = str.getClass();
		//2.类.Class
		clz = String.class;
		//3.完整路径
		clz = Class.forName("java.lang.String");
	}
}
