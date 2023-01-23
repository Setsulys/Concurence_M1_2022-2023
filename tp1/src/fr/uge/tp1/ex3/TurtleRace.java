package fr.uge.tp1.ex3;

import java.util.ArrayList;

public class TurtleRace {
	
	public final ArrayList<Thread> thrds = new ArrayList<>();
	public void newThread(int value, int [] time) {
		Runnable runnable = () -> {
			try {
				Thread.sleep(time[value]);
			} catch (InterruptedException e) {
				throw new AssertionError(e);
			}
			System.out.println("Turtle "+ value +" has finished");
		};
		thrds.add(new Thread(runnable));
	}
	
	public void threads(int value, int [] time){
		for(var i=0; i < value ; i++) {
			newThread(i,time);
		}
	}
	
	public void startRace(int[] time) {
		for(Thread t: thrds) {
			t.start();
			
		}
		
		
		for(var t : thrds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				throw new AssertionError(e);
			}
		}
	}


}
