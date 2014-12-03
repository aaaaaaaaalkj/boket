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
    Random rnd = new XSRandom();
    rndTest(rnd);

  }

  private static void rndTest(final Random rnd) {

    long l = System.nanoTime();

    for (int i = 0; i < 900000000; i++) {
      rnd.nextInt(1000);
    }

    l = System.nanoTime() - l;

    l = l / 1000000;

    System.out.println(l);
  }
}
