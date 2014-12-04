package managementcards.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

public final class Card implements Comparable<Card> {
  private final Rank rank;
  private final Suit suit;

  public Card(final Rank rank, final Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public int ordinal() {
    return rank.ordinal() * 4 + suit.ordinal() + 1;
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

  @SuppressWarnings("null")
  public static Card newInstance(final int rank, final Suit suit) {
    return new Card(Rank.values()[rank - 2], suit);
  }

  @SuppressWarnings("null")
  public static Card newInstance(final String str) {
    return new Card(Rank.fromShortString(str.substring(0, 1)),
        Suit.fromShortString(str.substring(1)));
  }

  public static Card newInstance(final Rank num, final Suit color) {
    return new Card(num, color);
  }

  public static Card newInstance(final Suit color, final Rank num) {
    return new Card(num, color);
  }

  private static final List<Card> ALL_CARDS = new ArrayList<>();
  static {
    for (Rank r : Rank.VALUES) {
      for (Suit s : Suit.VALUES) {
        ALL_CARDS
            .add(newInstance(r, s));
      }
    }
  }

  public static List<Card> getAllCards() {
    return Tools.unmodifiableList(ALL_CARDS);
  }

  public String toString() {
    // return this.number + " of " + this.color;
    return rank.shortString() + suit.shortString();
  }

  public void print() {
    System.out.println(this.shortString());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (suit.hashCode());
    result = prime * result + (rank.hashCode());
    return result;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Card other = (Card) obj;
    if (suit != other.suit) {
      return false;
    }
    if (rank != other.rank) {
      return false;
    }
    return true;
  }

  @Override
  public int compareTo(final Card c) {
    return this.ordinal() - c.ordinal();

    // int i = this.rank.compareTo(c.rank);
    // if (i == 0)
    // i = this.suit.compareTo(c.suit);
    // return i;
  }

  // public static Card getCard(int k1, Set<Card> deck) {
  // int c = X.randomIndex(Suit.values().length);
  // return getCard(k1, Suit.values()[c], deck);
  // }

  public static Card getCard(final int k1, final Suit color, final Set<Card> deck) {
    Card c = Card.newInstance(k1, color);
    if (deck.contains(c)) {
      deck.remove(c);
      return c;
    } else {
      throw new RuntimeException("no such card in deck!");
    }
  }

  public static Card getCard(final int k1, final Set<Suit> possibleColors,
      final Set<Card> deck) {
    ArrayList<Suit> suits = new ArrayList<>(possibleColors);
    Collections.shuffle(suits);

    return suits.stream()
        .map(suit -> Card.newInstance(k1, suit))
        .filter(deck::contains)
        .findAny()
        .map(card -> {
          deck.remove(card);
          return card;
        })
        .orElseThrow(IllegalStateException::new);
  }

  public String shortString() {
    return rank.shortString() + suit.shortString();
  }

  public String shortString2() {
    if (rank.ordinal() < 8) {
      return "_"
          + rank.shortString() + suit.shortString();
    } else {
      return rank.shortString() + suit.shortString();
    }
  }

  public static final Card D2 = new Card(Rank.Two, Suit.DIAMONDS);
  public static final Card D3 = new Card(Rank.Three, Suit.DIAMONDS);
  public static final Card D4 = new Card(Rank.Four, Suit.DIAMONDS);
  public static final Card D5 = new Card(Rank.Five, Suit.DIAMONDS);
  public static final Card D6 = new Card(Rank.Six, Suit.DIAMONDS);
  public static final Card D7 = new Card(Rank.Seven, Suit.DIAMONDS);
  public static final Card D8 = new Card(Rank.Eight, Suit.DIAMONDS);
  public static final Card D9 = new Card(Rank.Nine, Suit.DIAMONDS);
  public static final Card TD = new Card(Rank.Ten, Suit.DIAMONDS);
  public static final Card JD = new Card(Rank.Jack, Suit.DIAMONDS);
  public static final Card QD = new Card(Rank.Queen, Suit.DIAMONDS);
  public static final Card KD = new Card(Rank.King, Suit.DIAMONDS);
  public static final Card AD = new Card(Rank.Ace, Suit.DIAMONDS);
  public static final Card H2 = new Card(Rank.Two, Suit.HEARTS);
  public static final Card H3 = new Card(Rank.Three, Suit.HEARTS);
  public static final Card H4 = new Card(Rank.Four, Suit.HEARTS);
  public static final Card H5 = new Card(Rank.Five, Suit.HEARTS);
  public static final Card H6 = new Card(Rank.Six, Suit.HEARTS);
  public static final Card H7 = new Card(Rank.Seven, Suit.HEARTS);
  public static final Card H8 = new Card(Rank.Eight, Suit.HEARTS);
  public static final Card H9 = new Card(Rank.Nine, Suit.HEARTS);
  public static final Card TH = new Card(Rank.Ten, Suit.HEARTS);
  public static final Card JH = new Card(Rank.Jack, Suit.HEARTS);
  public static final Card QH = new Card(Rank.Queen, Suit.HEARTS);
  public static final Card KH = new Card(Rank.King, Suit.HEARTS);
  public static final Card AH = new Card(Rank.Ace, Suit.HEARTS);
  public static final Card S2 = new Card(Rank.Two, Suit.SPADES);
  public static final Card S3 = new Card(Rank.Three, Suit.SPADES);
  public static final Card S4 = new Card(Rank.Four, Suit.SPADES);
  public static final Card S5 = new Card(Rank.Five, Suit.SPADES);
  public static final Card S6 = new Card(Rank.Six, Suit.SPADES);
  public static final Card S7 = new Card(Rank.Seven, Suit.SPADES);
  public static final Card S8 = new Card(Rank.Eight, Suit.SPADES);
  public static final Card S9 = new Card(Rank.Nine, Suit.SPADES);
  public static final Card TS = new Card(Rank.Ten, Suit.SPADES);
  public static final Card JS = new Card(Rank.Jack, Suit.SPADES);
  public static final Card QS = new Card(Rank.Queen, Suit.SPADES);
  public static final Card KS = new Card(Rank.King, Suit.SPADES);
  public static final Card AS = new Card(Rank.Ace, Suit.SPADES);
  public static final Card C2 = new Card(Rank.Two, Suit.CLUBS);
  public static final Card C3 = new Card(Rank.Three, Suit.CLUBS);
  public static final Card C4 = new Card(Rank.Four, Suit.CLUBS);
  public static final Card C5 = new Card(Rank.Five, Suit.CLUBS);
  public static final Card C6 = new Card(Rank.Six, Suit.CLUBS);
  public static final Card C7 = new Card(Rank.Seven, Suit.CLUBS);
  public static final Card C8 = new Card(Rank.Eight, Suit.CLUBS);
  public static final Card C9 = new Card(Rank.Nine, Suit.CLUBS);
  public static final Card TC = new Card(Rank.Ten, Suit.CLUBS);
  public static final Card JC = new Card(Rank.Jack, Suit.CLUBS);
  public static final Card QC = new Card(Rank.Queen, Suit.CLUBS);
  public static final Card KC = new Card(Rank.King, Suit.CLUBS);
  public static final Card AC = new Card(Rank.Ace, Suit.CLUBS);

}
