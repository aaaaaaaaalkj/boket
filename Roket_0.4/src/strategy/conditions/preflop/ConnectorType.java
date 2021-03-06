package strategy.conditions.preflop;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.OptionalInt;

import managementcards.cards.Rank;
import managementcards.catrecnew.Window;
import strategy.ISituation;
import strategy.conditions.ICondition;

public enum ConnectorType implements ICondition {
  POCKET_PAIR, CONNECTOR, ONE_GAP, TWO_GAP, THREE_GAP, NONE;
  @SuppressWarnings("null")
  public static final EnumSet<ConnectorType> VALUES = EnumSet
      .allOf(ConnectorType.class);

  @Override
  public boolean eval(final ISituation sit) {
    return this == sit.getConnector();
  }

  public static ConnectorType fromRanks(final Rank r1, final Rank r2) {

    if (r1 == r2) {
      return POCKET_PAIR;
    }

    int numWindows = (int) Arrays.stream(Window.values())
        .filter(window -> window.contains(r1, r2))
        .count();

    switch (numWindows) {
    case 0:
      return NONE;
    case 1:
      return THREE_GAP;
    case 2:
      return TWO_GAP;
    case 3:
      return ONE_GAP;
    case 4:
      return CONNECTOR;
    default:
      throw new IllegalStateException("Impossible ConnectorType: " + r1
          + " " + r2);
    }

  }

  @SuppressWarnings("null")
  public static ConnectorType fromInt(final OptionalInt optionalInt) {
    if (optionalInt.isPresent()
        && optionalInt.getAsInt() <= 4
        && optionalInt.getAsInt() >= 0) {
      return ConnectorType.values()[optionalInt.getAsInt()];
    }
    return NONE;
  }

}
