package managementpaymentstmp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tools.Tools;

import common.IPlayer;

public class PayOuts {
	private final Map<IPlayer, AmountOfJetons> map = new HashMap<>();

  public final void add(final IPlayer player, final AmountOfJetons amount) {
    if (map.containsKey(player)) {
      amount = amount.plus(map.get(player));
    }
		map.put(player, amount);
	}

  public final Set<IPlayer> keySet() {
		return Tools.keySet(map);
	}

  public final AmountOfJetons get(final IPlayer player) {
		return map.get(player);
	}

}
