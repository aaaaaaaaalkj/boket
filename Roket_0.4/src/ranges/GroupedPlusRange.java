package ranges;

import managementCards.cards.Rank;

public class GroupedPlusRange implements Range {
	private final GroupedRange first;

	public GroupedPlusRange(GroupedRange first) {
		this.first = first;
	}

	public boolean isSuited() {
		return first.isSuited();
	}

	public boolean isPair() {
		return first.isPair();
	}

	@Override
	public boolean contains(ElementRange r) {
		if (r.isPair() && this.isPair()) {
			return r.r1.ordinal() >= first.r1.ordinal();
		} else {
			// r is not a pair
			return r.isSuited() == this.isSuited()
					&& r.isPair() == this.isPair()
					&& r.r1 == first.r1
					&& r.r2.ordinal() >= first.r2.ordinal();
		}
	}

	@Override
	public int size() {
		if (isPair()) {
			int count = Rank.VALUES.size() - first.r1.ordinal();
			// each grouped pair has 6 items in it
			return count * 6;
		} else {
			int count = first.r1.ordinal() - first.r2.ordinal();
			if (isSuited()) {
				return count * 4;
			} else {
				return count * 12;
			}
		}
	}

	public SimpleRange ungroup() {
		SimpleRange res = new SimpleRange();
		int top = first.isPair() ? Rank.VALUES.size() : first.r1.ordinal();
		for (int i = first.r2.ordinal(); i < top; i++) {
			Rank r2 = Rank.VALUES.get(i);
			Rank r1 = first.isPair() ? r2 : first.r1;
			GroupedRange.ungroup2(res, r1, r2, first.suited);
		}
		return res;
	}

}
