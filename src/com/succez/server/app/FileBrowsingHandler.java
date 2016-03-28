package com.succez.server.app;

import java.io.IOException;
import java.io.InputStream;

import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultHandler;

public class FileBrowsingHandler extends DefaultHandler {

	@Override
	public void doPost(Request request, Response response) {
		// 抛异常
	}

	@Override
	public void doGet(Request request, Response response) {
		//判断请求状态，分别进行处理。。。
		try {
			String querySource = getQuerySource(request.getInputStream());
			int status = getRequestType(querySource);
			switch(status) {
			case 1:
				handleIlegelPath();
				break;
			case 2:
				handleDirectory();
				break;
			case 3:
				handleOnlineViewing();
				break;
			case 4:
				hanleDownload();
			//....
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void hanleDownload() {
		// 文件下载
	}

	private void handleOnlineViewing() {
		// 在线浏览
	}

	private void handleDirectory() {
		// 目录请求
	}

	private void handleIlegelPath() {
		// 非法路径
	}

	private String getQuerySource(InputStream inputStream) {
		return null;
	}

	private int getRequestType(String querySource) {
		return 0;
	}
	

}
