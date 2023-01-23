package fr.uge.tp1.ex2;

import java.util.ArrayList;

public class HelloThreadJoin {

	public final ArrayList<Thread> thrds = new ArrayList<>();
	public void newThread(int value) throws InterruptedException {
		Runnable runnable = () -> {
			for(var i = 0; i <= 5000; i++) {
				System.out.println("Hello "+ value +" "+ i);
			}
		};
		thrds.add(new Thread(runnable));
	}
	
	public void threads(int value) throws InterruptedException {
		for(var i=0; i < value ; i++) {
			newThread(i);
		}
		thrds.forEach(t->t.start());
		
		for(var t : thrds) {
			t.join();
			System.out.println("Le thread a fini son Runnable");
		}
        
	}
}
