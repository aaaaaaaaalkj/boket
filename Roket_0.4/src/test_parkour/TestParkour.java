package test_parkour;

import input_output.Raw_Situation;
import pot_odds_strategy.PotOddsDecision;
import pot_odds_strategy.PotOddsStrategy;

public class TestParkour {

	void run(Raw_Situation raw) {
		PotOddsStrategy strategy = new PotOddsStrategy(raw);
		System.out.println(strategy);
		PotOddsDecision d = strategy.decide();
	}
}
