package ranges.action;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

import management.cards.cards.Card;
import ranges.ElementRange;
import ranges.SimpleRange;

public class Experiment {
	private static final int COUNT_COMMUNITY_CARDS = 5;
	private final List<Card> all;
	private final List<Card> communityCards;
	private final int countBaseCards;

	private final Random rnd;
	private final SimpleRange baseRange;
	private SimpleRange range;
	private final int countPlayers;

	private final EnumMap<ElementRange, Stat> stats;

	@SuppressWarnings("null")
	public Experiment(
			final SimpleRange range,
			final List<Card> communityBase,
			final int countPlayers,
			final Random rnd) {
		this.stats = new EnumMap<>(ElementRange.class);
		this.countPlayers = countPlayers;
		this.countBaseCards = communityBase.size();
		this.all = new ArrayList<>(Card.getAllCards());
		this.all.remove(communityBase);
		this.communityCards = new ArrayList<>(communityBase);
		this.rnd = rnd;
		this.baseRange = range.clone();
		this.baseRange.removeAssociated(communityBase);
		this.range = baseRange;
	}

	public final void next() {
		while (communityCards.size() < COUNT_COMMUNITY_CARDS) {
			int index = rnd.nextInt(all.size());
			Card c = all.remove(index);
			communityCards.add(c);
			range.removeAssociated(c);
		}

		for (int player = 0; player < countPlayers; player++) {
			ElementRange hand = range.getRandom(rnd);
			ElementRange._2c_2d.getCards();
			range.removeAssociated(hand);
		}

	}

	public final void reverse() {
		List<Card> newCards = communityCards
				.subList(countBaseCards, COUNT_COMMUNITY_CARDS);
		all.addAll(newCards);
		newCards.clear();
		this.range = baseRange;
	}

}
