package strategy.conditions.postflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum StraightDanger implements ICondition {
	LOW, // max 1 community-card in each straight-window
	MIDDLE, // max 2 community-cards in each straight-window
	HIGH; // 3 or more community-cards in some straight-window
	public static final EnumSet<StraightDanger> VALUES = EnumSet
			.allOf(StraightDanger.class);

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

}
