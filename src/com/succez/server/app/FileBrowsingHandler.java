package com.succez.server.app;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.succez.server.app.utils.FileUtils;
import com.succez.server.app.utils.IOUtils;
import com.succez.server.app.utils.SeparatorUtils;
import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultHandler;

public class FileBrowsingHandler extends DefaultHandler {

	@Override
	public void doPost(Request request, Response response) {

	}

	@Override
	public void doGet(Request request, Response response) {
		String url = request.getRequestUrl();

		if (url.trim().equals("")) {
			url = Context.getConfigVal("webRoot");
		}
		int status = getURLStatus(url);

		if (status == HandlerConst.ILLEGAL_PATH) {
			handleIllegalPath(response);
		} else if (status == HandlerConst.BROWSABLE_DIRECTORY) {
			handleDirectory(url, response);
		} else if (status == HandlerConst.BROWSABLE_TEXT) {
			handleBrowsableText(url, response);
		} else if (status == HandlerConst.BROWSABLE_IMAGE) {
			handleBrowsableImage(url, response);
		} else if (status == HandlerConst.DOWNLOAD_FILE) {
			handleDownload(url, response);
		} else {
			return;
		}

	}

	private void handleIllegalPath(Response response) {

	}

	private void handleDownload(String url, Response response) {
		try {
			File file = new File(url);
			String name = "Content-Disposition:attachment; filename=\""
					+ file.getName() + "\"" + SeparatorUtils.getLineSeparator();
			String length = "Accept-Length:" + file.length()
					+ SeparatorUtils.getLineSeparator()
					+ SeparatorUtils.getLineSeparator();
			;
			response.write(HandlerConst.HEAD_DOWNLOAD.getBytes("utf-8"));
			response.write(name.getBytes("utf-8"));
			response.write(length.getBytes("utf-8"));
			byte[] buf = IOUtils.file2buf(file);
			response.write(buf);
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void handleBrowsableImage(String url, Response response) {
		File file = new File(url);
		response.write(HandlerConst.HEAD_IMAGE.getBytes());
		response.write(IOUtils.file2buf(file));
	}

	private void handleBrowsableText(String url, Response response) {
		File file = new File(url);
		response.write(HandlerConst.HEAD_TEXT.getBytes());
		response.write(IOUtils.file2buf(file));

	}

	private void handleDirectory(String url, Response response) {
		File file = new File(url);
		File[] subFiles = file.listFiles();
		response.write(HandlerConst.HEAD_TEXT.getBytes());
		response.write(IOUtils.file2buf(new File("webroot/pre.txt")));
		String temp;
		if (subFiles != null & subFiles.length > 0) {
			for (int i = 0; i < subFiles.length; i++) {
				temp = "<tr><td><a href=\"/" + FileUtils.path2URL(subFiles[i])
						+ "\">" + subFiles[i].getName() + "</a></td></tr>";
				try {
					response.write(temp.getBytes("utf-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		response.write(IOUtils.file2buf(new File("webroot/last.txt")));

	}

	private int getURLStatus(String url) {

		if (!url.startsWith(Context.getConfigVal("webRoot"))) {
			return HandlerConst.ILLEGAL_PATH;
		}
		File file = new File(url);
		if (!file.exists()) {
			return HandlerConst.ILLEGAL_PATH;
		}
		if (file.isDirectory()) {
			return HandlerConst.BROWSABLE_DIRECTORY;
		}
		if (file.isFile()
				&& Context.isBrowsalbeText(url.substring(url.lastIndexOf('.')))) {
			return HandlerConst.BROWSABLE_TEXT;
		}

		if (file.isFile()
				&& Context
						.isBrowsalbeImage(url.substring(url.lastIndexOf('.')))) {
			return HandlerConst.BROWSABLE_IMAGE;
		}

		return HandlerConst.DOWNLOAD_FILE;
	}

}
