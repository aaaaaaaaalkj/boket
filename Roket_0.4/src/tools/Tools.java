package tools;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

	@SuppressWarnings({ "unchecked", "null" })
	public static final <T> List<T> emptyList() {
		return (List<T>) Collections.emptyList();
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
}
