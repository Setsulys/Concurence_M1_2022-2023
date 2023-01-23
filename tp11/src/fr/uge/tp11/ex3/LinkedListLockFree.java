package fr.uge.tp11.ex3;

import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class LinkedListLockFree<E> {

  private record Link<E>(E value, Link<E> next) {
      private Link {
          Objects.requireNonNull(value);
      }
  }
  
  private AtomicReference<Link<E>> head = new AtomicReference<>();
  
  /**
   * Add the non-null value at the start of the list
   *
   * @param value
   */
  public void addFirst(E value) {
      Objects.requireNonNull(value);
      head = new AtomicReference<>(new Link<>(value, head.get()));
  }
  
  /**
   * applies the consumer the elements of the list in order
   *
   * @param consumer
   */
  public void forEach(Consumer<? super E> consumer) {
      Objects.requireNonNull(consumer);
      for (var current = head.get(); current != null; current = current.next) {
          consumer.accept(current.value);
      }
  }
  
  public E pollFirst() {
    if (head == null) {
        return null;
    }
    for(;;) {
      var value = head.get();
      if(head.compareAndSet(value, head.get())) {
        return value.value();
      } 
    }
  }   
  
  public static void main(String[] args) {
      var list = new LinkedList<String>();
      list.addFirst("Noel");
      list.addFirst("papa");
      list.addFirst("petit");
      list.pollFirst();
      list.forEach(System.out::println);
  }
}
