package management.cards.cards;

import static management.cards.cards.Rank.Ace;
import static management.cards.cards.Rank.Eight;
import static management.cards.cards.Rank.Five;
import static management.cards.cards.Rank.Four;
import static management.cards.cards.Rank.Jack;
import static management.cards.cards.Rank.King;
import static management.cards.cards.Rank.Nine;
import static management.cards.cards.Rank.Queen;
import static management.cards.cards.Rank.Seven;
import static management.cards.cards.Rank.Six;
import static management.cards.cards.Rank.Ten;
import static management.cards.cards.Rank.Three;
import static management.cards.cards.Rank.Two;
import static management.cards.cards.Suit.CLUBS;
import static management.cards.cards.Suit.DIAMONDS;
import static management.cards.cards.Suit.HEARTS;
import static management.cards.cards.Suit.SPADES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

/**
 * 
 * A Card has a rank and a suit. There 52 of them.
 * 
 * @author Combat-Ready
 *
 */

public enum Card {
  D2(Two, DIAMONDS),
  H2(Two, HEARTS),
  S2(Two, SPADES),
  C2(Two, CLUBS),
  D3(Three, DIAMONDS),
  H3(Three, HEARTS),
  S3(Three, SPADES),
  C3(Three, CLUBS),
  D4(Four, DIAMONDS),
  H4(Four, HEARTS),
  S4(Four, SPADES),
  C4(Four, CLUBS),
  D5(Five, DIAMONDS),
  H5(Five, HEARTS),
  S5(Five, SPADES),
  C5(Five, CLUBS),
  D6(Six, DIAMONDS),
  H6(Six, HEARTS),
  S6(Six, SPADES),
  C6(Six, CLUBS),
  D7(Seven, DIAMONDS),
  H7(Seven, HEARTS),
  S7(Seven, SPADES),
  C7(Seven, CLUBS),
  D8(Eight, DIAMONDS),
  H8(Eight, HEARTS),
  S8(Eight, SPADES),
  C8(Eight, CLUBS),
  D9(Nine, DIAMONDS),
  H9(Nine, HEARTS),
  S9(Nine, SPADES),
  C9(Nine, CLUBS),
  DT(Ten, DIAMONDS),
  HT(Ten, HEARTS),
  ST(Ten, SPADES),
  CT(Ten, CLUBS),
  DJ(Jack, DIAMONDS),
  HJ(Jack, HEARTS),
  SJ(Jack, SPADES),
  CJ(Jack, CLUBS),
  DQ(Queen, DIAMONDS),
  HQ(Queen, HEARTS),
  SQ(Queen, SPADES),
  CQ(Queen, CLUBS),
  DK(King, DIAMONDS),
  HK(King, HEARTS),
  SK(King, SPADES),
  CK(King, CLUBS),
  DA(Ace, DIAMONDS),
  HA(Ace, HEARTS),
  SA(Ace, SPADES),
  CA(Ace, CLUBS);

  // should be 9, but offset is = 2, so 9-2 = 7
  private static final int GREATEST_NUMBER_RANK = 7;
  private final Rank rank;
  private final Suit suit;

  private Card(final Rank rank, final Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public Rank getRank() {
    return rank;
  }

  public Suit getSuit() {
    return suit;
  }

  @Override
  public String toString() {
    // return this.number + " of " + this.color;
    return rank.shortString() + suit.shortString();
    // return suit.shortString().toUpperCase() + rank.shortString();
  }

  public String shortString() {
    return rank.shortString() + suit.shortString();
  }

  public String shortString2() {
    String res = rank.shortString() + suit.shortString();

    if (rank.ordinal() <= GREATEST_NUMBER_RANK) {
      res = "_" + res;
    }
    return res;
  }

  @SuppressWarnings("null")
  public static final List<@NonNull Card> VALUES = Collections
      .unmodifiableList(Arrays.asList(values()));

  public static Card instance(final Rank rank, final Suit suit) {
    return VALUES.get(rank.ordinal() * Suit.count() + suit.ordinal());
  }

  public static List<Card> getAllCards() {
    return VALUES;
  }

  @Nullable
  private static List<Card> broadwayCards;

  public static List<Card> broadwayCards() {
    if (null != broadwayCards) {
      return broadwayCards;
    }
    List<Rank> ranks = Tools.asList(
        Rank.Ace,
        Rank.King,
        Rank.Queen,
        Rank.Jack,
        Rank.Ten);

    List<Card> cards = new ArrayList<>();

    for (Rank rank : ranks) {
      for (Suit suit : Suit.VALUES) {
        cards.add(Card.instance(rank, suit));
      }
    }
    broadwayCards = cards;
    return cards;
  }
}
