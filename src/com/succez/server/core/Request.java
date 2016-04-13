package com.succez.server.core;


public interface Request {

	public static final String GET = "GET";
	public static final String POST = "POST";

	public String getMethod();
	
	public String getHead();
	
	public String getHead(String name);
	
	public String getRequestUrl();

	

}
