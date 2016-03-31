package com.succez.server.sample;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.succez.server.core.Response;

public class DefaultResponse implements Response {

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

}
