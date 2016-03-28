package com.succez.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.succez.server.app.FileBrowsingHandler;
import com.succez.server.core.Handler;
import com.succez.server.core.Request;
import com.succez.server.core.Response;
import com.succez.server.sample.DefaultRequest;
import com.succez.server.sample.DefaultResponse;

public class HttpServer {
	
	public void start(){
		initService();
		ServerSocket socket = null;
		doService(socket);
	}
	
	private void doService(ServerSocket server) {
		boolean status = false;
		while(status){
			Socket client;
			try {
				client = server.accept();
				ServerThread serverthread = new ServerThread(client);
				serverthread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	private static void initService() {
		// 初始化參數
	}

	public void shutdown(){
		// 关闭服务器
	}
	
	public static void main(String[] args) {
		new HttpServer().start();
	}

}

class ServerThread extends Thread{
	
	 Socket client;
	
	public ServerThread(Socket client){
		this.client = client;
	}
	public void run(){
		Request request = new DefaultRequest(client);
		Response response = new DefaultResponse(client);
		// 下面应通过反射机制修改为可配置的
		Handler handler = new FileBrowsingHandler();
		handler.service(request, response);
	}
	
}

