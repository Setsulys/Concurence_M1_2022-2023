package fr.uge.tp6.ex1;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UnboundedSafeQueue<v> {
  
  private final ArrayDeque<v> fifo = new ArrayDeque<>();
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();
  
  public void add(v value) {
    Objects.requireNonNull(value);
    lock.lock();
    try {
      fifo.add(value);
      condition.signal();
    } finally{
      lock.unlock();
    }
  }

  public v take() throws InterruptedException {
    v rmv;
    lock.lock();
    try {
      while(fifo.isEmpty()) {
        condition.await();
      }
      rmv =  fifo.remove();
    }finally {
      lock.unlock();
    }
    return rmv;
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
