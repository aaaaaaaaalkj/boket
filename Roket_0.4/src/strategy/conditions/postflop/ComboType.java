package strategy.conditions.postflop;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum ComboType implements ICondition {
	HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, GOOD_SET, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;
	public static final ComboType[] VALUES = values();

	public static int getCount() {
		return VALUES.length;
	}

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getCombo();
	}

	public ICondition orBetter() {
		return (sit -> sit.getCombo().ordinal() >= ordinal());
	}

}
