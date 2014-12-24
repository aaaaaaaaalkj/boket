package tools.lists;

import java.util.Iterator;

import org.eclipse.jdt.annotation.NonNull;

public class IteratorWrapper<E> implements ReadOnlyIterator<E> {
  private final Iterator<E> javaIterator;

  public IteratorWrapper(Iterator<E> javaIteraotr) {
    this.javaIterator = javaIteraotr;
  }

  @Override
  public boolean hasNext() {
    return javaIterator.hasNext();
  }

  @Override
  public @NonNull E next() {
    E e = javaIterator.next();

    if (e == null) {
      throw new IllegalStateException("ReadOnlyIterator dont permit null's");
    }
    return e;
  }

}
