package ranges.action;

import java.util.List;
import java.util.Random;

import management.cards.cards.Card;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ranges.SimpleRange;
import tools.Tools;
import tools.XSRandom;

final class Action {
  @SuppressWarnings("null")
  static final Logger LOG = LoggerFactory.getLogger(Action.class);

  private Action() {

  }

  /**
   * @param hand
   */
  public static void start(
      final List<Card> hand,
      final List<Card> community,
      final int countPlayers) {

    LOG.info("{}", community);

    Random rnd = new XSRandom();

    Experiment exp = new Experiment(
        SimpleRange.full(),
        community,
        countPlayers,
        rnd);

    long l = System.nanoTime();
    for (int i = 0; i < 100000; i++) {
      exp.next();
    }

    l = System.nanoTime() - l;

    l = l / 1000000;

    System.out.println(l + " millis elapsed");

    exp.assignScore();

    exp.printStats();

  }

  public static void main(final String[] args) {

    start(Tools.asList(Card.TWO_CLUBS, Card.SEVEN_CLUBS),
        Tools.asList(), 9);

  }
}
