package fr.uge.tp4.ex2;

import java.util.ArrayList;
import java.util.Objects;

public class UnboundedSafeQueue<V> {

  private final ArrayList<V> threadList = new ArrayList<V>();
  private final Object lock = new Object();
  
  
  public void add(V elt) {
    synchronized (lock) {
      Objects.requireNonNull(elt);
      threadList.add(elt); 
      lock.notify();
    }
  }
  
  public V take() throws InterruptedException { 
    synchronized (lock) {
      while(threadList.isEmpty()) {
        lock.wait();
        Thread.sleep(1);
      }   
      return threadList.remove(0);
    }
  }
  
  public static void main(String[] args) {
    var thrd = new UnboundedSafeQueue<>();
    for(var i=0 ; i < 3 ;i++) {
      Thread.ofPlatform().start(() ->{
        for(;;) {

          try {
            Thread.sleep(2000);
            thrd.add(Thread.currentThread().getName());
          } catch (Exception e) {
            System.out.print(e.getMessage());
          }

          
        }
      });
    }
    for(;;) {
      try {
        System.out.println(thrd.take());
      } catch (InterruptedException e) {
        System.out.println(e.getMessage());
      }
    }
  }
  
}
