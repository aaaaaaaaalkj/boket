package pot_odds_strategy;

import managementPaymentsNew.decisions.DecisionType;

public class PotOddsDecision {
	private final DecisionType dec;
	private final double value;
	private final static double INVALID_VALUE = -1;

	private PotOddsDecision(DecisionType dec, double value) {
		this.dec = dec;
		this.value = value;
	}

	public DecisionType getDec() {
		return dec;
	}

	public double getValue() {
		if (value == INVALID_VALUE) {
			throw new IllegalStateException(dec + " dosnt have a value");
		}
		return value;
	}

	public static PotOddsDecision fold() {
		return new PotOddsDecision(DecisionType.FOLD, INVALID_VALUE);
	}

	public static PotOddsDecision call() {
		return new PotOddsDecision(DecisionType.CALL, INVALID_VALUE);
	}

	public static PotOddsDecision raise(double d) {
		return new PotOddsDecision(DecisionType.RAISE, d);
	}

	@Override
	public String toString() {
		return "decision = " + dec
				+ (value == INVALID_VALUE ? " " + value : "") + "";
	}

}
