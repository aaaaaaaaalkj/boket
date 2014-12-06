package strategy.conditions.postflop;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public enum PairBasedDanger {
	NONE, // no pair in community-cards
	MODERATE, // one pair in community-cards
	HIGH, // two pairs or three of a kind in community-cards
	CERTAIN_COMBO; // full-house of four-of-a-kind in communit-cards

	@SuppressWarnings("null")
	public static final List<PairBasedDanger> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));

	public static int getCount() {
		return VALUES.size();
	}



  public static PairBasedDanger fromLong(final long l) {
		return VALUES.get(Math.min((int) l, getCount() - 1));
	}

	public String toString() {
		@SuppressWarnings("null")
		@NonNull
		String res = super.toString();
		if (this == NONE) {
			return "NO";
		} else {
			return res;
		}
	}


}
