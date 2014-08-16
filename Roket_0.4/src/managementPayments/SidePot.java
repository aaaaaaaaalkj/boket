package managementPayments;

import java.util.ArrayList;
import java.util.List;

import common.IPlayer;

public class SidePot {
	AmountOfJetons value;
	List<IPlayer> participants;

	public SidePot() {
		value = AmountOfJetons.ZERO;
		participants = new ArrayList<>();
	}

	// players who folded, contribute to the pot, but they cannot win the pot
	public void add(AmountOfJetons amount) {
		value = value.plus(amount);
	}

	public void add(IPlayer p, AmountOfJetons amount) {
		value = value.plus(amount);
		if (participants.contains(p)) {
			throw new IllegalStateException("Player " + p
					+ " contributes several times to one pot.");
		}
		participants.add(p);
	}
}
