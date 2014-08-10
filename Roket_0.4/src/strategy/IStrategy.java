package strategy;

import strategy.conditions.ISituation;

public interface IStrategy {
	public TypeOfDecision decide(ISituation situation);
}
