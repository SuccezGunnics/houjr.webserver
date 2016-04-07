package com.succez.server.app;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultHandler;
import com.succez.server.utils.CommonUtils;

public class FileBrowsingHandler extends DefaultHandler {

	@Override
	public void doPost(Request request, Response response) {

	}

	@Override
	public void doGet(Request request, Response response) {
		String url = request.getRequestUrl();

		if (url.trim().equals("")) {
			url = HandlerConst.WEB_ROOT;
		}
		System.out.println(url);
		// if (status == HandlerConst.ILLEGAL_PATH) {
		// handleIllegalPath(response);
		// } else if (status == HandlerConst.BROWSABLE_DIRECTORY) {
		// handleDirectory(url, response);
		// } else if (status == HandlerConst.BROWSABLE_TEXT) {
		// handleBrowsableText(url, response);
		// } else if (status == HandlerConst.BROWSABLE_IMAGE) {
		// handleBrowsableImage(url, response);
		// } else if (status == HandlerConst.DOWNLOAD_FILE) {
		// handleDownload(url, response);
		// } else {
		// return;
		// }
		File file = new File(url);
		if (!url.startsWith(HandlerConst.WEB_ROOT)) {
			handleIllegalPath(response);
		}else if (!file.exists()) {
			handleIllegalPath(response);
		} else if (file.isDirectory()) {
			handleDirectory(file, response);
		} else if (file.isFile()
				&& isBrowsalbeText(url.substring(url.lastIndexOf('.')))) {
			handleBrowsableText(file, response);
		} else if (file.isFile()
				&& isBrowsalbeImage(url.substring(url.lastIndexOf('.')))) {
			handleBrowsableImage(file, response);
		}else if(file.isFile()){
			handleDownload(file, response);
		}else {
			handleIllegalPath(response);
		}
	}

	private boolean isBrowsalbeImage(String fileType) {
		return HandlerConst.imageList.contains(fileType.toUpperCase());
	}

	private boolean isBrowsalbeText(String fileType) {
		return HandlerConst.textList.contains(fileType.toUpperCase());
	}

	private void handleIllegalPath(Response response) {
		try {
			response.write(HandlerConst.HEAD_TEXT.getBytes(HandlerConst.DEFAULT_CHARSET));
			response.write(new File("webroot/404.html"));
			response.closeStream();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	private void handleDownload(File file, Response response) {
		try {
			String name = "Content-Disposition:attachment; filename=\""
					+ file.getName() + "\"" + CommonUtils.getLineSeparator();
			String length = "Accept-Length:" + file.length()
					+ CommonUtils.getLineSeparator()
					+ CommonUtils.getLineSeparator();
			;
			response.write(HandlerConst.HEAD_DOWNLOAD.getBytes(HandlerConst.DEFAULT_CHARSET));
			response.write(name.getBytes(HandlerConst.DEFAULT_CHARSET));
			response.write(length.getBytes(HandlerConst.DEFAULT_CHARSET));
			// byte[] buf = IOUtils.file2buf(file);
			response.write(file);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			response.closeStream();
		}
	}

	private void handleBrowsableImage(File file, Response response) {
		response.write(HandlerConst.HEAD_IMAGE.getBytes());
		// response.write(IOUtils.file2buf(file));
		response.write(file);
		response.closeStream();
	}

	private void handleBrowsableText(File file, Response response) {
		response.write(HandlerConst.HEAD_TEXT.getBytes());
		// response.write(IOUtils.file2buf(file));
		response.write(file);
		response.closeStream();
	}

	private void handleDirectory(File file, Response response) {
		File[] subFiles = file.listFiles();
		response.write(HandlerConst.HEAD_TEXT.getBytes());
		// response.write(IOUtils.file2buf(new File("webroot/pre.txt")));
		// response.write(new File("webroot/pre.txt"));
		response.write(HandlerConst.HTML_PRE, "utf-8");
		for (int i = 0; i < subFiles.length; i++) {
			// temp = "<tr><td><a href=\"/" + FileUtils.path2URL(subFiles[i])
			// + "\">" + subFiles[i].getName() + "</a></td></tr>";
			// try {
			// response.write(temp.getBytes("utf-8"));
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			response.write("<tr><td><a href=\"/", HandlerConst.DEFAULT_CHARSET);
			response.write(CommonUtils.path2URL(subFiles[i]), HandlerConst.DEFAULT_CHARSET);
			response.write("\">", HandlerConst.DEFAULT_CHARSET);
			response.write(subFiles[i].getName(), HandlerConst.DEFAULT_CHARSET);
			response.write("</a></td></tr>", HandlerConst.DEFAULT_CHARSET);
		}
		// response.write(IOUtils.file2buf(new File("webroot/last.txt")));
		response.write(HandlerConst.HTML_LAST, HandlerConst.DEFAULT_CHARSET);
		response.closeStream();
	}

}
