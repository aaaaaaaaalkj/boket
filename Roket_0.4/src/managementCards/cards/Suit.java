package managementCards.cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Suit {
	DIAMONDS, HEARTS, SPADES, CLUBS;

	// values() creates a new array each call. better to cache
	public static final List<Suit> VALUES = Collections.unmodifiableList(Arrays
			.asList(values()));

	public static Suit random(Random r) {
		return VALUES.get(r.nextInt(VALUES.size()));
	}

	public String shortString() {
		switch (this) {
		case DIAMONDS:
			return "d";
		case HEARTS:
			return "h";
		case SPADES:
			return "s";
		case CLUBS:
			return "c";
		default:
			throw new RuntimeException("Unexpected suit: " + this);
		}
	}

	public static Suit fromShortString(String s) {
		if (s.equals("d"))
			return DIAMONDS;
		if (s.equals("h"))
			return HEARTS;
		if (s.equals("s"))
			return SPADES;
		if (s.equals("c"))
			return CLUBS;
		throw new RuntimeException(s + " is not a short String for a color!");
	}
};