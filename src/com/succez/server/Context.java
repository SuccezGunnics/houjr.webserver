package com.succez.server;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import com.succez.server.utils.XmlUtils;

public class Context {

	private static Map<String, String> configMap = new HashMap<String, String>();

	public static void load() {
		File file = new File("src/server.xml");
		Element root = null;
		try {
			root = XmlUtils.getRoot(XmlUtils.load(file));
			Element port = XmlUtils.getChildByName(root, "default-port");
			Element max_conNumber = XmlUtils.getChildByName(root,
					"max-conNumber");
			Element handler = XmlUtils.getChildByName(root, "handler");
			Element handlerClass = XmlUtils.getChildByName(handler,
					"handler-class");
			String max_ConNumber = XmlUtils.getElementText(max_conNumber);
			String default_HandlerClass = XmlUtils.getElementText(handlerClass);
			String default_Port = XmlUtils.getElementText(port);
			configMap.put("max_ConNumber", max_ConNumber);
			configMap.put("default_HandlerClass", default_HandlerClass);
			configMap.put("default_Port", default_Port);
			System.out.println("初始化成功~~~");
		} catch (Exception e) {
			System.out.println("初始化失败~~~");
			e.printStackTrace();
		}
	}
	public static String getConfigVal(String key) {
		return configMap.get(key);
	}

}
