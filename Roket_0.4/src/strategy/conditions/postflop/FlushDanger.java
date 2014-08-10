package strategy.conditions.postflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum FlushDanger implements ICondition {
	LOW, // rainbow
	MIDDLE, // two cards of the same suit in community-cards
	HIGH; // three or four of the same suit in community-cards
	public static final EnumSet<FlushDanger> VALUES = EnumSet
			.allOf(FlushDanger.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getFlushDanger();
	}

	public ICondition orHigher() {
		return (sit -> sit.getFlushDanger().ordinal() >= ordinal());
	}

}
