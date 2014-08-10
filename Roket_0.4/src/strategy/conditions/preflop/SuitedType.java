package strategy.conditions.preflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum SuitedType implements ICondition {
	SUITED, OFF_SUIT;

	public static final EnumSet<SuitedType> VALUES = EnumSet
			.allOf(SuitedType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getSuit();
	}

}
