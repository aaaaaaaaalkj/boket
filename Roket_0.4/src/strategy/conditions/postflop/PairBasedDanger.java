package strategy.conditions.postflop;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum PairBasedDanger implements ICondition {
	LOW, // no pair in community-cards
	MIDDLE, // one pair in community-cards
	HIGH; // two pairs or three of a kind or higher in community-cards
	public static final PairBasedDanger[] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getPairBasedDanger();
	}

	public ICondition orHigher() {
		return (sit -> sit.getPairBasedDanger().ordinal() >= ordinal());
	}

	public static PairBasedDanger fromLong(long l) {
		return VALUES[Math.min((int) l, VALUES.length - 1)];
	}
}
