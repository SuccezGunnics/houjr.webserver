package com.succez.server.sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.succez.server.core.Request;

public class DefaultRequest implements Request {

	private Socket client;


	public DefaultRequest(Socket client) {
		super();
		this.client = client;
	}

	@Override
	public InputStream getInputStream() {
		InputStream in = null;
		try {
			in = client.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}

	@Override
	public String getMethod() {
		return "GET";
	}

}
