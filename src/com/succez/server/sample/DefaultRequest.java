package com.succez.server.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.succez.server.core.Request;

/**
 * Request的实现类
 * 
 * @author lenovo
 *
 */
public class DefaultRequest implements Request {

	private Socket client;
	private Map<String, String> headers;

	public DefaultRequest(Socket client) {
		super();
		this.client = client;
		this.headers = getRequestInfo();
	}

	private Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public String getMethod() {
		String method = null;
		String head = getHeaders().get("Head");
		method = head.substring(0, head.indexOf(' '));
		return method;
	}

	private Map<String, String> getRequestInfo() {
		Map<String, String> requestInfo = new HashMap<String, String>();
		BufferedReader br = null;
		String line = null;
		InputStream in = null;
		try {
			in = client.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "utf-8"));
			String head = br.readLine();
			if (head != null) {
				requestInfo.put("Head", URLDecoder.decode(head, CHARSET));
			}

			while ((line = br.readLine()) != null) {
				if (line.trim().equals("")) {
					break;
				}
				requestInfo.put(parseKey(line), parseValue(line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return requestInfo;
	}

	private String parseValue(String line) {
		int index = line.indexOf(": ");
		if (line != null && index > 0) {
			return line.substring(index + 2);
		}
		return null;
	}

	private String parseKey(String line) {
		int index = line.indexOf(": ");
		if (line != null && index > 0) {
			return line.substring(0, index);
		}
		return null;
	}

	@Override
	public String getRequestUrl() {
		String url = null;
		String head = getHeaders().get("Head");
		int beginIndex = head.indexOf('/') + 1;
		int endIndex = head.lastIndexOf("HTTP") - 1;
		if (beginIndex > 0 && endIndex > 0) {
			url = head.substring(beginIndex, endIndex);
		}
		return url;
	}

	@Override
	public String getHeader(String name) {
		return getHeaders().get(name);
	}
}
