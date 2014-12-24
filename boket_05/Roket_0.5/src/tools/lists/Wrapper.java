package tools.lists;

import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Wrapper<E> implements ReadOnlyList<E> {
  private final List<E> javaList;

  public Wrapper(List<E> javaList) {
    this.javaList = javaList;
  }

  @Override
  public int size() {
    return javaList.size();
  }

  @Override
  public boolean contains(E o) {
    return javaList.contains(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return javaList.containsAll(c);
  }

  @SuppressWarnings("null")
  @Override
  public E get(int index) {
    return javaList.get(index);
  }

  @Override
  public int indexOf(E o) {
    return javaList.indexOf(o);
  }

  @Override
  public int lastIndexOf(E o) {
    return javaList.lastIndexOf(o);
  }

  @Override
  public @NonNull ReadOnlyList<@NonNull E> subList(int fromIndex, int toIndex) {
    @Nullable
    List<@NonNull E> sub = javaList.subList(fromIndex, toIndex);

    if (sub == null) {
      throw new IllegalStateException("should never happen");
    }

    return new Wrapper<>(sub);
  }

  @SuppressWarnings({ "null" })
  @Override
  public ReadOnlyIterator<E> iterator() {
    return new IteratorWrapper<E>(javaList.iterator());
  }

  @Override
  public boolean isEmpty() {
    return javaList.isEmpty();
  }

}
