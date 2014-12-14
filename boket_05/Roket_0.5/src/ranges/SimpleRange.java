package ranges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import management.cards.cards.Rank;

import org.eclipse.jdt.annotation.Nullable;

import tools.RandomEnumSet;
import tools.UnmodifiableIterator;

public class SimpleRange implements Range, Cloneable, Iterable<ElementRange> {
  private final RandomEnumSet<ElementRange> elements;

  @Override
  public final SimpleRange clone() {
    return new SimpleRange(elements.clone());
  }

  @SuppressWarnings("null")
  public SimpleRange() {
    this(RandomEnumSet.noneOf(ElementRange.class));
  }

  public SimpleRange(final RandomEnumSet<ElementRange> elements) {
    this.elements = elements;
  }

  public final void add(final ElementRange e) {
    elements.add(e);
  }

  public final boolean remove(final ElementRange e) {
    return elements.remove(e);
  }

  @Override
  public final ElementRange getRandom(final Random rnd) {
    return elements.getRandom(rnd);
  }

  public final SimpleRange removeAssociated(final Iterable<Card> cards) {
    for (Card c : cards) {
      elements.removeAll(ElementRange.findAssociated(c));
    }
    return this;
  }

  public final SimpleRange removeAssociated(final Card card) {
    elements.removeAll(ElementRange.findAssociated(card));
    return this;
  }

  public final SimpleRange removeAssociated(final ElementRange e) {
    elements.removeAll(ElementRange.findAssociated(e.getFirstCard()));
    elements.removeAll(ElementRange.findAssociated(e.getSecondCard()));
    return this;
  }

  @Override
  public final boolean contains(final ElementRange r) {
    return elements.contains(r);
  }

  @Override
  public final int size() {
    return elements.size();
  }

  public final void addAll(final SimpleRange other) {
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

  public final boolean containsAll(final SimpleRange target) {
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
