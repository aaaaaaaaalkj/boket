package pot_odds_strategy;

import managementPaymentsNew.decisions.DecisionType;

public class PotOddsDecision {
	private final DecisionType dec;
	private final Double value;

	private PotOddsDecision(DecisionType dec, double value) {
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
		return new PotOddsDecision(DecisionType.FOLD, 0.);
	}

	public static PotOddsDecision call() {
		return new PotOddsDecision(DecisionType.CALL, 0.);
	}

	public static PotOddsDecision raise(double d) {
		return new PotOddsDecision(DecisionType.RAISE, d);
	}

	@Override
	public String toString() {
		return "decision = " + dec
				+ (value == 0 ? " " + value : "") + "";
	}

}
