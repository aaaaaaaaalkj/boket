package ranges.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import ranges.ConsistencyChecker;
import ranges.ElementRange;
import ranges.FlexRange;

public class CardsManager {
  private static final int COUNT_COMMUNITY_CARDS = 5;
  private final int countBaseCards;
  private final List<Card> deck;
  private final List<Card> communityCards;
  private final List<Card> sevenCards;
  private final List<ElementRange> hands;

  public CardsManager(List<Card> communityBase, int countPlayers) {
    this.deck = new ArrayList<>(Card.getAllCards());
    this.deck.removeAll(communityBase);
    this.countBaseCards = communityBase.size();
    this.sevenCards = new ArrayList<>();
    this.hands = new ArrayList<>();
    this.communityCards = new ArrayList<>(communityBase);

    init(countPlayers);

  }

  /**
   * prefill with junk for speed-optimization.
   * 
   * We can use set(index,value) instead of add()... clear()
   * 
   * @param countPlayers
   */
  private void init(int countPlayers) {
    for (int i = 0; i < countPlayers; i++) {
      this.hands.add(ElementRange._2c_2d);
    }

    for (int i = 0; i < 7; i++) {
      this.sevenCards.add(Card.C2);
    }

    while (communityCards.size() < COUNT_COMMUNITY_CARDS) {
      communityCards.add(Card.C2);
    }
  }

  /**
   * get a list with 7 cards for player. The list is shared for speed optimization. And the list
   * will be modified (sorted) by the client. So we cant assume that the first cards remain
   * community cards. We need to set them each time this method is called.
   */
  List<Card> sevenCards(int player) {
    sevenCards.set(0, communityCards.get(0));
    sevenCards.set(1, communityCards.get(1));
    sevenCards.set(2, communityCards.get(2));
    sevenCards.set(3, communityCards.get(3));
    sevenCards.set(4, communityCards.get(4));

    List<Card> hand = hands.get(player).getCards();
    sevenCards.set(5, hand.get(0));
    sevenCards.set(6, hand.get(1));
    return sevenCards;
  }

  /**
   * Put the randomly drawn cards back into the deck after an experiment is over. So we can start a
   * new experiment by draw new random community cards. The communityBase-cards are fixed. So only
   * the last cards are resettet.
   */
  void reverse() {
    for (int i = countBaseCards; i < COUNT_COMMUNITY_CARDS; i++) {
      deck.add(communityCards.get(i));
    }
  }

  /**
   * Fill the community-cards with random cards from deck. Let the communityBase-cards fixed. They
   * never change.
   * 
   * @param rnd
   */
  void drawCommunityCards(Random rnd, FlexRange range) {
    for (int i = countBaseCards; i < COUNT_COMMUNITY_CARDS; i++) {
      int index = rnd.nextInt(deck.size());
      Card c = deck.remove(index);
      range.blockCards(c);
      communityCards.set(i, c);
    }
  }

  /**
   * Tests wheter the hand is consistent with communityCards and with hands which already are
   * randomly selected.
   * 
   * @param hand
   *          - Hand to be tested for consistency
   * @param player
   *          - player index of the player for which the hand is generated
   * @return - true iff consistent
   */
  @SuppressWarnings("null")
  public boolean isConsistent(ElementRange hand, int player) {
    List<ElementRange> handsSoFar = hands.subList(0, player);
    return ConsistencyChecker.isConsistent(hand, handsSoFar, communityCards);
  }

  /**
   * Tests wheter all hands and community cards are consistent.
   * 
   * @return
   */
  public boolean areConsistent() {
    return ConsistencyChecker.areConsistent(hands, communityCards);
  }

  public ElementRange get(int player) {
    return hands.get(player);
  }

  public void set(int player, ElementRange hand) {
    this.hands.set(player, hand);
  }
}
