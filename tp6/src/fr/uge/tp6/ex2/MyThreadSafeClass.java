package fr.uge.tp6.ex2;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadSafeClass {
  
  private final ArrayDeque<Long> prems = new ArrayDeque<>();
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();
  private final int capacity;
  
  public MyThreadSafeClass(int capacity) {
    if(capacity <=0) {
      throw new IllegalArgumentException();
    }
    this.capacity=capacity;
  }
  
  public static boolean isPrime(long l) {
    if (l <= 1) {
        return false;
    }
    for (long i = 2L; i <= l / 2; i++) {
        if (l % i == 0) {
            return false;
        }
    }
    return true;
  }
  
  
  
  public void put(long l) {
    lock.lock();
    try {
      if(prems.size()>=capacity) {
        return;
      }
      prems.add(l);
      condition.signal();
    }finally {
      lock.unlock();
    }
  }
  
  public long sum () throws InterruptedException {
    lock.lock();
    try {
      while(prems.size()!=capacity) {
        condition.await();
      }
      return prems.stream().mapToLong(e -> e.longValue()).sum();
    }finally {
      lock.unlock();
    }
  }
  
  
  
  public static void main(String[] args) throws InterruptedException {
    var prime = new MyThreadSafeClass(10);
    for(var i=0;i<10;i++) {
      Thread.ofPlatform().daemon().start(()->{
        var random = new Random();
        for (;;) {
          long nb = 1_000_000_000L + (random.nextLong() % 1_000_000_000L);
          if (isPrime(nb)) {
              System.out.println("le nombre premier : "+ nb);
              prime.put(nb);
          }
        }
      });
    }
   System.out.println(prime.sum());
  }
}


// Décrire le contrat (c'est à dire quelles sont les méthodes qu'elle doit fournir et ce qu'elles font précisément) 
//d'une classe MyThreadSafeClass permettant de réaliser cette tâche.
