package tools.lists;

import java.util.Collection;

import org.eclipse.jdt.annotation.Nullable;

public interface ReadOnlyList<E> {
  int size();

  boolean isEmpty();

  boolean contains(E o);

  boolean containsAll(Collection<?> c);

  @Override
  boolean equals(@Nullable Object o);

  @Override
  int hashCode();

  E get(int index);

  int indexOf(E o);

  int lastIndexOf(E o);

  ReadOnlyList<E> subList(int fromIndex, int toIndex);

  ReadOnlyIterator<E> iterator();
}
