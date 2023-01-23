package fr.uge.tp1.ex3;

public class Main {

	public static void main(String[] args){
		  System.out.println("On your mark!");
		  try {
			Thread.sleep(30_00);
		} catch (InterruptedException e) {
			throw new AssertionError(e);
		}
		  System.out.println("Go!");
		  int[] times = {25_000, 10_000, 20_000, 5_000, 50_000, 60_000};
		  var race = new TurtleRace();
		  race.threads(times.length,times);
		  race.startRace(times);
		  
		}
}
