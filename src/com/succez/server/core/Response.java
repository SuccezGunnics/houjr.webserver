package com.succez.server.core;

import java.io.File;

public interface Response {
	
	public void write(byte[] responseInfo);
	
	public void write(File file);
	
	public void write(String str,String charset);
	
	public void closeStream();

}
