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

	public void addCondition(ICondition cond, TypeOfDecision dec) {
		list.add(new StrategyItem(cond, dec));
	}

	public TypeOfDecision decide(ISituation situation) {
		for (StrategyItem i : list) {
			if (i.getCondition().eval(situation)) {
				System.out.println(i.getCondition() + " -> " + i.getDecision());
				return i.getDecision();
			}
		}
		return TypeOfDecision.FOLD;
	}

	public String toString() {
		String res = "";
		for (StrategyItem i : list) {
			res += i.getCondition() + " -> " + i.getDecision() + "\n";
		}
		return res;
	}

	public void preflop(ICondition cond, TypeOfDecision dec) {
		addCondition(Round.PREFLOP.and(cond), dec);
	}

	public void flop(ICondition cond, TypeOfDecision dec) {
		addCondition(Round.FLOP.and(cond), dec);
	}

	public void turn(ICondition cond, TypeOfDecision dec) {
		addCondition(Round.TURN.and(cond), dec);
	}

	public void river(ICondition cond, TypeOfDecision dec) {
		addCondition(Round.RIVER.and(cond), dec);
	}

}
