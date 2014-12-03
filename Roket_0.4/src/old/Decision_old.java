package old;

import managementPayments.AmountOfJetons;
import strategy.TypeOfDecision;

public class Decision_old {
  public static final Decision_old FOLD = new Decision_old(TypeOfDecision.FOLD,
      AmountOfJetons.ZERO);
  public static final Decision_old CHECK = new Decision_old(TypeOfDecision.CALL,
      AmountOfJetons.ZERO);

  // Call Any
  public static final Decision_old CALL = new Decision_old(TypeOfDecision.CALL,
      new AmountOfJetons(9999));

  public static Decision_old CALL(final AmountOfJetons value) {
    return new Decision_old(TypeOfDecision.CALL, value);
  }

  public static Decision_old RAISE(final AmountOfJetons amount) {
    return new Decision_old(TypeOfDecision.RAISE_QUARTER_POT, amount);
  }

  public static Decision_old RAISE(final int bb) {
    return new Decision_old(TypeOfDecision.RAISE_QUARTER_POT,
        new AmountOfJetons(bb * 2));
  }

  private final TypeOfDecision type;
  private final AmountOfJetons value;

  public Decision_old(final TypeOfDecision type, final AmountOfJetons bb) {
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
