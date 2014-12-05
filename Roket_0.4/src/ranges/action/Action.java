package ranges.action;

import java.util.List;
import java.util.Random;

import managementcards.cards.Card;
import ranges.SimpleRange;
import tools.XSRandom;

final class Action {

  private Action() {

  }

  public static void start(
      final List<Card> hand,
      final List<Card> community,
      final int countPlayers) {

    Random rnd = new XSRandom();

    Experiment exp = new Experiment(
        SimpleRange.full(),
        community,
        countPlayers,
        rnd);

    exp.next();

  }

  public static void main(final String[] args) {

  }

}
