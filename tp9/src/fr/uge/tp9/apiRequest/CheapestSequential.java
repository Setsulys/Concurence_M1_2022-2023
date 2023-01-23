package fr.uge.tp9.apiRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheapestSequential {

    private final String item;
    private final int timeoutMilliPerRequest;

    public CheapestSequential(String item, int timeoutMilliPerRequest) {
      Objects.requireNonNull(item);  
      this.item = item;
      this.timeoutMilliPerRequest = timeoutMilliPerRequest;
    }

    /**
     * @return the cheapest price for item if it is sold
     */
    public Optional<Answer> retrieve() throws InterruptedException {
        var answers = new ArrayList<Answer>();
        for(var elem : Request.ALL_SITES) {
          var request = new Request(elem, item);
          var answer = request.request(timeoutMilliPerRequest);
          if (answer.isSuccessful()){
              answers.add(answer);
          }
        }
      return answers.stream().map(e->e).collect(Collectors.minBy(Comparator.comparing(e -> e.price())));
    }

    public static void main(String[] args) throws InterruptedException {
        var agregator = new CheapestSequential("pikachu", 2_000);
        var answer = agregator.retrieve();
        System.out.println(answer); // Optional[pikachu@darty.fr : 214]
    }
}
