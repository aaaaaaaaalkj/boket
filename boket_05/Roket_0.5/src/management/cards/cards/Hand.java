package management.cards.cards;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;

public final class Hand {
	private Card first;
	private Card second;

	public Hand(final Card c1, final Card c2) {
		this.first = c1;
		this.second = c2;
	}

	public Card getFirst() {
		return first;
	}

	public Card getSecond() {
		return second;
	}

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
	public boolean equals(@Nullable final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Hand other = (Hand) obj;
		if (!first.equals(other.first)) {
			return false;
		}
		if (!second.equals(other.second)) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("null")
	public List<Card> getCards() {
		return Arrays.asList(first, second);
	}

	public boolean isPocketPair() {
		return first.getRank() == second.getRank();
	}

	public boolean isSuited() {
		return first.getSuit() == second.getSuit();
	}

	public boolean isConnector() {
		return Math.abs(first.getRank().ordinal() - second.getRank().ordinal()) == 1;
	}


}
