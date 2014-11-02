package strategy.manualStrategy;

import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

public class StrategyItem {
	private final ICondition cond;
	private final TypeOfDecision dec;

	public StrategyItem(ICondition cond, TypeOfDecision dec) {
		this.cond = cond;
		this.dec = dec;
	}

	public ICondition getCondition() {
		return cond;
	}

	public TypeOfDecision getDecision() {
		return dec;
	}

}
