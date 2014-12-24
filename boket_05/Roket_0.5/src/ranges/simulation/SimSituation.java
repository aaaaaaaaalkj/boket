package ranges.simulation;

import java.util.List;

import management.cards.cards.Card;
import ranges.atry.ScoreInterval;

public interface SimSituation {

  List<ScoreInterval> getScores();

  List<Card> getCommunityCards();

  List<Card> getHand();

}
