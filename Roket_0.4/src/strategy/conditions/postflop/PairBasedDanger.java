package strategy.conditions.postflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum PairBasedDanger implements ICondition {
	LOW, // no pair in community-cards
	MIDDLE, // one pair in community-cards
	HIGH; // two pairs or three of a kind or higher in community-cards
	public static final EnumSet<PairBasedDanger> VALUES = EnumSet
			.allOf(PairBasedDanger.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getPairBasedDanger();
	}

	public ICondition orHigher() {
		return (sit -> sit.getPairBasedDanger().ordinal() >= ordinal());
	}

}
