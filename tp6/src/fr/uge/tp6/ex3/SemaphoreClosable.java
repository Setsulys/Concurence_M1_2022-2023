package fr.uge.tp6.ex3;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreClosable {
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition(); 
  private int permis;
  private int nbth;
  
  public SemaphoreClosable(int nb) {
    lock.lock();
    try {
      if(nb < 0) {
        throw new IllegalArgumentException();
      }
      this.permis=nb;
    } finally{
      lock.unlock();
    }
  }
  
  public void release() {
    lock.lock();
    try {
      this.nbth--;
      this.permis++;
      condition.signal();
    } finally{
      lock.unlock();
    }
  }
  
  public boolean tryAcquire() {
    lock.lock();
    try {
      if(permis==0) {
        this.nbth++;
        return false;
      }
      permis--;
      return true;
    } finally{
      lock.unlock();
    }
  }
  
  public void acquire() throws InterruptedException {
    lock.lock();
    try {
      if(permis==0) {
        this.nbth++;
        condition.await();
      }
      permis--;
    } finally{
      lock.unlock();
    }
  }
  
  public int waitingForPermits() {
    lock.lock();
    try {
      return this.nbth;
    } finally {
      lock.unlock();
    }
  }
  
  public void close() {
    
  }
  
  public static void main(String[] args) throws InterruptedException {
    var s = new SemaphoreClosable(5);
    for(var i=0;i<10;i++) {
      var th = i;
      Thread.ofPlatform().start(()->{
        var random = new Random();
         try {
           Thread.sleep(1000);
           s.acquire();
           s.waitingForPermits();
           System.out.println("thread : "+ th + " permis acquired" );
           Thread.sleep(1000);
           s.release();
           System.out.println("thread : "+ th + " permis released" );
         } catch (InterruptedException e) {
             throw new AssertionError(e);
        }
      });
    }
  }
}
