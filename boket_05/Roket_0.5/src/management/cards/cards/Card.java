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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

/**
 * 
 * A Card has a rank and a suit. There 52 of them - all static final. No need to
 * override equals/hashCode, because multiton pattern is used here.
 * 
 * @author Combat-Ready
 *
 */

public final class Card implements Comparable<Card> {
	private static final int GREATEST_NUMBER_RANK = 9;
	private final Rank rank;
	private final Suit suit;
	private final int ordinal; // cached ordinal

	private Card(final Rank rank, final Suit suit) {
		this.rank = rank;
		this.suit = suit;
		this.ordinal = rank.ordinal() * Suit.count() + suit.ordinal() + 1;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	@Override
	public int compareTo(final Card c) {
		return this.ordinal() - c.ordinal();
	}

	public int ordinal() { // 1-based
		return ordinal;
	}

	@Override
	public int hashCode() {
		return ordinal; // cached unique value for each card
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		return this == obj; // multiton pattern
	}

	public String toString() {
		// return this.number + " of " + this.color;
		return rank.shortString() + suit.shortString();
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

	public void print() {
		System.out.println(this.shortString());
	}

	public static final Card D2 = new Card(Two, DIAMONDS);
	public static final Card D3 = new Card(Three, DIAMONDS);
	public static final Card D4 = new Card(Four, DIAMONDS);
	public static final Card D5 = new Card(Five, DIAMONDS);
	public static final Card D6 = new Card(Six, DIAMONDS);
	public static final Card D7 = new Card(Seven, DIAMONDS);
	public static final Card D8 = new Card(Eight, DIAMONDS);
	public static final Card D9 = new Card(Nine, DIAMONDS);
	public static final Card DT = new Card(Ten, DIAMONDS);
	public static final Card DJ = new Card(Jack, DIAMONDS);
	public static final Card DQ = new Card(Queen, DIAMONDS);
	public static final Card DK = new Card(King, DIAMONDS);
	public static final Card DA = new Card(Ace, DIAMONDS);
	public static final Card H2 = new Card(Two, HEARTS);
	public static final Card H3 = new Card(Three, HEARTS);
	public static final Card H4 = new Card(Four, HEARTS);
	public static final Card H5 = new Card(Five, HEARTS);
	public static final Card H6 = new Card(Six, HEARTS);
	public static final Card H7 = new Card(Seven, HEARTS);
	public static final Card H8 = new Card(Eight, HEARTS);
	public static final Card H9 = new Card(Nine, HEARTS);
	public static final Card HT = new Card(Ten, HEARTS);
	public static final Card HJ = new Card(Jack, HEARTS);
	public static final Card HQ = new Card(Queen, HEARTS);
	public static final Card HK = new Card(King, HEARTS);
	public static final Card HA = new Card(Ace, HEARTS);
	public static final Card S2 = new Card(Two, SPADES);
	public static final Card S3 = new Card(Three, SPADES);
	public static final Card S4 = new Card(Four, SPADES);
	public static final Card S5 = new Card(Five, SPADES);
	public static final Card S6 = new Card(Six, SPADES);
	public static final Card S7 = new Card(Seven, SPADES);
	public static final Card S8 = new Card(Eight, SPADES);
	public static final Card S9 = new Card(Nine, SPADES);
	public static final Card ST = new Card(Ten, SPADES);
	public static final Card SJ = new Card(Jack, SPADES);
	public static final Card SQ = new Card(Queen, SPADES);
	public static final Card SK = new Card(King, SPADES);
	public static final Card SA = new Card(Ace, SPADES);
	public static final Card C2 = new Card(Two, CLUBS);
	public static final Card C3 = new Card(Three, CLUBS);
	public static final Card C4 = new Card(Four, CLUBS);
	public static final Card C5 = new Card(Five, CLUBS);
	public static final Card C6 = new Card(Six, CLUBS);
	public static final Card C7 = new Card(Seven, CLUBS);
	public static final Card C8 = new Card(Eight, CLUBS);
	public static final Card C9 = new Card(Nine, CLUBS);
	public static final Card CT = new Card(Ten, CLUBS);
	public static final Card CJ = new Card(Jack, CLUBS);
	public static final Card CQ = new Card(Queen, CLUBS);
	public static final Card CK = new Card(King, CLUBS);
	public static final Card CA = new Card(Ace, CLUBS);

	private static final Map<Suit, Map<Rank, Card>> CARDS = new HashMap<>();
	static {
		for (Suit s : Suit.VALUES) {
			CARDS.put(s, new HashMap<>());
		}
		CARDS.get(CLUBS).put(Two, C2);
		CARDS.get(CLUBS).put(Three, C3);
		CARDS.get(CLUBS).put(Four, C4);
		CARDS.get(CLUBS).put(Five, C5);
		CARDS.get(CLUBS).put(Six, C6);
		CARDS.get(CLUBS).put(Seven, C7);
		CARDS.get(CLUBS).put(Eight, C8);
		CARDS.get(CLUBS).put(Nine, C9);
		CARDS.get(CLUBS).put(Ten, CT);
		CARDS.get(CLUBS).put(Jack, CJ);
		CARDS.get(CLUBS).put(Queen, CQ);
		CARDS.get(CLUBS).put(King, CK);
		CARDS.get(CLUBS).put(Ace, CA);

		CARDS.get(HEARTS).put(Two, H2);
		CARDS.get(HEARTS).put(Three, H3);
		CARDS.get(HEARTS).put(Four, H4);
		CARDS.get(HEARTS).put(Five, H5);
		CARDS.get(HEARTS).put(Six, H6);
		CARDS.get(HEARTS).put(Seven, H7);
		CARDS.get(HEARTS).put(Eight, H8);
		CARDS.get(HEARTS).put(Nine, H9);
		CARDS.get(HEARTS).put(Ten, HT);
		CARDS.get(HEARTS).put(Jack, HJ);
		CARDS.get(HEARTS).put(Queen, HQ);
		CARDS.get(HEARTS).put(King, HK);
		CARDS.get(HEARTS).put(Ace, HA);

		CARDS.get(SPADES).put(Two, S2);
		CARDS.get(SPADES).put(Three, S3);
		CARDS.get(SPADES).put(Four, S4);
		CARDS.get(SPADES).put(Five, S5);
		CARDS.get(SPADES).put(Six, S6);
		CARDS.get(SPADES).put(Seven, S7);
		CARDS.get(SPADES).put(Eight, S8);
		CARDS.get(SPADES).put(Nine, S9);
		CARDS.get(SPADES).put(Ten, ST);
		CARDS.get(SPADES).put(Jack, SJ);
		CARDS.get(SPADES).put(Queen, SQ);
		CARDS.get(SPADES).put(King, SK);
		CARDS.get(SPADES).put(Ace, SA);

		CARDS.get(DIAMONDS).put(Two, D2);
		CARDS.get(DIAMONDS).put(Three, D3);
		CARDS.get(DIAMONDS).put(Four, D4);
		CARDS.get(DIAMONDS).put(Five, D5);
		CARDS.get(DIAMONDS).put(Six, D6);
		CARDS.get(DIAMONDS).put(Seven, D7);
		CARDS.get(DIAMONDS).put(Eight, D8);
		CARDS.get(DIAMONDS).put(Nine, D9);
		CARDS.get(DIAMONDS).put(Ten, DT);
		CARDS.get(DIAMONDS).put(Jack, DJ);
		CARDS.get(DIAMONDS).put(Queen, DQ);
		CARDS.get(DIAMONDS).put(King, DK);
		CARDS.get(DIAMONDS).put(Ace, DA);
	}

	public static Card newInstance(final Rank rank, final Suit suit) {
		return CARDS.get(suit).get(rank);
	}

	private static final List<Card> ALL_CARDS = new ArrayList<>();
	static {
		for (Rank r : Rank.VALUES) {
			for (Suit s : Suit.VALUES) {
				ALL_CARDS.add(newInstance(r, s));
			}
		}
	}

	public static List<Card> getAllCards() {
		return Tools.unmodifiableList(ALL_CARDS);
	}
}
