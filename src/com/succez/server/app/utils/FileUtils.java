package com.succez.server.app.utils;

import java.io.File;

public class FileUtils {
	
	public static String path2URL(File file){
		String filePath = file.getAbsolutePath();
		return filePath.replace('\\', '/');
	}
	

}
