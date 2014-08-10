package old;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;

public class Flop implements java.lang.Iterable<Card> {
	public final Card first;
	public final Card second;
	public final Card third;

	private Flop(Card first, Card second, Card third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public static Flop newInstance(Card c1, Card c2, Card c3) {
		if (null == c1 || null == c2 || null == c3)
			throw new IllegalArgumentException(c1 + "_ " + c2 + "_ " + c3
					+ "_ ");
		return new Flop(c1, c2, c3);
	}

	public static Flop threeSuite(int f, int s, int t, Set<Card> deck) {
		return threeSuite(Rank.values()[f - 2], Rank.values()[s - 2],
				Rank.values()[t - 2], deck);
	}



	// for example: s == "[3c 9h 6c]"
	public static Flop newInstance(String s) {
		int mid1 = s.indexOf(" ");
		int mid2 = s.lastIndexOf(" ");
		Card c1 = Card.newInstance(s.substring(s.indexOf("[") + 1, mid1));
		Card c2 = Card.newInstance(s.substring(mid1 + 1, mid2));
		Card c3 = Card.newInstance(s.substring(mid2 + 1, s.indexOf("]")));
		return new Flop(c1, c2, c3);
	}

	public static Flop threeSuite(Rank f, Rank s, Rank t,
			Set<Card> deck) {
		for (Suit c : Suit.values()) {
			Card c1, c2, c3;
			c1 = Card.newInstance(f, c);
			c2 = Card.newInstance(s, c);
			c3 = Card.newInstance(t, c);
			if (!deck.contains(c1))
				continue;
			if (!deck.contains(c2))
				continue;
			if (!deck.contains(c2))
				continue;
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

	private class FlopIterator implements Iterator<Card> {
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
