package tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public class Tools {
	@SafeVarargs
	public static <T> List<@NonNull T> asList(T... ts) {
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

	public static @NonNull String[] split(String s, String regex) {
		@SuppressWarnings("null")
		@NonNull
		String @NonNull [] res = s.split(regex);

		return res;
	}

	public static double parseDouble(String s) {
		double res = Double.parseDouble(s);
		return res;
	}

	public static Double parseDouble2(String s) {
		double res = Double.parseDouble(s);
		return res;
	}

	public static <T> Optional<T> of(T d) {
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
			@Nullable Stream<@Nullable Integer> stream) {
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
	public static <T> List<T> unmodifiableList(List<? extends T> list) {
		return Collections.unmodifiableList(list);
	}

	@SuppressWarnings("null")
	public static String substring(String s, int beginIndex, int endIndex) {
		return s.substring(beginIndex, endIndex);
	}

	@SuppressWarnings("null")
	public static final <T> @NonNull List<T> emptyList() {
		return Collections.emptyList();
	}

	@SuppressWarnings("null")
	public static <K> Set<K> keySet(Map<K, ?> set) {
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
	public static <T> List<T> nCopies(int n, T o) {
		return Collections.nCopies(n, o);
	}

	@SuppressWarnings("null")
	public static <T, K, A, D>
			Collector<T, ?, Map<K, D>> groupingBy(
					Function<? super T, ? extends K> classifier,
					Collector<? super T, A, D> downstream) {
		return Collectors.groupingBy(classifier, downstream);

	}

	@SuppressWarnings("null")
	public static <E> List<E> subList(List<E> list, int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	public static <E> List<E> flatten(List<List<E>> listOfLists) {
		List<E> tie2 = new ArrayList<>();
		for (List<E> list : listOfLists) {
			for (E r : list) {
				tie2.add(r);
			}
		}
		return tie2;
	}

	public static <E> List<E> first(int num, List<E> list) {
		return subList(list, 0, Math.min(num, list.size()));
	}

	public static <E, K, V> Map<K, List<V>> groupingBy(Iterable<E> col,
			Function<E, K> key_func, Function<E, V> val_func) {
		Map<K, List<V>> map = new HashMap<>();
		for (E c : col) {
			K key = key_func.apply(c);
			if (!map.containsKey(key)) {
				map.put(key, new ArrayList<>());
			}
			map.get(key_func.apply(c)).add(val_func.apply(c));
		}
		return map;
	}

	/**
	 * Checks whters each element from the first Iterable is contained in the
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
	public static <T> boolean contains(List<T> col1, Collection<T> col2) {
		for (int i = 0; i < col1.size(); i++) {
			if (!col2.contains(col1.get(i))) {
				return false;
			}
		}
		return true;
	}

	public static <F, T> List<T> map(List<F> list, Function<F, T> mapper) {
		List<T> res = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			res.add(mapper.apply(list.get(i)));
		}
		return res;
	}
}
