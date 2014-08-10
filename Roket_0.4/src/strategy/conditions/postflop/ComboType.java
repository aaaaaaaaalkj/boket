package strategy.conditions.postflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum ComboType implements ICondition {
	HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, GOOD_SET, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;
	public static final EnumSet<ComboType> VALUES = EnumSet
			.allOf(ComboType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getCombo();
	}

	public ICondition orBetter() {
		return (sit -> sit.getCombo().ordinal() >= ordinal());
	}

}
