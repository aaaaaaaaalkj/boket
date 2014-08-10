package strategy;

import static common.Round.FLOP;
import static common.Round.PREFLOP;
import static common.Round.RIVER;
import static common.Round.TURN;
import strategy.conditions.ICondition;
import strategy.conditions.ISituation;

public interface IStrategy {
	public TypeOfDecision decide(ISituation situation);

	/**
	 * Creates a new Strategy, which maps the condition "cond" to the decision
	 * "dec". Behavies like this strategy for all other cases.
	 * 
	 * @param cond
	 *            Condition
	 * @param dec
	 *            Decision
	 * @return A new strategy which maps cond to dec and behavies like this
	 *         strategy otherwise.
	 */
	default IStrategy extend(ICondition cond, TypeOfDecision dec) {
		return (sit -> cond.eval(sit) ? dec : decide(sit));
	}

	/**
	 * Extends this strategy only for the preflop-case
	 * 
	 * @param cond
	 * @param dec
	 * @return A new Strategy with an extra mapping cond->dec for preflop
	 */
	default IStrategy preflop(ICondition cond, TypeOfDecision dec) {
		return extend(PREFLOP.and(cond), dec);
	}

	default IStrategy flop(ICondition cond, TypeOfDecision dec) {
		return extend(FLOP.and(cond), dec);
	}

	default IStrategy turn(ICondition cond, TypeOfDecision dec) {
		return extend(TURN.and(cond), dec);
	}

	default IStrategy river(ICondition cond, TypeOfDecision dec) {
		return extend(RIVER.and(cond), dec);
	}

}
