package managementCards.cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class Hand {
	private Optional<Card> first;
	private Optional<Card> second;

	private Hand(Card c1, Card c2) {
		this.first = Optional.ofNullable(c1);
		this.second = Optional.ofNullable(c2);
	}

	public static Hand empty() {
		return new Hand(null, null);
	}

	public Optional<Card> getFirst() {
		return first;
	}

	public Optional<Card> getSecond() {
		return second;
	}

	public void setCards(Card first, Card second) {
		this.first = Optional.of(first);
		this.second = Optional.of(second);
	}

	// public ConnectorType getConnectorType() {
	// if (first.isPresent() && second.isPresent()) {
	// Rank rank1 = first.get().getRank();
	// Rank rank2 = second.get().getRank();
	//
	// return ConnectorType.fromInt(rank1.ordinal() - rank2.ordinal());
	// } else {
	// return ConnectorType.NONE;
	// }
	// }
	//
	// public SuitedType getSuit() {
	// if (first.isPresent() && second.isPresent()) {
	// Suit suit1 = first.get().getSuit();
	// Suit suit2 = second.get().getSuit();
	// return suit1 == suit2 ? SuitedType.SUITED : SuitedType.OFF_SUIT;
	// }
	// return SuitedType.NONE;
	// }

	public String toString() {
		return first + " " + second;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
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
		Hand other = (Hand) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	public List<Card> getCards() {
		if (first.isPresent() && second.isPresent()) {
			return Arrays.asList(first.get(), second.get());
		} else {
			return Collections.emptyList();
		}
	}

}
