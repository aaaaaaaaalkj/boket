package old;

import managementpaymentstmp.AmountOfJetons;
import strategy.TypeOfDecision;

public class DecisionOld {
  public static final DecisionOld FOLD = new DecisionOld(TypeOfDecision.FOLD,
      AmountOfJetons.ZERO);
  public static final DecisionOld CHECK = new DecisionOld(TypeOfDecision.CALL,
      AmountOfJetons.ZERO);

  // Call Any
  public static final DecisionOld CALL = new DecisionOld(TypeOfDecision.CALL,
      new AmountOfJetons(9999));

  public static DecisionOld call(final AmountOfJetons value) {
    return new DecisionOld(TypeOfDecision.CALL, value);
  }

  public static DecisionOld raise(final AmountOfJetons amount) {
    return new DecisionOld(TypeOfDecision.RAISE_QUARTER_POT, amount);
  }

  public static DecisionOld raise(final int bb) {
    return new DecisionOld(TypeOfDecision.RAISE_QUARTER_POT,
        new AmountOfJetons(bb * 2));
  }

  private final TypeOfDecision type;
  private final AmountOfJetons value;

  public DecisionOld(final TypeOfDecision type, final AmountOfJetons bb) {
    this.type = type;
    this.value = bb;
  }

  public final boolean fold() {
    return type == TypeOfDecision.FOLD;
  }

  public final boolean call() {
    return type == TypeOfDecision.CALL;
  }

  public final boolean raise() {
    return type == TypeOfDecision.RAISE_QUARTER_POT;
  }

  @Override
  public final String toString() {
    switch (type) {
    case CALL:
      return "CALL";
    case FOLD:
      return "FOLD";
    case RAISE_QUARTER_POT:
      return "RAISE " + value;
    case ALL_IN:
      return "ALL_IN (" + value + ")";
    default:
      return type + " is not implemented yet";
    }
  }

}
