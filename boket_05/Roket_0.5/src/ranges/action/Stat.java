package ranges.action;

import org.eclipse.jdt.annotation.Nullable;

import ranges.ElementRange;
import tools.Tools;
import tools.collections.HasScore;

public final class Stat implements HasScore {
  ElementRange hand;
  private int played;
  private int won;
  private double score;

  public Stat(ElementRange hand) {
    this.hand = hand;
    this.played = 0;
    this.won = 0;
  }

  public void outcome(final boolean won2) {
    if (won2) {
      won++;
    }
    played++;
  }

  public void computeScore(double min, double max) {
    this.score = Tools.procent((success() - min) / (max - min));
  }

  @Override
  public double getScore() {
    return score;
  }

  public int getPlayed() {
    return played;
  }

  public int getWon() {
    return won;
  }

  public double success() {
    return Tools.round((double) won / played);
  }

  @Override
  public String toString() {
    return hand + " " + score + " %\n";
  }

  @Override
  public int compareTo(@Nullable HasScore o) {
    if (o == null) {
      throw new NullPointerException("parameter is null");
    }
    double s1 = getScore();
    double s2 = o.getScore();
    if (s1 > s2) {
      return 1;
    } else if (s2 > s1) {
      return -1;
    } else {
      return 0;
    }
  }

  public ElementRange getHand() {
    return hand;
  }

}
