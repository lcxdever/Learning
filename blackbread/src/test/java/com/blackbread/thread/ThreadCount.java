package com.blackbread.thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.blackbread.thread.GoodListHelper.PrintA;

/**
 * 
 * @Description:多线程计数问题，解决:加synchronized 或者使用AtomicInteger
 * @author chengxiang.li
 * @date 2014年9月4日 下午2:48:18
 * @version V1.0
 */
public class ThreadCount {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(100);
		Count count = new Count();
		for (int i = 0; i < 1000; i++) {
			Thread t1 = new Thread(new CountThread(count), "t" + i);
			executor.execute(t1);
		}
		executor.shutdown();
	}
}

class CountThread implements Runnable {
	private Count count;

	public CountThread(Count count) {
		super();
		this.count = count;
	}

	@Override
	public void run() {
		try {
			Thread.currentThread().sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		count.add();
		System.out.println(Thread.currentThread().getName() + ":"
				+ count.getCount());
	}
}

class Count {
	private int count = 0;

	public int getCount() {
		return count;
	}

	public synchronized void add() {
		count++;
	}
}
// class Count {
// private AtomicInteger count = new AtomicInteger(0);
//
// public int getCount() {
// return count.get();
// }
//
// public void add() {
// count.incrementAndGet();
// }
// }
