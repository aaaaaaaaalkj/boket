package strategy;

import common.Round;

public class StrategyWaiting {
	private final IStrategy strategy;
	private final Condition cond;
	private final Round round;
	private Condition currentCond;

	public StrategyWaiting(IStrategy s, Condition cond, Round round) {
		this.strategy = s;
		this.cond = cond;
		this.round = round;
	}

	public StrategyWaiting if_(Condition c) {
		currentCond = c;
		return this;
	}

	public StrategyWaiting then(TypeOfDecision d) {
		switch (round) {
		case PREFLOP:
			strategy.preFlop(cond.and(currentCond), d);
			break;
		case FLOP:
			strategy.flop(cond.and(currentCond), d);
			break;
		case TURN:
			strategy.turn(cond.and(currentCond), d);
			break;
		case RIVER:
			strategy.river(cond.and(currentCond), d);
			break;
		default:
			throw new IllegalStateException("No behaviour is defined for "
					+ round);
		}
		currentCond = null;
		return this;
	}
}
