package potoddsstrategy;

import java.util.HashMap;
import java.util.Map;

public class Memory {
	private final Map<Integer, Double> highestBid = new HashMap<>();
	private final Map<Integer, Double> pot = new HashMap<>();

  final void setHighestBid(final Integer round, final Double value) {
		highestBid.put(round, value);
	}

  final double getighestBid(final int round) {
		if (round < 0) {
			return 0.0; // return 0 on preflop
		}
		if (highestBid.containsKey(round)) {
			return highestBid.get(round);
		} else {
			return 0.;
		}
	}

  final void setPot(final Integer round, final Double value) {
		pot.put(round, value);
	}

  final double getPot(final int round) {
		if (round < 0) {
			return 0.01; // return 0.01 on preflop
		}
		if (pot.containsKey(round)) {
			return pot.get(round);
		} else {
			return 0.01;
		}
	}

}
