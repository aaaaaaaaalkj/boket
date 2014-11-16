package pot_odds_strategy;

import java.util.HashMap;
import java.util.Map;

public class Memory {
	private final Map<Integer, Double> highestBid = new HashMap<>();
	private final Map<Integer, Double> pot = new HashMap<>();

	void setHighestBid(int round, double value) {
		highestBid.put(round, value);
	}

	double getighestBid(int round) {
		if (round < 0) {
			return 0.0; // return 0 on preflop
		}
		Double res = highestBid.get(round);
		if (res == null) {
			return 0.;
		} else {
			return res;
		}
	}

	void setPot(int round, double value) {
		pot.put(round, value);
	}

	double getPot(int round) {
		if (round < 0) {
			return 0.01; // return 0.01 on preflop
		}
		Double res = pot.get(round);
		if (res == null) {
			return 0.01;
		} else {
			return res;
		}
	}

}
