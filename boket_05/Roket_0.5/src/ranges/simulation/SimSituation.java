package ranges.simulation;

import io.screen.CardSituation;

import java.util.List;

import ranges.atry.ScoreInterval;

public interface SimSituation extends CardSituation {

  List<ScoreInterval> getScores();

}
