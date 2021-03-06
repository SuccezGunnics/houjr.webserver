package com.succez.server.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

	public static void writeFile(File file, OutputStream out, int cache)
			throws IOException {
		if (cache <= 0) {
			throw new IOException("缓存不能为非正");
		}
		if (!file.exists() || !file.isFile()) {
			throw new IOException("文件不存在");
		}
		if (out == null) {
			throw new IOException("流不存在");
		}
		FileInputStream fis = null;
		byte[] buf = new byte[cache];
		int total;
		fis = new FileInputStream(file);
		while ((total = fis.read(buf)) != -1) {
			out.write(buf, 0, total);
		}
		free(fis);
	}

	public static byte[] file2buf(File fobj) throws IOException {
		if (!fobj.exists() || !fobj.isFile()) {
			throw new IOException("文件不存在");
		}
		byte[] buf = null;
		InputStream in = null;
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
		free(in);
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

	@Deprecated
	public static String setContentType(String fileName) {
		String contentType = "application/octet-stream";
		if (fileName.lastIndexOf(".") < 0)
			return contentType;
		fileName = fileName.toLowerCase();
		fileName = fileName.substring(fileName.lastIndexOf(".") + 1);

		if (fileName.equals("html") || fileName.equals("htm")
				|| fileName.equals("shtml")) {
			contentType = "text/html";
		} else if (fileName.equals("css")) {
			contentType = "text/css";
		} else if (fileName.equals("xml")) {
			contentType = "text/xml";
		} else if (fileName.equals("gif")) {
			contentType = "image/gif";
		} else if (fileName.equals("jpeg") || fileName.equals("jpg")) {
			contentType = "image/jpeg";
		} else if (fileName.equals("js")) {
			contentType = "application/x-javascript";
		} else if (fileName.equals("atom")) {
			contentType = "application/atom+xml";
		} else if (fileName.equals("rss")) {
			contentType = "application/rss+xml";
		} else if (fileName.equals("mml")) {
			contentType = "text/mathml";
		} else if (fileName.equals("txt")) {
			contentType = "text/plain";
		} else if (fileName.equals("jad")) {
			contentType = "text/vnd.sun.j2me.app-descriptor";
		} else if (fileName.equals("wml")) {
			contentType = "text/vnd.wap.wml";
		} else if (fileName.equals("htc")) {
			contentType = "text/x-component";
		} else if (fileName.equals("png")) {
			contentType = "image/png";
		} else if (fileName.equals("tif") || fileName.equals("tiff")) {
			contentType = "image/tiff";
		} else if (fileName.equals("wbmp")) {
			contentType = "image/vnd.wap.wbmp";
		} else if (fileName.equals("ico")) {
			contentType = "image/x-icon";
		} else if (fileName.equals("jng")) {
			contentType = "image/x-jng";
		} else if (fileName.equals("bmp")) {
			contentType = "image/x-ms-bmp";
		} else if (fileName.equals("svg")) {
			contentType = "image/svg+xml";
		} else if (fileName.equals("jar") || fileName.equals("var")
				|| fileName.equals("ear")) {
			contentType = "application/java-archive";
		} else if (fileName.equals("doc")) {
			contentType = "application/msword";
		} else if (fileName.equals("pdf")) {
			contentType = "application/pdf";
		} else if (fileName.equals("rtf")) {
			contentType = "application/rtf";
		} else if (fileName.equals("xls")) {
			contentType = "application/vnd.ms-excel";
		} else if (fileName.equals("ppt")) {
			contentType = "application/vnd.ms-powerpoint";
		} else if (fileName.equals("7z")) {
			contentType = "application/x-7z-compressed";
		} else if (fileName.equals("rar")) {
			contentType = "application/x-rar-compressed";
		} else if (fileName.equals("swf")) {
			contentType = "application/x-shockwave-flash";
		} else if (fileName.equals("rpm")) {
			contentType = "application/x-redhat-package-manager";
		} else if (fileName.equals("der") || fileName.equals("pem")
				|| fileName.equals("crt")) {
			contentType = "application/x-x509-ca-cert";
		} else if (fileName.equals("xhtml")) {
			contentType = "application/xhtml+xml";
		} else if (fileName.equals("zip")) {
			contentType = "application/zip";
		} else if (fileName.equals("mid") || fileName.equals("midi")
				|| fileName.equals("kar")) {
			contentType = "audio/midi";
		} else if (fileName.equals("mp3")) {
			contentType = "audio/mpeg";
		} else if (fileName.equals("ogg")) {
			contentType = "audio/ogg";
		} else if (fileName.equals("m4a")) {
			contentType = "audio/x-m4a";
		} else if (fileName.equals("ra")) {
			contentType = "audio/x-realaudio";
		} else if (fileName.equals("3gpp") || fileName.equals("3gp")) {
			contentType = "video/3gpp";
		} else if (fileName.equals("mp4")) {
			contentType = "video/mp4";
		} else if (fileName.equals("mpeg") || fileName.equals("mpg")) {
			contentType = "video/mpeg";
		} else if (fileName.equals("mov")) {
			contentType = "video/quicktime";
		} else if (fileName.equals("flv")) {
			contentType = "video/x-flv";
		} else if (fileName.equals("m4v")) {
			contentType = "video/x-m4v";
		} else if (fileName.equals("mng")) {
			contentType = "video/x-mng";
		} else if (fileName.equals("asx") || fileName.equals("asf")) {
			contentType = "video/x-ms-asf";
		} else if (fileName.equals("wmv")) {
			contentType = "video/x-ms-wmv";
		} else if (fileName.equals("avi")) {
			contentType = "video/x-msvideo";
		}
		return contentType;
	}
}
