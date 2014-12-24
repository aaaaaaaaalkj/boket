package tools.collections;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import ranges.ElementRange;
import tools.Tools;

public class RandomEnumSet<@NonNull E extends Enum<E>> extends AbstractSet<E>
		implements Cloneable {
	private final List<E> list;
	private final EnumMap<E, @Nullable Integer> set;
	private final Class<E> clazz;

	public RandomEnumSet(Class<E> clazz) {
		this(clazz, Tools.emptyList());
	}

  @Override
  public RandomEnumSet<E> clone() {
		return new RandomEnumSet<>(clazz, list);
	}

	public RandomEnumSet(Class<E> clazz, Collection<E> items) {
		this.clazz = clazz;
    this.list = new ArrayList<>();
    this.set = new EnumMap<>(clazz);
		addAll(items);
	}

	@Override
	public boolean add(E item) {
		if (set.containsKey(item)) {
			return false;
		}
		set.put(item, list.size());
		list.add(item);
		return true;
	}

	/**
	 * Override element at position <code>id</code> with last element.
	 * 
	 * @param id
	 */
	@SuppressWarnings("null")
	public E removeAt(int id) {
		if (id >= list.size()) {
			throw new IndexOutOfBoundsException(id + " is >= than "
					+ list.size());
		}
		E res = list.get(id);
		set.remove(res);

		E last = list.remove(list.size() - 1);
		// skip filling the hole if last is removed
		if (id < list.size()) {
			set.put(last, id);
			list.set(id, last);
		}
		return res;
	}

	@Override
	public boolean remove(@Nullable Object item) {
		Integer id = set.get(item);
		if (id == null) {
			return false;
		}
		removeAt(id);
		return true;
	}

	@SuppressWarnings("null")
	public E get(int i) {
		return list.get(i);
	}

	@SuppressWarnings("null")
	public E pollRandom(Random rnd) {
		if (list.isEmpty()) {
			throw new IllegalStateException("cant poll from empty RandomSet");
		}
		int id = rnd.nextInt(list.size());
		return removeAt(id);
	}

	@SuppressWarnings("null")
	public E getRandom(Random rnd) {
		if (list.isEmpty()) {
			throw new IllegalStateException("cant pick from empty RandomSet");
		}
		int id = rnd.nextInt(list.size());
		return get(id);
	}

	@Override
	public int size() {
		return list.size();
	}

	@SuppressWarnings("null")
	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	public static RandomEnumSet<@NonNull ElementRange> noneOf(
			Class<ElementRange> clazz) {
		return new RandomEnumSet<>(clazz);
	}

	@Override
	public int hashCode() {
		return set.keySet().hashCode();
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		RandomEnumSet<E> other = (RandomEnumSet<E>) obj;
		return set.keySet().equals(other.set.keySet());
	}

}