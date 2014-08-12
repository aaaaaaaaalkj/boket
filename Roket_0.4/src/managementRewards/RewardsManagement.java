package managementRewards;

import java.util.LinkedHashMap;
import java.util.Map;

import managementPayments.AmountOfJetons;
import strategy.ISituation;
import strategy.Stat;
import strategy.conditions.ICondition;
import common.Round;

public class RewardsManagement {
	private Map<Round, Map<ICondition, Stat>> stats = new LinkedHashMap<>();

	public RewardsManagement() {
		stats.put(Round.PREFLOP, new LinkedHashMap<>());
		stats.put(Round.FLOP, new LinkedHashMap<>());
		stats.put(Round.TURN, new LinkedHashMap<>());
		stats.put(Round.RIVER, new LinkedHashMap<>());
	}

	public void add(Round round, ICondition cond) {
		stats.get(round).put(cond, new Stat());
	}

	public void saveStat(ISituation situation, AmountOfJetons amount) {
		Map<ICondition, Stat> map = stats.get(situation.getRound());
		for (ICondition c : map.keySet()) {
			if (c.eval(situation)) {
				// map.get(c).payed(amount);

			}
		}
	}

}
