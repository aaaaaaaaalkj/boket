package card_simulation;

import java.util.Arrays;
import java.util.List;

import managementCards.cards.Card;

public class PossibleHand {
	private final Card first;
	private final Card second;
	private Double score;

	public PossibleHand(Card first, Card second, Double result) {
		this.first = first;
		this.second = second;
		this.score = result;
	}

	public Double getScore() {
		return score;
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

}
