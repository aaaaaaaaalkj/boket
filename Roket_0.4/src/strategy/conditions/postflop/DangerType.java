package strategy.conditions.postflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum DangerType implements ICondition {
	LOW, MIDDLE, HIGH;
	public static final EnumSet<DangerType> VALUES = EnumSet
			.allOf(DangerType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getDanger();
	}

	public ICondition orHigher() {
		return (sit -> sit.getDanger().ordinal() >= ordinal());
	}

}
