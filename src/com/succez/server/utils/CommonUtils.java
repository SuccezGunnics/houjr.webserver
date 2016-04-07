package com.succez.server.utils;

import java.io.File;
import java.util.Properties;

public class CommonUtils {
	
	public static String path2URL(File file){
		String filePath = file.getAbsolutePath();
		return filePath.replace('\\', '/');
	}
	

    /* system properties to get separators */
    static final Properties PROPERTIES = new Properties(System.getProperties());

    /**
     * get line separator on current platform
     * @return line separator
     */
    public static String getLineSeparator(){
        return PROPERTIES.getProperty("line.separator");
    }

    /**
     * get path separator on current platform
     * @return path separator
     */
    public static String getPathSeparator(){
        return PROPERTIES.getProperty("path.separator");
    }
	

}
