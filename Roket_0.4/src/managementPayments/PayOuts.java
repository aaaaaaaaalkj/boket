package managementPayments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.PlayerId;

public class PayOuts {
	private final Map<PlayerId, AmountOfJetons> map = new HashMap<>();

	private final List<PlayerId> loosers = new ArrayList<>();

	public void add(PlayerId player, AmountOfJetons amount) {
		if (map.containsKey(player))
			amount = amount.plus(map.get(player));
		map.put(player, amount);
	}

	public Set<PlayerId> keySet() {
		return map.keySet();
	}

	public AmountOfJetons get(PlayerId player) {
		return map.get(player);
	}

	public List<PlayerId> getLoosers() {
		return loosers;
	}

}
