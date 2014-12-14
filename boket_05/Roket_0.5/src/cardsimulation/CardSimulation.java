package cardsimulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import management.cards.cards.Hand;
import management.cards.catrecnew.CatRec;
import management.cards.catrecnew.ResultImpl;

import org.slf4j.LoggerFactory;

import tools.ListWrapper;
import tools.Tools;
import cardsimulation.flopSimulation.CommunityCardsX;
import cardsimulation.flopSimulation.Generator;
import cardsimulation.flopSimulation.LateRoundHand;

public class CardSimulation {
  private static final int NUM_EXPERIMENTS = 6000;
  @SuppressWarnings("null")
  static final org.slf4j.Logger LOG = LoggerFactory
      .getLogger(CardSimulation.class);
  private final List<Card> hand;
  private final List<Card> community;
  private final int numPlayers;
  private final Random rnd = new Random();
  // private final List<List<PossibleHand>> hands;
  private static final HandSupplier PREFLOP = new PreflopProbabilities();
  private List<Double> activeContributors;
  private List<Double> stdDev;

  public CardSimulation(final int count,
      final List<Double> activeContributors,
      final List<Double> stddev,
      final Hand hand,
      final List<Card> community) {
    this.stdDev = stddev;
    this.activeContributors = activeContributors;
    this.numPlayers = count;
    this.hand = Tools.asList(hand.getFirst(), hand.getSecond());
    this.community = new ArrayList<>();
    this.community.addAll(community);
    // this.hands = new ArrayList<>();

  }

  private static final int NUMBER_OF_SIMULATIONS = 30000;

  public final double run() {

    int won = 0;

    double faktor;

    HandSupplier handGen;
    if (community.size() == 0) {
      faktor = 1;
      handGen = PREFLOP;
    } else {
      faktor = 2;
      List<LateRoundHand> hands = null;

      CommunityCardsX community1 = new CommunityCardsX(community);
      hands = LateRoundHand.createAll(community1);
      LateRoundHand.simulation(numPlayers, hands, community1,
          NUMBER_OF_SIMULATIONS);
      handGen = new Generator(hands);
    }

    debug(handGen, faktor);

    for (int i = 0; i < NUM_EXPERIMENTS; i++) {
      won += experiment(handGen, faktor, prepairDeck());
    }
    double res = Tools.round(((double) won) / NUM_EXPERIMENTS);

    return res;
  }

  private void debug(final HandSupplier handGen, final double faktor) {
    for (int player = 1; player < numPlayers; player++) {
      List<Card> cards = handGen.getHand(numPlayers,
          activeContributors.get(player - 1) * faktor,
          stdDev.get(player - 1));
      LOG.debug("{}: {{} {}]", player, cards.get(0), cards.get(1));

    }

  }

  private List<Card> prepairDeck() {
    List<Card> list = new ArrayList<>(Card.getAllCards());
    list.remove(hand.get(0));
    list.remove(hand.get(1));
    list.removeAll(community);
    Collections.shuffle(list, rnd);
    return list;
  }

  private static List<Card> pop(final List<Card> deck, final int count) {
    List<Integer> indexe = new ArrayList<>();

    while (indexe.size() < count) {
      int i = (int) Math.floor(Math.random() * deck.size());
      do {
        i++;
        if (i >= deck.size()) {
          i = 0;
        }
      } while (indexe.contains(i));
      indexe.add(i);
    }
    return indexe.stream().map(deck::get).collect(Tools.toList());
  }

  private static final int MAX_NUMBER_OF_COMMUNITY_CARDS = 5;

  public final int experiment(final HandSupplier handGen,
      final double faktor,
      final List<Card> deck) {

    List<Card> communityCards;

    if (community.size() >= MAX_NUMBER_OF_COMMUNITY_CARDS) {
      communityCards = community;
    } else {
      communityCards = new ArrayList<>();
      communityCards.addAll(community);

      communityCards.addAll(pop(deck, MAX_NUMBER_OF_COMMUNITY_CARDS
          - community.size()));
    }

    ResultImpl myResult = new CatRec().check(new ListWrapper<>(hand,
        communityCards));

    // logger.info(myResult);

    for (int player = 1; player < numPlayers; player++) {
      List<Card> hisHand;

      hisHand = handGen.getHand(numPlayers,
          activeContributors.get(player - 1) * faktor,
          stdDev.get(player - 1)
          );

      // logger.info(hisHand);

      ResultImpl result = new CatRec().check(new ListWrapper<>(hisHand,
          communityCards));
      if (result.compareTo(myResult) > 0) {
        // logger.info("-----" + result);
        return 0;
      }
    }
    return 1;
  }
}
