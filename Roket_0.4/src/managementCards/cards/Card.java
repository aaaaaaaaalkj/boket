package managementCards.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import tools.X;

public final class Card implements Comparable<Card> {
	private final Rank rank;
	private final Suit suit;

	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public static Card newInstance(int rank, Suit suit) {
		return new Card(Rank.values()[rank - 2], suit);
	}

	public static Card newInstance(String str) {
		return new Card(Rank.fromShortString(str.substring(0, 1)),
				Suit.fromShortString(str.substring(1)));
	}

	public static Card newInstance(Rank num, Suit color) {
		return new Card(num, color);
	}

	public static Card newInstance(Suit color, Rank num) {
		return new Card(num, color);
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
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit != other.suit)
			return false;
		if (rank != other.rank)
			return false;
		return true;
	}

	@Override
	public int compareTo(Card c) {
		int i = this.rank.compareTo(c.rank);
		if (i == 0)
			i = this.suit.compareTo(c.suit);
		return i;
	}

	public static Card getCard(int k1, Set<Card> deck) {
		int c = X.randomIndex(Suit.values().length);
		return getCard(k1, Suit.values()[c], deck);
	}

	public static Card getCard(int k1, Suit color, Set<Card> deck) {
		Card c = Card.newInstance(k1, color);
		if (deck.contains(c)) {
			deck.remove(c);
			return c;
		} else
			throw new RuntimeException("no such card in deck!");
	}

	public static Card getCard(int k1, Set<Suit> possibleColors,
			Set<Card> deck) {
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
				.orElse(null);
	}

	public String shortString() {
		return rank.shortString() + suit.shortString();
	}
}
