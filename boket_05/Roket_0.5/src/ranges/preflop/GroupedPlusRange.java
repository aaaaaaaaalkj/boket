package ranges.preflop;

import java.util.Random;

import management.cards.cards.Card;
import management.cards.cards.Rank;
import management.cards.cards.Suit;
import ranges.ElementRange;
import ranges.Range;
import ranges.SimpleRange;

public class GroupedPlusRange implements Range {
	private final GroupedRange first;
	private final int size;

	public GroupedPlusRange(final GroupedRange first) {
		this.first = first;
		this.size = computeSize();
	}

	public final boolean isSuited() {
		return first.isSuited();
	}

	public final boolean isPair() {
		return first.isPair();
	}

	@Override
	public final boolean contains(final ElementRange r) {
		if (r.isPair() != this.isPair()) {
			return false;
		}
		if (r.isSuited() != this.isSuited()) {
			return false;
		}
		if (r.getRank2().ordinal() < first.getRank2().ordinal()) {
			return false;
		}
		if (!r.isPair()) {
			// for non-pairs the first rank must be equal
			return r.getRank1() == first.getRank1();
		}
		return true;
	}

	private static final int NUMBER_OF_EQUAL_PAIRS = 6;
	private static final int NUMBER_OF_EQUAL_SUITED = 4;
	private static final int NUMBER_OF_EQUAL_OFFSUITS = 12;

	private final int computeSize() {
		if (isPair()) {
			int count = Rank.VALUES.size() - first.getRank1().ordinal();
			// each grouped pair has 6 items in it
			return count * NUMBER_OF_EQUAL_PAIRS;
		} else {
			int count = first.getRank1().ordinal() - first.getRank2().ordinal();
			if (isSuited()) {
				return count * NUMBER_OF_EQUAL_SUITED;
			} else {
				return count * NUMBER_OF_EQUAL_OFFSUITS;
			}
		}
	}

	@Override
	public final int size() {
		return size;
	}

	public final SimpleRange ungroup() {
		SimpleRange res = new SimpleRange();
		int top = first.isPair() ? Rank.VALUES.size() : first.getRank1()
				.ordinal();
		for (int i = first.getRank2().ordinal(); i < top; i++) {
			Rank r2 = Rank.VALUES.get(i);
			Rank r1 = first.isPair() ? r2 : first.getRank1();
			GroupedRange.ungroup2(res, r1, r2, first.isSuited());
		}
		return res;
	}

	@Override
	public final ElementRange getRandom(final Random rnd) {
		final Suit s1 = Suit.random(rnd);
		final Suit s2;
		if (isSuited()) {
			s2 = s1;
		} else {
			// random but not equal to suit1
			s2 = Suit.random2(rnd, /* dead suit: */s1);
		}
		final Rank r1, r2;

		if (isPair()) {
			int f = first.getRank1().ordinal();
			int index = f + rnd.nextInt(Rank.count() - f);
			r1 = Rank.VALUES.get(index);
			r2 = r1;
		} else {
			r1 = first.getRank1();
			int f = first.getRank2().ordinal();
			int index = f + rnd.nextInt(r1.ordinal() - f);
			r2 = Rank.VALUES.get(index);
		}
		return ElementRange.find(Card.instance(r1, s1), Card.instance(r2, s2));
	}
}
