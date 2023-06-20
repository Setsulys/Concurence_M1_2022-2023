package td4.ex2;

import java.util.ArrayList;
import java.util.Objects;

public class BoundedSafeQueue<V> {
	private final ArrayList<V> queue;
	private final Object lock = new Object();
	private final int size;
	
	public BoundedSafeQueue(int size) {
		synchronized (lock) {
			if(size <= 0) {
				throw new IllegalArgumentException();
			}
			this.size = size;
			this.queue = new ArrayList<>(size);
		}
	
	}
	
	
	public void put(V value) throws InterruptedException {
		Objects.requireNonNull(value);
		synchronized (lock) {
			while(size <= queue.size()) {
				lock.wait();
			}
			lock.notify();
			queue.add(value);
			
		}
	}
	
	public V take() throws InterruptedException {
		synchronized (lock) {
			while(queue.isEmpty()) {
				lock.wait();
			}
			lock.notify();
			return queue.remove(0);
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		var nbThread = 50;
		var queue = new BoundedSafeQueue<String>(10);
		for(var i = 0 ; i < nbThread ; i++) {
			Thread.ofPlatform().start(()->{
				while(true) {
					try {
						Thread.sleep(2000);
						queue.put(Thread.currentThread().getName());
					}catch(InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			});
		}
		while(true) {
			System.out.println(queue.take());
		}
	}
	
	
}
