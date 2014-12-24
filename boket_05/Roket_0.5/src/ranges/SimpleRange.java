package ranges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import management.cards.cards.Rank;

import org.eclipse.jdt.annotation.Nullable;

import tools.collections.RandomEnumSet;
import tools.collections.UnmodifiableIterator;

public final class SimpleRange implements Range {
  private final RandomEnumSet<ElementRange> elements;

  @SuppressWarnings("null")
  public SimpleRange() {
    this(RandomEnumSet.noneOf(ElementRange.class));
  }

  public SimpleRange(final RandomEnumSet<ElementRange> elements) {
    this.elements = elements;
  }

  public void add(final ElementRange e) {
    elements.add(e);
  }

  @Override
  public ElementRange getRandom(final Random rnd, double score) {
    return elements.getRandom(rnd);
  }

  // public SimpleRange removeAssociated(final Iterable<Card> cards) {
  // for (Card c : cards) {
  // elements.removeAll(ElementRange.findAssociated(c));
  // }
  // return this;
  // }

  // public SimpleRange removeAssociated(final Card card) {
  // elements.removeAll(ElementRange.findAssociated(card));
  // return this;
  // }

  // public SimpleRange removeAssociated(final ElementRange e) {
  // elements.removeAll(ElementRange.findAssociated(e.getFirstCard()));
  // elements.removeAll(ElementRange.findAssociated(e.getSecondCard()));
  // return this;
  // }

  @Override
  public boolean contains(final ElementRange r) {
    return elements.contains(r);
  }

  @Override
  public int size() {
    return elements.size();
  }

  public void addAll(final SimpleRange other) {
    this.elements.addAll(other.elements);
  }

  public static void main(final String[] args) {
    code4Enum();
  }

  private static void code4Enum() {
    List<Card> cards = Card.getAllCards();

    for (Card c1 : cards) {
      for (Card c2 : cards) {
        if (c1.ordinal() <= c2.ordinal()) {
          continue;
        }
        // int ord = code(c1.ordinal(), c2.ordinal());
        System.out.println(c1.shortString2() + "_"
            + c2.shortString()
            + "(" + c1.getRank() + "," + c1.getSuit() + ","
            + c2.getRank() + "," + c2.getSuit() + "),");
      }
    }

    // for (int i = cards.size() - 1; i >= 0; i--) {
    // for (int j = i - 1; j >= 0; j--) {
    // Card c1 = cards.get(i);
    // Card c2 = cards.get(j);
    // System.out.println(c1.shortString2() + "_" + c2.shortString()
    // + "(" + c1.getRank() + "," + c1.getSuit() + ","
    // + c2.getRank() + "," + c2.getSuit() + "),");
    // }
    // }
  }

  @SuppressWarnings("unused")
  private static void code4Enum2() {
    List<Rank> ranks = new ArrayList<>(Rank.VALUES);
    Collections.reverse(ranks);

    String s = "";

    for (int i = 0; i < ranks.size(); i++) {
      Rank r1 = ranks.get(i);
      String s1WithUnderscore = r1.shortString2();
      String s1Simple = r1.shortString();
      s += s1WithUnderscore + s1Simple + "(" + r1 + "," + r1
          + ",false),\n";

      for (int j = i + 1; j < ranks.size(); j++) {
        Rank r2 = ranks.get(j);
        String s2 = r2.shortString();
        s += s1WithUnderscore + s2 + "(" + r1 + "," + r2 + ",true),\n";
        s += s1WithUnderscore + s2 + "o(" + r1 + "," + r2
            + ",false),\n";
      }

    }

    System.out.println(s);

  }

  @SuppressWarnings("null")
  public static SimpleRange full() {
    return new SimpleRange(new RandomEnumSet<>(ElementRange.class,
        ElementRange.VALUES));
  }

  public static SimpleRange broadwayHands() {

    List<Card> cards = Card.broadwayCards();

    SimpleRange res = new SimpleRange();

    for (int i = 0; i < cards.size(); i++) {
      for (int j = i + 1; j < cards.size(); j++) {
        res.add(ElementRange.find(cards.get(i), cards.get(j)));
      }
    }

    return res;
  }

  public boolean containsAll(final SimpleRange target) {
    return elements.containsAll(target.elements);
  }

  @Override
  public final int hashCode() {
    return elements.hashCode();
  }

  @Override
  public final boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SimpleRange other = (SimpleRange) obj;
    return elements.equals(other.elements);
  }

  @SuppressWarnings("all")
  @Override
  public final Iterator<ElementRange> iterator() {
    // dont allow the iterator to remove elements
    return new UnmodifiableIterator<ElementRange>(elements.iterator());
  }

}
