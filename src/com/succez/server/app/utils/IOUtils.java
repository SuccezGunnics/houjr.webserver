package com.succez.server.app.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOUtils {
	
	public static String inputStream2Str(InputStream in){
		String info = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			info = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	public static byte[] file2buf(File fobj) {
		byte[] buf = null;
		InputStream in = null;
		try {
			in = new FileInputStream(fobj);
			int fileLength = (int) fobj.length();
			buf = new byte[fileLength];
			int offset = 0;
			int readCount = 0;
			int length = fileLength > 4096 ? 4096 : fileLength;
			while (offset < fileLength
					&& (readCount = in.read(buf, offset, length)) >= 0) {
				offset += readCount;
			}
		} catch (Exception e) {
		} finally {
			free(in);
		}
		return buf;
	}

	public static void free(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
