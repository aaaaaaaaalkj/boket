package managementRewards;

import java.util.LinkedHashMap;
import java.util.Map;

import managementPayments.AmountOfJetons;
import strategy.Condition;
import strategy.Stat;
import strategy.conditions.ISituation;

import common.Round;

public class RewardsManagement {
	private Map<Round, Map<Condition, Stat>> stats = new LinkedHashMap<Round, Map<Condition, Stat>>();

	public RewardsManagement() {
		stats.put(Round.PREFLOP, new LinkedHashMap<Condition, Stat>());
		stats.put(Round.FLOP, new LinkedHashMap<Condition, Stat>());
		stats.put(Round.TURN, new LinkedHashMap<Condition, Stat>());
		stats.put(Round.RIVER, new LinkedHashMap<Condition, Stat>());
	}

	public void add(Round round, Condition cond) {
		stats.get(round).put(cond, new Stat());
	}

	public void saveStat(ISituation situation, AmountOfJetons amount) {
		Map<Condition, Stat> map = stats.get(situation.getRound());
		for (Condition c : map.keySet()) {
			if (c.eval(situation)) {
				// map.get(c).payed(amount);

			}
		}
	}

}
