package strategy.conditions.postflop;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum StraightDanger implements ICondition {
	NONE, // no straight possible
	MODERATE, // exactly one straight-window with 3 or more community-cards
	SIGNIFICANT, // exactly two straight-windows with 3 or more community-cards
	HIGH, // exactly three straight-windows with 3 or more community-cards
	VERY_HIGH, // exactly four straight-windows with 3 or more community-cards
	CERTAIN_STRAIGHT; // 5 straight-windows with 3 or more community-cards

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

	public static StraightDanger fromLong(
			long count_of_straight_windows_with_3_or_more_ranks) {
		return VALUES[Math.min(
				(int) count_of_straight_windows_with_3_or_more_ranks,
				VALUES.length - 1)];
	}
}
