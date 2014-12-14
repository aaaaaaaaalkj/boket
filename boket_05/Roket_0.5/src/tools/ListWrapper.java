package tools;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jdt.annotation.Nullable;

@SuppressWarnings("null")
public class ListWrapper<E> implements List<E> {
  private List<List<@Nullable E>> lists;

  @SafeVarargs
  public
  ListWrapper(List<E>... lists) {
    this.lists = Tools.asList(lists);
  }

  @Nullable
  @Override
  public E get(final int index) {
    int index2 = index;
    for (List<@Nullable E> l : lists) {
      if (index2 < l.size()) {
        return l.get(index2);
      }
      index2 -= l.size();
    }
    throw new IndexOutOfBoundsException(index + " is not between 0 and "
        + size());
  }

  @Override
  public int size() {
    int sum = 0;
    for (List<@Nullable E> l : lists) {
      sum += l.size();
    }
    return sum;
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException(
        "clear() is not supported for ListWrapper");
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean contains(@Nullable Object o) {
    for (List<@Nullable E> l : lists) {
      if (l.contains(o)) {
        return true;
      }
    }
    return false;
  }

  private class CustomIterator<@Nullable F> implements Iterator<F> {
    int listIndex;
    int index;

    public CustomIterator() {
      listIndex = 0;
      index = 0;
    }

    @Override
    public boolean hasNext() {
      return listIndex < lists.size() && index < lists.get(listIndex).size();
    }

    @Override
    public @Nullable F next() {
      @SuppressWarnings("unchecked")
      List<@Nullable F> list = (List<F>) lists.get(listIndex);

      index++;
      if (index >= lists.get(listIndex).size()) {
        index = 0;
        listIndex++;
      }

      @Nullable
      F res = list.get(index);

      return res;
    }

  }

  @SuppressWarnings("unused")
  @Override
  public Iterator<@Nullable E> iterator() {
    return new CustomIterator<E>();
  }

  @Override
  public Object[] toArray() {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public <T> T[] toArray(T @Nullable [] a) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public boolean add(@Nullable E e) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public boolean remove(@Nullable Object o) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public boolean containsAll(@Nullable Collection<?> c) {
    if (c == null) {
      throw new IllegalArgumentException("parameter is null");
    }
    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(@Nullable Collection<? extends E> c) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public boolean addAll(int index, @Nullable Collection<? extends E> c) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public boolean removeAll(@Nullable Collection<?> c) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public boolean retainAll(@Nullable Collection<?> c) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public E set(int index, @Nullable E element) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public void add(int index, @Nullable E element) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");

  }

  @Override
  public E remove(int index) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public int indexOf(@Nullable Object o) {
    int prev_index = 0;
    for (List<@Nullable E> l : lists) {
      int index = l.indexOf(o);
      if (index != -1) {
        return prev_index + index;
      }
      prev_index += l.size();
    }
    return -1;
  }

  @Override
  public int lastIndexOf(@Nullable Object o) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public ListIterator<E> listIterator() {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    throw new UnsupportedOperationException(
        "not supported for ListWrapper");
  }
}
