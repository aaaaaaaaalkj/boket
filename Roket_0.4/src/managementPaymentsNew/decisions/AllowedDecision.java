package managementPaymentsNew.decisions;

import static managementPaymentsNew.decisions.DecisionType.ALL_IN;
import static managementPaymentsNew.decisions.DecisionType.BET;
import static managementPaymentsNew.decisions.DecisionType.CALL;
import static managementPaymentsNew.decisions.DecisionType.CHECK;
import static managementPaymentsNew.decisions.DecisionType.FOLD;
import static managementPaymentsNew.decisions.DecisionType.RAISE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

public class AllowedDecision {
	private final Collection<DecisionType> allowedDecisions;
	private final Integer maxBetOrRaiseValue;

	private AllowedDecision(Integer value, DecisionType... col) {
		this.allowedDecisions = Collections.unmodifiableCollection(
				EnumSet.copyOf(Arrays.asList(col)));
		this.maxBetOrRaiseValue = value;
	}

	public static AllowedDecision foldAllin() {
		return new AllowedDecision(null, FOLD, ALL_IN);
	}

	public static AllowedDecision call() {
		return new AllowedDecision(null, FOLD, ALL_IN, CALL);
	}

	public static AllowedDecision callRaise(int value) {
		return new AllowedDecision(value, FOLD, ALL_IN, CALL, RAISE);
	}

	public static AllowedDecision checkBet(int value) {
		return new AllowedDecision(value, FOLD, ALL_IN, CHECK, BET);
	}

	public boolean isAllowed(Decision d) {
		return allowedDecisions.contains(d.getDecisionType())
				&& (d.getDecisionType() == BET || d.getDecisionType() == RAISE ?
						d.getValue() <= maxBetOrRaiseValue : true);
	}

	public boolean isCallAllowed() {
		return allowedDecisions.contains(CALL);
	}

	public boolean isRaiseAllowed() {
		return allowedDecisions.contains(RAISE);
	}

	public boolean isCheckBetAllowed() {
		return allowedDecisions.contains(CHECK)
				&& allowedDecisions.contains(BET);
	}

	public Integer maxBetRaiseAllowed() {
		return maxBetOrRaiseValue;
	}

}
