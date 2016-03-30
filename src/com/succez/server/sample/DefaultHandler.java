package com.succez.server.sample;


import com.succez.server.core.Handler;
import com.succez.server.core.Request;
import com.succez.server.core.Response;

public abstract class DefaultHandler implements Handler {

	@Override
	public void service(Request request, Response response) {
		if (request.getMethod().equalsIgnoreCase(Request.GET)) {
			System.out.println("holly,doGet");
			doGet(request, response);
		}

		if (request.getMethod().equalsIgnoreCase(Request.POST)) {
			doPost(request, response);
		}
	}

	@Override
	public abstract void doPost(Request request, Response response);

	@Override
	public abstract void doGet(Request request, Response response);
}

