package ranges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import management.cards.cards.Rank;

import org.eclipse.jdt.annotation.Nullable;

import tools.RandomEnumSet;

public class SimpleRange implements Range, Cloneable {
  private final RandomEnumSet<ElementRange> elements;

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

  public final ElementRange getRandom(final Random rnd) {
    return elements.getRandom(rnd);
  }

  public final SimpleRange normalize(final Card c) {
    elements.removeAll(ElementRange.find(c));
    return this;
  }

  public final SimpleRange removeAssociated(final Iterable<Card> cards) {
    for (Card c : cards) {
      elements.removeAll(ElementRange.find(c));
    }
    return this;
  }

  public final SimpleRange removeAssociated(final Card card) {
    elements.removeAll(ElementRange.find(card));
    return this;
  }

  public final SimpleRange normalize(final ElementRange e) {
    elements.removeAll(ElementRange.find(e));
    return this;
  }

  @Override
  public final boolean contains(final ElementRange r) {
    return elements.contains(r);
  }

  public final int size() {
    return elements.size();
  }

  public final void addAll(final SimpleRange other) {
    this.elements.addAll(other.elements);
  }

  public static void main(final String[] args) {
    code4Enum2();
  }

  @SuppressWarnings("unused")
  private static void code4Enum() {
    List<Card> cards = Card.getAllCards();
    for (int i = cards.size() - 1; i >= 0; i--) {
      for (int j = i - 1; j >= 0; j--) {
        Card c1 = cards.get(i);
        Card c2 = cards.get(j);
        System.out.println(c1.shortString2() + "_" + c2.shortString()
            + "(" + c1.getRank() + "," + c1.getSuit() + ","
            + c2.getRank() + "," + c2.getSuit() + "),");
      }
    }
  }

  private static void code4Enum2() {
    List<Rank> ranks = new ArrayList<>(Rank.VALUES);
    Collections.reverse(ranks);

    String s = "";

    for (int i = 0; i < ranks.size(); i++) {
      Rank r1 = ranks.get(i);
      String s1WithUnderscore = r1.shortString2();
      String s1Simple = r1.shortString();
      s += s1WithUnderscore + s1Simple + "(" + r1 + "," + r1 + ",false),\n";

      for (int j = i + 1; j < ranks.size(); j++) {
        Rank r2 = ranks.get(j);
        String s2 = r2.shortString();
        s += s1WithUnderscore + s2 + "(" + r1 + "," + r2 + ",true),\n";
        s += s1WithUnderscore + s2 + "o(" + r1 + "," + r2 + ",false),\n";
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
    for (ElementRange e : target.elements) {
      if (!contains(e)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public final int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + elements.hashCode();
    return result;
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
    if (!elements.equals(other.elements)) {
      return false;
    }
    return true;
  }

}
