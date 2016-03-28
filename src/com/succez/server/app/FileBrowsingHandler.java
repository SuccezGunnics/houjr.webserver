package com.succez.server.app;

import java.io.IOException;
import java.io.InputStream;

import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultHandler;

public class FileBrowsingHandler extends DefaultHandler {

	@Override
	public void doPost(Request request, Response response) {
		// ���쳣
	}

	@Override
	public void doGet(Request request, Response response) {
		//�ж�����״̬���ֱ���д�������
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
		// �ļ�����
	}

	private void handleOnlineViewing() {
		// �������
	}

	private void handleDirectory() {
		// Ŀ¼����
	}

	private void handleIlegelPath() {
		// �Ƿ�·��
	}

	private String getQuerySource(InputStream inputStream) {
		return null;
	}

	private int getRequestType(String querySource) {
		return 0;
	}
	

}
