package server.demo._04;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析xml文件
 * 一个特定字符串和多个特定字符串之间的键值映射
 * 例子：
 * <servlet-mapping>
		<servlet-name>login</servlet-name>>
		<url-pattern>/login</url-pattern>
	</servlet-mapping>

	<servlet-mapping>
		<servlet-name>login</servlet-name>>
		<url-pattern>/log</url-pattern>
	</servlet-mapping>
 * @author fukur
 *
 */
public class Mapping {
	private String name;
	private List<String> urlPattern;//存放多个特定字符串
	
	public Mapping() {
		urlPattern = new ArrayList<String>();//准备好容器
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(List<String> urlPattern) {
		this.urlPattern = urlPattern;
	}
	
	
}
