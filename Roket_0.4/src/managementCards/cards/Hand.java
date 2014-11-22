package managementCards.cards;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public final class Hand {
	private Card first;
	private Card second;

	public Hand(Card c1, Card c2) {
		this.first = c1;
		this.second = c2;
	}

	public Card getFirst() {
		return first;
	}

	public Card getSecond() {
		return second;
	}

	public void setCards(Card first, Card second) {
		this.first = first;
		this.second = second;
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
		result = prime * result + (first.hashCode());
		result = prime * result + (second.hashCode());
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
		Hand other = (Hand) obj;
		if (!first.equals(other.first))
			return false;
		if (!second.equals(other.second))
			return false;
		return true;
	}

	@SuppressWarnings("null")
	public List<Card> getCards() {
		return Arrays.asList(first, second);
	}

}
