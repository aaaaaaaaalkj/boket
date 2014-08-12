package strategy.conditions.common;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum PotType implements ICondition {
	LOW, MIDDLE, HIGH;
	public static final PotType[] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getPot();
	}

	public ICondition orMore() {
		return (sit -> sit.getPot().ordinal() >= ordinal());
	}

}
