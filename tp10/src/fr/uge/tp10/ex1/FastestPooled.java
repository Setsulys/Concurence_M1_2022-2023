package fr.uge.tp10.ex1;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class FastestPooled {


  private final String item;
  private final int timeoutMilliPerRequest;
  private final int poolSize;
  
  public FastestPooled(String item, int timeoutMilliPerRequest,int poolSize) {
    this.item = item;
    this.timeoutMilliPerRequest = timeoutMilliPerRequest;
    this.poolSize=poolSize;
  }
  
  public Optional<Answer> retrieved(){
    var executorServices = Executors.newFixedThreadPool(poolSize);
    var callable = new ArrayList<Callable<Answer>>();
    Answer ans;
    Request.ALL_SITES.forEach(sites ->  callable.add(() -> {
      var requests = new Request(sites, item);
      var answer = Answer.empty();
      try {
        answer = requests.request(timeoutMilliPerRequest);
        if(!answer.isSuccessful()) {
          throw new AssertionError();
        }
        return answer;
      } catch (Exception e) {
        throw new AssertionError(e.getCause());
      }

    }));
    try {
      ans = executorServices.invokeAny(callable);
    }catch(InterruptedException | ExecutionException e) {
      throw new AssertionError(e.getCause());
    }
    executorServices.shutdown();
    return Optional.ofNullable(ans);
  }
  
  public static void main(String[] args) {
    var agregator = new FastestPooled("tortank", 2_000,5);
    var answer = agregator.retrieved();
    System.out.println(answer);
  }
}
