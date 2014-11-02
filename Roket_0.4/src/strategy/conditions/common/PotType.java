package strategy.conditions.common;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum PotType implements ICondition {
	SMALL(.33), MEDIUM(.66), BIG(1.);
	public static final PotType[] VALUES = values();

	private final double value;

	private PotType(double value) {
		this.value = value;
	}

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
		if (d < SMALL.value) {
			return SMALL;
		} else if (d < MEDIUM.value) {
			return MEDIUM;
		} else {
			return BIG;
		}
	}

}
