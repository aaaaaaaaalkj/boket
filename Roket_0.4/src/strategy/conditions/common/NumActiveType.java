package strategy.conditions.common;

import java.util.EnumSet;

import strategy.ISituation;
import strategy.conditions.ICondition;

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

	public static NumActiveType fromInt(int size) {
		assert size >= 2 && size <= 9 : "Too many or too few active players";
		return values()[size - 2];
	}

}
