package com.blackbread.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TestSocket {

	public static void main(String[] args) {
		try {
			Callable<String> callable = new Callable<String>() {
				@Override
				public String call() throws Exception {
					return null;
				}
			};
			ServerSocket server = new ServerSocket(2309);
			for (;;) {
				final Socket socket = server.accept();
				InputStream in = socket.getInputStream();
				InputStreamReader ir = new InputStreamReader(in);
				char[] buff = new char[1024];
				int len = 0;
				System.out.println("有数据才执行啊");
				while ((len = ir.read(buff)) != -1) {
					System.out.println(new String(buff));
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
