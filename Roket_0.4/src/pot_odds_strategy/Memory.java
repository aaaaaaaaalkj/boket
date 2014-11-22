package pot_odds_strategy;

import java.util.HashMap;
import java.util.Map;

public class Memory {
	private final Map<Integer, Double> highestBid = new HashMap<>();
	private final Map<Integer, Double> pot = new HashMap<>();

	void setHighestBid(Integer round, Double value) {
		highestBid.put(round, value);
	}

	double getighestBid(int round) {
		if (round < 0) {
			return 0.0; // return 0 on preflop
		}
		if (highestBid.containsKey(round)) {
			return highestBid.get(round);
		} else {
			return 0.;
		}
	}

	void setPot(Integer round, Double value) {
		pot.put(round, value);
	}

	double getPot(int round) {
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
