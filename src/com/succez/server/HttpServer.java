package com.succez.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.succez.server.core.Handler;
import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultRequest;
import com.succez.server.sample.DefaultResponse;

public class HttpServer {

	private int port;
	private int maxConNumber;
	private String handlerClass;
	private Handler handler;
	private boolean shutdownCommand = false;

	/**
	 * 日志管理工具
	 */
	public final static Logger LOGGER = LoggerFactory
			.getLogger(HttpServer.class);

	public static void main(String[] args) {
		new HttpServer().start();
	}

	public void start() {
		try {
			initService();
			LOGGER.info("Successfully init the Server...");
		} catch (Exception e1) {
			LOGGER.error("failed to init Server: " + e1.getStackTrace());
		}
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
			LOGGER.info("The server has been started successfuly!");
		} catch (IOException e) {
			LOGGER.error("failed to start Server: " + e.getStackTrace());
		}
		doService(socket);
	}

	private void doService(ServerSocket server) {
		boolean status = true;
		ExecutorService threadPool = Executors.newFixedThreadPool(maxConNumber);
		threadPool.execute(new ShutdownThread());
		while (status) {
			Socket client;
			try {
				client = server.accept();
				ServerThread serverthread = new ServerThread(client);
				threadPool.execute(serverthread);
				if (shutdownCommand) {
					threadPool.shutdown();
					LOGGER.info("The server is shutting down...");
					break;
				}
			} catch (IOException e) {
				LOGGER.error("An error occured while Server running: "
						+ e.getStackTrace());
			}
		}
	}

	public void initService() throws Exception {
		Context.load();
		port = Integer.valueOf(Context.getConfigVal("default_Port"));
		maxConNumber = Integer.valueOf(Context.getConfigVal("max_ConNumber"));
		handlerClass = Context.getConfigVal("default_HandlerClass");
	}

	private class ServerThread implements Runnable {

		Socket client;

		public ServerThread(Socket client) {
			this.client = client;
		}

		public Handler getHandlerInstance() {
			if (handler == null) {
				Object instance = null;
				try {
					Class<?> cls = Class.forName(handlerClass);
					instance = cls.newInstance();
					if (instance instanceof Handler) {
						handler = (Handler) instance;
					}
				} catch (Exception e) {
					LOGGER.error("failed to get Handler instance: "
							+ e.getStackTrace());
				}
			}
			return handler;
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

	private class ShutdownThread implements Runnable {

		@Override
		public void run() {
			Scanner scan = new Scanner(System.in);
			// 下一步放在配置文件中
			while (!scan.next().equalsIgnoreCase("exit"))
				;
			shutdownCommand = true;
			scan.close();
		}
	}
}
