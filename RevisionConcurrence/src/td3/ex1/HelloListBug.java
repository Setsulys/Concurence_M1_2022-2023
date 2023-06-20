package td3.ex1;

import java.util.stream.IntStream;
import java.util.ArrayList;

public class HelloListBug {
	private static final Object lock = new Object();
	
	public static void main(String[] args) throws InterruptedException {
		var nbThreads = 4;
		var threads = new Thread[nbThreads]; 
    
		var list = new ArrayList<Integer>(5_000 * nbThreads);

		IntStream.range(0, nbThreads).forEach(j -> {
			Runnable runnable = () -> {
				synchronized (lock) {
					for (var i = 0; i < 5_000; i++) {
					
						list.add(i);
					}
					
				}
			};

			threads[j] = Thread.ofPlatform().start(runnable);
		});

		for (Thread thread : threads) {
			thread.join();
		}
		System.out.println("taille de la liste:" + list.size());
	}
}
