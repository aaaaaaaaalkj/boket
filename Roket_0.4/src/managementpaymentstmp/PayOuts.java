package managementpaymentstmp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tools.Tools;

import common.IPlayer;

public class PayOuts {
	private final Map<IPlayer, AmountOfJetons> map = new HashMap<>();

  public final void add(final IPlayer player, final AmountOfJetons amount) {
    AmountOfJetons amount1 = amount;
    if (map.containsKey(player)) {
      amount1 = amount1.plus(map.get(player));
    }
    map.put(player, amount1);
	}

  public final Set<IPlayer> keySet() {
		return Tools.keySet(map);
	}

  public final AmountOfJetons get(final IPlayer player) {
		return map.get(player);
	}

}
