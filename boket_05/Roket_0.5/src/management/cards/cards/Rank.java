package management.cards.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

import tools.Tools;

public enum Rank {
  Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
  private static final String CHARS = "23456789TJQKA";

  public boolean isConnected(final Rank rank) {
    return Math.abs(ordinal() - rank.ordinal()) == 1;
  }

  // values() creates a new array each call. better to cache
  @SuppressWarnings("null")
  public static final List<@NonNull Rank> VALUES = Tools
      .unmodifiableList(Tools
          .asList(values()));

  public static List<Rank> valuesReversed() {
    List<Rank> res = new ArrayList<>(VALUES);
    Collections.reverse(res);
    return res;
  }

  public static Rank random(final Random r) {
    return VALUES.get(r.nextInt(VALUES.size()));
  }

  public Rank prev() {
    if (this == Two) {
      return Ace;
    }
    return VALUES.get(this.ordinal() - 1);
  }

  public Rank next(final int i) {
    if (i == -1) {
      return prev();
    }
    if (i == 0) {
      return this;
    }
    if (this == Ace) {
      return VALUES.get(i - 1);
    }
    return VALUES.get(this.ordinal() + i);
  }

  public Rank next() {
    if (this == Ace) {
      return Two;
    }
    return VALUES.get(this.ordinal() + 1);
  }

  public boolean lessThan(final Rank r) {
    return this.ordinal() < r.ordinal();
  }

  public static Rank fromShortString(final String str) {
    if (str.length() != 1 || !CHARS.contains(str)) {
      throw new IllegalArgumentException(str
          + " is not a short String for a CardNum!");
    }
    return VALUES.get(CHARS.indexOf(str));
  }

  public String shortString() {
    return Tools.substring(CHARS, this.ordinal(), this.ordinal() + 1);
  }

  private static final int GREATEST_NUMBER_RANK = 7;

  public String shortString2() {
    return (ordinal() <= GREATEST_NUMBER_RANK ? "_" : "")
        + Tools.substring(CHARS, this.ordinal(), this.ordinal() + 1);
  }

  public void print() {
    System.out.println(this);
  }

  public static int count() {
    return VALUES.size();
  }

  public static Rank max(Rank r1, Rank r2) {
    return r1.ordinal() > r2.ordinal() ? r1 : r2;
  }

  public static Rank min(Rank r1, Rank r2) {
    return r1.ordinal() > r2.ordinal() ? r2 : r1;
  }
}
