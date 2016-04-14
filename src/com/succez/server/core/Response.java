package com.succez.server.core;

import java.io.File;
import java.io.IOException;

public interface Response {

	/**
	 * 默认编码格式
	 */
	public static final String CHARSET = "UTF-8";

	/**
	 * 向输出流中写入字符数组
	 * 
	 * @param responseInfo
	 * @throws IOException
	 */
	public void write(byte[] responseInfo) throws IOException;

	/**
	 * 向 输出流中写入数组指定索引的数据
	 * 
	 * @param responseInfo
	 * @param fromIndex
	 * @param endIndex
	 * @throws IOException
	 */
	public void write(byte[] responseInfo, int fromIndex, int endIndex)
			throws IOException;

	/**
	 * 向输出流写入文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void write(File file) throws IOException;

	/**
	 * 指定编码，向输出流写字符串
	 * 
	 * @param str
	 * @param charset
	 * @throws IOException
	 */
	//public void write(String str, String charset) throws IOException;
	
	/**
	 * 打印字符串
	 */
	public void print(String content) throws IOException;

	/**
	 * 关闭输出流
	 */
	public void close();

}
