package com.succez.server.core;

public interface Request {

	/**
	 * GET请求常数
	 */
	public static final String GET = "GET";

	/**
	 * POST请求常数
	 */
	public static final String POST = "POST";

	/**
	 * 默认编码
	 */
	public static final String CHARSET = "utf-8";

	/**
	 * 获取请求方法： 一般为GET和POST
	 * 
	 * @return
	 */
	public String getMethod();

	/**
	 * 获取请求头的参数
	 * 
	 * @param name
	 *            请求参数名
	 * @return 请求参数值
	 */
	public String getHeader(String name);

	/**
	 * 返回请求头的url信息
	 * 
	 * @return
	 */
	public String getRequestUrl();

}
