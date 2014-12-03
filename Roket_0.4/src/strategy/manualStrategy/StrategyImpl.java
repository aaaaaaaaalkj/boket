package strategy.manualStrategy;

import java.util.ArrayList;
import java.util.List;

import strategy.ISituation;
import strategy.IStrategy;
import strategy.TypeOfDecision;
import strategy.conditions.ICondition;

import common.Round;

public class StrategyImpl implements IStrategy {
	private final List<StrategyItem> list;

	public StrategyImpl() {
		list = new ArrayList<>();
	}

  public final void addCondition(final ICondition cond, final TypeOfDecision dec) {
		list.add(new StrategyItem(cond, dec));
	}

  public final TypeOfDecision decide(final ISituation situation) {
		for (StrategyItem i : list) {
			if (i.getCondition().eval(situation)) {
				System.out.println(i.getCondition() + " -> " + i.getDecision());
				return i.getDecision();
			}
		}
		return TypeOfDecision.FOLD;
	}

  public final String toString() {
		String res = "";
		for (StrategyItem i : list) {
			res += i.getCondition() + " -> " + i.getDecision() + "\n";
		}
		return res;
	}

  public final void preflop(final ICondition cond, final TypeOfDecision dec) {
		addCondition(Round.PREFLOP.and(cond), dec);
	}

  public final void flop(final ICondition cond, final TypeOfDecision dec) {
		addCondition(Round.FLOP.and(cond), dec);
	}

  public final void turn(final ICondition cond, final TypeOfDecision dec) {
		addCondition(Round.TURN.and(cond), dec);
	}

  public final void river(final ICondition cond, final TypeOfDecision dec) {
		addCondition(Round.RIVER.and(cond), dec);
	}

}
