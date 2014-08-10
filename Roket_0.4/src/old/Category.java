package old;

import static managementCards.cards.Rank.Ace;
import static managementCards.cards.Rank.Four;
import static managementCards.cards.Rank.Jack;
import static managementCards.cards.Rank.King;
import static managementCards.cards.Rank.Queen;
import static old.Super_category.*;

import java.util.ArrayList;
import java.util.List;

import managementCards.cards.Rank;
import tools.MapOfInteger;

public class Category {
	Super_category super_category;
	List<Rank> list;

	public Category(Super_category cat, Rank c1, Rank c2, Rank c3,
			Rank c4, Rank c5) {
		super_category = cat;
		list = new ArrayList<>();
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		list.add(c5);
	}

	public final static Category PAIR_OF_ACES = new Category(PAIR, Ace, Ace,
			King, Jack, Four);

	public final static Category PAIR_OF_KINGS = new Category(PAIR, King, King,
			Queen, Jack, Four);

	public void test(Hand hand, Category c) {

		MapOfInteger<Rank> need = new MapOfInteger<>();

		for (Rank t : c.list)
			need.inc(t);

	}

}
