package card_simulation.flopSimulation;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import managementcards.cards.Card;
import managementcards.cards.Rank;
import managementcards.cards.Suit;

import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

public class CommunityCardsX implements Comparable<CommunityCardsX> {
  private List<Card> cards;

  public final boolean contains(final Card c) {
		return cards.contains(c);
	}

  public CommunityCardsX(final List<Card> cards) {
		// assert cards.size() == 3;
		this.cards = cards;
	}

  public final void normalize() {
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

  public final void normalize2() {
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
			// if there are diamonds, replace them with one other suit, which is
			// not present in this flop
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

		// replace all suits which equal suit of the first card with diamonds
		Suit suitOfFirst = cards.get(0).getSuit();
		for (int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if (card.getSuit() == suitOfFirst) {
				cards.set(i, new Card(card.getRank(), Suit.diamonds()));
			}
		}
		if (cards.stream().map(Card::getSuit).collect(Tools.toSet()).size() == 1) {
			return; // all diamonds now
		}
		// if only one other suit as diamonds is present, replace it with hearts
		if (cards.get(1).getSuit() == cards.get(2).getSuit()) {
			cards.set(1, new Card(cards.get(1).getRank(), Suit.HEARTS));
			cards.set(2, new Card(cards.get(2).getRank(), Suit.HEARTS));
			return;
		}
		// if the suit of the second card is diamonds, replace the third suit
		// with
		// hearts
		if (cards.get(1).getSuit() == Suit.DIAMONDS) {
			cards.set(2, new Card(cards.get(2).getRank(), Suit.HEARTS));
			return;
		}
		// if the suit of the third card is diamonds then replace the suit of
		// the second card with hearts
		if (cards.get(2).getSuit() == Suit.DIAMONDS) {
			cards.set(1, new Card(cards.get(1).getRank(), Suit.HEARTS));
			return;
		}
		// since the suits of second and third card differ and are not diamonds
		// at this position, we can savely replace them with hearts and spades
		cards.set(1, new Card(cards.get(1).getRank(), Suit.HEARTS));
		cards.set(2, new Card(cards.get(2).getRank(), Suit.SPADES));
	}

	@Override
  public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cards.hashCode());
		return result;
	}

	@Override
  public final boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
		CommunityCardsX other = (CommunityCardsX) obj;
    if (!cards.equals(other.cards)) {
      return false;
    }
		return true;
	}

  public static void main(final String[] args) {
		Set<CommunityCardsX> set = new HashSet<>();

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
					CommunityCardsX f = new CommunityCardsX(cards);
					f.normalize();
					set.add(f);
				}
			}
		}
		List<CommunityCardsX> flops = new ArrayList<>();
		flops.addAll(set);
		Collections.sort(flops);

		System.out.println(flops.stream().map(Object::toString)
				.collect(joining("\n")));

		System.out.println(flops.size());

	}

	@Override
  public final String toString() {
		return "[" + cards.get(0) + "|" + cards.get(1) + "|" + cards.get(2)
				+ "]";
	}

	@Override
  public final int compareTo(final CommunityCardsX o) {
		int i = cards.get(0).compareTo(o.cards.get(0));
		if (i == 0) {
			i = cards.get(1).compareTo(o.cards.get(1));
			if (i == 0) {
				i = cards.get(2).compareTo(o.cards.get(2));
			}
		}
		return i;
	}

  public final List<Card> getCards() {
		return cards;
	}
}
