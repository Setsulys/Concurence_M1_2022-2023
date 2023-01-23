package fr.uge.tp5.ex2;
import java.util.LinkedHashMap;
import java.util.Objects;



public class RoomsTemp {
  private final static Object lock = new Object();
  private static final LinkedHashMap<String, Integer> roomtemps = new LinkedHashMap<String, Integer>();
  private final int roomNumber;
  
  public RoomsTemp(int number){
    if(number < 0) {
      throw new IllegalArgumentException();
    }
    this.roomNumber = number;
  }
  
  public void add(String room, int temp) {
    Objects.requireNonNull(room);
    synchronized(lock) {
      if(roomtemps.computeIfPresent(room, (k,v)->temp) == null) {
        roomtemps.put(room,temp);
      }
      if(roomtemps.size() == roomNumber) {
        lock.notifyAll();
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
