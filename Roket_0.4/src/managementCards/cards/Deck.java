package managementCards.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
	List<Card> cards;

	public Deck(Random rand) {
		cards = new ArrayList<>();
		for (Rank n : Rank.values()) {
			for (Suit c : Suit.values()) {
				cards.add(new Card(n, c));
			}
		}
		shuffle(rand);
	}

	public void shuffle(Random rand) {
		Collections.shuffle(cards, rand);
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

}
