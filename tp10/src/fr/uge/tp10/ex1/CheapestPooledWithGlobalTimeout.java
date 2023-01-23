package fr.uge.tp10.ex1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CheapestPooledWithGlobalTimeout {

  private final String item;
  private final int timeoutMilliPerRequest;
  private final int poolSize;
  private final int GlobalTimeout;
  
  public CheapestPooledWithGlobalTimeout(String item, int timeoutMilliPerRequest,int poolSize,int GlobalTimeOut) {
    this.item = item;
    this.timeoutMilliPerRequest = timeoutMilliPerRequest;
    this.poolSize=poolSize;
    this.GlobalTimeout = GlobalTimeOut;
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
    var futures = executorServices.invokeAll(callable,GlobalTimeout,TimeUnit.MILLISECONDS);
    executorServices.shutdown();
   
    return futures.stream()
        .filter(i-> i.state().equals(Future.State.SUCCESS))
        .map(i->{return i.resultNow();})
        .filter(i-> i.isSuccessful())
        .min(Comparator.comparingInt(Answer::price));
  }
  
  public static void main(String[] args) throws InterruptedException {
    var aggregator = new CheapestPooledWithGlobalTimeout("Pikachu",2000,5,5000);
    System.out.println(aggregator.retrieved());
    
  }
}
