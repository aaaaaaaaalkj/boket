package management.cards.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

public enum Suit {
  DIAMONDS, HEARTS, SPADES, CLUBS;

  public static Suit diamonds() {
    return DIAMONDS;
  }

  // values() creates a new array each call. better to cache
  @SuppressWarnings("null")
  public static final List<@NonNull Suit> VALUES = Collections
      .unmodifiableList(Arrays
          .asList(values()));

  public static List<Suit> getValues() {
    return VALUES;
  }

  @SuppressWarnings("null")
  private static final EnumMap<Suit, List<Suit>> WITHOUT = new EnumMap<>(
      Suit.class);
  static {
    WITHOUT.put(CLUBS, new ArrayList<>(VALUES));
    WITHOUT.put(SPADES, new ArrayList<>(VALUES));
    WITHOUT.put(HEARTS, new ArrayList<>(VALUES));
    WITHOUT.put(DIAMONDS, new ArrayList<>(VALUES));

    WITHOUT.get(CLUBS).remove(CLUBS);
    WITHOUT.get(DIAMONDS).remove(DIAMONDS);
    WITHOUT.get(HEARTS).remove(HEARTS);
    WITHOUT.get(SPADES).remove(SPADES);
  }

  public static Suit random(final Random rnd) {
    return VALUES.get(rnd.nextInt(VALUES.size()));
  }

  public static Suit random2(final Random rnd, final Suit deadSuit) {
    final List<Suit> selection = WITHOUT.get(deadSuit);
    return selection.get(rnd.nextInt(selection.size()));
  }

  public String shortString() {
    switch (this) {
    case DIAMONDS:
			return "♦";
    case HEARTS:
			return "♥";
    case SPADES:
			return "♠";
    case CLUBS:
			return "♣";
    default:
      throw new RuntimeException("Unexpected suit: " + this);
    }
  }

	// public static Suit fromShortString(final String s) {
	// if (s.equals("♦")) {
	// return DIAMONDS;
	// }
	// if (s.equals("♥")) {
	// return HEARTS;
	// }
	// if (s.equals("♠")) {
	// return SPADES;
	// }
	// if (s.equals("♣")) {
	// return CLUBS;
	// }
	// throw new RuntimeException(s + " is not a short String for a color!");
	// }

  public static int count() {
    return VALUES.size();
  }
}