package fr.uge.tp8.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class CountAndInterrupt{
  private final LinkedHashMap<Integer,Integer> counter = new LinkedHashMap<>();
  private static final Object lock = new Object();
  
  private int increaseCounter(int number) {
    synchronized (lock) {
      if(counter.getOrDefault(number, null)==null) {
        counter.put(number,1);
      }
      else {
        counter.computeIfPresent(number, (k,v)-> v+1);
      }
      return counter.get(number);
    }
  }
  
  public static void main(String[] args) throws IOException {

          
    var count = new CountAndInterrupt();
    var threads = new Thread[4];
    for(var i=0; i < 4; i++) {
      var number = i;
      threads[i] = Thread.ofPlatform().name(number +"").start(() ->{ 
        
        for(;;) {
          try {
            Thread.sleep(1000); 
            System.out.println("Thread " + Thread.currentThread().getName() +" counter :"+ count.increaseCounter(number));   
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }          
        }
      }); 
    }
    System.out.println("enter a thread id:");
    try (var input = new InputStreamReader(System.in);
         var reader = new BufferedReader(input)) {
      String line;
      while ((line = reader.readLine()) != null) {
        var threadId = Integer.parseInt(line);
        threads[threadId].interrupt();
      }
    }
  }
}
