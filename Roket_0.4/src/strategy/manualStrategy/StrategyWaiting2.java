package strategy.manualStrategy;

import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

public class StrategyWaiting2 {
	private StrategyWaiting waiting;
	private ICondition ifCond;

  public StrategyWaiting2(final StrategyWaiting waiting, final ICondition ifCond) {
		this.waiting = waiting;
		this.ifCond = ifCond;
	}

  public final StrategyWaiting then(final TypeOfDecision dec) {
		return waiting.extend(ifCond, dec);
	}
}
