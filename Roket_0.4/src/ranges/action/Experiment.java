package ranges.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import managementcards.cards.Card;
import ranges.SimpleRange;

public class Experiment {
  private static final int COUNT_COMMUNITY_CARDS = 5;
	private final List<Card> all;
	private final List<Card> community_cards;
	private final int count_base_cards;
	private final Random rnd;
	private final SimpleRange base_range;
	private SimpleRange range;

	public Experiment(
      final SimpleRange range,
      final List<Card> community_base,
      final int countPlayers,
      final Random rnd) {
		this.count_base_cards = community_base.size();
		this.all = new ArrayList<>(Card.getAllCards());
		this.all.remove(community_base);
		this.community_cards = new ArrayList<>(community_base);
		this.rnd = rnd;
		this.base_range = range.clone();
		this.base_range.removeAssociated(community_base);
		this.range = base_range;
	}

  public final void next() {
		while (community_cards.size() < COUNT_COMMUNITY_CARDS) {
			int index = rnd.nextInt(all.size());
			Card c = all.remove(index);
			community_cards.add(c);
			range.removeAssociated(c);
		}

	}

  public final void reverse() {
		List<Card> new_cards = community_cards
				.subList(count_base_cards, COUNT_COMMUNITY_CARDS);
		all.addAll(new_cards);
		new_cards.clear();
		this.range = base_range;
	}

}
