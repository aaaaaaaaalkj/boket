package ranges.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import management.cards.evaluator.HandEvaluator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ranges.ElementRange;
import ranges.FlexRange;
import ranges.SimpleRange;
import tools.Tools;

public final class Experiment {
  @SuppressWarnings("null")
  static final Logger LOG = LoggerFactory.getLogger(Experiment.class);

  private final CardsManager cards;

  private final Random rnd;
  private final FlexRange baseRange;
  private final int countPlayers;

  private final EnumMap<ElementRange, Stat> stats;

  private final List<Short> scores;
  private final HandEvaluator rec;

  private int numExperiments;
  private int numConsistent;

  @SuppressWarnings("null")
  public Experiment(
      final SimpleRange range,
      final List<Card> communityBase,
      final int countPlayers,
      final Random rnd) {
    this.numExperiments = 0;
    this.numConsistent = 0;
    this.stats = new EnumMap<>(ElementRange.class);

    this.countPlayers = countPlayers;
    this.cards = new CardsManager(communityBase, countPlayers);
    this.rnd = rnd;
    this.baseRange = new FlexRange(range);
    this.baseRange.blockCards(communityBase);
    this.baseRange.setMarker("start");

    this.scores = new ArrayList<>();
    this.rec = HandEvaluator.getInstance();

    for (int i = 0; i < countPlayers; i++) {
      // prefill with junk for speed-optimization
      // we can use set(index,value) instead of add()... clear()
      this.scores.add((short) 0);
    }
    for (ElementRange e : baseRange) {
      stats.put(e, new Stat(e));
    }

  }

  @SuppressWarnings("null")
  private void updateScores() {
    for (int player = 0; player < countPlayers; player++) {
      // cards will be sorted by rec
      short score = rec.getScore(cards.sevenCards(player));
      scores.set(player, score);
    }
  }

  private short computeMaxScore() {
    short maxScore = 0;
    for (int player = 0; player < countPlayers; player++) {
      maxScore = Tools.max(maxScore, scores.get(player));
    }
    return maxScore;
  }

  private void updateStats(int maxScore) {
    for (int player = 0; player < countPlayers; player++) {
      boolean result = scores.get(player) == maxScore;
      ElementRange hand = cards.get(player);
      stats.get(hand).outcome(result);
    }
  }

  private void dealHands() {
    for (int player = 0; player < countPlayers; player++) {
      ElementRange hand = baseRange.getRandom(rnd, 1 /* 100% */);
      cards.set(player, hand);
      // baseRange.blockHand(hand);
    }
  }

  public void next() {
    cards.drawCommunityCards(rnd, baseRange);
    baseRange.setMarker("community cards");

    for (int i = 0; i < 100; i++) {
      dealHands();

      numExperiments++;
      if (cards.areConsistent()) {
        numConsistent++;
      }

      // LOG.info("{}", hands);

      updateScores();

      short maxScore = computeMaxScore();

      updateStats(maxScore);
      baseRange.resetTo("community cards");

    }
    cards.reverse();
    baseRange.resetTo("start");
  }

  public void assignScore() {
    List<Stat> res = new ArrayList<>(stats.values());
    Collections.sort(res);

    double min = res.get(0).success();
    double max = res.get(res.size() - 1).success();

    for (int i = 0; i < res.size(); i++) {
      res.get(i).computeScore(min, max);
    }
  }

  public void printStats() {
    double consistent = Tools.round((double) numConsistent / numExperiments) * 100;
    System.out.println(consistent + " % consistent");

    List<Stat> res = new ArrayList<>(stats.values());

    Collections.sort(res);

    System.out.println(res);

  }

}
