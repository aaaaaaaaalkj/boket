package tools;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StreamTools {

	public static <X> List<X> unpack(List<Optional<X>> list) {
		return list.stream()
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

}
