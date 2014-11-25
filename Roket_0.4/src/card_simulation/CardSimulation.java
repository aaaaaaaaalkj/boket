package card_simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import managementCards.cards.Card;
import managementCards.cards.Deck;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.ResultImpl;
import old.Hand;

import org.slf4j.LoggerFactory;

import tools.Tools;
import card_simulation.flopSimulation.CommunityCardsX;
import card_simulation.flopSimulation.Generator;
import card_simulation.flopSimulation.LateRoundHand;

public class CardSimulation {
	@SuppressWarnings("null")
	final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(CardSimulation.class);
	private final List<Card> hand;
	private final List<Card> community;
	private final int numPlayers;
	private final Random rand = new Random();
	// private final List<List<PossibleHand>> hands;
	private static final HandGenerator preflop = new PreflopProbabilities();
	List<Double> activeContributors;

	public CardSimulation(int count, List<Double> activeContributors,
			Hand hand,
			List<Card> community) {
		this.activeContributors = activeContributors;
		this.numPlayers = count;
		this.hand = Tools.asList(hand.first, hand.second);
		this.community = new ArrayList<>();
		this.community.addAll(community);
		// this.hands = new ArrayList<>();

	}

	public double run() {
		int numExperiments = 6000;
		int won = 0;

		double faktor;

		HandGenerator handGen;
		if (community.size() == 0) {
			faktor = 1;
			handGen = preflop;
		} else {
			faktor = 2;
			List<LateRoundHand> hands = null;

			CommunityCardsX community1 = new CommunityCardsX(community);
			hands = LateRoundHand.createAll(community1);
			LateRoundHand.simulation(numPlayers, hands, community1, 30000);
			handGen = new Generator(hands);
		}

		debug(handGen, faktor);

		for (int i = 0; i < numExperiments; i++) {
			won += experiment(handGen, faktor, prepairDeck());
		}
		double res = round(((double) won) / numExperiments);

		return res;
	}

	private void debug(HandGenerator handGen, double faktor) {
		for (int player = 1; player < numPlayers; player++) {
			List<Card> cards = handGen.getHand(numPlayers,
					activeContributors.get(player - 1) * faktor);
			logger.debug("{}: {{} {}]", player, cards.get(0), cards.get(1));

		}

	}

	private double round(double d) {
		return ((double) Math.round(d * 100) / 100);
	}

	private List<Card> prepairDeck() {
		Deck deck = Deck.freshDeck(rand);
		deck.remove(hand.get(0));
		deck.remove(hand.get(1));
		community.forEach(card -> deck.remove(card));
		return deck.toList();
	}

	private List<Card> pop(List<Card> deck, int count) {
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

	public int experiment(HandGenerator handGen, double faktor, List<Card> deck) {

		List<Card> community_cards;

		if (community.size() >= 5) {
			community_cards = community;
		} else {
			community_cards = new ArrayList<>();
			community_cards.addAll(community);

			community_cards.addAll(pop(deck, 5 - community.size()));
		}

		ResultImpl myResult = new Cat_Rec(hand, community_cards).check();

		// logger.info(myResult);

		for (int player = 1; player < numPlayers; player++) {
			List<Card> hisHand;

			hisHand = handGen.getHand(numPlayers,
					activeContributors.get(player - 1) * faktor);

			// logger.info(hisHand);

			ResultImpl result = new Cat_Rec(hisHand, community_cards).check();
			if (result.compareTo(myResult) > 0) {
				// logger.info("-----" + result);
				return 0;
			} else {
				// logger.info(myResult + " ------------ " + result);
			}
		}
		return 1;
	}
}
