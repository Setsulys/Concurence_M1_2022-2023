package td4.ex2;

import java.util.ArrayList;
import java.util.Objects;

public class UnboundedSafeQueue<V> {

	private final ArrayList<V> queue = new ArrayList<>();
	private final Object lock = new Object();
	
	
	public void add(V value) {
		Objects.requireNonNull(value);
		synchronized (lock) {
			queue.add(value);
			lock.notifyAll();
		}
	}
	
	public V take() throws InterruptedException {
		synchronized (lock) {
			while(queue.isEmpty()) {
				lock.wait();
			}
			return queue.remove(0);
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		var queue = new UnboundedSafeQueue<String>();
		for(var i = 0 ; i < 3 ; i++) {
			Thread.ofPlatform().start(()->{
				while(true) {
					try {
						Thread.sleep(2000);
						queue.add(Thread.currentThread().getName());
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
