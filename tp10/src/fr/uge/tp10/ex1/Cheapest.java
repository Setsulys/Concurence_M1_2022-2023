package fr.uge.tp10.ex1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

public class Cheapest {
	private final String item;
	private final int timeoutMilliPerRequest;

	private final static int NB_SITES = Request.ALL_SITES.size();

	private final ArrayBlockingQueue<Answer> queue = new ArrayBlockingQueue<>(NB_SITES);

	public Cheapest(String item, int timeoutMilliPerRequest) {
		this.item = item;
		this.timeoutMilliPerRequest = timeoutMilliPerRequest;
	}

	public Optional<Answer> retrieved() throws InterruptedException {
		var threads = new Thread[NB_SITES];
		IntStream.range(0, NB_SITES).forEach(j -> {
			threads[j] = new Thread(() -> {
				var request = new Request(Request.ALL_SITES.get(j), item);
				try {
					queue.put(request.request(timeoutMilliPerRequest));
				} catch (InterruptedException e) {
					return;
				}
			});
			threads[j].start();
		});

		try {
			var answers = new ArrayList<Answer>();
			for (int i = 0; i < NB_SITES; i++) {
				answers.add(queue.take());
			}
			return answers.stream().filter(Answer::isSuccessful).min(Answer.ANSWER_COMPARATOR);
		} finally {
			Arrays.stream(threads).forEach(Thread::interrupt);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		var agregator = new Cheapest("pikachu", 2_000);
		var answer = agregator.retrieved();
		System.out.println(answer);
	}
}
