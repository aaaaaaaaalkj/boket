package potodd.sstrategy;

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
		}
    return 0.;
	}

	final void setPot(final Integer round, final Double value) {
		pot.put(round, value);
	}

	private static final double MIN_VALUE = 0.01;

	final double getPot(final int round) {
		if (round < 0) {
			return MIN_VALUE; // return 0.01 on preflop
		}
		if (pot.containsKey(round)) {
			return pot.get(round);
		}
    return MIN_VALUE;
	}

}
