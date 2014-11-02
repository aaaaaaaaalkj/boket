package strategy.conditions.postflop;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum PairBasedDanger implements ICondition {
	NONE, // no pair in community-cards
	MODERATE, // one pair in community-cards
	HIGH, // two pairs or three of a kind in community-cards
	CERTAIN_COMBO; // full-house of four-of-a-kind in communit-cards

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

	public String toString() {
		if (this == NONE) {
			return "NO";
		} else {
			return super.toString();
		}
	}

	public ICondition orLower() {
		return (sit -> sit.getPairBasedDanger().ordinal() <= ordinal());
	}
}
