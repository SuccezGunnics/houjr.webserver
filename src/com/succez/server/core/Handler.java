package com.succez.server.core;

public interface Handler {
	
	public void service(Request request, Response response);

	public void doGet(Request request, Response response);
	
	public void doPost(Request request, Response response);

}
