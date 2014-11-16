package card_simulation.flopSimulation;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.ResultImpl;

public class FlopHand {
	List<Card> cards;

	int won = 0;
	int played = 0;

	public FlopHand(Card c1, Card c2) {
		cards = new ArrayList<>();
		cards.add(c1);
		cards.add(c2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cards == null) ? 0 : cards.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlopHand other = (FlopHand) obj;
		if (cards == null) {
			if (other.cards != null)
				return false;
		} else if (!cards.equals(other.cards))
			return false;
		return true;
	}

	public static List<Card> createAllCards() {
		List<Card> cards = new ArrayList<Card>();
		for (Suit suit : Suit.VALUES) {
			for (Rank rank : Rank.VALUES) {
				Card c = new Card(rank, suit);
				cards.add(c);
			}
		}
		return cards;
	}

	public static List<FlopHand> createAll(Flop flop) {
		List<Card> cards = createAllCards();
		cards.removeAll(flop.getCards());

		List<FlopHand> hands = new ArrayList<FlopHand>();

		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				hands.add(new FlopHand(cards.get(i), cards.get(j)));
			}
		}
		return hands;

	}

	public static void simulation(int num_players, List<FlopHand> hands,
			Flop flop, int count_iter) {

		List<Card> community = new ArrayList<Card>();
		community.addAll(flop.getCards());

		for (int run = 0; run < count_iter; run++) {
			Set<Card> all_cards = new HashSet<Card>();
			all_cards.addAll(createAllCards());
			all_cards.removeAll(flop.getCards());

			List<FlopHand> players = new ArrayList<FlopHand>();

			while (players.size() < num_players) {
				int index = (int) (Math.random() * hands.size());

				FlopHand hand = hands.get(index);

				if (all_cards.contains(hand.getFirst())
						&& all_cards.contains(hand.getSecond())) {
					all_cards.remove(hand.getFirst());
					all_cards.remove(hand.getSecond());
					players.add(hand);
				}
			}

			List<Card> all_cards2 = new ArrayList<Card>();
			all_cards2.addAll(all_cards);

			int index1 = (int) (Math.random() * all_cards2.size());
			int index2 = (int) (Math.random() * all_cards2.size());
			if (index1 == index2) {
				continue; // TODO
			}

			Card card1 = all_cards2.get(index1);
			Card card2 = all_cards2.get(index2);

			community.subList(3, community.size()).clear();
			community.add(card1);
			community.add(card2);

			Map<FlopHand, ResultImpl> results = new HashMap<>();

			for (int i = 0; i < players.size(); i++) {
				ResultImpl r = new Cat_Rec(players.get(i).getCards(), community)
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

	public List<Card> getCards() {
		return cards;
	}

	private Card getSecond() {
		return cards.get(1);
	}

	private Card getFirst() {
		return cards.get(0);
	}

	public static void main(String[] args) {

		Flop flop = new Flop(Arrays.asList(Card.Ad, Card._3s, Card._7c));

		List<FlopHand> hands = createAll(flop);

		simulation(5, hands, flop, 10000);

		hands.sort((a, b) -> {
			double a_score = a.getScore();
			double b_score = b.getScore();

			if (a_score > b_score) {
				return 1;
			} else {
				return -1;
			}

		});

		String s = hands.stream().map(Object::toString)
				.collect(joining("\n"));

		System.out.println(s);

	}

	public void print() {
		System.out.println(this);
	}

	private Double my_score = null;

	public double getScore() {
		if (my_score == null) {
			my_score = ((double) Math.round(((double) won / played) * 1000)) / 1000;
		}
		return my_score;
	}

	@Override
	public String toString() {
		return cards.get(0) + " " + cards.get(1) + " -> " + played + " "
				+ getScore();
	}
}
