package managementPaymentsNew.decisions;

import static managementPaymentsNew.decisions.DecisionType.ALL_IN;
import static managementPaymentsNew.decisions.DecisionType.BET;
import static managementPaymentsNew.decisions.DecisionType.CALL;
import static managementPaymentsNew.decisions.DecisionType.CHECK;
import static managementPaymentsNew.decisions.DecisionType.FOLD;
import static managementPaymentsNew.decisions.DecisionType.RAISE;

public class Decision {
	private final DecisionType decisionType;
	private final Integer value; // in small-blinds

	private Decision(DecisionType decisionType, Integer value) {
		this.decisionType = decisionType;
		this.value = value;
		if (value < 0 || value % 2 == 1) {
			throw new IllegalArgumentException(
					"Only positive even values (bb) allowed for BET or RAISE");
		}
	}

	public static Decision bet(int value) {
		return new Decision(BET, value);
	}

	public static Decision raise(int value) {
		return new Decision(RAISE, value);
	}

	public static Decision allin() {
		return new Decision(ALL_IN, null);
	}

	public static Decision fold() {
		return new Decision(FOLD, null);
	}

	public static Decision call() {
		return new Decision(CALL, null);
	}

	public static Decision check() {
		return new Decision(CHECK, null);
	}

	public DecisionType getDecisionType() {
		return decisionType;
	}

	public int getValue() {
		return value;
	}

}
