package strategy.conditions.common;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum ContributionType implements ICondition {
	LOW, MIDDLE, HIGH;
	public static final EnumSet<ContributionType> VALUES = EnumSet
			.allOf(ContributionType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getContribution();
	}

	public ICondition orHigher() {
		return (sit -> sit.getContribution().ordinal() >= ordinal());
	}



}
