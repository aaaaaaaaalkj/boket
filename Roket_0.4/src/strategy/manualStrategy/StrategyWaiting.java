package strategy.manualStrategy;

import org.apache.commons.lang3.NotImplementedException;

import strategy.ISituation;
import strategy.IStrategy;
import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

public class StrategyWaiting implements IStrategy {
	// private ICondition cond;
	private IStrategy str;

  public StrategyWaiting(final ICondition cond, final IStrategy str) {
		// this.cond = cond;
		this.str = str;
	}

  public final StrategyWaiting2 if_(final ICondition if_) {
		return new StrategyWaiting2(this, if_);
	}

  public final StrategyWaiting extend(final ICondition if_, final TypeOfDecision dec) {
		// str.extend(cond.and(if_), dec);
		throw new NotImplementedException("todo");
		// return this;
	}

	@Override
  public final TypeOfDecision decide(final ISituation situation) {
		return str.decide(situation);
	}

}
