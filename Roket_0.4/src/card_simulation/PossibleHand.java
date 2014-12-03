package card_simulation;

import java.util.List;

import managementcards.cards.Card;
import tools.Tools;

public class PossibleHand {
	private final Card first;
	private final Card second;
	private Double score;

  public PossibleHand(final Card first, final Card second, final Double result) {
		this.first = first;
		this.second = second;
		this.score = result;
	}

  public final Double getScore() {
		return score;
	}

  public final List<Card> getHand() {
		return Tools.asList(first, second);
	}

	@Override
  public final String toString() {
		String res = "[" + first.shortString() + "," + second.shortString()
				+ "]";
		return res;
	}

}
