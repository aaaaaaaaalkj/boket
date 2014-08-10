package managementCards.cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Suit {
	Diamonds, Hearts, Spades, Clubs;

	// values() creates a new array each call. better to cache
	public static final List<Suit> VALUES = Collections.unmodifiableList(Arrays
			.asList(values()));

	public static Suit random(Random r) {
		return VALUES.get(r.nextInt(VALUES.size()));
	}

	public String shortString() {
		switch (this) {
		case Diamonds:
			return "d";
		case Hearts:
			return "h";
		case Spades:
			return "s";
		case Clubs:
			return "c";
		default:
			throw new RuntimeException("Unexpected suit: " + this);
		}
	}

	public static Suit fromShortString(String s) {
		if (s.equals("d"))
			return Diamonds;
		if (s.equals("h"))
			return Hearts;
		if (s.equals("s"))
			return Spades;
		if (s.equals("c"))
			return Clubs;
		throw new RuntimeException(s + " is not a short String for a color!");
	}
};