package strategy.conditions.preflop;

import java.util.EnumSet;
import java.util.OptionalInt;

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

	public static ConnectorType fromInt(OptionalInt optionalInt) {
		if (optionalInt.isPresent()
				&& optionalInt.getAsInt() <= 4
				&& optionalInt.getAsInt() >= 0) {
			return ConnectorType.values()[optionalInt.getAsInt()];
		}
		return NONE;
	}

}
