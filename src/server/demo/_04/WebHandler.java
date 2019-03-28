package server.demo._04;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WebHandler extends DefaultHandler {
	private List<Entity> entityList;

	private List<Mapping> mappingList;

	private Entity entity;

	private Mapping mapping;

	private String beginTag;// 记录xml下的各种不同的起始tag

	private boolean isMap;

	/**
	 * 以下重写父类方法
	 */
	@Override
	public void startDocument() throws SAXException {
		// 文档解析开始，准备好容器
		entityList = new ArrayList<Entity>();
		mappingList = new ArrayList<Mapping>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// 开始元素
		if (qName != null) {
			beginTag = qName;// 记录起始tag
			if (qName.equals("servlet")) { // xml配置文件所拥有的的起始tag
				isMap = false;
				entity = new Entity();//新建对象，准备存放数据
			} else if (qName.equals("servlet-mapping")) {// xml配置文件所拥有的的起始tag
				isMap = true;
				mapping = new Mapping();//新建对象，准备存放数据
			}
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		// 解析内容
		if (beginTag != null) {
			String str = new String(ch, start, length); // 记录头尾tag中间的信息
			if (isMap) {
				if (beginTag.equals("servlet-name")) {
					mapping.setName(str);
				} else if (beginTag.equals("url-pattern")) {
					mapping.getUrlPattern().add(str);//容器为ArrayList<String>，存的是url子串（例如/login）
				}
			} else {
				if (beginTag.equals("servlet-name")) {
					entity.setName(str);
				} else if (beginTag.equals("servlet-class")) {
					entity.setClz(str);
				}
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		// 结束元素
		if (qName != null) {
			if (qName.equals("servlet")) {
				entityList.add(entity); //前面记录完之后，这里就正式加进去容器中
			} else if (qName.equals("servlet-mapping")) {
				mappingList.add(mapping); //前面记录完之后，这里就正式加进去容器中
			}
		}
		beginTag = null;
	}

	@Override
	public void endDocument() throws SAXException {
		// 文档解析结束
	}

	public List<Entity> getEntityList() {
		return entityList;
	}

	public List<Mapping> getMappingList() {
		return mappingList;
	}
}
