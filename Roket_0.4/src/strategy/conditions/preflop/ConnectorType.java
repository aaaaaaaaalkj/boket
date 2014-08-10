package strategy.conditions.preflop;

import java.util.EnumSet;

import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public enum ConnectorType implements ICondition {
	POCKET_PAIR, CONNECTOR, ONE_GAP, TWO_GAP, THREE_GAP, NONE;
	public static final EnumSet<ConnectorType> VALUES = EnumSet
			.allOf(ConnectorType.class);

	@Override
	public boolean eval(ISituation sit) {
		return this == sit.getConnector();
	}

}
