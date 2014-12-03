package old;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import managementcards.cards.Card;
import managementcards.cards.Rank;
import managementcards.cards.Suit;
import tools.Tools;

public final class Flop implements java.lang.Iterable<Card> {
  private final Card first;
  private final Card second;
  private final Card third;

  private Flop(final Card first, final Card second, final Card third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }

  public static Flop newInstance(final Card c1, final Card c2, final Card c3) {
    return new Flop(c1, c2, c3);
  }

  public static Flop threeSuite(final int f, final int s, final int t, final Set<Card> deck) {
    return threeSuite(Rank.VALUES.get(f - 2), Rank.VALUES.get(s - 2),
        Rank.VALUES.get(t - 2), deck);
  }

  // for example: s == "[3c 9h 6c]"
  public static Flop newInstance(final String s) {
    int mid1 = s.indexOf(" ");
    int mid2 = s.lastIndexOf(" ");
    Card c1 = Card
        .newInstance(Tools.substring(s, s.indexOf("[") + 1, mid1));
    Card c2 = Card.newInstance(Tools.substring(s, mid1 + 1, mid2));
    Card c3 = Card
        .newInstance(Tools.substring(s, mid2 + 1, s.indexOf("]")));
    return new Flop(c1, c2, c3);
  }

  public static Flop threeSuite(final Rank f, final Rank s, final Rank t,
      final Set<Card> deck) {
    for (Suit c : Suit.VALUES) {
      Card c1, c2, c3;
      c1 = Card.newInstance(f, c);
      c2 = Card.newInstance(s, c);
      c3 = Card.newInstance(t, c);
      if (!deck.contains(c1)) {
        continue;
      }
      if (!deck.contains(c2)) {
        continue;
      }
      if (!deck.contains(c2)) {
        continue;
      }
      deck.remove(c1);
      deck.remove(c2);
      deck.remove(c3);
      return new Flop(c1, c2, c3);
    }
    throw new RuntimeException("this three-suite flop is impossible!");
  }

  @Override
  public Iterator<Card> iterator() {
    return new FlopIterator();
  }

  public String toString() {
    return "[" + first.shortString() + ", " + second.shortString() + ", "
        + third.shortString() + "]";
  }

  public void print() {
    System.out.println("*** Flop: " + this + "");
  }

  private final class FlopIterator implements Iterator<Card> {
    private int pos;

    private FlopIterator() {
      pos = 0;
    }

    @Override
    public boolean hasNext() {
      return pos < 3;
    }

    @Override
    public Card next() {
      if (pos == 0) {
        pos = 1;
        return first;
      }
      if (pos == 1) {
        pos = 2;
        return second;
      }
      if (pos == 2) {
        pos = 3;
        return third;
      }
      throw new NoSuchElementException();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

}
