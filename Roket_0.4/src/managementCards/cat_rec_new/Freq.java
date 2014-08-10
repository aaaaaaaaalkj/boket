package managementCards.cat_rec_new;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Freq {
	ZERO(0), ONE(1), TWO(2), THREE(3), FOUR(4);

	public static final List<Freq> VALUES = Collections.unmodifiableList(Arrays
			.asList(values()));

	public int value;

	Freq(int k) {
		value = k;
	}
}
