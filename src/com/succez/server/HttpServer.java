package com.succez.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.succez.server.app.Context;
import com.succez.server.core.Handler;
import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultRequest;
import com.succez.server.sample.DefaultResponse;

public class HttpServer {

	private static int port;
	private static int maxConNumber;
	private static String handlerClass;

	public static void main(String[] args) {
		new HttpServer().start();
	}

	public void start() {
		initService();
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("服务器启动失败");
			e.printStackTrace();
		}
		doService(socket);
	}

	private void doService(ServerSocket server) {
		boolean status = true;
		ExecutorService threadPool = Executors.newFixedThreadPool(maxConNumber);
		while (status) {
			Socket client;
			try {
				client = server.accept();
				ServerThread serverthread = new ServerThread(client);
				threadPool.execute(serverthread);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void initService() {
		Context.load();
		port = Integer.valueOf(Context.getConfigVal("default_Port"));
		maxConNumber = Integer.valueOf(Context.getConfigVal("max_ConNumber"));
		handlerClass = Context.getConfigVal("default_HandlerClass");
	}

	public void shutdown() {
		// 关闭服务器
	}

	private class ServerThread extends Thread {

		Socket client;

		public ServerThread(Socket client) {
			this.client = client;
		}

		public Handler getHandlerInstance() {
			Object instance = null;
			try {
				Class<?> cls = Class.forName(handlerClass);
				try {
					instance = cls.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}
				if (instance instanceof Handler) {
					return (Handler) instance;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}

		public void run() {
			Request request = new DefaultRequest(client);
			Response response = new DefaultResponse(client);
			Handler handler = getHandlerInstance();
			if (handler != null) {
				handler.service(request, response);
			}
		}
	}
}
