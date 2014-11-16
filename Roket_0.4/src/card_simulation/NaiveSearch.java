package card_simulation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import managementCards.all_cathegories.AllResults;
import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.ResultImpl;

public class NaiveSearch {
	List<Card> myHand;
	List<Card> community;
	List<Card> deck;
	List<PossibleHand> hands;
	AllResults allRes;

	private NaiveSearch(List<Card> myHand, List<Card> community) {
		this.myHand = myHand;
		this.community = community;

		try {
			this.allRes = AllResults
					.getInstance("cathegories.txt");
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

		prepareDeck();
		run();
	}

	private static NaiveSearch instance;

	public static NaiveSearch getInstance(List<Card> myHand,
			List<Card> community) {
		if (instance == null || !instance.myHand.equals(myHand)
				|| !instance.community.equals(community)) {
			instance = new NaiveSearch(myHand, community);
		} else {
			System.out.println("reusing NativeSearch");
		}
		return instance;
	}

	public List<PossibleHand> getPossibleHands(double min, double max,
			double count_opponents) {
		if (hands.size() == 0) {
			throw new IllegalStateException("no hands available");
		}
		int start = (int) (hands.size() * Math.pow(min, 1. / count_opponents));
		int end = (int) (hands.size() * Math.pow(max, 1. / count_opponents));

		List<PossibleHand> res = hands.subList(start, end);

		if (res.size() == 0) {
			throw new IllegalArgumentException(
					"no hands found between min " + start + " and max " + end
							+ ". hands.size = " + hands.size());
		} else {
			return res;
		}
	}

	public PossibleHand getPossibleHand(int index) {
		if (hands.size() == 0) {
			throw new IllegalStateException("no hands available");
		}
		return hands.get(index);
	}

	private void run() {
		hands = new ArrayList<>();

		System.out.println("start");
		if (community.size() == 3) { // flop

			CountDownLatch latch = new CountDownLatch(2);

			Worker w1 = new Worker(community,
					deck, allRes,
					0,
					deck.size() * 1 / 3,
					latch);
			Worker w2 = new Worker(community,
					deck, allRes,
					deck.size() * 1 / 3,
					deck.size(),
					latch);

			w2.start();
			w1.start();

			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			System.out.println("done");

			// score = (w1.getScore() + w2.getScore()) / 990;

			hands.addAll(w1.getHands());
			hands.addAll(w2.getHands());
		}

		for (int first = 0; first < deck.size(); first++) {
			for (int second = first + 1; second < deck.size(); second++) {

				Card first_ = deck.get(first);
				Card second_ = deck.get(second);

				List<Card> communityCards = new ArrayList<>();

				double score = 0;

				if (community.size() == 4) { // turn
					for (int i = 0; i < deck.size(); i++) {
						if (i != first && i != second) {
							communityCards.clear();
							communityCards.addAll(community);
							communityCards.add(deck.get(i));

							ResultImpl res = new Cat_Rec(
									first_, second_, communityCards)
									.check();

							score += allRes.getScore(res);
						}
					}
					hands.add(new PossibleHand(first_, second_,
							score / 44));
				}
				if (community.size() == 5) { // river
					ResultImpl res = new Cat_Rec(
							first_, second_, community)
							.check();
					score += allRes.getScore(res);
					hands.add(new PossibleHand(first_, second_,
							score / 1));
				}
			}
		}
		hands.sort((a, b) -> a.getScore().compareTo(b.getScore()));
	}

	private void prepareDeck() {
		deck = new ArrayList<>();
		for (Suit suit : Suit.VALUES) {
			for (Rank rank : Rank.VALUES) {
				Card c = new Card(rank, suit);
				if (myHand.contains(c)
						|| community.contains(c)) {
					continue;
				} else {
					deck.add(c);
				}
			}
		}
		deck.sort((a1, a2) -> {
			if (a1.getRank().compareTo(a2.getRank()) > 0) {
				return 1;
			} else if (a1.getRank().compareTo(a2.getRank()) < 0) {
				return -1;
			} else { // a1.rank == a2.rank here
				if (a1.getSuit().compareTo(a2.getSuit()) > 0) {
					return 1;
				} else if (a1.getSuit().compareTo(a2.getSuit()) < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}

	public int size() {
		return hands.size();
	}
}
