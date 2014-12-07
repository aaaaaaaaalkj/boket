package ranges.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import managementcards.cards.Card;
import ranges.SimpleRange;

public class Experiment {
  private static final int COUNT_COMMUNITY_CARDS = 5;
  private final List<Card> all;
  private final List<Card> communityCards;
  private final int countBaseCards;
  private final Random rnd;
  private final SimpleRange baseRange;
  private SimpleRange range;
  private final int countPlayers;

  public Experiment(
      final SimpleRange range,
      final List<Card> communityBase,
      final int countPlayers,
      final Random rnd) {
    this.countPlayers = countPlayers;
    this.countBaseCards = communityBase.size();
    this.all = new ArrayList<>(Card.getAllCards());
    this.all.remove(communityBase);
    this.communityCards = new ArrayList<>(communityBase);
    this.rnd = rnd;
    this.baseRange = range.clone();
    this.baseRange.removeAssociated(communityBase);
    this.range = baseRange;
  }

  public final void next() {
    while (communityCards.size() < COUNT_COMMUNITY_CARDS) {
      int index = rnd.nextInt(all.size());
      Card c = all.remove(index);
      communityCards.add(c);
      range.removeAssociated(c);
    }

    for (int player = 0; player < countPlayers; player++) {
      // List<Card> hand = range.getRandom(rnd);
    }

  }

  public final void reverse() {
    List<Card> newCards = communityCards
        .subList(countBaseCards, COUNT_COMMUNITY_CARDS);
    all.addAll(newCards);
    newCards.clear();
    this.range = baseRange;
  }

}
