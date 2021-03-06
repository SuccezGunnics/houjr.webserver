package com.succez.server.sample;

import com.succez.server.core.Handler;
import com.succez.server.core.Request;
import com.succez.server.core.Response;

/**
 * Handler的实现类，实现service方法
 * 
 * @author lenovo
 *
 */
public abstract class DefaultHandler implements Handler {

	@Override
	public void service(Request request, Response response) {
		if (request.getMethod() != null) {
			if (request.getMethod().equalsIgnoreCase(Request.GET)) {
				doGet(request, response);
			}
			if (request.getMethod().equalsIgnoreCase(Request.POST)) {
				doPost(request, response);
			}
		}
	}

	@Override
	public abstract void doPost(Request request, Response response);

	@Override
	public abstract void doGet(Request request, Response response);
}
