package card_simulation;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import managementCards.cards.Card;
import managementCards.cards.Deck;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.ResultImpl;
import old.Hand;
import card_simulation.flopSimulation.Flop;
import card_simulation.flopSimulation.FlopHand;
import card_simulation.flopSimulation.FlopHands;

public class CardSimulation {
	private final List<Card> hand;
	private final List<Card> community;
	private final int numPlayers;
	private final Random rand = new Random();
	// private final List<List<PossibleHand>> hands;
	private static final HandGenerator p = new PreflopProbabilities();
	List<Double> activeContributors;
	private HandGenerator suche;

	public CardSimulation(int count, List<Double> activeContributors,
			Hand hand,
			List<Card> community) {
		this.activeContributors = activeContributors;
		this.numPlayers = count;
		this.hand = Arrays.asList(hand.first, hand.second);
		this.community = new ArrayList<>();
		this.community.addAll(community);
		// this.hands = new ArrayList<>();

		if (community.size() >= 4) {
			long l = System.currentTimeMillis();
			//
			suche = NaiveSearch.getInstance(this.hand,
					this.community);

			l = System.currentTimeMillis() - l;

			System.out.println("NativeSearch took " + l + " millis");

			for (double d : activeContributors) {

				double min = Math.max(0, d - .05);
				double max = Math.min(1, d + .8);

				// List<PossibleHand> list = suche.getPossibleHands(min, max,
				// count - 1);
				// this.hands.add(list);

				// System.out.println(((double) Math.round(d * 100) / 100)
				// + " -> "
				// + list.get(0) + " "
				// + list.get(list.size() / 2)
				// + " " + list.get(list.size() - 1));
			}
		}
	}

	public double run() {
		int numExperiments = 6000;
		int won = 0;

		long l = System.currentTimeMillis();

		List<FlopHand> hands = null;
		FlopHands flopHands = null;
		Flop flop = new Flop(community);
		if (community.size() == 3) {

			hands = FlopHand.createAll(flop);
			FlopHand.simulation(numPlayers, hands, flop, 30000);

			flopHands = new FlopHands(hands);

			for (int player = 1; player < numPlayers; player++) {
				List<Card> cards = flopHands.getHand(
						activeContributors.get(player - 1) * 2);

				System.out.println(player + ": [" + cards.get(0) + " "
						+ cards.get(1) + "]");

			}

			System.out
					.println("contribution: " + activeContributors.get(0) * 2);

			// String s = hands.stream().map(Object::toString)
			// .collect(joining("\n"));
			// System.out.println(s);
		}

		if (community.size() == 0) {
			for (int player = 1; player < numPlayers; player++) {
				List<Card> hand = p.getHand(numPlayers,
						activeContributors.get(player - 1).doubleValue());
				System.out.println((player - 1) + ": " + hand);
			}
		}
		double faktor = 1;

		HandGenerator handGen = null;
		if (community.size() == 0) {
			faktor = 1;
			handGen = p;
		} else if (community.size() == 3) {
			faktor = 2;
			handGen = flopHands;
		} else if (community.size() >= 4) {
			faktor = 3;
			handGen = suche;
		}
		for (int i = 0; i < numExperiments; i++) {
			won += experiment(handGen, faktor, prepairDeck());
		}
		double res = round(((double) won) / numExperiments);

		l = System.currentTimeMillis() - l;
		System.out.println("card simulations took " + l + " millis");

		return res;
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
		return indexe.stream().map(deck::get).collect(toList());
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

		// System.out.println(myResult);

		for (int player = 1; player < numPlayers; player++) {
			List<Card> hisHand;

			hisHand = handGen.getHand(numPlayers,
					activeContributors.get(player - 1) * faktor);

			ResultImpl result = new Cat_Rec(hisHand, community_cards).check();
			if (result.compareTo(myResult) == 1) {
				// System.out.println("-----" + result);
				return 0;
			} else {
				// System.out.println(myResult + " ------------ " + result);
			}
		}
		return 1;
	}
}
