package strategy.conditions.preflop;

import java.util.EnumSet;

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

	public static ConnectorType fromInt(int i) {
		if (i > 4 || i < 0) {
			return NONE;
		}
		return ConnectorType.values()[i];
	}

}
