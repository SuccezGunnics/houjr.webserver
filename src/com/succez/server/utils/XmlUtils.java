package com.succez.server.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML工具类:对xml进行读写操作
 * 
 * @author Gunnics
 *
 */

public class XmlUtils {

	/**
	 * 判断文件是否是XML文件 <blockquote>
	 * 
	 * <pre>
	 * "a.xml"  returns true
	 * "a.xML"  returns true
	 * "a.XML"  returns true
	 * "a.axml" returns false
	 * "a.ml"   returns false
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @param file
	 * @return
	 */
	public static boolean isXml(File file) {
		if (file.getName().lastIndexOf('.') == -1)
			return false;
		String fileType = file.getName().substring(
				file.getName().lastIndexOf('.') + 1);
		if ("xml".equalsIgnoreCase(fileType)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 将xml文件加载到document对象
	 * 
	 * @param fileName
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 * @throws Exception
	 */
	public static Document load(File fileName) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(false);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setValidating(false);
		factory.setCoalescing(false);
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(fileName);
	}

	/**
	 * 获得xml文件的根节点
	 * 
	 * @param xmlDoc
	 * @return
	 */
	public static Element getRoot(Document xmlDoc) {
		return xmlDoc.getDocumentElement();
	}

	/**
	 * 获得一个节点的所有子节点
	 * 
	 * @param parent
	 * @return
	 */
	public static Element[] getChildren(Element parent) {
		NodeList nodes = parent.getChildNodes();
		int len = nodes.getLength();
		List<Element> list = new ArrayList<Element>(len);
		if (nodes != null && len > 0) {
			for (int i = 0; i < len; i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					list.add((Element) nodes.item(i));
				}
			}
		}
		return list.toArray(new Element[list.size()]);
	}

	/**
	 * 通过节点名获取子节点
	 * 
	 * @param parent
	 * @param elemName
	 * @return
	 */
	public static Element[] getChildrenByName(Element parent, String elemName) {
		NodeList nodes = parent.getChildNodes();
		int len = nodes.getLength();
		List<Element> list = new ArrayList<Element>(len);
		if (nodes != null && len > 0) {
			for (int i = 0; i < len; i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE
						&& nodes.item(i).getNodeName().equals(elemName)) {
					list.add((Element) nodes.item(i));
				}
			}
		}
		return list.toArray(new Element[list.size()]);
	}

	/**
	 * 获取parent节点中 属性名为属性值的所有子节点。
	 * 
	 * @param parent
	 *            父节点
	 * @param attrName
	 *            属性名
	 * @param attrValue
	 *            属性值
	 * @return
	 */
	public static Element[] getChildrenByAttrubte(Element parent,
			String attrName, String attrValue) {
		NodeList nodes = parent.getChildNodes();
		int len = nodes.getLength();
		Element temp;
		List<Element> list = new ArrayList<Element>(len);
		if (nodes != null && len > 0) {
			for (int i = 0; i < len; i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					temp = (Element) nodes.item(i);
					if (getAttribute(temp, attrName)
							.equalsIgnoreCase(attrValue))
						list.add(temp);
				}
			}
		}
		return list.toArray(new Element[list.size()]);
	}

	/**
	 * 通过名称获取子元素
	 * 
	 * @param parent
	 * @param nodeName
	 * @return
	 */
	public static Element getChildByName(Element parent, String nodeName) {
		NodeList nodes = parent.getChildNodes();
		int len = nodes.getLength();
		if (nodes != null && len > 0) {
			for (int i = 0; i < len; i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE
						&& nodes.item(i).getNodeName().equals(nodeName)) {
					return (Element) nodes.item(i);
				}
			}
		}
		return null;
	}

	/**
	 * 获取节点文本值
	 * 
	 * @param elem
	 * @return
	 */
	public static String getElementText(Element elem) {
		return elem.getTextContent();
	}

	public static String getAttribute(Element elem, String attrName) {
		return elem.getAttribute(attrName);
	}
}
