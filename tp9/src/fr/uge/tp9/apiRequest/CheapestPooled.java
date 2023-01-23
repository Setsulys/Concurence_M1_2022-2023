package fr.uge.tp9.apiRequest;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;


public class CheapestPooled {

  private final String item;
  private final int timeoutMilliPerRequest;

  private final static int NB_SITES = Request.ALL_SITES.size();
  private final ArrayBlockingQueue<String> siteQueue = new ArrayBlockingQueue<>(NB_SITES);
  private final ArrayBlockingQueue<Answer> answerQueue = new ArrayBlockingQueue<Answer>(NB_SITES);

  
  
  public CheapestPooled(String item, int timeoutMilliPerRequest) {
    this.item = item;
    this.timeoutMilliPerRequest = timeoutMilliPerRequest;
  }

  public Optional<Answer> retrieved() throws InterruptedException {
    var init =false;
    Answer min = null;
    for(var i = 0 ; i < CheapestPooled.NB_SITES;i++) {
      var answer = answerQueue.take();
      if(answer.isSuccessful()) {
        if(!init) {
          init = true;
          min = answer;
        }
        else if(min.price()> answer.price()){
          min = answer;
        }
      }
    }
    return Optional.of(min);
  }
  
  public void addSites() throws InterruptedException {
    for(var elem : Request.ALL_SITES) {
      siteQueue.put(elem);
    }
  }
  
  public void workerthread() throws InterruptedException {
    for(;!Thread.currentThread().isInterrupted();) {
      var elem = siteQueue.take();
      var request = new Request(elem.toString(),this.item);
      answerQueue.put(request.request(this.timeoutMilliPerRequest));
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    var aggregator = new CheapestPooled("Pikachu",2000);
    var threadArray = new ArrayList<Thread>();
      threadArray.add( 
          Thread.ofPlatform().start(()-> {
            for(;!Thread.currentThread().isInterrupted();) {
              try {
                Thread.sleep(1000);
                aggregator.addSites();
              }catch(InterruptedException e) {
                return;
              }
            }    
         })
      );  
    for(var i =0 ; i < 3;i++) {
      threadArray.add(Thread.ofPlatform().start(()->{
        for(;!Thread.currentThread().isInterrupted();) {
          try {
            Thread.sleep(1000);
            aggregator.workerthread();
          }catch(InterruptedException e) {
            return;
          }
        }
      })
    );
    }
   threadArray.add(
       Thread.ofPlatform().start(()-> {
         try {
           Thread.sleep(1000);
           System.out.println(aggregator.retrieved());
           for(var t : threadArray) {
             t.interrupt();
           }
         }catch(InterruptedException e) {
           return;
         }
       }));
  }
}
