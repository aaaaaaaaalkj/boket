package strategy.conditions.preflop;

import java.util.EnumSet;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum SuitedType implements ICondition {
	SUITED, OFF_SUIT, NONE;

	public static final EnumSet<SuitedType> VALUES = EnumSet
			.allOf(SuitedType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getSuit();
	}

}
