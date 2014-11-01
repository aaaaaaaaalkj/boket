package strategy.conditions.common;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum ContributionType implements ICondition {
	LOW(.1), MIDDLE(.25), HIGH(.5);

	private final double value;

	private ContributionType(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public static final ContributionType[] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getContribution();
	}

	public ICondition orHigher() {
		return (sit -> sit.getContribution().ordinal() >= ordinal());
	}

	public ICondition orLower() {
		return (sit -> sit.getContribution().ordinal() <= ordinal());
	}

	public static ContributionType fromDouble(double d) {
		if (d < LOW.value) {
			return LOW;
		} else if (d < MIDDLE.value) {
			return MIDDLE;
		} else if (d < HIGH.value) {
			return HIGH;
		} else {
			throw new IllegalArgumentException(d + " is too big");
		}
	}

}
