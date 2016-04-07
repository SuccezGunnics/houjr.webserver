package com.succez.server.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;

import com.succez.server.core.Request;
import com.succez.server.utils.CommonUtils;

public class DefaultRequest implements Request {

	private Socket client;
	private String head;


	public DefaultRequest(Socket client) {
		super();
		this.client = client;
		this.head = getRequestInfo();
	}
	
	@Override
	public String getHead(){
		return head;
	}


	@Override
	public String getMethod() {
		String method = null;
		if(getHead()!=null && getHead().indexOf(' ')!=-1)
			method = getHead().substring(0,getHead().indexOf(' '));
		return method;
	}

	private String getRequestInfo() {
		StringBuilder buf = new StringBuilder();
		BufferedReader br = null;
		String line = null;
		InputStream in = null;
		try {
			in = client.getInputStream();
			br = new BufferedReader(new InputStreamReader(in,"utf-8"));
			while((line=br.readLine())!=null){
				if(line.trim().equals("")){
					break;
				}
				buf.append(URLDecoder.decode(line, "utf-8")+CommonUtils.getLineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	@Override
	public String getRequestUrl() {
		String url = null;
		int beginIndex = head.indexOf('/') + 1;
		//int endIndex = head.lastIndexOf("HTTP") - 1;
		int endIndex = head.substring(0,head.indexOf("\n")).lastIndexOf("HTTP")-1;
		if (beginIndex > 0 && endIndex > 0) {
			url = head.substring(beginIndex,endIndex);
		}
		return url;
	}

}
