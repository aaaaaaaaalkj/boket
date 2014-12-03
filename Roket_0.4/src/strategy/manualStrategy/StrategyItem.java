package strategy.manualStrategy;

import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

public class StrategyItem {
	private final ICondition cond;
	private final TypeOfDecision dec;

  public StrategyItem(final ICondition cond, final TypeOfDecision dec) {
		this.cond = cond;
		this.dec = dec;
	}

  public final ICondition getCondition() {
		return cond;
	}

  public final TypeOfDecision getDecision() {
		return dec;
	}

}
