package card_simulation.flopSimulation;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;

import org.eclipse.jdt.annotation.Nullable;

public class Flop implements Comparable<Flop> {
	List<Card> cards;

	public boolean contains(Card c) {
		return cards.contains(c);
	}

	public Flop(List<Card> cards) {
		this.cards = cards;
	}

	public void normalize() {
		normalize2();
		cards.sort((a, b) -> {
			if (a.getRank().compareTo(b.getRank()) != 0) {
				return a.getRank().compareTo(b.getRank());
			} else {
				return a.getSuit().compareTo(b.getSuit()) * (-1);
			}
		});
		Collections.reverse(cards);
	}

	public void normalize2() {
		cards.sort((a, b) -> {
			if (a.getRank().compareTo(b.getRank()) != 0) {
				return a.getRank().compareTo(b.getRank());
			} else {
				return a.getSuit().compareTo(b.getSuit());
			}
		});
		Collections.reverse(cards);
		long diamonds = cards.stream()
				.filter(card -> card.getSuit() == Suit.DIAMONDS)
				.count();
		if (diamonds > 0) {
			Suit missingSuit = Suit.VALUES
					.stream()
					.filter(s -> cards.stream().map(Card::getSuit)
							.filter(s::equals).count() == 0)
					.findAny().orElseThrow(IllegalStateException::new);
			for (int i = 0; i < cards.size(); i++) {
				Card card = cards.get(i);
				if (card.getSuit() == Suit.DIAMONDS) {
					cards.set(i, new Card(card.getRank(), missingSuit));
				}
			}
		}
		Suit suitOfFirst = cards.get(0).getSuit();
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if (card.getSuit() == suitOfFirst) {
				cards.set(i, new Card(card.getRank(), Suit.DIAMONDS));
			}
		}
		if (cards.stream().map(Card::getSuit).collect(toSet()).size() == 1) {
			return; // all diamonds now
		}
		if (cards.get(1).getSuit() == cards.get(2).getSuit()) {
			cards.set(1, new Card(cards.get(1).getRank(), Suit.HEARTS));
			cards.set(2, new Card(cards.get(2).getRank(), Suit.HEARTS));
			return;
		}
		if (cards.get(1).getSuit() == Suit.DIAMONDS) {
			cards.set(2, new Card(cards.get(2).getRank(), Suit.HEARTS));
			return;
		}
		if (cards.get(2).getSuit() == Suit.DIAMONDS) {
			cards.set(1, new Card(cards.get(1).getRank(), Suit.HEARTS));
			return;
		}
		cards.set(1, new Card(cards.get(1).getRank(), Suit.HEARTS));
		cards.set(2, new Card(cards.get(2).getRank(), Suit.SPADES));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		return result;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flop other = (Flop) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		return true;
	}

	public static void main(String[] args) {
		Set<Flop> set = new HashSet<>();

		List<Card> list = new ArrayList<>();

		for (Suit s : Suit.VALUES) {
			for (Rank r : Rank.VALUES) {
				list.add(new Card(r, s));
			}
		}
		for (int i = 0; i < list.size(); i++) {
			for (int j = i + 1; j < list.size(); j++) {
				for (int k = j + 1; k < list.size(); k++) {
					List<Card> cards = new ArrayList<>();
					cards.add(list.get(i));
					cards.add(list.get(j));
					cards.add(list.get(k));
					Flop f = new Flop(cards);
					f.normalize();
					set.add(f);
				}
			}
		}
		List<Flop> flops = new ArrayList<>();
		flops.addAll(set);
		Collections.sort(flops);

		System.out.println(flops.stream().map(Object::toString)
				.collect(joining("\n")));

		System.out.println(flops.size());

	}

	@Override
	public String toString() {
		return "[" + cards.get(0) + "|" + cards.get(1) + "|" + cards.get(2)
				+ "]";
	}

	@Override
	public int compareTo(Flop o) {
		int i = cards.get(0).compareTo(o.cards.get(0));
		if (i == 0) {
			i = cards.get(1).compareTo(o.cards.get(1));
			if (i == 0) {
				i = cards.get(2).compareTo(o.cards.get(2));
			}
		}
		return i;
	}

	public List<Card> getCards() {
		return cards;
	}
}
