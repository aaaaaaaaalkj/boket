package managementCards.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * 
 * For each game a fresh Deck should be initialized. Dont reuse
 */
public class Deck {
	List<Card> cards;

	private Deck(Random rand) {
		cards = new ArrayList<>();
		for (Rank n : Rank.values()) {
			for (Suit c : Suit.values()) {
				cards.add(new Card(n, c));
			}
		}
		Collections.shuffle(cards, rand);
	}

	public static Deck freshDeck() {
		return new Deck(new Random());
	}

	public static Deck freshDeck(Random r) {
		return new Deck(r);
	}

	public Card pop() {
		assert cards.size() > 0;
		return cards.remove(cards.size() - 1);
	}

	public int size() {
		return cards.size();
	}

	public boolean contains(Card card) {
		return cards.contains(card);
	}

	public boolean remove(Card card) {
		return cards.remove(card);
	}

	@Override
	public String toString() {
		return cards.toString();
	}

}
