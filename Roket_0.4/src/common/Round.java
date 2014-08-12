package common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum Round implements ICondition {
	PREFLOP, FLOP, TURN, RIVER;

	public Round next() {
		if (this == RIVER)
			return null;
		else
			return Round.values()[this.ordinal() + 1];
	}

	public boolean followsAfter(Round round) {
		return this.ordinal() - round.ordinal() == 1;
	}

	public static final List<Round> VALUES = Collections
			.unmodifiableList(Arrays
					.asList(values()));

	@Override
	public boolean eval(ISituation sit) {
		return sit.getRound() == this;
	}
}
