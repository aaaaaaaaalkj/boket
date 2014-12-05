package cardsimulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import managementcards.cards.Card;
import managementcards.cards.Deck;
import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.ResultImpl;
import old.Hand;

import org.slf4j.LoggerFactory;

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
  private final Random rand = new Random();
  // private final List<List<PossibleHand>> hands;
  private static final HandGenerator PREFLOP = new PreflopProbabilities();
  private List<Double> activeContributors;
  private List<Double> stdDev;

  public CardSimulation(final int count, final List<Double> activeContributors,
      final List<Double> stddev,
      final Hand hand,
      final List<Card> community) {
    this.stdDev = stddev;
    this.activeContributors = activeContributors;
    this.numPlayers = count;
    this.hand = Tools.asList(hand.first, hand.second);
    this.community = new ArrayList<>();
    this.community.addAll(community);
    // this.hands = new ArrayList<>();

  }

  public final double run() {

    int won = 0;

    double faktor;

    HandGenerator handGen;
    if (community.size() == 0) {
      faktor = 1;
      handGen = PREFLOP;
    } else {
      faktor = 2;
      List<LateRoundHand> hands = null;

      CommunityCardsX community1 = new CommunityCardsX(community);
      hands = LateRoundHand.createAll(community1);
      LateRoundHand.simulation(numPlayers, hands, community1, 30000);
      handGen = new Generator(hands);
    }

    debug(handGen, faktor);

    for (int i = 0; i < NUM_EXPERIMENTS; i++) {
      won += experiment(handGen, faktor, prepairDeck());
    }
    double res = Tools.round(((double) won) / NUM_EXPERIMENTS);

    return res;
  }

  private void debug(final HandGenerator handGen, final double faktor) {
    for (int player = 1; player < numPlayers; player++) {
      List<Card> cards = handGen.getHand(numPlayers,
          activeContributors.get(player - 1) * faktor,
          stdDev.get(player - 1));
      LOG.debug("{}: {{} {}]", player, cards.get(0), cards.get(1));

    }

  }

  private List<Card> prepairDeck() {
    Deck deck = Deck.freshDeck(rand);
    deck.remove(hand.get(0));
    deck.remove(hand.get(1));
    community.forEach(card -> deck.remove(card));
    return deck.toList();
  }

  private List<Card> pop(final List<Card> deck, final int count) {
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

  public final int experiment(final HandGenerator handGen, final double faktor,
      final List<Card> deck) {

    List<Card> communityCards;

    if (community.size() >= 5) {
      communityCards = community;
    } else {
      communityCards = new ArrayList<>();
      communityCards.addAll(community);

      communityCards.addAll(pop(deck, 5 - community.size()));
    }

    ResultImpl myResult = new CatRec(hand, communityCards).check();

    // logger.info(myResult);

    for (int player = 1; player < numPlayers; player++) {
      List<Card> hisHand;

      hisHand = handGen.getHand(numPlayers,
          activeContributors.get(player - 1) * faktor,
          stdDev.get(player - 1)
          );

      // logger.info(hisHand);

      ResultImpl result = new CatRec(hisHand, communityCards).check();
      if (result.compareTo(myResult) > 0) {
        // logger.info("-----" + result);
        return 0;
      }
    }
    return 1;
  }
}
