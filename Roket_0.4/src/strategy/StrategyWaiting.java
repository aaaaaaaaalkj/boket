package strategy;

import strategy.conditions.ICondition;

public class StrategyWaiting {
	private ICondition cond;
	private IStrategy str;

	private ICondition lastCond;

	public StrategyWaiting(ICondition cond, IStrategy str) {
		this.cond = cond;
		this.str = str;
	}

	public StrategyWaiting if_(ICondition cond) {
		lastCond = cond;
		return this;
	}

	public StrategyWaiting then(TypeOfDecision dec) {
		if (lastCond == null) {
			throw new IllegalStateException(
					"StrategyWaiting is in an invalid state");
		}
		str.extend(cond.and(lastCond), dec);
		lastCond = null;
		return this;
	}

	public IStrategy build() {
		if (lastCond != null) {
			throw new IllegalStateException(
					"StrategyWaiting is in an invalid state 2");
		}
		return str;
	}

}
