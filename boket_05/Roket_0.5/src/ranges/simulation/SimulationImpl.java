package ranges.simulation;

import io.screen.RawSituation;

import java.util.List;

import management.cards.cards.Card;

import org.eclipse.jdt.annotation.NonNull;

import ranges.atry.ScoreInterval;

public class SimulationImpl implements Simulation {

  @Override
  public @NonNull SimSituation prepareSituation(@NonNull RawSituation raw) {
    return new SimSituation() {
      @Override
      public List<ScoreInterval> getScores() {
        return null; // TODO
      }

      @Override
      public List<Card> getHand() {
        return raw.getHand();
      }

      @Override
      public List<Card> getCommunityCards() {
        return raw.getCommunityCards();
      }
    };
  }

  @Override
  public double simulate(@NonNull SimSituation situation) {
    return 0;
  }
}
