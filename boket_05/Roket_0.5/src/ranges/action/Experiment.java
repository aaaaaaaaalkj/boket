package ranges.action;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import management.cards.evaluator.HandEvaluator;
import ranges.ElementRange;
import ranges.SimpleRange;

public final class Experiment {
  private static final int COUNT_COMMUNITY_CARDS = 5;
  private final List<Card> all;
  private final List<Card> communityCards;
  private final int countBaseCards;

  private final Random rnd;
  private final SimpleRange baseRange;
  private SimpleRange range;
  private final int countPlayers;

  private final EnumMap<ElementRange, Stat> stats;
  private final List<ElementRange> hands;
  private final List<Short> scores;
  private final HandEvaluator rec;

  private final List<Card> sevenCards;

  @SuppressWarnings("null")
  public Experiment(
      final SimpleRange range,
      final List<Card> communityBase,
      final int countPlayers,
      final Random rnd) {
    this.stats = new EnumMap<>(ElementRange.class);
    this.sevenCards = new ArrayList<>();
    this.countPlayers = countPlayers;
    this.countBaseCards = communityBase.size();
    this.all = new ArrayList<>(Card.getAllCards());
    this.all.remove(communityBase);
    this.communityCards = new ArrayList<>(communityBase);
    this.rnd = rnd;
    this.baseRange = range.clone();
    this.baseRange.removeAssociated(communityBase);
    this.range = baseRange.clone();
    this.hands = new ArrayList<>();
    this.scores = new ArrayList<>();
    this.rec = HandEvaluator.getInstance();

    for (int i = 0; i < 7; i++) {
      // prefill with junk for speed-optimization
      // we can use set(index,value) instead of add()... clear()
      this.sevenCards.add(Card.C2);
    }
    for (int i = 0; i < countPlayers; i++) {
      // prefill with junk for speed-optimization
      // we can use set(index,value) instead of add()... clear()
      this.hands.add(ElementRange._2c_2d);
      this.scores.add((short) 0);
    }
    for (ElementRange e : baseRange) {
      stats.put(e, new Stat());
    }

  }

  private void fillCommunityCards() {
    while (communityCards.size() < COUNT_COMMUNITY_CARDS) {
      int index = rnd.nextInt(all.size());
      Card c = all.remove(index);
      communityCards.add(c);
      range.removeAssociated(c);
    }
  }

  private void dealHands(SimpleRange workingRange) {
    for (int player = 0; player < countPlayers; player++) {
      ElementRange hand = workingRange.getRandom(rnd);
      hands.set(player, hand);
      workingRange.removeAssociated(hand);
    }
  }

  private List<Card> cards(List<Card> hand, List<Card> community) {
    sevenCards.set(0, hand.get(0));
    sevenCards.set(1, hand.get(1));

    sevenCards.set(2, community.get(0));
    sevenCards.set(3, community.get(1));
    sevenCards.set(4, community.get(2));
    sevenCards.set(5, community.get(3));
    sevenCards.set(6, community.get(4));
    return sevenCards;
  }

  public void next() {
    fillCommunityCards();

    for (int i = 0; i < 100; i++) {
      dealHands(range.clone());

      short maxScore = 0;

      for (int player = 0; player < countPlayers; player++) {
        List<Card> hand = hands.get(player).getCards();

        // cards will be sorted by rec
        short score = rec.getScore(cards(hand, communityCards));
        if (score > maxScore) {
          score = maxScore;
        }
        scores.set(player, score);
      }

      for (int player = 0; player < countPlayers; player++) {
        stats.get(hands.get(player))
            .won(scores.get(player) == maxScore);
      }

    }
    revoke();
  }

  private void revoke() {
    hands.clear();
  }

  public final void reverse() {
    List<Card> newCards = communityCards
        .subList(countBaseCards, COUNT_COMMUNITY_CARDS);
    all.addAll(newCards);
    newCards.clear();
    this.range = baseRange.clone();
  }

}
