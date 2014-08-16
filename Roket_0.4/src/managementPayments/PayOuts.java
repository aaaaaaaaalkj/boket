package managementPayments;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import common.IPlayer;

public class PayOuts {
	private final Map<IPlayer, AmountOfJetons> map = new HashMap<>();


	public void add(IPlayer player, AmountOfJetons amount) {
		if (map.containsKey(player))
			amount = amount.plus(map.get(player));
		map.put(player, amount);
	}

	public Set<IPlayer> keySet() {
		return map.keySet();
	}

	public AmountOfJetons get(IPlayer player) {
		return map.get(player);
	}


}
