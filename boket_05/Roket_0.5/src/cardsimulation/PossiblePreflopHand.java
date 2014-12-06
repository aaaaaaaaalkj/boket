package cardsimulation;

import java.util.Comparator;
import java.util.List;

import management.cards.cards.Card;
import tools.Tools;

public class PossiblePreflopHand {
  // there are probabilities for 2 to 10 players in the file. hence there are 9 values
  private static final int COUNT_PLAYER_COUNTS = 9;
  // the first value is for two players and is located at position 0. therefore we need to substract
  // an offset of 2
  private static final int OFFSET = 2;
  private final Card first;
  private final Card second;
  private List<Double> score;

  public PossiblePreflopHand(final Card first, final Card second, final List<Double> result) {
    assert result.size() == COUNT_PLAYER_COUNTS;
    this.first = first;
    this.second = second;
    this.score = result;
  }

  public final Double getScore(final int countPlayers) {
    return score.get(countPlayers - OFFSET);
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
