package card_simulation;

import java.util.Comparator;
import java.util.List;

import managementcards.cards.Card;
import tools.Tools;

public class PossiblePreflopHand {
	private final Card first;
	private final Card second;
	private List<Double> score;

  public PossiblePreflopHand(final Card first, final Card second, final List<Double> result) {
		assert result.size() == 9;
		this.first = first;
		this.second = second;
		this.score = result;
	}

  public final Double getScore(final int countPlayers) {
		return score.get(countPlayers - 2);
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

	public static final Comparator<PossiblePreflopHand> getComparator(
      final int countPlayers) {
		return (hand1, hand2) -> hand1.getScore(countPlayers)
				.compareTo(hand2.getScore(countPlayers));
	}

}
