package ranges;

import management.cards.cards.Rank;

public class GroupedPlusRange implements Range {
	private final GroupedRange first;

	public GroupedPlusRange(final GroupedRange first) {
		this.first = first;
	}

	public final boolean isSuited() {
		return first.isSuited();
	}

	public final boolean isPair() {
		return first.isPair();
	}

	@Override
	public final boolean contains(final ElementRange r) {
		if (r.isPair() && this.isPair()) {
			return r.getRank1().ordinal() >= first.getRank1().ordinal();
		} else {
			// r is not a pair
			return r.isSuited() == this.isSuited()
					&& r.isPair() == this.isPair()
					&& r.getRank1() == first.getRank1()
					&& r.getRank2().ordinal() >= first.getRank2().ordinal();
		}
	}

	private static final int NUMBER_OF_EQUAL_PAIRS = 6;
	private static final int NUMBER_OF_EQUAL_SUITED = 4;
	private static final int NUMBER_OF_EQUAL_OFFSUITS = 12;

	@Override
	public final int size() {
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

}
