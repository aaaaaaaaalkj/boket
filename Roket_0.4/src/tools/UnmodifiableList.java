package tools;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public final class UnmodifiableList<E> {
	private final List<E> list;

  private UnmodifiableList(final List<E> list) {
		this.list = list;
	}

	public List<E> toJavaList() {
		return list;
	}

	@SuppressWarnings("null")
  public static <E> UnmodifiableList<E> of(final List<E> list) {
		return new UnmodifiableList<>(Collections.unmodifiableList(list));
	}

	public int size() {
		return list.size();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

  public boolean contains(final Object o) {
		return list.contains(o);
	}

	@SuppressWarnings("null")
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@SuppressWarnings("null")
	public Object[] toArray() {
		return list.toArray();
	}

	@SuppressWarnings("null")
  public <T> T[] toArray(final T[] a) {
		return list.toArray(a);
	}

  public boolean containsAll(final Collection<?> c) {
		return list.containsAll(c);
	}

  @Nullable
  public E get(final int index) {
		return list.get(index);
	}

  public int indexOf(final E e) {
		return list.indexOf(e);
	}

  public int lastIndexOf(final E e) {
		return list.lastIndexOf(e);
	}

	@SuppressWarnings("null")
  public UnmodifiableList<E> subList(final int fromIndex, final int toIndex) {
		return new UnmodifiableList<>(list.subList(fromIndex, toIndex));
	}

}
