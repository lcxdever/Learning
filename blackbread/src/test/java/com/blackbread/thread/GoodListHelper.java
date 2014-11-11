package com.blackbread.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoodListHelper<E> {
	public List<E> list = Collections.synchronizedList(new ArrayList<E>());

	public boolean putIfAbsent(E x) throws InterruptedException {
		synchronized (list) {
			boolean absent = !list.contains(x);
			if (absent) {
				if (list.contains(x))
					System.out.println(list.contains(x));
				list.add(x);
			}
			return absent;
		}
	}

	public class PrintA extends Thread {
		private GoodListHelper<String> goodListHelper;
		private String value;

		public PrintA(GoodListHelper<String> goodListHelper, String value) {
			this.goodListHelper = goodListHelper;
			this.value = value;
		}

		@Override
		public void run() {
			try {
				goodListHelper.putIfAbsent(value);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public class PrintB extends Thread {
		private GoodListHelper<String> goodListHelper;
		private String value;

		public PrintB(GoodListHelper<String> goodListHelper, String value) {
			this.goodListHelper = goodListHelper;
			this.value = value;
		}

		@Override
		public void run() {
			goodListHelper.list.add(value);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		final GoodListHelper<String> goodListHelper = new GoodListHelper<String>();
		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(50);
				for (int i = 0; i < 10000; i++) {
					Thread t = goodListHelper.new PrintA(goodListHelper,
							String.valueOf(i));
					executor.execute(t);
				}
				executor.shutdown();
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(50);
				for (int i = 0; i < 10000; i++) {
					Thread t = goodListHelper.new PrintB(goodListHelper,
							String.valueOf(i));
					executor.execute(t);
				}
				executor.shutdown();
			}
		}).start();
	}
}
