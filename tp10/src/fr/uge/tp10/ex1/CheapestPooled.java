package fr.uge.tp10.ex1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class CheapestPooled {

  private final String item;
  private final int timeoutMilliPerRequest;
  private final int poolSize;
  
  public CheapestPooled(String item, int timeoutMilliPerRequest,int poolSize) {
    this.item = item;
    this.timeoutMilliPerRequest = timeoutMilliPerRequest;
    this.poolSize=poolSize;
  }

  public Optional<Answer> retrieved() throws InterruptedException {
    var executorServices = Executors.newFixedThreadPool(poolSize);
    var callable = new ArrayList<Callable<Answer>>();
    Request.ALL_SITES.forEach(sites ->  callable.add(() -> {
      var requests = new Request(sites, item);
      try {
        return requests.request(timeoutMilliPerRequest);
      } catch (Exception e) {
        throw new AssertionError(e.getCause());
      }
    }));
    var futures = executorServices.invokeAll(callable);
    executorServices.shutdown();
   
    return futures.stream()
        .filter(i-> i.state().equals(Future.State.SUCCESS))
        .map(i->{return i.resultNow();})
        .filter(i-> i.isSuccessful())
        .min(Comparator.comparingInt(Answer::price));
  }
  
  public static void main(String[] args) throws InterruptedException {
    var aggregator = new CheapestPooled("Pikachu",2000,5);
    System.out.println(aggregator.retrieved());
    
  }
}
