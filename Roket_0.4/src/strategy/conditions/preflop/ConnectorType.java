package strategy.conditions.preflop;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.OptionalInt;

import managementCards.cards.Rank;
import managementCards.cat_rec_new.Window;
import strategy.ISituation;
import strategy.conditions.ICondition;

public enum ConnectorType implements ICondition {
	POCKET_PAIR, CONNECTOR, ONE_GAP, TWO_GAP, THREE_GAP, NONE;
	public static final EnumSet<ConnectorType> VALUES = EnumSet
			.allOf(ConnectorType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getConnector();
	}

	public static ConnectorType fromRanks(final Rank r1, final Rank r2) {

		if (r1 == r2) {
			return POCKET_PAIR;
		}

		int numWindows = Arrays.stream(Window.values())
				.map(window -> window.contains(r1, r2) ? 1 : 0)
				.reduce(0, Integer::sum);

		switch (numWindows) {
		case 0:
			return NONE;
		case 1:
			return THREE_GAP;
		case 2:
			return TWO_GAP;
		case 3:
			return ONE_GAP;
		case 4:
			return CONNECTOR;
		default:
			throw new IllegalStateException("Impossible ConnectorType: " + r1
					+ " " + r2);
		}

	}

	public static ConnectorType fromInt(OptionalInt optionalInt) {
		if (optionalInt.isPresent()
				&& optionalInt.getAsInt() <= 4
				&& optionalInt.getAsInt() >= 0) {
			return ConnectorType.values()[optionalInt.getAsInt()];
		}
		return NONE;
	}

}
