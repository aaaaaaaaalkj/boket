package strategy.conditions.postflop;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum FlushDanger implements ICondition {
	LOW, // rainbow
	MIDDLE, // two cards of the same suit in community-cards
	HIGH; // three or four of the same suit in community-cards
	public static final FlushDanger[] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getFlushDanger();
	}

	public static FlushDanger fromLong(long i) {
		return values()[Math.min((int) i, VALUES.length - 1)];
	}

	public ICondition orHigher() {
		return (sit -> sit.getFlushDanger().ordinal() >= ordinal());
	}

}
