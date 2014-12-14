package tools;

import java.util.Iterator;

public class UnmodifiableIterator<E> implements Iterator<E> {
  private final Iterator<E> innerIterator;

  public UnmodifiableIterator(Iterator<E> iterator) {
    this.innerIterator = iterator;
  }

  @Override
  public boolean hasNext() {
    return innerIterator.hasNext();
  }

  @SuppressWarnings("null")
  @Override
  public E next() {
    return innerIterator.next();
  }

  /**
   * remove is not supported by SimpleIterator. This is the whole point of this class.
   */
  public void remove() {
    throw new UnsupportedOperationException("remove");
  }
}
