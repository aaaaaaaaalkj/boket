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

	public static PotType of(double d) {
		if (d < .1) {
			return LOW;
		} else if (d < .3) {
			return MIDDLE;
		} else {
			return HIGH;
		}
	}

}
