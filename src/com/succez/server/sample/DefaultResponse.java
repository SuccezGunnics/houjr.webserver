package com.succez.server.sample;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.succez.server.core.Response;

public class DefaultResponse implements Response{
	
	private Socket client;
	private byte[] responseInfo;
	
	public Socket getClient() {
		return client;
	}

	public void setClient(Socket client) {
		this.client = client;
	}

	public byte[] getResponseInfo() {
		return responseInfo;
	}

	public void setResponseInfo(byte[] responseInfo) {
		this.responseInfo = responseInfo;
	}

	public DefaultResponse(Socket client){
		this.client = client;
	}
	
	public  OutputStream getOutputStream() throws IOException{
		return client.getOutputStream();
		
	}
	

	@Override
	public void write(OutputStream out) {
		try {
			out.write(responseInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
