package fr.uge.tp1.ex1;

public class HelloThread {

	public void newThread(int value) {
		Runnable runnable = () -> {
			for(var i = 0; i <= 5000; i++) {
				System.out.println("Hello "+ value +" "+ i);
			}
		};
		Thread thread = Thread.ofPlatform().start(runnable);
		
	}
	
	public void threads(int value) {
		for(var i=0; i < value ; i++) {
			newThread(i);
		}
	}
	
}
