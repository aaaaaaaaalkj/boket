package tools;

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

public class RandomEnumSet<@NonNull E extends Enum<E>> extends AbstractSet<E>
    implements Cloneable {
  private final List<E> dta;
  private final EnumMap<E, @Nullable Integer> idx;
  private final Class<E> clazz;

  public RandomEnumSet(Class<E> clazz) {
    this(clazz, Tools.emptyList());
  }

  public RandomEnumSet<E> clone() {
    return new RandomEnumSet<>(clazz, dta);
  }

  public RandomEnumSet(Class<E> clazz, Collection<E> items) {
    this.clazz = clazz;
    this.dta = new ArrayList<E>();
    this.idx = new EnumMap<E, @Nullable Integer>(clazz);
    addAll(items);
  }

  @Override
  public boolean add(E item) {
    if (idx.containsKey(item)) {
      return false;
    }
    idx.put(item, dta.size());
    dta.add(item);
    return true;
  }

  /**
   * Override element at position <code>id</code> with last element.
   * 
   * @param id
   */
  @SuppressWarnings("null")
  public E removeAt(int id) {
    if (id >= dta.size()) {
      throw new IndexOutOfBoundsException(id + " is >= than "
          + dta.size());
    }
    E res = dta.get(id);
    idx.remove(res);

    E last = dta.remove(dta.size() - 1);
    // skip filling the hole if last is removed
    if (id < dta.size()) {
      idx.put(last, id);
      dta.set(id, last);
    }
    return res;
  }

  @Override
  public boolean remove(@Nullable Object item) {
    Integer id = idx.get(item);
    if (id == null) {
      return false;
    }
    removeAt(id);
    return true;
  }

  @SuppressWarnings("null")
  public E get(int i) {
    return dta.get(i);
  }

  @SuppressWarnings("null")
  public E pollRandom(Random rnd) {
    if (dta.isEmpty()) {
      throw new IllegalStateException("cant poll from empty RandomSet");
    }
    int id = rnd.nextInt(dta.size());
    return removeAt(id);
  }

  @SuppressWarnings("null")
  public E getRandom(Random rnd) {
    if (dta.isEmpty()) {
      throw new IllegalStateException("cant poll from empty RandomSet");
    }
    int id = rnd.nextInt(dta.size());
    return get(id);
  }

  @Override
  public int size() {
    return dta.size();
  }

  @SuppressWarnings("null")
  @Override
  public Iterator<E> iterator() {
    return dta.iterator();
  }

  public static RandomEnumSet<@NonNull ElementRange> noneOf(
      Class<ElementRange> clazz) {
    return new RandomEnumSet<>(clazz);
  }
}