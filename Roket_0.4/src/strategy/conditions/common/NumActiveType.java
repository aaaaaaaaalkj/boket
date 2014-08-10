package strategy.conditions.common;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum NumActiveType implements ICondition {
	TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;
	public static final EnumSet<NumActiveType> VALUES = EnumSet
			.allOf(NumActiveType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getNumActive();
	}

	public ICondition orMore() {
		return (sit -> sit.getNumActive().ordinal() >= ordinal());
	}

	public ICondition orLess() {
		return (sit -> sit.getNumActive().ordinal() <= ordinal());
	}



}
