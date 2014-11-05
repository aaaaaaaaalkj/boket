package pot_odds_strategy;

import managementPaymentsNew.decisions.DecisionType;

public class PotOddsDecision {
	private final DecisionType dec;
	private final Double value;

	private PotOddsDecision(DecisionType dec, Double value) {
		this.dec = dec;
		this.value = value;
	}

	public DecisionType getDec() {
		return dec;
	}

	public Double getValue() {
		return value;
	}

	public static PotOddsDecision fold() {
		return new PotOddsDecision(DecisionType.FOLD, null);
	}

	public static PotOddsDecision call() {
		return new PotOddsDecision(DecisionType.CALL, null);
	}

	public static PotOddsDecision raise(double d) {
		return new PotOddsDecision(DecisionType.RAISE, d);
	}

	@Override
	public String toString() {
		return "PotOddsDecision [dec=" + dec + ", value=" + value + "]";
	}

}
