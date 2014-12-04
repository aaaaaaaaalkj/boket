package managementpaymentsnewtmp.decisions;

import static managementpaymentsnewtmp.decisions.DecisionType.ALL_IN;
import static managementpaymentsnewtmp.decisions.DecisionType.BET;
import static managementpaymentsnewtmp.decisions.DecisionType.CALL;
import static managementpaymentsnewtmp.decisions.DecisionType.CHECK;
import static managementpaymentsnewtmp.decisions.DecisionType.FOLD;
import static managementpaymentsnewtmp.decisions.DecisionType.RAISE;

public final class Decision {
	private final DecisionType decisionType;
	private final int value; // in small-blinds

	private static final int ILLEGAL_VALUE = -1;

  private Decision(final DecisionType decisionType, final int value) {
		this.decisionType = decisionType;
		this.value = value;
		if (value != ILLEGAL_VALUE && (value < 0 || value % 2 == 1)) {
			throw new IllegalArgumentException(
					"Only positive even values (bb) allowed for BET or RAISE");
		}
	}

  public static Decision bet(final int value) {
		return new Decision(BET, value);
	}

  public static Decision raise(final int value) {
		return new Decision(RAISE, value);
	}

	public static Decision allin() {
		return new Decision(ALL_IN, ILLEGAL_VALUE);
	}

	public static Decision fold() {
		return new Decision(FOLD, ILLEGAL_VALUE);
	}

	public static Decision call() {
		return new Decision(CALL, ILLEGAL_VALUE);
	}

	public static Decision check() {
		return new Decision(CHECK, ILLEGAL_VALUE);
	}

	public DecisionType getDecisionType() {
		return decisionType;
	}

	public int getValue() {
		return value;
	}

}
