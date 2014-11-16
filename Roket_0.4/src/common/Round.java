package common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import strategy.ISituation;
import strategy.conditions.ICondition;

public enum Round implements ICondition {
	PREFLOP, FLOP, TURN, RIVER, SHOWDOWN, QUIET_END;

	public static final List<Round> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));

	public Round next() {
		if (this == SHOWDOWN || this == QUIET_END)
			return null;
		else
			return VALUES.get(this.ordinal() + 1);
	}

	public Round last() {
		if (this == PREFLOP)
			return null;
		else
			return VALUES.get(this.ordinal() - 1);
	}

	@Override
	public boolean eval(ISituation sit) {
		return sit.getRound() == this;
	}

	public boolean gameEnded() {
		return this == SHOWDOWN || this == QUIET_END;
	}

}
