package com.succez.server.app;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

import com.succez.server.app.utils.XmlUtils;

public class Context {

	private static Map<String, String> configMap = new HashMap<String, String>();
	private static List<String> textList = new ArrayList<String>();
	private static List<String> imageList = new ArrayList<String>();

	public static void load() {

		File file = new File("src/properties.xml");
		Element root = null;
		try {
			root = XmlUtils.getRoot(XmlUtils.load(file));
			Element port = XmlUtils.getChildByName(root, "default-port");
			Element max_conNumber = XmlUtils.getChildByName(root,
					"max-conNumber");
			Element handler = XmlUtils.getChildByName(root, "handler");
			Element handlerClass = XmlUtils.getChildByName(handler,
					"handler-class");
			Element webRootEle = XmlUtils.getChildByName(root, "default-root");

			Element browsable = XmlUtils.getChildByName(root, "browsable");
			Element textNodes = XmlUtils.getChildByName(browsable, "text");
			Element[] textElems = XmlUtils.getChildren(textNodes);
			for (int i = 0; i < textElems.length; i++) {
				textList.add(textElems[i].getTextContent());
			}
			Element imageNodes = XmlUtils.getChildByName(browsable, "image");
			Element[] imageElems = XmlUtils.getChildren(imageNodes);
			for (int i = 0; i < imageElems.length; i++) {
				imageList.add(imageElems[i].getTextContent());
			}

			String max_ConNumber = XmlUtils.getElementText(max_conNumber);
			String default_HandlerClass = XmlUtils.getElementText(handlerClass);
			String default_Port = XmlUtils.getElementText(port);
			String webRoot = XmlUtils.getElementText(webRootEle);
			configMap.put("max_ConNumber", max_ConNumber);
			configMap.put("default_HandlerClass", default_HandlerClass);
			configMap.put("default_Port", default_Port);
			configMap.put("webRoot", webRoot);
			System.out.println("初始化成功~~~");
		} catch (Exception e) {
			System.out.println("初始化失败~~~");
			e.printStackTrace();
		}
	}

	public static boolean isBrowsalbeText(String content) {
		if (textList.contains(content.toUpperCase())) {
			return true;
		}
		return false;
	}

	public static boolean isBrowsalbeImage(String content) {
		if (imageList.contains(content.toUpperCase())) {
			return true;
		}
		return false;
	}

	public static String getConfigVal(String key) {
		return configMap.get(key);
	}

}
