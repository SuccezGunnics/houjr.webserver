package com.succez.server.core;

import java.io.InputStream;

public interface Request {

	public static final String GET = "GET";
	public static final String POST = "POST";

	public String getMethod();

	public InputStream getInputStream();
	

}
