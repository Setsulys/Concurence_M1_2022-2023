package fr.uge.tp6.ex1;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedSafeQueue<V> {

  private final ArrayDeque<V> fifo = new ArrayDeque<>();
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition1 = lock.newCondition();
  private final Condition condition2 = lock.newCondition();
  private final int capacity;
  
  public BoundedSafeQueue(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException();
    }
    this.capacity = capacity;
  }

  public void put(V value) throws InterruptedException {
    lock.lock();
    try {
      while (fifo.size() == capacity) {
        condition1.await();
      }
      fifo.add(value);
      condition2.signal();
    } finally {
      lock.unlock();
    }
  }

  public V take() throws InterruptedException {
    lock.lock();
    try {
      while (fifo.isEmpty()) {
        condition2.await();
      }
      condition1.signal();
      return fifo.remove();
    } finally {
      lock.unlock();
    }
  }
  
  public static void main(String[] args) {
    var thrd = new BoundedSafeQueue<>(12);
    for(var i=0 ; i < 5 ;i++) {
      Thread.ofPlatform().start(() ->{
        for(;;) {

          try {
            Thread.sleep(2000);
            thrd.put(Thread.currentThread().getName());
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
