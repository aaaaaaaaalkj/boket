package card_simulation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import managementCards.cards.Card;

public class PossiblePreflopHand {
	private final Card first;
	private final Card second;
	private List<Double> score;

	public PossiblePreflopHand(Card first, Card second, List<Double> result) {
		this.first = first;
		this.second = second;
		this.score = result;
	}

	public Double getScore(int countPlayers) {
		return score.get(countPlayers - 2);
	}

	public List<Card> getHand() {
		return Arrays.asList(first, second);
	}

	@Override
	public String toString() {
		String res = "[" + first.shortString() + "," + second.shortString()
				+ "]";
		return res;
	}

	public static final Comparator<PossiblePreflopHand> getComparator(
			int countPlayers) {
		return (hand1, hand2) -> hand1.getScore(countPlayers)
				.compareTo(hand2.getScore(countPlayers));
	}

}
