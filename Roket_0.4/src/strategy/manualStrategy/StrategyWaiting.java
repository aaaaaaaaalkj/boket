package strategy.manualStrategy;

import org.apache.commons.lang3.NotImplementedException;

import strategy.ISituation;
import strategy.IStrategy;
import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

public class StrategyWaiting implements IStrategy {
	// private ICondition cond;
	private IStrategy str;

	public StrategyWaiting(ICondition cond, IStrategy str) {
		// this.cond = cond;
		this.str = str;
	}

	public StrategyWaiting2 if_(ICondition if_) {
		return new StrategyWaiting2(this, if_);
	}

	public StrategyWaiting extend(ICondition if_, TypeOfDecision dec) {
		// str.extend(cond.and(if_), dec);
		throw new NotImplementedException("todo");
		// return this;
	}

	@Override
	public TypeOfDecision decide(ISituation situation) {
		return str.decide(situation);
	}

}
