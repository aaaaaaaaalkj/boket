package managementrewards;

import java.util.LinkedHashMap;
import java.util.Map;

import strategy.IStrategy;
import strategy.manualStrategy.Stat;

public class RewardsManagement {
	private Map<IStrategy, Stat> stats = new LinkedHashMap<>();

	public RewardsManagement() {
	}

  public final void saveStat(final IStrategy stratetgy, final Stat s) {
		stats.put(stratetgy, s);
	}

  public final Stat getStat(final IStrategy strategy) {
		return stats.get(strategy);
	}

}
