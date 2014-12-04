package managementpaymentsnewtmp.decisions;

import static managementpaymentsnewtmp.decisions.DecisionType.ALL_IN;
import static managementpaymentsnewtmp.decisions.DecisionType.BET;
import static managementpaymentsnewtmp.decisions.DecisionType.CALL;
import static managementpaymentsnewtmp.decisions.DecisionType.CHECK;
import static managementpaymentsnewtmp.decisions.DecisionType.FOLD;
import static managementpaymentsnewtmp.decisions.DecisionType.RAISE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

public final class AllowedDecision {
  private final Collection<DecisionType> allowedDecisions;
  private final int maxBetOrRaiseValue;
  private static final int ILLEGAL_VALUE = -1;

  @SuppressWarnings("null")
  private AllowedDecision(final int value, final DecisionType... col) {
    this.allowedDecisions = Collections.unmodifiableCollection(
        EnumSet.copyOf(Arrays.asList(col)));
    this.maxBetOrRaiseValue = value;
  }

  public static AllowedDecision foldAllin() {
    return new AllowedDecision(ILLEGAL_VALUE, FOLD, ALL_IN);
  }

  public static AllowedDecision call() {
    return new AllowedDecision(ILLEGAL_VALUE, FOLD, ALL_IN, CALL);
  }

  public static AllowedDecision callRaise(final int value) {
    return new AllowedDecision(value, FOLD, ALL_IN, CALL, RAISE);
  }

  public static AllowedDecision checkBet(final int value) {
    return new AllowedDecision(value, FOLD, ALL_IN, CHECK, BET);
  }

  public boolean isAllowed(final Decision d) {
    boolean validValue = true;
    if (d.getDecisionType() == BET || d.getDecisionType() == RAISE) {
      validValue = d.getValue() <= maxBetOrRaiseValue;
    }

    return allowedDecisions.contains(d.getDecisionType()) && validValue;
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

  public int maxBetRaiseAllowed() {
    return maxBetOrRaiseValue;
  }

}
