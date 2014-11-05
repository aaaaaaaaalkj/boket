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

public class CardSimulation {
	private final List<Card> hand;
	private final List<Card> community;
	private final int numPlayers;
	private final Random rand = new Random();
	private final List<Double> activeContributors;

	public CardSimulation(List<Double> activeContributors, Hand hand,
			List<Card> community) {
		this.activeContributors = activeContributors;
		this.numPlayers = activeContributors.size();
		this.hand = Arrays.asList(hand.first, hand.second);
		this.community = new ArrayList<>();
		this.community.addAll(community);
	}

	public CardSimulation(int count, Hand hand,
			List<Card> community) {
		this.activeContributors = new ArrayList<>();
		while (activeContributors.size() < count) {
			activeContributors.add(0.5);
		}
		this.numPlayers = activeContributors.size();
		this.hand = Arrays.asList(hand.first, hand.second);
		this.community = new ArrayList<>();
		this.community.addAll(community);
	}

	public double run() {
		int numExperiments = 6000;
		int sum = 0;

		for (int i = 0; i < numExperiments; i++) {
			sum += experiment();
		}
		double res = ((double) Math
				.round(((double) sum) / numExperiments * 100)) / 100;
		return res;
	}

	private Deck prepairDeck() {
		Deck deck = Deck.freshDeck(rand);
		deck.remove(hand.get(0));
		deck.remove(hand.get(1));
		community.forEach(card -> deck.remove(card));
		return deck;

	}

	public int experiment() {
		Deck deck = prepairDeck();

		List<Card> community_cards = new ArrayList<>();
		community_cards.addAll(community);
		while (community_cards.size() < 5) {
			community_cards.add(deck.pop());
		}

		ResultImpl myResult = new Cat_Rec(hand, community_cards).check();

		// System.out.println(myResult);

		for (int player = 1; player < numPlayers; player++) {
			double contribution = this.activeContributors.get(player);
			List<Card> hisHand = Arrays.asList(deck.pop(), deck.pop());

			ResultImpl result = new Cat_Rec(hisHand, community_cards).check();
			if (result.compareTo(myResult) == 1) {
				// System.out.println("-----" + result);
				return 0;
			}
		}
		return 1;
	}
}
