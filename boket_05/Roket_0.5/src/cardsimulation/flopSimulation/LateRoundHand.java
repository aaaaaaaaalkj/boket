package cardsimulation.flopSimulation;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import management.cards.cards.Card;
import management.cards.cards.Rank;
import management.cards.cards.Suit;
import management.cards.catrecnew.CatRec;
import management.cards.catrecnew.ResultImpl;

import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

public class LateRoundHand {
	private List<Card> cards;

	private int won = 0;
	private int played = 0;

	public LateRoundHand(final Card c1, final Card c2) {
		cards = new ArrayList<>();
		cards.add(c1);
		cards.add(c2);
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (cards.hashCode());
		return result;
	}

	@Override
	public final boolean equals(@Nullable final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LateRoundHand other = (LateRoundHand) obj;
		if (!cards.equals(other.cards)) {
			return false;
		}
		return true;
	}

	public static List<Card> createAllCards() {
		List<Card> cards = new ArrayList<Card>();
		for (Suit suit : Suit.VALUES) {
			for (Rank rank : Rank.VALUES) {
				Card c = Card.newInstance(rank, suit);
				cards.add(c);
			}
		}
		return cards;
	}

	public static List<LateRoundHand> createAll(final CommunityCardsX community) {
		List<Card> cards = createAllCards();
		cards.removeAll(community.getCards());

		List<LateRoundHand> hands = new ArrayList<LateRoundHand>();

		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				hands.add(new LateRoundHand(cards.get(i), cards.get(j)));
			}
		}
		return hands;

	}

	public static void simulation(final int numPlayers,
			final List<LateRoundHand> hands,
			final CommunityCardsX communityCards, final int countIter) {

		List<Card> community = new ArrayList<Card>();
		community.addAll(communityCards.getCards());

		for (int run = 0; run < countIter; run++) {
			Set<Card> allCards = new HashSet<Card>();
			allCards.addAll(createAllCards());
			allCards.removeAll(communityCards.getCards());

			List<LateRoundHand> players = new ArrayList<LateRoundHand>();

			while (players.size() < numPlayers) {
				int index = (int) (Math.random() * hands.size());

				LateRoundHand hand = hands.get(index);

				if (allCards.contains(hand.getFirst())
						&& allCards.contains(hand.getSecond())) {
					allCards.remove(hand.getFirst());
					allCards.remove(hand.getSecond());
					players.add(hand);
				}
			}

			List<Card> allCards2 = new ArrayList<Card>();
			allCards2.addAll(allCards);

			int index1 = (int) (Math.random() * allCards2.size());
			int index2 = (int) (Math.random() * allCards2.size());
			if (index1 == index2) {
				continue; // TODO
			}

			Card card1 = allCards2.get(index1);
			Card card2 = allCards2.get(index2);

			final int numOfFlopCards = 3;

			community.subList(numOfFlopCards, community.size()).clear();
			community.add(card1);
			community.add(card2);

			Map<LateRoundHand, ResultImpl> results = new HashMap<>();

			for (int i = 0; i < players.size(); i++) {
				ResultImpl r = new CatRec(players.get(i).getCards(), community)
						.check();
				results.put(players.get(i), r);
			}

			ResultImpl best = results.values().stream()
					.reduce(ResultImpl.bottom(), (a, b) -> {
						if (a.compareTo(b) > 0) {
							return a;
						} else {
							return b;
						}
					});

			results.entrySet().stream()
					.filter(entry -> entry.getValue().compareTo(best) >= 0)
					.forEach(entry -> entry.getKey().won());

			results.entrySet().stream()
					.filter(entry -> entry.getValue().compareTo(best) < 0)
					.forEach(entry -> entry.getKey().lost());

		}

	}

	private void won() {
		played++;
		won++;
	}

	private void lost() {
		played++;
	}

	public final List<Card> getCards() {
		return cards;
	}

	private Card getSecond() {
		return cards.get(1);
	}

	private Card getFirst() {
		return cards.get(0);
	}

	public static void main(final String[] args) {

		CommunityCardsX flop = new CommunityCardsX(Tools.asList(Card.DA,
				Card.S3, Card.C7));

		List<LateRoundHand> hands = createAll(flop);

		final int numSimulation = 10000;
		final int numPlayers = 5;
		simulation(numPlayers, hands, flop, numSimulation);

		hands.sort((a, b) -> {
			double aScore = a.getScore();
			double bScore = b.getScore();

			if (aScore > bScore) {
				return 1;
			} else {
				return -1;
			}

		});

		String s = hands.stream().map(Object::toString)
				.collect(joining("\n"));

		System.out.println(s);

	}

	public final void print() {
		System.out.println(this);
	}

	@Nullable
	private Double myScore;

	public final double getScore() {
		Double myScore2 = myScore;
		if (myScore2 == null) {
			myScore2 = Tools.round((double) won / played);
			myScore = myScore2;
		}
		return myScore2;
	}

	@Override
	public final String toString() {
		return cards.get(0) + " " + cards.get(1) + " -> " + played + " "
				+ getScore();
	}
}
