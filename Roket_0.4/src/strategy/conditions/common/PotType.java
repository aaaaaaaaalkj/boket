package strategy.conditions.common;

import java.util.EnumSet;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum PotType implements ICondition {
	LOW, MIDDLE, HIGH;
	public static final EnumSet<PotType> VALUES = EnumSet
			.allOf(PotType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getPot();
	}

	public ICondition orMore() {
		return (sit -> sit.getPot().ordinal() >= ordinal());
	}



}
