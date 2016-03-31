package com.succez.server.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.succez.server.app.utils.IOUtils;
import com.succez.server.app.utils.SeparatorUtils;
import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultHandler;

public class FileBrowsingHandler extends DefaultHandler {

	private static final String HEAD_TEXT = "HTTP/1.0200OK\nContent-Type:text/html\nServer:myserver\n"+SeparatorUtils.getLineSeparator();
	private static final String HEAD_IMAGE = "HTTP/1.0200OK\nContent-Type:image/jpeg\nServer:myserver\n"+SeparatorUtils.getLineSeparator();

	@Override
	public void doPost(Request request, Response response) {
		// 抛异常
	}

	@Override
	public void doGet(Request request, Response response) {
		String requestInfo = requestInfo(request.getInputStream());
		String url = requestInfo.substring(requestInfo.indexOf('/'),
				requestInfo.lastIndexOf(' '));
		if(url.equals("/"))
			url = Context.getConfigVal("webRoot");
		String method = requestInfo.substring(0, requestInfo.indexOf(' '));
		int state = getRequestType(method, url);
		switch (state) {
		case 0:
			doPost(request, response);
			break;
		case -1:
			break;
		case -2:
			break;
		case 1:
			handleDirectory(url, response);
			break;
		case 2:
			handleBrowsalbeText(url, response);
			break;
		case 3:
			handleBrowableImage(url, response);
			break;
		case 4:
			break;
		}
	}

	private void handleDirectory(String url, Response response) {
		File file = new File(url);
		File[] subFiles = file.listFiles();
		response.write(HEAD_TEXT.getBytes());
		response.write(IOUtils.file2buf(new File("webroot/pre.txt")));
		String temp;
		for (int i = 0; i < subFiles.length; i++) {
			temp = "<tr><td><a href=\"/" + subFiles[i].getAbsolutePath()
					+ "\">" + subFiles[i].getName() + "</a></td></tr>";
			try {
				response.write(temp.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		response.write(IOUtils.file2buf(new File("webroot/last.txt")));
	}

	private void handleBrowsalbeText(String url, Response response) {
		File file = new File(url);
		response.write(HEAD_TEXT.getBytes());
		response.write(IOUtils.file2buf(file));

	}

	private void handleBrowableImage(String url, Response response) {
		File file = new File(url);
		response.write(HEAD_IMAGE.getBytes());
		response.write(IOUtils.file2buf(file));
	}

	private String requestInfo(InputStream in) {
		String info = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in,"utf-8"));
			info = URLDecoder.decode(br.readLine(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	private int getRequestType(String method, String url) {

		if (!method.equalsIgnoreCase(Request.GET)) {
			return 0;
		}

		if (!url.startsWith(Context.getConfigVal("webRoot"))) {
			return -1;
		}
		File file = new File(url);
		if (!file.exists()) {
			return -2;
		}
		if (file.isDirectory()) {
			return 1;
		}
		if (file.isFile()
				&& Context.isBrowsalbeText(url.substring(url.lastIndexOf('.')))) {
			return 2;
		}

		if (file.isFile()
				&& Context
						.isBrowsalbeImage(url.substring(url.lastIndexOf('.')))) {
			return 3;
		}
		return 4;
	}

}
