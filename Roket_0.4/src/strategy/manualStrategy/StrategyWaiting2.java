package strategy.manualStrategy;

import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

public class StrategyWaiting2 {
	private StrategyWaiting waiting;
	private ICondition if_;

	public StrategyWaiting2(StrategyWaiting waiting, ICondition if_) {
		this.waiting = waiting;
		this.if_ = if_;
	}

	public StrategyWaiting then(TypeOfDecision dec) {
		return waiting.extend(if_, dec);
	}
}
