package tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.RandomAccess;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.LoggerFactory;

public final class Tools {

	private Tools() {

	}

	@SuppressWarnings("null")
	static final org.slf4j.Logger LOG = LoggerFactory
			.getLogger(Tools.class);

	@SafeVarargs
	public static <T> List<@NonNull T> asList(final T... ts) {
		@SuppressWarnings("null")
		@NonNull
		List<@NonNull T> res = Arrays.asList(ts);
		return res;
	}

	@SuppressWarnings("null")
	public static <T> Collector<@NonNull T, @NonNull ?, @NonNull Set<@NonNull T>> toSet() {
		@NonNull
		Collector<@NonNull T, ?, Set<@NonNull T>> res = Collectors
				.toSet();

		return res;
	}

	public static <T> Collector<? super T, @NonNull ?, List<T>> toList() {
		@SuppressWarnings("null")
		@NonNull
		Collector<T, @NonNull ?, List<T>> res = Collectors.toList();
		return res;
	}

	@SuppressWarnings("null")
	public static String[] split(final String s, final String regex) {
		String[] res = s.split(regex);
		return res;
	}

	public static double parseDouble(final String s) {
		double res = Double.parseDouble(s);
		return res;
	}

	public static Double parseDouble2(final String s) {
		double res = Double.parseDouble(s);
		return res;
	}

	public static <T> Optional<T> of(final T d) {
		// if (null == d) {
		// return empty();
		// } else {
		Optional<@NonNull T> opt = Optional.of(d);
		if (opt == null) {
			return empty();
		} else {
			return opt;
		}
		// }
	}

	public static <T> Optional<T> empty() {
		@SuppressWarnings("null")
		@NonNull
		Optional<T> opt = Optional.empty();
		return opt;
	}

	public static Optional<Integer> min(
			@Nullable final Stream<@Nullable Integer> stream) {
		if (stream == null) {
			return empty();
		} else {
			Optional<@Nullable Integer> res = stream.min(Comparator
					.naturalOrder());
			@NonNull
			Optional<@NonNull Integer> res2;
			if (res.isPresent()) {
				Integer nullable = res.get();
				if (nullable == null) {
					// will never happen
					res2 = empty();
				} else {
					res2 = of(nullable);
				}
			} else {
				res2 = empty();
			}
			return res2;
		}
	}

	@SuppressWarnings("null")
	public static <T> List<T> unmodifiableList(final List<? extends T> list) {
		return Collections.unmodifiableList(list);
	}

	@SuppressWarnings("null")
	public static <T> Set<T> unmodifiableSet(final Set<? extends T> list) {
		return Collections.unmodifiableSet(list);
	}

	@SuppressWarnings("null")
	public static String substring(final String s, final int beginIndex,
			final int endIndex) {
		return s.substring(beginIndex, endIndex);
	}

	@SuppressWarnings("null")
	public static <T> @NonNull List<T> emptyList() {
		return Collections.emptyList();
	}

	@SuppressWarnings("null")
	public static <K> Set<K> keySet(final Map<K, ?> set) {
		return set.keySet();
	}

	public static Collector<@NonNull Integer, @NonNull ?, @NonNull Integer>
			summingInt() {
		ToIntFunction<Integer> mapper = x -> x;
		@SuppressWarnings("null")
		@NonNull
		Collector<@NonNull Integer, @NonNull ?, @NonNull Integer> res = Collectors
				.summingInt(mapper);

		return res;

	}

	@SuppressWarnings("null")
	public static <T> List<T> nCopies(final int n, final T o) {
		return Collections.nCopies(n, o);
	}

	@SuppressWarnings("null")
	public static <T, K, A, D>
			Collector<T, ?, Map<K, D>> groupingBy(
					final Function<? super T, ? extends K> classifier,
					final Collector<? super T, A, D> downstream) {
		return Collectors.groupingBy(classifier, downstream);

	}

	@SuppressWarnings("null")
	public static <E> List<E> subList(final List<E> list, final int fromIndex,
			final int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public static <E> List<E> flatten(final List<List<E>> listOfLists) {
		List<E> tie2 = new ArrayList<>();
		for (List<E> list : listOfLists) {
			for (E r : list) {
				tie2.add(r);
			}
		}
		return tie2;
	}

  /**
   * Get the first num items from list
   * 
   * @param num
   * @param list
   * @return
   */
	public static <E> List<E> first(final int num, final List<E> list) {
		return subList(list, 0, Math.min(num, list.size()));
	}

	public static <E, K, V> Map<K, List<V>> groupingBy(final Iterable<E> col,
			final Function<E, K> keyFunc, final Function<E, V> valFunc) {
		Map<K, List<V>> map = new HashMap<>();
		for (E c : col) {
			K key = keyFunc.apply(c);
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<>());
			}
			map.get(keyFunc.apply(c)).add(valFunc.apply(c));
		}
		return map;
	}

	/**
	 * Checks wheter each element from the first Iterable is contained in the
	 * second one.
	 * 
	 * @param col1
	 *            - all elements of this collection must occour in the second
	 *            for this method to return true
	 * @param col2
	 *            - collection which will be searched
	 * @return true if and only if the second collection contains all the
	 *         elements of the first
	 */
	public static <T> boolean contains(final List<T> col1,
			final Collection<T> col2) {
		for (int i = 0; i < col1.size(); i++) {
			if (!col2.contains(col1.get(i))) {
				return false;
			}
		}
		return true;
	}

	public static <F, T> List<T> map(final List<F> list,
			final Function<F, T> mapper) {
		List<T> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(mapper.apply(list.get(i)));
		}
		return res;
	}

	public static void round(final List<Double> d) {
		for (int i = 0; i < d.size(); i++) {
			d.set(i, Tools.round(d.get(i)));
		}
	}

	private static final int ROUND_TO_DIGITS = 100;

	public static Double round(final double d) {
		return ((double) Math.round(d * ROUND_TO_DIGITS)) / ROUND_TO_DIGITS;
	}

	public static void serialize(final Object o, final String fileName)
			throws IOException {
		try (
				FileOutputStream fileOut = new FileOutputStream(fileName);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);) {
			out.writeObject(o);
			LOG.debug("Serialized data is saved in " + fileName);
		}
	}

	@SuppressWarnings("null")
	public static Object deserialize(final String fileName) throws IOException,
			ClassNotFoundException {
		long l = System.currentTimeMillis();
		try (
				FileInputStream fileIn = new FileInputStream(fileName);
				ObjectInputStream in = new ObjectInputStream(fileIn);) {
			Object o = in.readObject();
			l = System.currentTimeMillis() - l;
			LOG.debug("Deserializing data from " + fileName + " took " + l
					+ " millis");
			return o;
		}
	}

	public static void reverse(final int[] ar) {
		for (int i = 0; i < ar.length / 2; i++) {
			int x = ar[i];
			ar[i] = ar[ar.length - i - 1];
			ar[ar.length - i - 1] = x;
		}
	}

	/**
	 * Count how many elements from the first list are in the second list.
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static <E> int match(final List<E> first, final Collection<E> second) {
		int count = 0;
		if (first instanceof RandomAccess) {
			for (int i = 0; i < first.size(); i++) {
				if (second.contains(first.get(i))) {
					count++;
				}
			}
		} else { // theoretically faster for LinkedList
			for (E e : first) {
				if (second.contains(e)) {
					count++;
				}
			}
		}
		return count;
	}

}
