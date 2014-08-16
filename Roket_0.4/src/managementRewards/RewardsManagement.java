package managementRewards;

import java.util.LinkedHashMap;
import java.util.Map;

import strategy.IStrategy;
import strategy.manualStrategy.Stat;

public class RewardsManagement {
	private Map<IStrategy, Stat> stats = new LinkedHashMap<>();

	public RewardsManagement() {
	}

	public void saveStat(IStrategy stratetgy, Stat s) {
		stats.put(stratetgy, s);
	}

	public Stat getStat(IStrategy strategy) {
		return stats.get(strategy);
	}

}
