package com.succez.server.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

	public static void writeFile(File file, OutputStream out, int cache) {
		if (cache <= 0) {
			return;
		}
		FileInputStream fis = null;
		byte[] buf = new byte[cache];
		int total;
		try {
			fis = new FileInputStream(file);
			while ((total = fis.read(buf)) != -1) {
				out.write(buf, 0, total);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			free(fis);
		}

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
