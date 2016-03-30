package com.succez.server.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.succez.server.app.utils.IOUtils;
import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultHandler;

public class FileBrowsingHandler extends DefaultHandler {

	private static final String HEAD_TEXT = "HTTP/1.0200OK\nContent-Type:text/html\nServer:myserver\n\n";
//	private static final String HEAD_IMAGE = "HTTP/1.0200OK\nContent-Type:image/jpeg\nServer:myserver\n\n";
//	private static final Integer HEAD_TEXT_LENGTH = HEAD_TEXT.length();
//	private static final Integer HEAD_IMAGE_LENGTH = HEAD_IMAGE.length();

	@Override
	public void doPost(Request request, Response response) {
		// 抛异常
	}

	@Override
	public void doGet(Request request, Response response) {
		// 判断请求状态，分别进行处理。。。
		String requestInfo = requestInfo(request.getInputStream());
		System.out.println(requestInfo);
		sampleMethod(request, response);
		int state = getRequestType(requestInfo);
		switch (state) {
		case 0:
			doPost(request, response);
			break;
		case -1:
			handleIllegalPath(request, response);
			break;
		case -2:
			handleNotExitPath(request, response);
			break;
		case 1:
			handleDirectory(request, response);
			break;
		case 2:
			handleBroswableText(request, response);
			break;
		case 3:
			handleBrosableImage(request, response);
			break;
		case 4:
			handleDownload(request, response);
			break;
		}
	}

	private void handleBrosableImage(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	private void handleDownload(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	private void handleBroswableText(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	private void handleDirectory(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	private void handleNotExitPath(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	private void handleIllegalPath(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	private String requestInfo(InputStream in) {
		String info = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			info = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	private void sampleMethod(Request request, Response response) {
		// 此方法仅作为可行性测试。。。
		try {
			byte[] head = getHead().getBytes("utf-8");
			byte[] content = IOUtils.file2buf(new File("webroot/index.html"));
			byte[] total = new byte[head.length + content.length];
			for (int i = 0; i < head.length; i++) {
				total[i] = head[i];
			}
			for (int i = head.length; i < total.length; i++) {
				total[i] = content[i - head.length];
			}
			response.write(total);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private int getRequestType(String requestInfo) {

		String url = requestInfo.substring(requestInfo.indexOf('/') + 1,
				requestInfo.lastIndexOf(' '));
		String method = requestInfo.substring(0, requestInfo.lastIndexOf(' '));

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

	private String getHead() {
		return HEAD_TEXT;
	}

}
