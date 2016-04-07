package com.succez.server.sample;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

import com.succez.server.core.Response;
import com.succez.server.utils.IOUtils;

public class DefaultResponse implements Response {
	
	private static final int CACHE = 4096;

	private Socket client;

	public DefaultResponse(Socket client) {
		this.client = client;
	}

	@Override
	public void write(byte[] responseInfo) {
		OutputStream out = null;
		try {
			out = client.getOutputStream();
			out.write(responseInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(File file) {
		try {
			IOUtils.writeFile(file, client.getOutputStream(), CACHE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void closeStream() {
		try {
			client.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void write(String str,String charset) {
		try {
			write(str.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
