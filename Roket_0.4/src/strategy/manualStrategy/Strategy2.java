package strategy.manualStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import managementPayments.AmountOfJetons;
import strategy.ISituation;
import strategy.IStrategy;
import strategy.TypeOfDecision;
import strategy.conditions.ICondition;
import common.PlayerId;
import common.Round;

public class Strategy2 implements IStrategy {
	private Map<ICondition, StrategyDec> preflop = new LinkedHashMap<>();
	private Map<ICondition, StrategyDec> flop = new LinkedHashMap<>();
	private Map<ICondition, StrategyDec> turn = new LinkedHashMap<>();
	private Map<ICondition, StrategyDec> river = new LinkedHashMap<>();
	private Map<Round, Map<ICondition, StrategyDec>> maps = new LinkedHashMap<>();

	private Map<PlayerId, AmountOfJetons> payed = new HashMap<>();

	public Strategy2() {
		maps.put(Round.PREFLOP, preflop);
		maps.put(Round.FLOP, flop);
		maps.put(Round.TURN, turn);
		maps.put(Round.RIVER, river);
	}

	public void complete() {

	}

	public Map<Round, List<ICondition>> getConditions() {
		Map<Round, List<ICondition>> res = new LinkedHashMap<>();
		for (Round r : maps.keySet()) {
			res.put(r, new ArrayList<ICondition>());
			for (ICondition c : maps.get(r).keySet()) {
				res.get(r).add(c);
			}
		}
		return res;
	}

	public TypeOfDecision decide(ISituation situation) {
		Map<ICondition, StrategyDec> map = maps.get(situation.getRound());
		for (ICondition c : map.keySet()) {
			if (c.eval(situation)) {
				return map.get(c).getDecision();
			}
		}
		return TypeOfDecision.FOLD;
	}

	public String toString() {
		String res = "";
		for (Round r : maps.keySet()) {
			Map<ICondition, StrategyDec> map = maps.get(r);
			res += "== " + r + " ==\n";
			for (ICondition c : map.keySet()) {
				res += c + " -> " + map.get(c) + "\n";
			}
		}
		return res;
	}

	public void won(PlayerId player, AmountOfJetons amountOfJetons) {
		AmountOfJetons payed2 = payed.get(player);
		if (null == payed2) {
			// possible, because BB-Player can win without payments
			payed2 = AmountOfJetons.ZERO;
		}
		for (Round r : maps.keySet()) {
			Map<ICondition, StrategyDec> map = maps.get(r);
			for (ICondition c : map.keySet()) {
				map.get(c).won(player, amountOfJetons, payed2);
			}
		}
		payed.put(player, AmountOfJetons.ZERO);
	}

	public void lost(PlayerId player) {
		for (Round r : maps.keySet()) {
			Map<ICondition, StrategyDec> map = maps.get(r);
			for (ICondition c : map.keySet()) {
				map.get(c).lost(player);
			}
		}
		payed.put(player, AmountOfJetons.ZERO);
	}

	public void payed(PlayerId player, ISituation situation,
			AmountOfJetons amount) {
		if (payed.get(player) == null)
			payed.put(player, AmountOfJetons.ZERO);
		payed.put(player, payed.get(player).plus(amount));

		Map<ICondition, StrategyDec> map = maps.get(situation.getRound());
		for (ICondition c : map.keySet()) {
			if (c.eval(situation)) {
				map.get(c).payed(player, amount);
				break;
			}
		}
	}

}
