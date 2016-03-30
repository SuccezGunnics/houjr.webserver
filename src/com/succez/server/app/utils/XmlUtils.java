package com.succez.server.app.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML ������
 * 
 * @author Gunnics
 *
 */

public class XmlUtils {

	/**
	 * �ж�һ���ļ��Ƿ�ΪXML�ļ��� <blockquote>
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
	 * ��һ��xml�ļ����ص�document����
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Document load(File fileName) throws Exception {
		if (isXml(fileName)) {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setIgnoringComments(false);
			factory.setIgnoringElementContentWhitespace(true);
			factory.setValidating(false);
			factory.setCoalescing(false);

			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(fileName);
		}
		return null;
	}

	/**
	 * ��ȡһ��xmlDocument����ĸ��ڵ㡣
	 * 
	 * @param xmlDoc
	 * @return
	 */
	public static Element getRoot(Document xmlDoc) {
		return xmlDoc.getDocumentElement();
	}

	/**
	 * ���ص������ڵ�������ӽڵ�
	 * 
	 * @param parent
	 * @return
	 */
	public static Element[] getChildren(Element parent) {
		NodeList nodes = parent.getChildNodes();
		List<Element> list = new ArrayList<Element>();
		if (list != null && nodes.getLength() > 0) {
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
					list.add((Element) nodes.item(i));
				}
			}
		}
		return list.toArray(new Element[list.size()]);
	}

	/**
	 * ���ز����ڵ�����нڵ���ΪelemName���ӽڵ㡣
	 * 
	 * @param parent
	 * @param elemName
	 * @return
	 */
	public static Element[] getChildrenByName(Element parent, String elemName) {
		NodeList nodes = parent.getChildNodes();
		List<Element> list = new ArrayList<Element>();
		if (list != null && nodes.getLength() > 0) {
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE
						&& nodes.item(i).getNodeName().equals(elemName)) {
					list.add((Element) nodes.item(i));
				}
			}
		}
		return list.toArray(new Element[list.size()]);
	}

	/**
	 * ���ز����ڵ���nodeNameΪ�ڵ����ĵ�һ���ӽڵ�
	 * ��������ڣ��򷵻�null;
	 * @param parent
	 * @param nodeName
	 * @return
	 */
	public static Element getChildByName(Element parent, String nodeName) {
		Element[] elems = getChildrenByName(parent, nodeName);
		if (elems != null && elems.length > 0) {
			return elems[0];
		}
		return null;
	}
	
	/**
	 * ���ص�ǰ�ڵ�� �ı�ֵ
	 * @param elem
	 * @return
	 */
	public static String getElementText(Element elem){
		return elem.getTextContent();
	}
}
