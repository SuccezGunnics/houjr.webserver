package com.succez.server.utils;

import java.io.File;
import java.util.Properties;

public class CommonUtils {

	/**
	 * 经文件本地路径转换为url格式
	 * 当文件不存在时，返回null
	 * <pre>
	 *  d:\pictures\1.jpg returns d:/pictures/1.jpg
	 * </pre>
	 * @param file
	 * @return
	 */
	public static String path2URL(File file) {
		if (file.exists()) {
			String filePath = file.getAbsolutePath();
			return filePath.replace('\\', '/');
		}
		return null;
	}

	/* system properties to get separators */
	static final Properties PROPERTIES = new Properties(System.getProperties());

	/**
	 * get line separator on current platform
	 * 
	 * @return line separator
	 */
	public static String getLineSeparator() {
		return PROPERTIES.getProperty("line.separator");
	}

	/**
	 * get path separator on current platform
	 * 
	 * @return path separator
	 */
	public static String getPathSeparator() {
		return PROPERTIES.getProperty("path.separator");
	}

}
