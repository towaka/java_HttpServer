package server.demo._04;
/**
 * 实体类
 * 解析xml文件
 * 针对字串和相应的servlet类之间的键值映射
 * 例子：
 * <servlet>
		<servlet-name>login</servlet-name>>
		<servlet-class>server.demo._03.LoginServlet</servlet-class>
   </servlet>
 * @author fukur
 *
 */
public class Entity {
	private String name; //对应字串
	private String clz; //对应类
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClz() {
		return clz;
	}
	public void setClz(String clz) {
		this.clz = clz;
	}
	
	
	
}
