package com.blackbread.thread;

/**
 * 
 * @Description: Java死锁实例 
 * @author chengxiang.li
 * @date 2014年9月4日 上午10:13:23
 * @version V1.0
 */
public class DeadLock {
	public final static A a = new A();
	public final static B b = new B();

	public static void main(String[] args) throws InterruptedException {
		Thread ta = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						a.trace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		Thread tb = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						b.trace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		ta.start();
		tb.start();
	}

}

class A {
	public synchronized void trace() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + ":aaaaaaa");
		Thread.sleep(100);
		DeadLock.b.sys();
		System.out.println("释放锁");
	}

	public synchronized void sys() throws InterruptedException {
		System.out.println(Thread.currentThread().getName()
				+ ":aaaaaaa syssyssys");
	}
}

class B {
	public synchronized void trace() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + ":bbbbbbbb");
		Thread.sleep(300);
		DeadLock.a.sys();
		System.out.println("shifangsuo B");
	}

	public synchronized void sys() throws InterruptedException {
		System.out.println(Thread.currentThread().getName()
				+ ":bbbbbbbb syssyssys");
	}
}