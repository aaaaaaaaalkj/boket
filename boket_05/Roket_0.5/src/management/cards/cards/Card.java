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
	TWO_DIAMONDS(Two, DIAMONDS), TWO_HEARTS(Two, HEARTS), TWO_SPADES(Two,
			SPADES),
  TWO_CLUBS(Two, CLUBS),
  THREE_DIAMONDS(Three, DIAMONDS),
  THREE_HEARTS(Three, HEARTS),
  THREE_SPADES(Three, SPADES),
  THREE_CLUBS(Three, CLUBS),
  FOUR_DIAMONDS(Four, DIAMONDS),
  FOUR_HEARTS(Four, HEARTS),
  FOUR_SPADES(Four, SPADES),
  FOUR_CLUBS(Four, CLUBS),
  FIVE_DIAMONDS(Five, DIAMONDS),
  FIVE_HEARTS(Five, HEARTS),
  FIVE_SPADES(Five, SPADES),
  FIVE_CLUBS(Five, CLUBS),
  SIX_DIAMONDS(Six, DIAMONDS),
  SIX_HEARTS(Six, HEARTS),
  SIX_SPADES(Six, SPADES),
  SIX_CLUBS(Six, CLUBS),
  SEVEN_DIAMONDS(Seven, DIAMONDS),
  SEVEN_HEARTS(Seven, HEARTS),
  SEVEN_SPADES(Seven, SPADES),
  SEVEN_CLUBS(Seven, CLUBS),
  EIGHT_DIAMONDS(Eight, DIAMONDS),
  EIGHT_HEARTS(Eight, HEARTS),
  EIGHT_SPADES(Eight, SPADES),
  EIGHT_CLUBS(Eight, CLUBS),
  NINE_DIAMONDS(Nine, DIAMONDS),
  NINE_HEARTS(Nine, HEARTS),
  NINE_SPADES(Nine, SPADES),
  NINE_CLUBS(Nine, CLUBS),
  TEN_DIAMONDS(Ten, DIAMONDS),
  TEN_HEARTS(Ten, HEARTS),
  TEN_SPADES(Ten, SPADES),
  TEN_CLUBS(Ten, CLUBS),
  JACK_DIAMONDS(Jack, DIAMONDS),
  JACK_HEARTS(Jack, HEARTS),
  JACK_SPADES(Jack, SPADES),
  JACK_CLUBS(Jack, CLUBS),
  QUEEN_DIAMONDS(Queen, DIAMONDS),
  QUEEN_HEARTS(Queen, HEARTS),
  QUEEN_SPADES(Queen, SPADES),
  QUEEN_CLUBS(Queen, CLUBS),
  KING_DIAMONDS(King, DIAMONDS),
  KING_HEARTS(King, HEARTS),
  KING_SPADES(King, SPADES),
  KING_CLUBS(King, CLUBS),
  ACE_DIAMONDS(Ace, DIAMONDS),
  ACE_HEARTS(Ace, HEARTS),
  ACE_SPADES(Ace, SPADES),
  ACE_CLUBS(Ace, CLUBS);

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
