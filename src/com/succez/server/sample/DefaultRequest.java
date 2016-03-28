package com.succez.server.sample;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import com.succez.server.core.Request;

public class DefaultRequest  implements Request{
	
	private Socket client;
	
	public DefaultRequest(Socket client){
		this.client = client;
	}
	
	@Override
	public  InputStream getInputStream() throws IOException{
		return client.getInputStream();
	}

	@Override
	public String getMethod() {
		return null;
	}

}
 