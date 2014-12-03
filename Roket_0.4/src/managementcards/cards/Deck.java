package managementcards.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

/*
 * 
 * For each game a fresh Deck should be initialized. Dont reuse
 */
public final class Deck {
  private List<@NonNull Card> cards;

  private Deck(final Random rand) {
		cards = new ArrayList<>();
		for (Rank n : Rank.VALUES) {
			for (Suit c : Suit.VALUES) {
				cards.add(new Card(n, c));
			}
		}
		Collections.shuffle(cards, rand);
	}

	public static Deck freshDeck() {
		return new Deck(new Random());
	}

  public static Deck freshDeck(final Random r) {
		return new Deck(r);
	}

	public Card pop() {
		assert cards.size() > 0;
		return cards.remove(cards.size() - 1);
	}

	public int size() {
		return cards.size();
	}

  public boolean contains(final Card card) {
		return cards.contains(card);
	}

  public boolean remove(final Card card) {
		return cards.remove(card);
	}

	@SuppressWarnings("null")
	@Override
	public String toString() {
		return cards.toString();
	}

	@SuppressWarnings("null")
	public List<@NonNull Card> toList() {
		return Collections.unmodifiableList(cards);
	}

}
