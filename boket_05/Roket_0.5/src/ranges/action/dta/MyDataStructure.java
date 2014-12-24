package ranges.action.dta;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.NonNull;

public class MyDataStructure<E> implements Iterable<E> {
  private final List<@NonNull E> list;
  private int negativeIndex;
  private int positiveIndex;
  private Predicate<E> positivePredicate;

  public MyDataStructure(List<@NonNull E> list) {
    this.positivePredicate = x -> true;
    this.list = list;
    this.negativeIndex = list.size();
    this.positiveIndex = 0;
  }

  public void nextPredicate(Predicate<E> predicate) {
    this.positivePredicate = predicate;
    this.positiveIndex = 0;
    normalize();
  }

  public void reset(int size) {
    this.positivePredicate = x -> true;
    this.negativeIndex = size;
    this.positiveIndex = 0;
  }

  public E get(int index) {
    if (index >= negativeIndex) {
      throw new NoSuchElementException(index
          + " is out of bounds for allowed MyDataStructure range");
    }
    return list.get(index);
  }

  public int currentSize() {
    return negativeIndex;
  }

  public E getRandom(Random rnd) {
    return get(rnd.nextInt(currentSize()));
  }

  public boolean contains(E r) {
    int index = list.indexOf(r);
    return index >= 0 && index < currentSize();
  }

  @Override
  public Iterator<E> iterator() {
    return new Iterator1();
  }

  public void print() {
    System.out.println(list);
    System.out.println(positiveIndex);
    System.out.println(negativeIndex);
  }

  // +++++++++++++++++++++++++++++++++++++++++++++++

  private boolean cond() {
    return positiveIndex < negativeIndex;
  }

  private void normalize() {
    scroll();
    while (cond()) {
      switchPos();
      scroll();
    }
  }

  private void scroll() {
    // scroll negative index down to the last neg. element
    while (cond() && !positivePredicate.test(list.get(negativeIndex - 1))) {
      negativeIndex--;
    }
    // scroll the positive index up to the last pos. element
    while (cond() && positivePredicate.test(list.get(positiveIndex))) {
      positiveIndex++;
    }
  }

  private void switchPos() {
    E element1 = list.get(positiveIndex);
    E element2 = list.get(negativeIndex - 1);
    list.set(positiveIndex, element2);
    list.set(negativeIndex - 1, element1);
  }

  private class Iterator1 implements Iterator<E> {
    int index;

    public Iterator1() {
      this.index = 0;
    }

    @Override
    @SuppressWarnings("synthetic-access")
    public boolean hasNext() {
      return this.index < negativeIndex;
    }

    @Override
    @SuppressWarnings("synthetic-access")
    public E next() {
      if (this.index >= negativeIndex) {
        throw new NoSuchElementException();
      }
      return list.get(this.index++);
    }
  }

}
