package fr.uge.tp5.ex2;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RoomsTemp2 {
  private final static Object lock = new Object();
  private static final LinkedHashMap<String, Integer> roomtemps = new LinkedHashMap<String, Integer>();
  private final int roomNumber;
  private int n = 0;
  
  public RoomsTemp2(int number){
    if(number < 0) {
      throw new IllegalArgumentException();
    }
    this.roomNumber = number;
  }
  
  public void add(String room, int temp) {
    Objects.requireNonNull(room);
    synchronized(lock) {
      roomtemps.put(room,temp);
      n+=1;
      if(n == roomNumber) {
        n=0;
        lock.notify();
      }
    }
  }
  
  public Double globTemp() throws InterruptedException {
    synchronized (lock) {
      lock.wait();
      return roomtemps.entrySet().stream().mapToInt(e -> e.getValue()).average().getAsDouble();
    }
  }
}
