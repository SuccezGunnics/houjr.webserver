package com.succez.server.sample;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import com.succez.server.core.Response;
import com.succez.server.utils.IOUtils;

/**
 * response接口的实现类
 * 
 * @author lenovo
 *
 */
public class DefaultResponse implements Response {

	private static final int CACHE = 4096;

	private Socket client;
	private PrintStream printStream;

	public DefaultResponse(Socket client) {
		this.client = client;
	}

	@Override
	public void write(byte[] responseInfo) throws IOException {
		OutputStream out = null;
		out = client.getOutputStream();
		out.write(responseInfo);
	}

	@Override
	public void write(File file) throws IOException {
		IOUtils.writeFile(file, client.getOutputStream(), CACHE);
	}

	@Override
	public void close() {
		try {
			client.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@Override
//	public void write(String str, String charset) throws IOException {
//		write(str.getBytes(charset));
//	}

	@Override
	public void write(byte[] responseInfo, int fromIndex, int endIndex)
			throws IOException {
		client.getOutputStream().write(responseInfo, fromIndex, endIndex);
	}

	@Override
	public void print(String content) throws IOException {
		if (printStream == null) {
			printStream = new PrintStream(client.getOutputStream(), true,
					CHARSET);
		}
		printStream.print(content);
	}

}
