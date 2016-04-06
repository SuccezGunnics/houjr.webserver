package com.succez.server.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;

import com.succez.server.app.utils.SeparatorUtils;
import com.succez.server.core.Request;

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
		StringBuffer buf = new StringBuffer();
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
				buf.append(URLDecoder.decode(line, "utf-8")+SeparatorUtils.getLineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	@Override
	public String getRequestUrl() {
		// TODO Auto-generated method stub
		String url = head.substring(head.indexOf('/')+1, head.indexOf(' ',head.indexOf('/')));
		return url;
	}

}
