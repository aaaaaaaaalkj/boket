package strategy.manualStrategy;

import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

public class StrategyWaiting2 {
	private StrategyWaiting waiting;
	private ICondition if_;

  public StrategyWaiting2(final StrategyWaiting waiting, final ICondition if_) {
		this.waiting = waiting;
		this.if_ = if_;
	}

  public final StrategyWaiting then(final TypeOfDecision dec) {
		return waiting.extend(if_, dec);
	}
}
