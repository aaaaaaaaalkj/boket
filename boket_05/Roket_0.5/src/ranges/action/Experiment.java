package ranges.action;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
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
  private List<ElementRange> hands;

  @SuppressWarnings("null")
  public Experiment(
      final SimpleRange range,
      final List<Card> communityBase,
      final int countPlayers,
      final Random rnd) {
    this.stats = new EnumMap<>(ElementRange.class);
    this.countPlayers = countPlayers;
    this.countBaseCards = communityBase.size();
    this.all = new ArrayList<>(Card.getAllCards());
    this.all.remove(communityBase);
    this.communityCards = new ArrayList<>(communityBase);
    this.rnd = rnd;
    this.baseRange = range.clone();
    this.baseRange.removeAssociated(communityBase);
    this.range = baseRange;
    this.hands = new ArrayList<>();
  }

  private void fillCommunityCards() {
    while (communityCards.size() < COUNT_COMMUNITY_CARDS) {
      int index = rnd.nextInt(all.size());
      Card c = all.remove(index);
      communityCards.add(c);
      range.removeAssociated(c);
    }
  }

  private void dealHands(SimpleRange range) {
    hands.clear();
    for (int player = 0; player < countPlayers; player++) {
      ElementRange hand = range.getRandom(rnd);
      hands.add(hand);
      range.removeAssociated(hand);
      stats.put(hand, new Stat());
    }
  }

  public void next() {
    fillCommunityCards();

    // temporary range to pick hands
    SimpleRange workingRange;

    for (int i = 0; i < 100; i++) {
      workingRange = range.clone();
      dealHands(workingRange);

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
    this.range = baseRange;
  }

}
