package com.succez.server.core;

/**
 * 处理request和response的信息
 * 
 * @author lenovo
 *
 */

public interface Handler {

	/**
	 * 对request 方法进行判断，如果是Get方法，就调用doGet；如果是Post方法，就调用doPost
	 * 
	 * @param request
	 * @param response
	 */
	public void service(Request request, Response response);

	/**
	 * 处理GET请求
	 * 
	 * @param request
	 * @param response
	 */
	public void doGet(Request request, Response response);

	/**
	 * 处理POST请求
	 * 
	 * @param request
	 * @param response
	 */
	public void doPost(Request request, Response response);

}
