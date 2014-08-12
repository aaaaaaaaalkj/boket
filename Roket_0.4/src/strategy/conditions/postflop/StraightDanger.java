package strategy.conditions.postflop;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum StraightDanger implements ICondition {
	LOW, // max 1 community-card in each straight-window
	MIDDLE, // max 2 community-cards in each straight-window
	HIGH; // 3 or more community-cards in some straight-window
	public static final StraightDanger[] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getStraightDanger();
	}

	public ICondition orHigher() {
		return (sit -> sit.getStraightDanger().ordinal() >= ordinal());
	}

	public ICondition orLower() {
		return (sit -> sit.getStraightDanger().ordinal() <= ordinal());
	}

	public static StraightDanger fromLong(long value) {
		return VALUES[Math.min((int) value, VALUES.length - 1)];
	}
}
