package fr.uge.tp5.ex3;


  public class ExchangerReuse<T> {
    private T value  = null;
    private State done = State.BEGIN;
    private final static Object lock = new Object();
    
    public enum State {
      BEGIN, FIRST, THEN;
    }
    
    public T exchange(T valueEx) throws InterruptedException {
      synchronized(lock) {
        if (done != State.FIRST) {
          value = valueEx;
          done = State.FIRST;
          while(done == State.FIRST) { 
            lock.wait();
          }
          return value;
        } else {
          var tmp = this.value;
          value = valueEx;
          done = State.THEN;
          lock.notify();
          return tmp;
        }
      }
      
    }
  }
