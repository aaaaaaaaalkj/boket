package card_simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import managementCards.all_cathegories.AllResults;
import managementCards.cards.Card;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.ResultImpl;

public final class Worker extends Thread {
	private final List<Card> community;
	private final List<Card> deck;
	private final AllResults allRes;
	private final int offset;
	private final int limit;
	private final CountDownLatch latch;
	private List<PossibleHand> hands;

	public Worker(
			List<Card> community,
			List<Card> deck,
			AllResults allRes,
			int offset,
			int limit, CountDownLatch latch) {
		this.community = community;
		this.deck = deck;
		this.allRes = allRes;
		this.offset = offset;
		this.limit = limit;
		this.latch = latch;
		this.hands = new ArrayList<>();
	}

	public void run() {
		for (int first = offset; first < limit; first++) {
			for (int second = first + 1; second < deck.size(); second++) {
				double score = 0;
				for (int i = 0; i < deck.size(); i++) {
					for (int j = i + 1; j < deck.size(); j++) {
						if (i != first
								&& i != second
								&& j != first
								&& j != second) {
							List<Card> communityCards = new ArrayList<>();
							communityCards.addAll(community);
							communityCards.add(deck.get(i));
							communityCards.add(deck.get(j));

							ResultImpl res = new Cat_Rec(
									deck.get(first), deck.get(second),
									communityCards)
									.check();

							score += allRes.getScore(res);
						}
					}
				}
				hands.add(new PossibleHand(deck.get(first), deck.get(second),
						score / 990));
			}
		}
		latch.countDown();
	}

	public List<PossibleHand> getHands() {
		return hands;
	}
}
