package pot_odds_strategy;

import managementPaymentsNew.decisions.DecisionType;

public final class PotOddsDecision {
	private final DecisionType dec;
	private final double value;
  private static final double INVALID_VALUE = -1;

  private PotOddsDecision(final DecisionType dec, final double value) {
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

  public static PotOddsDecision call(final double toPay) {
		return new PotOddsDecision(DecisionType.CALL, toPay);
	}

  public static PotOddsDecision raise(final double d) {
		assert d >= 0;
		return new PotOddsDecision(DecisionType.RAISE, d);
	}

	@Override
	public String toString() {
		return "decision = " + dec
				+ (value != INVALID_VALUE ? " _ " + value : "_");
	}

  public boolean equalType(final PotOddsDecision d) {
		return d.getDec().equals(getDec());
	}

	public boolean hasValue() {
		return value != INVALID_VALUE;
	}
}
