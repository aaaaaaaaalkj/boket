package managementCards.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

public enum Rank {
	Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
	private final static String ALL = "23456789TJQKA";

	public boolean isConnected(Rank rank) {
		return Math.abs(ordinal() - rank.ordinal()) == 1;
	}

	// values() creates a new array each call. better to cache
	@SuppressWarnings("null")
	public static final List<@NonNull Rank> VALUES = Collections
			.unmodifiableList(Arrays
			.asList(values()));

	public static List<Rank> valuesReversed() {
		List<Rank> res = new ArrayList<Rank>(VALUES);
		Collections.reverse(res);
		return res;
	}

	public static Rank random(Random r) {
		return VALUES.get(r.nextInt(VALUES.size()));
	}

	public Rank prev() {
		if (this == Two) {
			return Ace;
		} else {
			return VALUES.get(this.ordinal() - 1);
		}
	}

	public Rank next(int i) {
		if (i == -1)
			return prev();
		if (i == 0)
			return this;
		if (this == Ace)
			return VALUES.get(i - 1);
		return VALUES.get(this.ordinal() + i);
	}

	public Rank next() {
		if (this == Ace)
			return Two;
		else
			return VALUES.get(this.ordinal() + 1);
	}

	public boolean lessThan(Rank r) {
		return this.ordinal() < r.ordinal();
	}

	public static Rank fromShortString(String str) {
		if (str == null || str.length() != 1 || !ALL.contains(str)) {
			throw new IllegalArgumentException(str
					+ " is not a short String for a CardNum!");
		}
		return VALUES.get(ALL.indexOf(str));
	}

	public String shortString() {
		return ALL.substring(this.ordinal(), this.ordinal() + 1);
	}

	public void print() {
		System.out.println(this);
	}
}
