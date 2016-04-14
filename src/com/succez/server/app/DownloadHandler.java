package com.succez.server.app;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.utils.IOUtils;

public class DownloadHandler {

	private long contentLength;

	public void download(Request request, Response response, File file)
			throws IOException {
		long pos = headerSetting(file, request, response);
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		byte[] b = new byte[1024];
		raf.seek(pos);

		int n = 0;
		while ((n = raf.read(b, 0, 1024)) != -1) {
			response.write(b, 0, n);
		}
		IOUtils.free(raf);
		response.close();

	}

	private long headerSetting(File file, Request request, Response response)
			throws IOException {
		long len = file.length();
		String range = request.getHeader("Range");
		if (range == null) {
			contentLength = file.length();
			setResponse(new RangeSettings(len), file.getName(), response);
			return 0;
		}
		range = range.replaceAll("bytes=", "");
		RangeSettings settings = getSettings(len, range);
		contentLength = settings.getContentLength();
		setResponse(settings, file.getName(), response);
		return settings.getStart();
	}

	private RangeSettings getSettings(long len, String range) {
		long contentLength = 0;
		long start = 0;
		long end = 0;
		if (range.startsWith("-"))// -500，最后500个
		{
			contentLength = Long.parseLong(range.substring(1));// 要下载的量
			end = len - 1;
			start = len - contentLength;
		} else if (range.endsWith("-"))// 从哪个开始
		{
			start = Long.parseLong(range.replace("-", ""));
			end = len - 1;
			contentLength = len - start;
		} else// 从a到b
		{
			String[] se = range.split("-");
			start = Long.parseLong(se[0]);
			end = Long.parseLong(se[1]);
			contentLength = end - start + 1;
		}
		return new RangeSettings(start, end, contentLength, len);
	}

	private void setResponse(RangeSettings settings, String name,
			Response response) throws IOException {
		response.print(new String("HTTP/1.1200OK" + "\n"));
		response.print("Content-Disposition:　");
		response.print("attachment; filename=\"");
		response.print(name);
		response.print("\"\n");

		response.print("Content-Type: ");
		String key = HandlerConst.CONTENT_TYPE_MAP.containsKey(name) ? name
				: "";
		response.print(HandlerConst.CONTENT_TYPE_MAP.get(key));
		response.print("\n");

		if (!settings.isRange()) {
			response.print("Content-Length: ");
			response.print(String.valueOf(contentLength));
			response.print("\n\n");
		} else {
			long start = settings.getStart();
			long end = settings.getEnd();
			long contentLength = settings.getContentLength();
			response.print("Content-Length: ");
			response.print(String.valueOf(contentLength));
			String contentRange = new StringBuffer("bytes ").append(start)
					.append("-").append(end).append("/")
					.append(settings.getTotalLength()).toString();
			response.print("content-Range: ");
			response.print(contentRange);
			response.print("\n\n");
		}

	}

}
