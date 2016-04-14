package com.succez.server.app;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;

import com.succez.server.HttpServer;
import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultHandler;
import com.succez.server.utils.CommonUtils;

public class FileBrowsingHandler extends DefaultHandler {

	Logger logger = HttpServer.LOGGER;

	@Override
	public void doPost(Request request, Response response) {
		handleIllegalPath(response);
	}

	@Override
	public void doGet(Request request, Response response) {
		String url = request.getRequestUrl();
		if (url.trim().equals("")) {
			url = HandlerConst.WEB_ROOT;
		}
		logger.debug(url);
		File file = new File(url);
		if (!url.startsWith(HandlerConst.WEB_ROOT)) {
			handleIllegalPath(response);
		} else if (!file.exists()) {
			handleIllegalPath(response);
		} else if (file.isDirectory()) {
			handleDirectory(file, response);
		} else if (file.isFile()
				&& isBrowsalbeText(url.substring(url.lastIndexOf('.')))) {
			handleBrowsableText(file, response);
		} else if (file.isFile()
				&& isBrowsalbeImage(url.substring(url.lastIndexOf('.')))) {
			handleBrowsableImage(file, response);
		} else if (file.isFile()) {
			// handleDownload(file, response);
			handleDownloadWithBP(request, response, file);
		} else {
			handleIllegalPath(response);
		}
	}

	private boolean isBrowsalbeImage(String fileType) {
		return HandlerConst.IMAGE_LIST.contains(fileType.toUpperCase());
	}

	private boolean isBrowsalbeText(String fileType) {
		return HandlerConst.TEXT_LIST.contains(fileType.toUpperCase());
	}

	private void handleIllegalPath(Response response) {
		try {
			response.print(HandlerConst.HEAD_TEXT);
			response.write(new File("webroot/404.html"));
			response.close();
		} catch (IOException e) {
			logger.warn("A error occured while handling the IllegalPath"
					+ e.getStackTrace());
		}

	}

	// private void handleDownload(File file, Response response) {
	// try {
	// String name = "Content-Disposition:attachment; filename=\""
	// + file.getName() + "\"" + CommonUtils.getLineSeparator();
	// String length = "Accept-Length:" + file.length()
	// + CommonUtils.getLineSeparator()
	// + CommonUtils.getLineSeparator();
	// ;
	// response.write(HandlerConst.HEAD_DOWNLOAD
	// .getBytes(HandlerConst.DEFAULT_CHARSET));
	// response.write(name.getBytes(HandlerConst.DEFAULT_CHARSET));
	// response.write(length.getBytes(HandlerConst.DEFAULT_CHARSET));
	// response.write(file);
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// } finally {
	// response.closeStream();
	// }
	// }

	public void handleDownloadWithBP(Request request, Response response,
			File file) {
		// ArcSyncHttpDownload.download(request, response, file);
		try {
			new DownloadHandler().download(request, response, file);
		} catch (IOException e) {
			logger.warn("A error occured while downlaoding"
					+ e.getStackTrace());
		}
	}

	private void handleBrowsableImage(File file, Response response) {
		try {
			response.print(HandlerConst.HEAD_IMAGE);
			response.write(file);
		} catch (IOException e) {
			logger.warn("A error occured while handling BrowsableImage"
					+ e.getStackTrace());
		}
		response.close();
	}

	private void handleBrowsableText(File file, Response response) {
		try {
			response.print(HandlerConst.HEAD_TEXT);
			response.write(file);
		} catch (IOException e) {
			logger.warn("A error occured while handling BrowsableText"
					+ e.getStackTrace());
		}
		response.close();
	}

	private void handleDirectory(File file, Response response) {
		try {
			File[] subFiles = file.listFiles();
			response.print(HandlerConst.HEAD_TEXT);
			response.print(HandlerConst.HTML_PRE);
			for (int i = 0; i < subFiles.length; i++) {
				response.print("<tr><td><a href=\"/");
				response.print(CommonUtils.path2URL(subFiles[i]));
				response.print("\">");
				response.print(subFiles[i].getName());
				response.print("</a></td></tr>");
			}
			response.print(HandlerConst.HTML_LAST);
		} catch (IOException e) {
			logger.warn("A error occured while handling Directory"
					+ e.getStackTrace());
		}
		response.close();
	}

}
