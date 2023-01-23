package fr.uge.tp9.ex1;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Codex2 {

  public static void main(String[] args) {
    var queue = new ArrayBlockingQueue<String>(10);
    var queueDecode = new ArrayBlockingQueue<String>(10);
    var threadArray = new ArrayList<Thread>();
    for(var i =0 ; i < 3;i++) {
      threadArray.add( 
        Thread.ofPlatform().start(()->{
          for(;!Thread.currentThread().isInterrupted();) {
            try {
              Thread.sleep(1000);
              var recived = CodeAPI.receive();
              queue.put(recived);
              System.out.println(Thread.currentThread().getName() +" Recived => " + recived);
            }catch(InterruptedException e) {
              return;
            }
          }
        })
      );
    }
    for(var i =0 ; i < 2;i++) {
      threadArray.add(
        Thread.ofPlatform().start(()->{
          var decoding = "";
          for(;!Thread.currentThread().isInterrupted();) {
            try {
              Thread.sleep(1000);
              decoding = queue.take();
              queueDecode.put(CodeAPI.decode(decoding));
              System.out.println(Thread.currentThread().getName() +" Decoding => " + decoding);
            }catch(InterruptedException e) {
              return;
            }catch(IllegalArgumentException e) {
              System.out.println(Thread.currentThread().getName() + " is Unable to Decode => " + decoding);
              for(var element : threadArray) {
                element.interrupt();
              }
            }
          }
        })
      );
    }
      
    threadArray.add(
      Thread.ofPlatform().start(()->{
        for(;!Thread.currentThread().isInterrupted();) {
          try {
            Thread.sleep(1000);
            CodeAPI.archive(queueDecode.take());
          }catch(InterruptedException e) {
            return;
          }
        }
      })
    );
  }
}
