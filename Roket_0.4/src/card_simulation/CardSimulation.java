package card_simulation;

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
	private static final PreflopProbabilities p = new PreflopProbabilities();
	List<Double> activeContributors;
	private NaiveSearch suche;

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

	private double preflopFaktor(int countPlayers) {
		double res = 2. - (double) countPlayers / 10;

		return res;
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
				PossiblePreflopHand hand = p.get(numPlayers,
						activeContributors.get(player - 1));
				System.out.println((player - 1) + ": " + hand);
			}
		}

		for (int i = 0; i < numExperiments; i++) {
			won += experiment(flopHands);
		}
		double res = ((double) Math
				.round(((double) won) / numExperiments * 100)) / 100;

		l = System.currentTimeMillis() - l;
		System.out.println("card simulations took " + l + " millis");

		return res;
	}

	private Deck prepairDeck() {
		Deck deck = Deck.freshDeck(rand);
		deck.remove(hand.get(0));
		deck.remove(hand.get(1));
		community.forEach(card -> deck.remove(card));
		return deck;

	}

	public int experiment(FlopHands flopHands) {
		Deck deck = prepairDeck();

		List<Card> community_cards = new ArrayList<>();
		community_cards.addAll(community);
		while (community_cards.size() < 5) {
			community_cards.add(deck.pop());
		}

		ResultImpl myResult = new Cat_Rec(hand, community_cards).check();

		// System.out.println(myResult);

		for (int player = 1; player < numPlayers; player++) {
			List<Card> hisHand;
			if (community.size() == 0) {
				hisHand = p.get(
						numPlayers,
						activeContributors.get(player - 1)
						).getHand();
				// System.out.println(hisHand);
			} else if (community.size() == 3) {

				hisHand = flopHands.getHand(
						2 * activeContributors.get(player - 1)
						);
			} else {
				Random r = new Random();

				double randNumber = (r.nextGaussian() * .06)
						+ activeContributors.get(player - 1) * 3;

				if (randNumber > 1) {
					randNumber = 1 - Math.abs(randNumber) % 1;
				} else {
					randNumber = Math.abs(randNumber) % 1;
				}

				int randIndex = (int) Math.floor(randNumber
						* suche.size());

				hisHand = suche.getPossibleHand(randIndex).getHand();

				// if (player == 1)
				// System.out.println(activeContributors.get(player - 1) * 3
				// + " "
				// + randNumber);

				// hisHand = hands.get(player - 1).get(randIndex).getHand();
			}

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
